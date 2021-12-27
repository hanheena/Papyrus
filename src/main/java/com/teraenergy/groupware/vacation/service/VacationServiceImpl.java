package com.teraenergy.groupware.vacation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.common.vo.UserVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovVO;
import com.teraenergy.groupware.vacation.mapper.VacationMapper;

@Service("VacationService")
public class VacationServiceImpl implements VacationService {

	@Autowired
	private VacationMapper vacationMapper;

    /**
    *  휴가 신청
    * @param reqParam
    */
	@Override
	public void dayOffWrite(Map<String, Object> reqParam) {
		vacationMapper.dayOffWrite(reqParam);	
	}
	
	/**
	 * 휴가 리스트
	 * @param userId
	 * @return List
	 */
	@Override
	public List<Map<String, Object>> VacationList(UserVO userId) {
		return vacationMapper.list(userId);
	}
	
	@Override
	public List<Map<String, Object>> dayOffListApprove(int userId) {
		return vacationMapper.dayOffListApprove(userId);
	}
	

	@Override
	public Map<String, Object> dayOffDetail(int id) {
		return vacationMapper.dayOffDetail(id);
	}

	@Override
	public void approve(Map<String, Object> reqParam) {
		vacationMapper.approve(reqParam);
		
	}

	@Override
	public void companion(Map<String, Object> reqParam) {
		vacationMapper.companion(reqParam);
		
	}
	@Override
	public void cancel(Map<String, Object> reqParam) {
		vacationMapper.cancel(reqParam);
		
	}

	@Override
	public  Map<String, Object> electApproId(int userId) {
		return vacationMapper.electApproId(userId);
		
	}

	@Override
	public Map<String, Object> electApprovDetail(int dayOffid) {
		return vacationMapper.electApprovDetail(dayOffid);
	}

	@Override
	public ElectApprovVO selectDayOffDetail(int eAId, int userId, String formTableName) {
		ElectApprovVO electApprov = vacationMapper.getAElectApprov(eAId,userId);
		System.out.println("==================VC Service    eAId :"+eAId+" ===userId  :"+userId);
		return electApprov;
	}


}

