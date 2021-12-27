package com.teraenergy.groupware.personnel.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teraenergy.common.excelDownload.ExcelDownloadService;
import com.teraenergy.common.file.model.CommonAttachFileModel;
import com.teraenergy.common.file.service.CommonAttachFileService;
import com.teraenergy.common.vo.CommonCodeVO;
import com.teraenergy.groupware.commonCode.service.CommonCodeService;
import com.teraenergy.groupware.personnel.mapper.PersonnelMapper;
import com.teraenergy.groupware.personnel.service.PersonnelService;
import com.teraenergy.groupware.personnel.vo.PlaceVO;
import com.teraenergy.groupware.personnel.vo.ProjectManageProjectinfoVO;
import com.teraenergy.groupware.personnel.vo.SkillInventoryCareerVO;

@Controller
public class PersonnelController {

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

	// 인사 카드 화면 이동
	@GetMapping(value = "/papyrus/personnel")
	public String personnel(HttpSession session, Model model, @RequestParam Map<String, Object> map) throws Exception {

		// 회원 정보
		Map<String, Object> userMap = new HashMap<String, Object>();
		Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");

		if (map.get("userId") == null || map.get("userId") == "") {
			userMap.put("userId", userInfoMap.get("userId"));
		} else {
			userMap.put("userId", map.get("userId"));
		}
		Map<String, Object> personnel = personnelService.selectUserInfo(userMap);
		model.addAttribute("personnel", personnel);

		// 부서 목록 & 인원 카운트
		Map<String, String> deptInfoMap = new HashMap<String, String>();
		deptInfoMap.put("codeValue", "dept");
		List<Map<String, String>> listDeptInfo = personnelService.listDept(deptInfoMap);
		model.addAttribute("listDeptInfo", listDeptInfo);

		// 부서 [공통코드]
		Map<String, Object> deptMap = new HashMap<String, Object>();
		deptMap.put("codeValue", "dept");
		List<Map<String, Object>> listDept = commonCodeService.listCommonCode(deptMap);

		model.addAttribute("listDept", listDept);
		// 직급 [공통코드]
		Map<String, Object> positionMap = new HashMap<String, Object>();
		positionMap.put("codeValue", "position");
		List<Map<String, Object>> listPosition = commonCodeService.listCommonCode(positionMap);
		model.addAttribute("listPosition", listPosition);
		// 직책 [공통코드]
		Map<String, Object> dutyMap = new HashMap<String, Object>();
		dutyMap.put("codeValue", "duty");
		List<Map<String, Object>> listDuty = commonCodeService.listCommonCode(dutyMap);
		model.addAttribute("listDuty", listDuty);
		// 업무 위치 [공통코드]
		Map<String, Object> placeMap = new HashMap<String, Object>();
		placeMap.put("codeValue", "place");
		List<Map<String, Object>> listPlace = commonCodeService.listCommonCode(placeMap);
		model.addAttribute("listPlace", listPlace);
		// 근로 형태 [공통코드]
		Map<String, Object> workTypeMap = new HashMap<String, Object>();
		workTypeMap.put("codeValue", "workType");
		List<Map<String, Object>> listWorkType = commonCodeService.listCommonCode(workTypeMap);
		model.addAttribute("listWorkType", listWorkType);
		// 상태 [공통코드]
		Map<String, Object> stateMap = new HashMap<String, Object>();
		stateMap.put("codeValue", "state");
		List<Map<String, Object>> listState = commonCodeService.listCommonCode(stateMap);
		model.addAttribute("listState", listState);
		// 등급 [공통코드]
		Map<String, Object> authMap = new HashMap<String, Object>();
		authMap.put("codeValue", "auth");
		List<Map<String, Object>> listAuth = commonCodeService.listCommonCode(authMap);
		model.addAttribute("listAuth", listAuth);
		
		model.addAttribute("menuCode", "006");

		/* 박종진 추가 */
		List<PlaceVO> placeList = personnelMapper.get_place_list();
		model.addAttribute("placeList", placeList);
		/* 박종진 추가 END */

		/* 소영추가 */
		Map<String, Object> sBasic = personnelService.skilInventoryBasicId(personnel);
		model.addAttribute("sBasic", sBasic);
		/* 소영추가 END */

		return "groupware/personnel/personnelInfo";
	}

	// 해당 부서 인원 목록 조회
	@PostMapping(value = "/papyrus/personnel/ajaxListDeptUser")
	@ResponseBody
	public List<Map<String, Object>> ajaxListDeptUser(@RequestParam Map<String, Object> para, HttpSession session)
			throws Exception {

		Map<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("department", para.get("department"));

		System.out.println(para.get("department"));
		if (para.get("workTypes") != null && para.get("workTypes") != "") {
			String[] item = ((String) para.get("workTypes")).split(",");
			List<String> list = new ArrayList<String>(Arrays.asList(item));
			reMap.put("workTypes", list);
		} else {
			reMap.put("workTypes", null);
		}
		System.out.println(reMap);
		// 해당 부서 인원 목록
		List<Map<String, Object>> listDeptUser = personnelService.listDeptUser(reMap);
		return listDeptUser;

	}

	// 인사카드 갱신
	@PostMapping(value = "/papyrus/personnel/ajaxUpdateUserInfo")
	@ResponseBody
	public void ajaxUpdateUserInfo(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		try {
			Map<String, Object> obj = (Map<String, Object>) session.getAttribute("userInfo");
			LocalDate now = LocalDate.now();

			para.put("updateId", obj.get("userId"));
			para.put("updateDt", now.toString());
			personnelService.updateUserInfo(para);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}

	// 인사카드 체크박스 기능(출퇴근, 스킬, 서류)
	@PostMapping(value = "/papyrus/personnel/ajaxUpdateChecked")
	@ResponseBody
	public void ajaxUpdateChecked(@RequestParam Map<String, Object> para, HttpSession session) throws Exception {
		try {
			Map<String, Object> obj = (Map<String, Object>) session.getAttribute("userInfo");

			personnelService.updateCheckedYn(para);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}

	// 파일 리스트
	@GetMapping(value = "/papyrus/personnel/userFilelist")
	@ResponseBody
	public Map<String, Object> userFilelist(HttpSession session, Model model, @RequestParam Map<String, Object> map)
			throws Exception {
		Map<String, Object> data = new HashMap<>();

		System.out.println("!@");
		System.out.println(map.get("userId"));
		int userId = Integer.parseInt(String.valueOf(map.get("userId")));
		CommonAttachFileModel commonAttachFileModel = new CommonAttachFileModel();
		commonAttachFileModel.setRefId(userId);
		commonAttachFileModel.setRefType("user");
		List<CommonAttachFileModel> fileList = commonAttachFileService.listFile(commonAttachFileModel);
		data.put("success", true);
		data.put("data", fileList);
		data.put("msg", "no_login_session");
		return data;
	}

	// 첨부서류 리스트
	@GetMapping(value = "/papyrus/personnel/etcFilelist")
	@ResponseBody
	public Map<String, Object> etcFilelist(HttpSession session, Model model, @RequestParam Map<String, Object> map)
			throws Exception {

		int userId = Integer.parseInt(String.valueOf(map.get("userId")));
		CommonAttachFileModel commonAttachFileModel = new CommonAttachFileModel();
		commonAttachFileModel.setRefId(userId);
		commonAttachFileModel.setRefType("user");
		List<CommonAttachFileModel> fileList = commonAttachFileService.etcFilelist(commonAttachFileModel);

		Map<String, Object> data = new HashMap<>();
		data.put("data", fileList);

		return data;
	}

	// 관리자 인사카드 작성할수 있는 숫자 생성
	@GetMapping(value = "/papyrus/register")
	public Map<String, Object> register(HttpSession session, @RequestParam Map<String, Object> map) throws Exception {

		int userNum = (int) (Math.random() * 999999999);
		map.put("userNum", userNum);
		map.put("userRegYn", "N");
		// insert

		Map<String, Object> data = new HashMap<>();
		data.put("data", "/papyrus/userRegister?userNum=" + userNum);
		return data;
	}

	// 사용자 인사카드 작성화면
	@GetMapping(value = "/papyrus/userRegister")
	public String userRegister(HttpSession session, HttpServletRequest request) throws Exception {

		System.out.println(request.getParameter("userNum"));
		return "groupware/personnel/register";
	}

	/* 박종진 추가 */
	@RequestMapping(value = "/papyrus/edit_skill_career", method = RequestMethod.POST)
	@ResponseBody
	public String edit_skill_career(SkillInventoryCareerVO skillInventoryCareerVO, Map<String, Object> param,
			Model model) throws Exception {
		int result = 0;
		try {
			if (skillInventoryCareerVO.getId() == null || "".equals(skillInventoryCareerVO.getId().trim())) {
				// insert
				result = personnelMapper.insert_skill_inventory_career(skillInventoryCareerVO);
			} else {
				// update
				result = personnelMapper.update_skill_inventory_career(skillInventoryCareerVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		return result + "";

	}

	@RequestMapping(value = "/papyrus/get_skill_table_data_by_userid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get_skill_table_data_by_userid(String userId, Model model) throws Exception {
		Map<String, Object> data = new HashMap<>();
		try {
			List<SkillInventoryCareerVO> skillList = personnelMapper.get_skill_career_by_userid(userId);
			data.put("success", true);
			data.put("data", skillList);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("success", false);
			return data;
		}
		return data;

	}

	@RequestMapping(value = "/papyrus/get_a_skill_data_by_id", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get_a_skill_data_by_id(String skillCareerId, Model model) throws Exception {
		Map<String, Object> data = new HashMap<>();
		try {
			SkillInventoryCareerVO skillCareer = personnelMapper.get_a_skill_career_by_id(skillCareerId);
			data.put("success", true);
			data.put("data", skillCareer);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("success", false);
			return data;
		}
		return data;

	}

	@RequestMapping(value = "/papyrus/get_project_list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get_project_list(Model model) throws Exception {
		Map<String, Object> data = new HashMap<>();
		try {
			List<ProjectManageProjectinfoVO> projectList = personnelMapper.get_project_list();
			data.put("success", true);
			data.put("data", projectList);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("success", false);
			return data;
		}
		return data;

	}
	
	@RequestMapping(value = "/papyrus/get_list_from_commoncode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get_list_from_commoncode(String codeTypeId,Model model) {
		Map<String, Object> data = new HashMap<>();
		try {
			List<CommonCodeVO> commonCodeList = personnelMapper.get_list_from_commoncode(codeTypeId);
			data.put("success", true);
			data.put("data", commonCodeList);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("success", false);
			return data;
		}
		return data;

	}
	
	@RequestMapping(value = "/papyrus/edit_project_mng_info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit_project_mng_info(ProjectManageProjectinfoVO projectManageProjectinfoVO,Model model) {
		Map<String, Object> data = new HashMap<>();
		try {
			personnelMapper.insert_project_mng_info(projectManageProjectinfoVO);
			data.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("success", false);
			return data;
		}
		return data;

	}
	
	@RequestMapping(value = "/papyrus/edit_commoncode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit_commoncode(CommonCodeVO commonCodeVO,Model model) {
		Map<String, Object> data = new HashMap<>();
		try {
			if(commonCodeVO.getCodeId()==null || "".equals(commonCodeVO.getCodeId().trim()) ) {
				//insert
				personnelMapper.insert_commoncode(commonCodeVO);
			}else {
				//update
				personnelMapper.update_commoncode(commonCodeVO);
			}
			data.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("success", false);
			return data;
		}
		return data;

	}
	
	@RequestMapping(value = "/papyrus/delete_skill_career", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_skill_career(String skillInvenCareerId,Model model) {
		Map<String, Object> data = new HashMap<>();
		try {
			personnelMapper.delete_skill_inven_career_by_id(skillInvenCareerId);
			data.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("success", false);
			return data;
		}
		return data;

	}
	@RequestMapping(value = "/papyrus/edit_project_info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit_project_info(ProjectManageProjectinfoVO projectManageProjectinfoVO,Model model) {
		Map<String, Object> data = new HashMap<>();
		try {
			personnelMapper.update_project_info(projectManageProjectinfoVO);
			data.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("success", false);
			return data;
		}
		return data;

	}
	
	/* 박종진 END */

	// 소영추가. 스킬 인벤토리 기본정보 입력
	@RequestMapping(value = "/papyrus/skilInventoryBasic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> skilInventoryBasic(@RequestParam Map<String, Object> param, Model model)
			throws Exception {
		System.out.println("===================skilInventoryBasic");
		System.out.println("===================param  :  " + param);

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = personnelService.skilInventoryBasic(param);
			model.addAttribute("sBasic", map);
			System.out.println("controller 성공인데;");
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "fail");
		}
		return result;

	}
	/* 소영추가 END */

	// 엑셀 다운로드
	@GetMapping(value = "/papyrus/pesonnel/downloadExcel", produces = "application/vnd.ms-excel")
	public void careerExcel(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String type = (String) map.get("type");
		// 회원 정보
		Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");
		Map<String, Object> personnel = personnelService.selectUserInfo(userInfoMap);

		Map<String, Object> careerInfo = personnelService.careerInfo(userInfoMap);
		List<Map<String, Object>> skillInfo = personnelService.skillInfo(userInfoMap);

		// 엑셀 다운로드 요청
		excelDownloadService.excelDownload(response, personnel, careerInfo, skillInfo, type);
	}

}