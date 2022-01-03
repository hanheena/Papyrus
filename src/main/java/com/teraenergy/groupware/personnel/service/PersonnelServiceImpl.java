package com.teraenergy.groupware.personnel.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.personnel.mapper.PersonnelMapper;

@Service("personnelService")
public class PersonnelServiceImpl implements PersonnelService {

	@Autowired
	private PersonnelMapper personnelMapper;
	
	@Autowired
	private JavaMailSender javaMailSender;	// 회원가입용 - spring-boot-mail 사용 주입 설정	
	
	@Override
	public List<Map<String, String>> listDept(Map<String, String> map) throws Exception {
		return personnelMapper.listDept(map);
	}

	@Override
	public List<Map<String, Object>> listDeptUser(Map<String, Object> map) throws Exception {
		return personnelMapper.listDeptUser(map);
	}

	@Override
	public Map<String, Object> selectUserInfo(Map<String, Object> map) throws Exception {
		return personnelMapper.selectUserInfo(map);
	}

	@Override
	public void updateUserInfo(Map<String, Object> map) {
		personnelMapper.updateUserInfo(map);
	}

	@Override
	public void updateCheckedYn(Map<String, Object> map) {
		personnelMapper.updateCheckedYn(map);
	}
	
	/*** 회원가입 START (문재영) ***/		
	@Override
	public void insertUserInfo(Map<String, Object> map) {
		personnelMapper.insertUserInfo(map);
	}	
	
	@Override
	public Map<String, Object> selectUserNum(String userNum) throws Exception {		
		return personnelMapper.selectUserNum(userNum);
	}
	
	@Override
	public void updateUserNumYN(String userId) {
		personnelMapper.updateUserNumYN(userId);
	}
	
	@Override
	public void ajaxUpdateUserReg(Map<String, Object> map) {
		personnelMapper.ajaxUpdateUserReg(map);
	}
	
	@Override
	public boolean sendMail(String sendTo, String mailTitle, String mailCont) throws Exception {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setTo(sendTo);
		simpleMailMessage.setSubject(mailTitle);
		simpleMailMessage.setText(mailCont);
		
		javaMailSender.send(simpleMailMessage);
		
		return true;
	}	
	
	@Override
	public int loginIdChk(String loginId) throws Exception{
		return personnelMapper.loginIdChk(loginId);
	}
	/*** 회원가입 END (문재영) ***/	
	
	// 소영추가. 스킬 인벤토리 insert update
	@Override
	public Map<String, Object> skilInventoryBasic(Map<String, Object> param) {

		if (personnelMapper.selectSkilInventoryBasic(param) == null) {
			personnelMapper.skilInventoryBasicInsert(param);
		} else {
			personnelMapper.skilInventoryBasicUpdate(param);
		}
		return personnelMapper.selectSkilInventoryBasic(param);
	}

	// 스킬 인벤토리 해당 user_id값 select
	@Override
	public Map<String, Object> skilInventoryBasicId(Map<String, Object> param) {
		return personnelMapper.selectSkilInventoryBasic(param);
	}

	// 스킬 인벤토리 해당 user_id값으로 경력사항 list가져오기
	@Override
	public List<Map<String, Object>> skilInventoryCareerPrint(Map<String, Object> param) {
		
		List<Map<String, Object>> map = personnelMapper.skilInventoryCareerPrint(param);
			
		if(map!=null) {
			return map;			
		}else {
			Map<String, Object> map2 = new HashMap<>();
			map2.put("map2", map2);
			List<Map<String, Object>> li = null;
			li.add(map2);
			return li;
			
		}
	}

	@Override
	public List<Map<String, Object>> chkPrintBtn(List<String> chkData) {
		return personnelMapper.chkPrintBtn(chkData);
	}

	@Override
	public Map<String, Object> careerInfo(Map<String, Object> map) throws Exception {
		return personnelMapper.careerInfo(map);
	}

	@Override
	public Map<String, Object> careerMonth(Map<String, Object> personnel) throws Exception {
		Map<String, Object> monthCareer = personnelMapper.careerMonth(personnel);
	    if(monthCareer != null) {
		// 지금까지 했던 경력 모두가 더해져서 ~~년 ~~ 개월 로 표시되어야함
		int sum = 0;
	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date startDate = format.parse((String) monthCareer.get("workStartDt"));
		Date endDate = format.parse((String) monthCareer.get("workEndDt"));
		
		int startMonth = (startDate.getYear() * 12) + startDate.getMonth(); int
		endMonth = (endDate.getYear() * 12) + endDate.getMonth();
		sum += endMonth - startMonth;
			

		//System.out.println("################====sum : "+sum);
		int year = (sum/12);
		//System.out.println("################====year : "+year);
		int month = (sum % 12) ;
		//System.out.println("################====month : "+month);
		if (month == 12) {
			year += year;
			month = 0;
		}
		 Map<String , Object> map = new HashMap<>();
		 map.put("workStartDt", monthCareer.get("workStartDt"));
		 map.put("workEndDt",monthCareer.get("workEndDt"));	 
		 map.put("year",year); 
		 map.put("month",month);
		 return map;
		}else {
			Map<String , Object> map2 = new HashMap<>();
			map2.put("map","map");
			return map2;
			
		}

	}


	@Override
	public List<Map<String, Object>> skillInfoUpload(Map<String, Object> personnel) {
		
		return personnelMapper.skillInfoUpload(personnel);
	}
	/* 소영 추가 END */
	
}