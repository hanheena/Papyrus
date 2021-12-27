package com.teraenergy.groupware.vacation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.teraenergy.common.vo.UserVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovLineVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovVO;

@Mapper
@Repository
public interface VacationMapper {
   
	public void dayOffWrite(Map<String, Object> reqParam);

	public List<Map<String, Object>> list(UserVO userId);

	public Map<String, Object> dayOffDetail(int id);

	public void approve(Map<String, Object> reqParam);

	public void companion(Map<String, Object> reqParam);

	public List<Map<String, Object>> dayOffListApprove(int userId);
	
	public void cancel(Map<String, Object> reqParam);

	public Map<String, Object> electApproId(int userId);
	
	public Map<String, Object> electApprovDetail(int dayOffid);
	

	public ElectApprovVO getAElectApprov(int id, int userId);

	public ElectApprovVO getAElectApprovCommon(int eAId);

	public List<UserVO> getRefUsers_for_detail_page(int id);

	public List<ElectApprovLineVO> getLineUsers(ElectApprovVO electApprovVO);
	
}
