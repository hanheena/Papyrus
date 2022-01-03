package com.teraenergy.groupware.dashboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DashboardMapper{

	/* 테스트 select */
    public List<Object> getBoard() throws Exception;
}