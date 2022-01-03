package com.teraenergy.groupware.personnel.service;

import java.util.List;
import java.util.Map;

public interface PersonnelService {
	
	
	public List<Map<String, String>> listDept(Map<String, String > map) throws Exception;
	public List<Map<String, Object>> listDeptUser(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectUserInfo(Map<String, Object> map)  throws Exception;
	public void updateUserInfo(Map<String, Object> map);
	public void updateCheckedYn(Map<String, Object> map);
	
	/*** 회원가입 START (문재영) ***/	 
	public void insertUserInfo(Map<String, Object> map);
	public Map<String, Object> selectUserNum(String userNum) throws Exception;
	public void updateUserNumYN(String userId);
	public void ajaxUpdateUserReg(Map<String, Object> map);
	
	public boolean sendMail(String sendTo, String mailTitle, String mailCont)  throws Exception;
	
	public int loginIdChk(String loginId) throws Exception;	// 아이디 중복체크
	/*** 회원가입 END (문재영) ***/	
	
	/* 소영 추가 */
	public Map<String, Object> skilInventoryBasic(Map<String, Object> param);
	
	public Map<String, Object> skilInventoryBasicId(Map<String, Object> map);
	
	public List<Map<String, Object>> skilInventoryCareerPrint(Map<String, Object> param);

	public List<Map<String, Object>> chkPrintBtn(List<String> chkData);
	
	public Map<String, Object> careerInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> careerMonth(Map<String, Object> personnel) throws Exception;
	
	public List<Map<String, Object>> skillInfoUpload(Map<String, Object> personnel);
	/* 소영 추가 END */
}