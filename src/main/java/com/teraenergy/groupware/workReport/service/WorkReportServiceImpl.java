package com.teraenergy.groupware.workReport.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.workReport.mapper.WorkReportMapper;

@Service("WorkReportService")
public class WorkReportServiceImpl implements WorkReportService {
	
	@Autowired
	private WorkReportMapper workReportMapper;
	
	//게시물 전체 개수 조회
	@Override
	public Double workReportCnt(Map<String, Object> map) throws Exception {
		double workReportCnt = workReportMapper.workReportCnt(map);
		return workReportCnt;
	}
	
	//업무보고 게시판 리스트
	@Override
	public List<Map<String, Object>> workReportList(Map<String, Object> map) throws Exception {
		return workReportMapper.workReportList(map);
	}
	
	//업무 보고 작성
	@Override
	public void workReportSave(Map<String, Object> map) throws Exception {
		workReportMapper.workReportSave(map);
	}

	//업무 보고 상세 조회
	@Override
	public Map<String, Object> workReportDetail(Map<String, Object> map) throws Exception {
		System.out.println("업무보고 상세 조회까지 들어옴 : " + map.get("id"));
		
		map.put("vo", workReportMapper.workReportDetail(map));
		return map;
	}
	
	//업무 보고 수정
	@Override
	public boolean workReportChange(Map<String, Object> map) throws Exception {
		System.out.println("보고 수정 : " + map.get("id"));
		return workReportMapper.workReportChange(map) > 0 ? true: false;
	}
	
	//업무 보고 삭제
	@Override
	public void workReportDelete(Map<String, Object> map) {
		workReportMapper.workReportDelete(map);
	}
	
	// 부서 불러오기
	@Override
	public String getDept(Map<String, Object> map) throws Exception{
		
		return workReportMapper.getDept(map);
	};
	
	@Override
	public String getModifyReport(int id) throws Exception {
		// TODO Auto-generated method stub
		return workReportMapper.getModifyReport(id);
	};
}