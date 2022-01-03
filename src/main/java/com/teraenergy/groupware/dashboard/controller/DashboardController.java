package com.teraenergy.groupware.dashboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.teraenergy.common.excelDownload.ExcelDownloadService;
import com.teraenergy.common.file.service.CommonAttachFileService;
import com.teraenergy.groupware.commonCode.service.CommonCodeService;
import com.teraenergy.groupware.personnel.mapper.PersonnelMapper;
import com.teraenergy.groupware.personnel.service.PersonnelService;
import com.teraenergy.groupware.personnel.vo.PlaceVO;

@Controller
public class DashboardController {
	@Autowired
	private PersonnelService personnelService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private CommonAttachFileService commonAttachFileService;

	@Autowired
	private PersonnelMapper personnelMapper;

	@Autowired
	private ExcelDownloadService excelDownloadService;


	@RequestMapping(value = "/papyrus/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(HttpSession session ,@RequestParam Map<String, Object> map)throws Exception {
		
		ModelAndView mav = new ModelAndView();
		// 회원 정보
				Map<String, Object> userMap = new HashMap<String, Object>();
				Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");

				if (map.get("userId") == null || map.get("userId") == "") {
					userMap.put("userId", userInfoMap.get("userId"));
				} else {
					userMap.put("userId", map.get("userId"));
				}
				Map<String, Object> personnel = personnelService.selectUserInfo(userMap);
				mav.addObject("personnel", personnel);

				// 부서 목록 & 인원 카운트
				Map<String, String> deptInfoMap = new HashMap<String, String>();
				deptInfoMap.put("codeValue", "dept");
				List<Map<String, String>> listDeptInfo = personnelService.listDept(deptInfoMap);
				mav.addObject("listDeptInfo", listDeptInfo);

				// 부서 [공통코드]
				Map<String, Object> deptMap = new HashMap<String, Object>();
				deptMap.put("codeValue", "dept");
				List<Map<String, Object>> listDept = commonCodeService.listCommonCode(deptMap);

				mav.addObject("listDept", listDept);
				// 직급 [공통코드]
				Map<String, Object> positionMap = new HashMap<String, Object>();
				positionMap.put("codeValue", "position");
				List<Map<String, Object>> listPosition = commonCodeService.listCommonCode(positionMap);
				mav.addObject("listPosition", listPosition);
				// 직책 [공통코드]
				Map<String, Object> dutyMap = new HashMap<String, Object>();
				dutyMap.put("codeValue", "duty");
				List<Map<String, Object>> listDuty = commonCodeService.listCommonCode(dutyMap);
				mav.addObject("listDuty", listDuty);
				// 업무 위치 [공통코드]
				Map<String, Object> placeMap = new HashMap<String, Object>();
				placeMap.put("codeValue", "place");
				List<Map<String, Object>> listPlace = commonCodeService.listCommonCode(placeMap);
				mav.addObject("listPlace", listPlace);
				// 근로 형태 [공통코드]
				Map<String, Object> workTypeMap = new HashMap<String, Object>();
				workTypeMap.put("codeValue", "workType");
				List<Map<String, Object>> listWorkType = commonCodeService.listCommonCode(workTypeMap);
				mav.addObject("listWorkType", listWorkType);
				// 상태 [공통코드]
				Map<String, Object> stateMap = new HashMap<String, Object>();
				stateMap.put("codeValue", "state");
				List<Map<String, Object>> listState = commonCodeService.listCommonCode(stateMap);
				mav.addObject("listState", listState);
				// 등급 [공통코드]
				Map<String, Object> authMap = new HashMap<String, Object>();
				authMap.put("codeValue", "auth");
				List<Map<String, Object>> listAuth = commonCodeService.listCommonCode(authMap);
				mav.addObject("listAuth", listAuth);


				/* 박종진 추가 */
				List<PlaceVO> placeList = personnelMapper.get_place_list();
				mav.addObject("placeList", placeList);
				/* 박종진 추가 END */

				/* 소영추가 */
				Map<String, Object> sBasic = personnelService.skilInventoryBasicId(personnel);
				mav.addObject("sBasic", sBasic);
				/* 소영추가 END */
		
		mav.addObject("userInfo", session.getAttribute("userInfo"));
		
		mav.setViewName("groupware/dashboard/dashboard");
		
		return mav;
	}
	

	/* 이미지 첨부 예시 소스
	 * @RequestMapping("/test_img_view") public @ResponseBody List<Object>
	 * test_img_view() throws Exception {
	 * 
	 * List<Object> getData = s.getBoard();
	 * 
	 * return getData; }
	 */

	// 이미지 첨부 예시 소스
	// @SuppressWarnings("unchecked")
	// @RequestMapping(value = "test_img_insert", headers = "Accept=*/*")
	/*
	 * public String test_img_insert(@RequestParam (name="test_img") MultipartFile
	 * test_img) throws Exception {
	 * 
	 * String filePath = "C:\\file_path";
	 * 
	 * String test_img_filePath = filePath + "\\" + test_img.getOriginalFilename();
	 * 
	 * if (!test_img.getOriginalFilename().isEmpty()) { test_img.transferTo(new
	 * File(filePath, test_img.getOriginalFilename())); }
	 * 
	 * // map에 저장 Map<String, Object> map = new HashMap<>();
	 * 
	 * map.put("filePath", test_img_filePath);
	 * 
	 * s.insert_file(map);
	 * 
	 * return "redirect:/dashboard"; }
	 */
}