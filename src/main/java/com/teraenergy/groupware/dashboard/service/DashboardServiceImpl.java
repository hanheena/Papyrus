package com.teraenergy.groupware.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.dashboard.mapper.DashboardMapper;

@Service("DashboardService")
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private DashboardMapper dashboardMapper;

	/* 테스트 select */
	@Override
	public List<Object> getBoard() throws Exception {

		return dashboardMapper.getBoard();
	}
}