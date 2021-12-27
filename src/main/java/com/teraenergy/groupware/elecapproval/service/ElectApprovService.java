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
		
		
		List<ElectApprovLineVO> electApprovLineList = electApprovLineVO.getElectApprovLineList();
		System.out.println("## electApprovLineList: "+electApprovLineList.toString());
		electApprov.setElectapprovId(electApprov.getId());
		
		int result = electApprovMapper.insertElecApprov(electApprov);
		if("elect_approv_form_common".equals(electApprov.getFormTableName().trim())) {
			electApprovMapper.insert_elect_approv_form_common(electApprov);
		}
		
		
		
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
				String strUserId=electApprovLineList.get(i).getUserId();
				if(null == strUserId || "".equals(strUserId.trim())) {
					electApprovLineList.remove(i);
				}
			}
			
			Collections.sort(electApprovLineList);
			for (int i = 0; i < electApprovLineList.size(); i++) {
				ElectApprovLineVO _electApprovLineVO = electApprovLineList.get(i);
				_electApprovLineVO.setElectapprovId(electApprov.getId());
				_electApprovLineVO.setLvl((i + 1) + "");
				if (i == 0) {
					_electApprovLineVO.setB4yn("Y");
				} else {
					_electApprovLineVO.setB4yn("N");
				}
				electApprovMapper.insertElecApprovLine(_electApprovLineVO);
			}
		}
		return electApprov.getId();

	}

	public int updateElectApprovService(ElectApprovVO electApprov, List<String> refUserList,
			ElectApprovLineVO electApprovLineVO) {
		List<ElectApprovLineVO> electApprovLineList = electApprovLineVO.getElectApprovLineList();
		electApprov.setElectapprovId(electApprov.getId());
		
		int result = electApprovMapper.updateElecApprov(electApprov);
		if("elect_approv_form_common".equals(electApprov.getFormTableName().trim())) {
			electApprovMapper.update_elect_approv_form_common(electApprov);
		}
		
		
		if (result > 0 && refUserList != null) {
			refUserList.removeAll(Arrays.asList(""," ", null));
			electApprovMapper.deleteElecApprovRefByElectAprovId(electApprov.getId());
			for (String e : refUserList) {
				ElectApprovRefVO electApprovRefVO = new ElectApprovRefVO();
				electApprovRefVO.setElectapprovId(electApprov.getId());
				electApprovRefVO.setUserId(Integer.valueOf(e));
				electApprovMapper.insertElecApprovRef(electApprovRefVO);
			}

		}
		if (result > 0 && electApprovLineList != null) {
			for(int i=0;i<electApprovLineList.size();i++) {
				String strUserId=electApprovLineList.get(i).getUserId();
				if(null == strUserId || "".equals(strUserId.trim())) {
					electApprovLineList.remove(i);
				}
			}
			
			electApprovMapper.deleteElecApprovLineByElectAprovId(electApprov.getId());
			Collections.sort(electApprovLineList);
			for (int i = 0; i < electApprovLineList.size(); i++) {
				ElectApprovLineVO _electApprovLineVO = electApprovLineList.get(i);
				_electApprovLineVO.setElectapprovId(electApprov.getId());
				_electApprovLineVO.setLvl((i + 1) + "");
				if (i == 0) {
					_electApprovLineVO.setB4yn("Y");
				} else {
					_electApprovLineVO.setB4yn("N");
				}
				electApprovMapper.insertElecApprovLine(_electApprovLineVO);
			}
		}
		return electApprov.getId();
	}

	public ElectApprovVO getElectApprovWithFormInfos(int elecApprovId,int userId, String formTableName) {
		ElectApprovVO electApprov = electApprovMapper.getAElectApprov(elecApprovId,userId);
		if("???".equals(formTableName.trim())) {
			
		}else {
			ElectApprovVO _electApprov=electApprovMapper.getAElectApprovCommon(elecApprovId);
			System.out.println("## _electApprov: "+_electApprov.toString());
			electApprov.setTitle(_electApprov.getTitle());
			electApprov.setContent(_electApprov.getContent());
		}
		return electApprov;
	}
	
	public int allowElectLine(int elecApprovId, int userId) {
		ElectApprovLineVO electApprovLineVO=new ElectApprovLineVO();
		electApprovLineVO.setElectapprovId(elecApprovId);
		electApprovLineVO.setUserId(userId+"");
		electApprovLineVO=electApprovMapper.get_a_lineuser_info(electApprovLineVO);
		electApprovLineVO.setAllowPressedYn("Y");
		electApprovMapper.updateElecApprovLine(electApprovLineVO);
		
		electApprovLineVO.setElectapprovId(elecApprovId);
		electApprovLineVO.setUserId(electApprovLineVO.getNextLineUserid());
		electApprovLineVO=electApprovMapper.get_a_lineuser_info(electApprovLineVO);
		if(electApprovLineVO!=null) {
			electApprovLineVO.setB4yn("Y");
			electApprovMapper.updateElecApprovLine(electApprovLineVO);
		}
		
		return 1;
	}

}
