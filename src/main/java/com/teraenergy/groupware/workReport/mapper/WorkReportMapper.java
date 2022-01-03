package com.teraenergy.groupware.workReport.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WorkReportMapper {
	//게시물 전체 개수
	Double workReportCnt (Map<String, Object> map);
	
	//업무보고 게시판 리스트
	List<Map<String, Object>> workReportList(Map<String, Object> map);
	
	//업무보고 작성
	void workReportSave(Map<String, Object> map);
	
	//업무보고 상세 조회
	Map<String, Object> workReportDetail(Map<String, Object> map);
	
	//업무보고 수정
	int workReportChange(Map<String, Object> map);
	
	//업무보고 삭제
	public void workReportDelete(Map<String, Object> map);

	// 부서 불러오기
	String getDept(Map<String, Object> map);
	
	String getModifyReport(int id);
}
