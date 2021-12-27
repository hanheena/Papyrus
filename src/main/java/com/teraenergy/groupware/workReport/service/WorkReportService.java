package com.teraenergy.groupware.workReport.service;

import java.util.List;
import java.util.Map;

public interface WorkReportService {
	//전체 게시물 리스트
	Double workReportCnt(Map<String, Object> map) throws Exception;
	
	//업무보고 리스트
	List<Map<String, Object>> workReportList(Map<String, Object> map) throws Exception;
	
	//업무보고 작성
	void workReportSave(Map<String, Object> map) throws Exception;
	
	//업무보고 상세 조회
	Map<String, Object> workReportDetail(Map<String, Object> map) throws Exception;
	
	//업무보고 수정
	boolean workReportChange(Map<String, Object> map) throws Exception;
	
	//업무보고 삭제
	void workReportDelete(Map<String, Object> map);
	
	// 부서 불러오기
	String getDept(Map<String, Object> map) throws Exception;
	
	//업무보고 수정 시 기존 데이터 값 가져오기
	String getModifyReport(int id) throws Exception;
}
