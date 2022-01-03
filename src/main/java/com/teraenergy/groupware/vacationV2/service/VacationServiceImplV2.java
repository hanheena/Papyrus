package com.teraenergy.groupware.vacationV2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teraenergy.groupware.vacationV2.mapper.VacationMapperV2;

@Service
public class VacationServiceImplV2 implements VacationServiceV2{
	
	@Autowired
	private VacationMapperV2 vacationMapperV2;

	@Override
	public List<Object> get_vacation() throws Exception {
		System.out.println("service");
		// TODO Auto-generated method stub
		return vacationMapperV2.get_vacation();
	}

}
