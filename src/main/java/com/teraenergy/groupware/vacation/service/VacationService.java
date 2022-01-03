package com.teraenergy.groupware.vacation.service;

import java.util.List;
import java.util.Map;

import com.teraenergy.common.vo.UserVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovVO;

public interface VacationService {
	
	
    /**
    *  휴가 신청
    * @param reqParam
    */
	void dayOffWrite(Map<String, Object> reqParam);
	
	/**
	 * 휴가 리스트
	 * @param personnel
	 * @return UserVO
	 */
	List<Map<String, Object>> VacationList(Map<String, Object> personnel);
	
	List<Map<String, Object>> dayOffListApprove(int userId);
	
	/**
	 * 휴가 상세페이지
	 * @param id
	 * @return Map
	 */
	Map<String, Object> dayOffDetail(int id);	
		
	/**
	 * 휴가승인
	 * @param reqParam
	 */
	void approve(Map<String, Object> reqParam);

	/**
	 * 휴가반려
	 * @param reqParam
	 */
	void companion(Map<String, Object> reqParam);

	/**
	 * 휴가취소
	 * @param reqParam
	 */
	void cancel(Map<String, Object> reqParam);
	
	

	Map<String, Object> electApproId(int use);

	Map<String, Object> electApprovDetail(int dayOffid);

	ElectApprovVO selectDayOffDetail(int eAId, int userId, String formTableName);



    
}
