package com.teraenergy.groupware.commonCode.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teraenergy.groupware.commonCode.service.CommonCodeService;

@Controller
public class CommonCodeController {
	
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	
	
	// ajax 코드 목록 
	@PostMapping(value= "/papyrus/commonCode/ajaxListCommonCode")
	@ResponseBody
	public List<Map<String, Object>> ajaxListCommonCode(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		// 해당 부서 인원 목록
		List<Map<String, Object>> listCommonCode = commonCodeService.listCommonCode(para);
		System.out.println("!@"+listCommonCode);
		return listCommonCode;
	}
	
	// ajax 코드 정보
	@PostMapping(value= "/papyrus/commonCode/ajaxCommonCode")
	@ResponseBody
	public Map<String, Object> ajaxCommonCode(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		
		// 해당 부서 인원 목록
		Map<String, Object> commonCode = commonCodeService.selectCommonCode(para);
		
		return commonCode;
	}
	
	
	// ajax 코드 업데이트
	@PostMapping(value= "/papyrus/commonCode/ajaxUpdateCommonCode")
	@ResponseBody
	public Map<String, Object> ajaxUpdateCommonCode(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {	
		String code = "0";
		String msg = "";
		try {
			int chkCount = commonCodeService.commonCodeValueCheck(para);
			int seq = Integer.parseInt(String.valueOf(para.get("seq")));
			
			// 코드 값 중복 체크 
			if(chkCount == 0) {
				// 수정할 정보 제외한 나머지 목록 호출
				List<Map<String, Object>> listMap = new ArrayList<>();  
				listMap = commonCodeService.listCommonCode2(para);
				
				// 업데이트 할 정보 삽입
				if(listMap.size() >= seq) {
					// 중간에 추가 될경우 List 인덱스 지정
					listMap.add(seq-1,para);				
				}else {
					// 순서가 마지막일경우 
					listMap.add(para);
				}
				// List 순서대로 seq 재 정의
				int seqNum = 1;
				for (Map<String, Object> map : listMap) {
					map.put("seq", seqNum);
					seqNum++;
					commonCodeService.updateCommonCode(map);
				}
				
			}else {
				// 중복값 존재하므로 변경작업 못함
				code = "1";
				msg = "중복값이 존재합니다.";
			}			
		} catch (Exception e) {
			System.out.println(e);
			
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	// ajax 코드 업데이트
	@PostMapping(value= "/papyrus/commonCode/ajaxInsertCommonCode")
	@ResponseBody
	public Map<String, Object> ajaxInsertCommonCode(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		String code = "0";
		String msg = "";
		try {
			int chkCount = commonCodeService.commonCodeValueCheck(para);
			System.out.println(para);
			if(chkCount == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codeValue", para.get("codeTypeValue"));
				Map<String, Object> resultMap  = commonCodeService.lastCommonCodeSeqNum(map);
				int seq = Integer.parseInt(String.valueOf(resultMap.get("seq")));
				seq++;
				para.put("seq", seq);
				para.put("codeTypeId", resultMap.get("codeTypeId"));
				System.out.println(para);
				commonCodeService.insertCommonCode(para);
			}else {
				// 중복값 존재하므로 변경작업 못함
				code = "1";
				msg = "중복값이 존재합니다.";
			}	
		} catch (Exception e) {
			System.out.println(e);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	// ajax 코드 타입과 코드 저장
	@PostMapping(value= "/papyrus/commonCode/ajaxInsertCommonCodeType")
	@ResponseBody
	public Map<String, Object> ajaxInsertCommonCodeType(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		String code = "0";
		int seq = 1;
		
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codeValueType",para.get("codeValueType"));
			map.put("codeNameType",para.get("codeNameType"));
			
			int dataYn = commonCodeService.commomCodeTypeYn(map);
			
			if(dataYn == 0) {
			   commonCodeService.insertCommonCodeType(map);
			}
			
			Map<String, Object> typeId  = commonCodeService.commonCodeTypeId(map);
			map.put("codeTypeId", typeId.get("codeTypeId"));
			
			Map<String, Object> resultMap  = commonCodeService.lastCommonCodeSeqNum2(map);
			
			if(resultMap != null) {
				seq = Integer.parseInt(String.valueOf(resultMap.get("seq")));
				seq++;
			}
			
			map.put("codeValue", para.get("codeValueType")+String.format("%03d", seq));
			map.put("codeName", para.get("docTxt"));
			map.put("seq", seq);
			commonCodeService.insertCommonCode(map);
			code = "1";
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", code);
		return resultMap;	
	}
	
	@PostMapping(value= "/papyrus/commonCode/listCommonCode3")
	@ResponseBody
	public List<Map<String, Object>> listCommonCode3(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		// 첨부서류 공통 코드 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeValueType",para.get("codeValueType"));
		System.out.println("listCommonCode3");
		List<Map<String, Object>> listCommonCode = commonCodeService.listCommonCode3(map);
		return listCommonCode;	
	}
	
	@RequestMapping(value = "/papyrus/commonCode/codeList")
	public String codeList(@RequestParam Map<String, Object> map, Model model, HttpSession session) throws Exception {
		
		List<Map<String, Object>> typeList = commonCodeService.listCommonCodeType();
		List<Map<String, Object>> codeList = commonCodeService.listCommonCodeAll();
				
		model.addAttribute("userInfo", session.getAttribute("userInfo"));
		map.put("userInfo", session.getAttribute("userInfo"));
		model.addAttribute("map", map);
        model.addAttribute("typeResultList", typeList);
        model.addAttribute("codeResultList", codeList);
        
		return "groupware/setting/commonCode";
	}
	
	@PostMapping(value="/papyrus/commonCode/codeTypeSave")
	@ResponseBody
	public List<Map<String, Object>> codeTypeSave(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeValueType",para.get("codeValueType"));
		map.put("codeNameType",para.get("codeNameType"));
		
		int dataYn = commonCodeService.commomCodeTypeYn(map);
		
		if(dataYn == 0) {
			commonCodeService.insertCommonCodeType(map);
		}
		
		List<Map<String, Object>> typeList = commonCodeService.listCommonCodeType();
		
		return typeList;	
	}
	
	@PostMapping(value="/papyrus/commonCode/codeSave")
	@ResponseBody
	public List<Map<String, Object>> codeSave(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		int seq = 1;
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codeValueType",para.get("codeValueType"));
			map.put("codeNameType",para.get("codeNameType"));
			
			Map<String, Object> typeId  = commonCodeService.commonCodeTypeId(map);
			map.put("codeTypeId", typeId.get("codeTypeId"));
			
			Map<String, Object> resultMap  = commonCodeService.lastCommonCodeSeqNum2(map);
			
			if(resultMap != null) {
				seq = Integer.parseInt(String.valueOf(resultMap.get("seq")));
				seq++;
			}
			
			map.put("codeValue", para.get("codeValueType")+String.format("%03d", seq));
			map.put("codeName", para.get("codeName"));
			map.put("seq", seq);
			commonCodeService.insertCommonCode(map);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		List<Map<String, Object>> codeList = commonCodeService.listCommonCodeAll();
		
		return codeList;	
	}
	
	@RequestMapping(value = "/papyrus/commonCode/codeTypeDel", method= {RequestMethod.GET, RequestMethod.POST})
	public String codeTypeDel(@RequestParam Map<String, Object> para, Model model, HttpSession session) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeTypeId",  para.get("valueDel"));
		
		String[] codeTypeId = para.get("valueDel").toString().split(",");
		
		for(int i=0; i<codeTypeId.length; i++) {
			map.put("codeTypeId", codeTypeId[i]);
			commonCodeService.codeTypeDel(map);
			commonCodeService.codeDel(map);
		}
		
		return "redirect:/papyrus/commonCode/codeList";
	}
	
	@RequestMapping(value = "/papyrus/commonCode/codeDel", method= {RequestMethod.GET, RequestMethod.POST})
	public String codeDel(@RequestParam Map<String, Object> para, Model model, HttpSession session) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeId", para.get("valueDel"));
		
		String[] codeId = para.get("valueDel").toString().split(",");
		
		for(int i=0; i<codeId.length; i++) {
			map.put("codeId", codeId[i]);
			commonCodeService.codeDel(map);
		}
		
		return "redirect:/papyrus/commonCode/codeList";
	}
}