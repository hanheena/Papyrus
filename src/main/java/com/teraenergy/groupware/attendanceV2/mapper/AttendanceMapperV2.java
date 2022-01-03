package com.teraenergy.groupware.attendanceV2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AttendanceMapperV2 {

	List<Object> get_attend(int userId);

}
