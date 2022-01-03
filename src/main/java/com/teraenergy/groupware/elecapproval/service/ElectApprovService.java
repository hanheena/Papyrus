package com.teraenergy.groupware.elecapproval.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teraenergy.groupware.elecapproval.mapper.ElectApprovMapper;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovLineVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovRefVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovVO;

@Service
@Transactional
public class ElectApprovService {
	@Autowired
	private ElectApprovMapper electApprovMapper;

	// list에서 null값 빼기 필요
	public int insertElectApprovService(ElectApprovVO electApprov, List<String> refUserList,
			ElectApprovLineVO electApprovLineVO) {
		
		//컨트롤러에서 넘어온 결제선 리스트
		List<ElectApprovLineVO> electApprovLineList = electApprovLineVO.getElectApprovLineList();
		System.out.println("## electApprovLineList: "+electApprovLineList.toString());
		electApprov.setElectapprovId(electApprov.getId());
		
		int result = electApprovMapper.insertElecApprov(electApprov);
		
		// 일반 결제용지면 elect_approv_form_common 테이블에 insert (아직 기획이 나온게 없어서 일반 결제용지만 insert, update 구현)
		if("elect_approv_form_common".equals(electApprov.getFormTableName().trim())) {
			electApprovMapper.insert_elect_approv_form_common(electApprov);
		}
		
		
		//참조인 insert
		if (result > 0 && refUserList != null) {
			refUserList.removeAll(Arrays.asList(""," ", null));
			System.out.println("## refUserList: "+refUserList.toString());

			for (String e : refUserList) {
				ElectApprovRefVO electApprovRefVO = new ElectApprovRefVO();
				electApprovRefVO.setElectapprovId(electApprov.getId());
				electApprovRefVO.setUserId(Integer.valueOf(e));
				electApprovMapper.insertElecApprovRef(electApprovRefVO);
			}

		}
		
		if (result > 0 && electApprovLineList != null) {
			for(int i=0;i<electApprovLineList.size();i++) {
				// 결제선 리스트에 null값이 들어있는건 객체 제거
				String strUserId=electApprovLineList.get(i).getUserId();
				if(null == strUserId || "".equals(strUserId.trim())) {
					electApprovLineList.remove(i);
				}
			}
			
			//list element에 들어있는 seq 값을 기준으로 오름차 정렬
			Collections.sort(electApprovLineList);
			for (int i = 0; i < electApprovLineList.size(); i++) {
				ElectApprovLineVO _electApprovLineVO = electApprovLineList.get(i);
				_electApprovLineVO.setElectapprovId(electApprov.getId());
				//결제선 순서 재부여
				_electApprovLineVO.setLvl((i + 1) + "");
				if (i == 0) {
					// 첫번째 결제선으로 할당된 사람은 열람 가능하도록 설정
					_electApprovLineVO.setB4yn("Y");
				} else {
					// 나머지 사람들은 아직 열람 불가능으로 설정
					_electApprovLineVO.setB4yn("N");
				}
				electApprovMapper.insertElecApprovLine(_electApprovLineVO);
			}
		}
		return electApprov.getId();

	}

	public int updateElectApprovService(ElectApprovVO electApprov, List<String> refUserList,
			ElectApprovLineVO electApprovLineVO) {
		//결제선 리스트
		List<ElectApprovLineVO> electApprovLineList = electApprovLineVO.getElectApprovLineList();
		electApprov.setElectapprovId(electApprov.getId());
		
		int result = electApprovMapper.updateElecApprov(electApprov);
		
		// 일반 결제용지면 elect_approv_form_common 테이블에 update (아직 기획이 나온게 없어서 일반 결제용지만 insert, update 구현)
		if("elect_approv_form_common".equals(electApprov.getFormTableName().trim())) {
			electApprovMapper.update_elect_approv_form_common(electApprov);
		}
		
		//예전 참조인 데이터는 지우고 새로 insert
		if (result > 0 && refUserList != null) {
			// "", " ",null 값인 객체는 지우기
			refUserList.removeAll(Arrays.asList(""," ", null));
			electApprovMapper.deleteElecApprovRefByElectAprovId(electApprov.getId());
			for (String e : refUserList) {
				ElectApprovRefVO electApprovRefVO = new ElectApprovRefVO();
				electApprovRefVO.setElectapprovId(electApprov.getId());
				electApprovRefVO.setUserId(Integer.valueOf(e));
				electApprovMapper.insertElecApprovRef(electApprovRefVO);
			}

		}
		
		//null 값인 객체는 지우기
		if (result > 0 && electApprovLineList != null) {
			for(int i=0;i<electApprovLineList.size();i++) {
				String strUserId=electApprovLineList.get(i).getUserId();
				if(null == strUserId || "".equals(strUserId.trim())) {
					electApprovLineList.remove(i);
				}
			}
			
			//예전 결제라인 데이터는 지우고 새로 insert
			electApprovMapper.deleteElecApprovLineByElectAprovId(electApprov.getId());
			// list 객체안의 seq값을 기준으로 오름차순 정렬
			Collections.sort(electApprovLineList);
			
			//순서 
			for (int i = 0; i < electApprovLineList.size(); i++) {
				ElectApprovLineVO _electApprovLineVO = electApprovLineList.get(i);
				_electApprovLineVO.setElectapprovId(electApprov.getId());
				//결제선 순서 재부여
				_electApprovLineVO.setLvl((i + 1) + "");
				if (i == 0) {
					// 첫번째 결제선으로 할당된 사람은 열람 가능하도록 설정
					_electApprovLineVO.setB4yn("Y");
				} else {
					// 나머지 사람들은 아직 열람 불가능으로 설정
					_electApprovLineVO.setB4yn("N");
				}
				electApprovMapper.insertElecApprovLine(_electApprovLineVO);
			}
		}
		return electApprov.getId();
	}

	//결제건 상제보기를 하면 특정 기안용지 테이블의 데이터를 가져오는 함수
	public ElectApprovVO getElectApprovWithFormInfos(int elecApprovId,int userId, String formTableName) {
		//유저가 참조인인지, 승인할수 있는 상태인지까지 쿼리로 체크
		ElectApprovVO electApprov = electApprovMapper.getAElectApprov(elecApprovId,userId);
		if("???".equals(formTableName.trim())) {
			
		}else {
			ElectApprovVO _electApprov=electApprovMapper.getAElectApprovCommon(elecApprovId);
			electApprov.setTitle(_electApprov.getTitle());
			electApprov.setContent(_electApprov.getContent());
		}
		return electApprov;
	}
	
	// 예: 결제라인에서 첫번째 사람이 결제 승인을 하면 다음순번에 있는 사람이 열람가능하게 하는 함수
	public int allowElectLine(int elecApprovId, int userId) {
		//DB 조회를 위한 셋팅
		ElectApprovLineVO electApprovLineVO=new ElectApprovLineVO();
		electApprovLineVO.setElectapprovId(elecApprovId);
		electApprovLineVO.setUserId(userId+"");
		
		//현재 결제인 정보 가져옴
		electApprovLineVO=electApprovMapper.get_a_lineuser_info(electApprovLineVO);
		//결제승인 버튼 눌렀다는 표시
		electApprovLineVO.setAllowPressedYn("Y");
		electApprovMapper.updateElecApprovLine(electApprovLineVO);
		
		//다음순번에 있는 결제자
		electApprovLineVO.setElectapprovId(elecApprovId);
		electApprovLineVO.setUserId(electApprovLineVO.getNextLineUserid());
		electApprovLineVO=electApprovMapper.get_a_lineuser_info(electApprovLineVO);
		if(electApprovLineVO!=null) {
			//결제건 열람 가능 하도록 설정
			electApprovLineVO.setB4yn("Y");
			electApprovMapper.updateElecApprovLine(electApprovLineVO);
		}
		
		return 1;
	}

}
