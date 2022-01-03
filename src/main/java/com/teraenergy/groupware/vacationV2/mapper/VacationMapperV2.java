package com.teraenergy.groupware.vacationV2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VacationMapperV2 {

	List<Object> get_vacation() throws Exception;

}
