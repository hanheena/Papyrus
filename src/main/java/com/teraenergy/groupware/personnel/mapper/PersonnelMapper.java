package com.teraenergy.groupware.personnel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.teraenergy.common.vo.CommonCodeVO;
import com.teraenergy.groupware.personnel.vo.PlaceVO;
import com.teraenergy.groupware.personnel.vo.ProjectManageProjectinfoVO;
import com.teraenergy.groupware.personnel.vo.SkillInventoryCareerVO;


@Mapper
@Repository
public interface PersonnelMapper {
	
	public List<Map<String, String>> listDept(Map<String, String> map);
	public List<Map<String, Object>> listDeptUser(Map<String, Object> map);
	
	public Map<String, Object> selectUserInfo(Map<String, Object> map);
	public void updateUserInfo(Map<String, Object> map);

	public void updateCheckedYn(Map<String, Object> map);
	
	/*** 회원가입 START (문재영) ***/
	public void insertUserReg(Map<String, Object> map);
	public Map<String, Object> selectUserNum(String userNum);
	public void updateUserNumYN(String userId);
	public void ajaxUpdateUserReg(Map<String, Object> map);
	
	public void insertUserInfo(Map<String, Object> map);
	
	public int loginIdChk(String loginId);
	/*** 회원가입 END (문재영) ***/
	
	/* 박종진 추가*/
	public List<PlaceVO> get_place_list();
	public List<ProjectManageProjectinfoVO> get_project_list();
	public int insert_skill_inventory_career(SkillInventoryCareerVO skillInventoryCareerVO);
	public List<SkillInventoryCareerVO> get_skill_career_by_userid(String userId);
	public SkillInventoryCareerVO get_a_skill_career_by_id(String id);
	
	public int update_skill_inventory_career(SkillInventoryCareerVO skillInventoryCareerVO);
	
	public List<CommonCodeVO> get_list_from_commoncode(String codeTypeId);
	
	public int insert_project_mng_info(ProjectManageProjectinfoVO projectManageProjectinfoVO);
	public int insert_commoncode(CommonCodeVO commonCodeVO);
	public int update_commoncode(CommonCodeVO commonCodeVO);
	public int delete_skill_inven_career_by_id(String skillInvenCareerId);
	public int update_project_info(ProjectManageProjectinfoVO projectManageProjectinfoVO);
	/* 박종진 추가 끝*/
	
	/* 소영 추가 */
	public Map<String, Object> selectSkilInventoryBasic(Map<String, Object> param);
	public int skilInventoryBasicInsert(Map<String, Object> param);
	public int skilInventoryBasicUpdate(Map<String, Object> param);
	
	//경력사항 list로 가져오기
	public List<Map<String, Object>> skilInventoryCareerPrint(Map<String, Object> param);
	//exceldownload 해당check id값들의 data 가져옴
	public List<Map<String, Object>> chkPrintBtn(List<String> chkData);
    // carrer 기본정보 data 가져오기
	public Map<String, Object> careerInfo(Map<String, Object> map);
	
	public Map<String, Object> careerMonth(Map<String, Object> personnel);
	
	public List<Map<String, Object>> skillInfoUpload(Map<String, Object> personnel);

	/* 소영 추가 END */

}