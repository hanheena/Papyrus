package com.teraenergy.groupware.commonCode.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface CommonCodeMapper {
	
	public List<Map<String, Object>> listCommonCode(Map<String, Object> map);
	public Map<String, Object> selectCommonCode(Map<String, Object> map);
	
	// 공통코드 값 변경시 value 중복 체크 
	public int commonCodeValueCheck(Map<String, Object> map);
	// 공통코드 순서 변경시 기준 순서 위로 호출 
	public List<Map<String, Object>> listCommonCode2(Map<String, Object> map);
	// 수정
	public void updateCommonCode(Map<String, Object> map);
	// 등록
	public void insertCommonCode(Map<String, Object> map);
	// 마지막 값(순서)
	public Map<String, Object> lastCommonCodeSeqNum(Map<String, Object> map);
	// 공통코드타입 삽입
	public void insertCommonCodeType(Map<String, Object> map);
	// 공통코드타입 ID
	public Map<String, Object> commonCodeTypeId(Map<String, Object> map);
	// 공통코드타입 삽입 유무
	public int commomCodeTypeYn(Map<String, Object> map);
	// 마지막 값(순서)
	public Map<String, Object> lastCommonCodeSeqNum2(Map<String, Object> map);
	// 첨부서류 코드 호출
	public List<Map<String, Object>> listCommonCode3(Map<String, Object> map);
	
	public List<Map<String, Object>> listCommonCodeType();
	
	public List<Map<String, Object>> listCommonCodeAll();
	
	public void codeTypeDel(Map<String, Object> map);
	
	public void codeDel(Map<String, Object> map);
}