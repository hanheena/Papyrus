package com.teraenergy.groupware.projectmanage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.teraenergy.groupware.projectmanage.vo.ProjectManageProjectinfoVO;
import com.teraenergy.groupware.projectmanage.vo.ProjectManageVO;
import com.teraenergy.groupware.projectmanage.vo.ProjectManagelocinfoVO;


@Mapper
@Repository
public interface ProjectManageMapper {
	
	public List<ProjectManageVO> get_all_manage();
	public ProjectManageVO get_a_manage(int id);
	public List<ProjectManageProjectinfoVO> get_all_projectinfo();
	public List<ProjectManagelocinfoVO> get_all_locinfo();
	
	public int insert_manage(ProjectManagelocinfoVO projectManageVO);
	public int insert_projectinfo(ProjectManageProjectinfoVO projectManageProjectinfoVO);
	public int insert_locinfo(ProjectManageVO projectManagelocinfoVO);
	public int update_manage(ProjectManagelocinfoVO projectManageVO);
	public int delete_manage(int id);
	
	// 이한솔 추가	
	List<Map<String, Object>> getProjectName();
	List<Map<String,Object>> selectProeject(Map<String, Object> projectMap);
	List<Map<String, Object>> getAttendList();
	
}