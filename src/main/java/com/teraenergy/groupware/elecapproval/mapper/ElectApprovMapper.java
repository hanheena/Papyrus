package com.teraenergy.groupware.elecapproval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.teraenergy.common.vo.UserVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovCategoryVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovFileVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovLineVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovRefVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovVO;

@Mapper
@Repository
public interface ElectApprovMapper {
	public int insertElecApprov(ElectApprovVO elecApprov);
	public List<ElectApprovVO> getAllElectApprovByStatus(ElectApprovVO electApprovVO);
	public ElectApprovVO getAElectApprov(@Param("id")int id,@Param("userId")int userId);
	public List<ElectApprovCategoryVO> getAllCategories();
	public List<ElectApprovLineVO> getLineUsers(ElectApprovVO elecApprov);
	public ElectApprovCategoryVO getACategoryById(int id);
	
	public ElectApprovFileVO get_a_electapprov_uploaded_file(int electapprovId);
	public List<ElectApprovFileVO> get_electapprov_uploaded_files(int electapprovId);
	
	public ElectApprovVO getAElectApprovCommon(int electApprovId);
	
	public ElectApprovLineVO get_a_lineuser_info(ElectApprovLineVO electApprovLineVO);
	
	
	public int updateElecApprov(ElectApprovVO elecApprov);
	public int deleteElecApprov(ElectApprovVO elecApprov);
	
	public List<UserVO> getRefUsers(int electapprovId);
	public List<UserVO> getRefUsers_for_detail_page(int electapprovId);
	
	public List<UserVO> get_user_departments();
	public List<UserVO> get_user_by_department(String department);
	
	public int insertElecApprovRef(ElectApprovRefVO electApprovRefVO);
	public int insertElectApprovLine(ElectApprovVO electApprovVO);
	
	public int insertElecApprovLine(ElectApprovLineVO electApprovLineVO);
	public int updateElecApprovLine(ElectApprovLineVO electApprovLineVO);
	
	public int insert_elect_approv_form_common(ElectApprovVO electApprovVO);
	public int update_elect_approv_form_common(ElectApprovVO electApprovVO);
	
	public int deleteElecApprovLineByElectAprovId(int id);
	public int deleteElecApprovRefByElectAprovId(int id);
	
	public int delete_electapprov_file_by_id(int id);
	
	public int auto_approve_electapprov(int id);
	
	public int insert_electapprov_files(ElectApprovFileVO electApprovFileVO);
}
