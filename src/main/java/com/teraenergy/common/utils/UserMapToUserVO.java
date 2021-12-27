package com.teraenergy.common.utils;

import java.util.Date;
import java.util.Map;

import com.teraenergy.common.vo.UserVO;

public class UserMapToUserVO {
	public static UserVO userMaptoUserVO(Map<String, Object> map_userinfo) {
		UserVO userVO = new UserVO();
		userVO.setDeleteDt((Date)map_userinfo.get("deleteDt"));
		userVO.setLoginId((String)map_userinfo.get("loginId"));
		userVO.setAuth((String)map_userinfo.get("auth"));
		userVO.setCommuteYn((String)map_userinfo.get("commuteYn"));
		userVO.setPhoneNum((String)map_userinfo.get("phoneNum"));
		
		userVO.setUpdateId(-1);
		if(map_userinfo.get("updateId")!=null && !"".equals(map_userinfo.get("updateId")) && !"null".equals(map_userinfo.get("updateId")) ) {
			userVO.setUpdateId((int)map_userinfo.get("updateId"));	
		}
		
		userVO.setPassword((String)map_userinfo.get("password"));
		userVO.setKrName((String)map_userinfo.get("krName"));
		userVO.setEnName((String)map_userinfo.get("enName"));
		userVO.setState((String)map_userinfo.get("state"));
		userVO.setDepartment((String)map_userinfo.get("department"));
		userVO.setEmail((String)map_userinfo.get("email"));
		userVO.setInsertDt((Date)map_userinfo.get("insertDt"));
		userVO.setBirthDt((Date)map_userinfo.get("birthDt"));
		userVO.setAddr2((String)map_userinfo.get("addr2"));
		userVO.setAddr1((String)map_userinfo.get("addr1"));
		userVO.setPostNum((String)map_userinfo.get("postNum"));
		userVO.setUpdateDt((Date)map_userinfo.get("updateDt"));
		userVO.setUserId((int)map_userinfo.get("userId"));
		userVO.setDelYn((String)map_userinfo.get("delYn"));
		userVO.setResignDt((Date)map_userinfo.get("resignDt"));
		userVO.setEmplyDt((Date)map_userinfo.get("emplyDt"));
		userVO.setWorkType((String)map_userinfo.get("workType"));
		userVO.setPosition((String)map_userinfo.get("position"));
		userVO.setSkillYn((String)map_userinfo.get("skillYn"));
		
		userVO.setDeleteId(-1);
		if(map_userinfo.get("deleteId")!=null && !"".equals(map_userinfo.get("deleteId")) && !"null".equals(map_userinfo.get("deleteId"))) {
			userVO.setDeleteId((int)map_userinfo.get("deleteId"));	
		}
		
		userVO.setPapersYn((String)map_userinfo.get("papersYn"));
		
		userVO.setInsertId(-1);
		if(map_userinfo.get("insertId")!=null && !"".equals(map_userinfo.get("insertId")) && !"null".equals(map_userinfo.get("insertId"))) {
			userVO.setInsertId((int)map_userinfo.get("insertId"));	
		}
		return userVO;
	}
}
