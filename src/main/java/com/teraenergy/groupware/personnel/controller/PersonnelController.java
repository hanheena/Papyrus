package com.teraenergy.groupware.personnel.controller;

import java.net.Inet4Address;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.teraenergy.common.config.EtcSetting;
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
import com.teraenergy.groupware.vacation.service.VacationService;

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
	
	@Autowired
	private VacationService vcationService;

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
		//인사카드 휴가 기본정보
		List<Map<String,Object>> list = vcationService.VacationList(personnel);
		model.addAttribute("vacationlist", list);	
		
		//인사카드 스킬인벤토리 기본정보
		Map<String, Object> sBasic = personnelService.skilInventoryBasicId(personnel);
		model.addAttribute("sBasic", sBasic);

		//인사카드 스킬인벤토리 경력정보
		List<Map<String, Object>> careerList = personnelService.skilInventoryCareerPrint(personnel);
		model.addAttribute("careerList", careerList);
		
		//인사카드 스킬인벤토리 경력정보 년월일수
		Map<String, Object> careerMonth = personnelService.careerMonth(personnel);
		model.addAttribute("careerMonth", careerMonth);

		/* 소영추가 END */
		
		/*이한솔 추가*/
		if(map.get("code") != null && map.get("code").equals("detail")) return "groupware/personnel/detail/personnelDetail";

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

	/*** 회원가입용 메일전송 및 신규가입처리 START (문재영) ***/
	// 메일전송 및 1차 회원가입(이름, 이메일 - insert)  
	@GetMapping(value = "/papyrus/mailSubmit")
	@ResponseBody
	public boolean sendMail(@RequestParam Map<String, String> map, HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		int userNum = (int)(Math.random() * 999999999);	//userNum 랜덤숫자	생성	
		String ipAddr = Inet4Address.getLocalHost().getHostAddress().toString();	// localAddress	
		
		String name = map.get("regName");	// 이름
		String email = map.get("regEmail");	// 이메일
		
		String userNumUrl;
		if(EtcSetting.ENVMODE.equals("local") || EtcSetting.ENVMODE.equals("staging") ) {
			userNumUrl = "http://" + ipAddr + ":8080/papyrus/userRegister/" + userNum;
		}else {
			Map<String, Object> result = new HashMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders header = new HttpHeaders();
			HttpEntity<?> entity = new HttpEntity<>(header);

			//api 주소
			String url = "https://api.ipify.org?format=json";

			// 이게 urlencode 까지 다해줌
			UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

			// 이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
			ResponseEntity<Map> resultMap =null;
			try {
				resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			result.put("statusCode", resultMap.getStatusCodeValue()); // http status code를 확인
			result.put("header", resultMap.getHeaders()); // 헤더 정보 확인
			result.put("body", resultMap.getBody()); // 실제 데이터 정보 확인
			
			/*
			 * Map<String,Object> 로 안받은 이유?? 몰?루
			 * ## lm: {cursor=null, success=true, users=[{avatar_url=null, department=null, display_name=박종진, id=7129012, identifications=[{type=email, value=najongjin3@hotmail.com}], mobiles=[], name=박종진, nickname=null, position=null, responsibility=null, space_id=178453, tels=[], vacation_end_time=null, vacation_start_time=null, work_end_time=null, work_start_time=null}, {avatar_url=null, department=null, display_name=문재영 (문재영), id=7131355, identifications=[{type=email, value=taijigom@gmail.com}], mobiles=[], name=문재영, nickname=문재영, position=null, responsibility=null, space_id=178453, tels=[], vacation_end_time=null, vacation_start_time=null, work_end_time=null, work_start_time=null}, {avatar_url=null, department=null, display_name=이소영 (이소영), id=7131356, identifications=[{type=email, value=dud9364@gmail.com}], mobiles=[], name=이소영, nickname=이소영, position=null, responsibility=null, space_id=178453, tels=[], vacation_end_time=null, vacation_start_time=null, work_end_time=null, work_start_time=null}]}
			 */
			LinkedHashMap lm = (LinkedHashMap) resultMap.getBody();
			String produAddr;
			try {
				produAddr=lm.get("ip").toString();
			} catch (Exception e) {
				produAddr=EtcSetting.PRODUCTION_ADDR;
			}
			
			userNumUrl= "http://"+produAddr+":"+EtcSetting.PRODUCTION_PORT+"/papyrus/userRegister/"+ userNum;
		}
		 

		String mailTitle = "테라에너지 'PAPYRUS' 회원가입 메일인증입니다.";
		String mailCont = name + " 님, 회원등록을 위해서 아래의 경로를 클릭해 주세요.\n" + userNumUrl;
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userNum", userNum);
		userMap.put("krName", name);
		userMap.put("email", email);
		personnelService.insertUserInfo(userMap);	// 인사카드생성 팝업 입력정보를 바탕으로 회원정보 DB insert

		personnelService.sendMail(email, mailTitle, mailCont);
		System.out.println("### 회원가입용 메일전송 완료!! ###");
		return true;
	}
	
	// 사용자 인사카드 작성화면 - 받은이메일에서 경로를 클릭하면 해당 컨트롤러를 실행 후, 리턴 된 경로값으로 이동
	@GetMapping(value = "/papyrus/userRegister/{userNum}")
	public String userRegister(@PathVariable("userNum") String userNum, HttpSession session, HttpServletRequest request, Model model) throws Exception {						
		Map<String, Object> userMap = personnelService.selectUserNum(userNum);
		model.addAttribute("userInfo", userMap);
		String userNumRst="";
		try {
			userNumRst = userMap.get("userNum").toString();
		} catch (Exception e) {
			userNumRst="";
		}
		
		// 랜덤숫자 비교
		if (userNum.equals(userNumRst)) {
			 personnelService.updateUserNumYN(userMap.get("userId").toString());	// user_num_yn 값을 'Y'로 변경
			return "groupware/personnel/register";
		} else	{ 		
			return "redirect:/papyrus/login";
		}		
	}
	
	// 2차 회원가입(id 및 나머지 정보 입력 - update) 
	@PostMapping(value = "/papyrus/personnel/ajaxUpdateUserReg")
	@ResponseBody
	public void ajaxUpdateUserReg(@RequestParam Map<String, Object> userMap, HttpSession session) throws Exception {
		try {
			LocalDate now = LocalDate.now();			
			userMap.put("updateDt", now.toString());

			personnelService.ajaxUpdateUserReg(userMap);
		} catch (Exception e) {
			System.out.println(e);
		}
	}	
	
	// 아이디 중복체크
	@PostMapping(value = "/loginIdChk")
	@ResponseBody
	public Map<String, Object> loginIdChk(String loginId) throws Exception {
		System.out.println("###loginIdChk###");
				
		int count = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		
		count = personnelService.loginIdChk(loginId);
		
		System.out.println("count ::: " + count);
		
		map.put("cnt", count);
		
		return map;
	}	
	/*** 회원가입용 메일전송 및 신규가입처리 END (문재영) ***/

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

		Map<String, Object> result = new HashMap<String, Object>();
		try {

			Map<String, Object> map = personnelService.skilInventoryBasic(param);
			model.addAttribute("sBasic", map);
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "fail");
		}
		return result;

	}

	// 경력스킬출력버튼 클릭
	@RequestMapping(value = "/papyrus/chkPrintBtn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chkPrintBtn(@RequestParam(value = "chk_data") List<String> chkData,
			HttpServletRequest request, Model model) throws Exception {
		//System.out.println("==========##### chkPrintBtnController ");
		//System.out.println("==========##### chkData: "+chkData);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = personnelService.chkPrintBtn(chkData);
			result.put("result", "success");
			result.put("chkCarrer", list);
			model.addAttribute("menuCode", "006");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "fail");
		}
		return result;

	}

	/* 소영추가 END */

	// 엑셀 다운로드 http://localhost:8080/papyrus/pesonnel/downloadExcels
	@RequestMapping(value = "/papyrus/pesonnel/downloadExcels", produces = "application/vnd.ms-excel", method = RequestMethod.POST)
	public void careerExcel(@RequestParam(value = "chk_data") String chkData, @RequestParam(value = "type") String type,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model)
			throws Exception {
		model.addAttribute("menuCode", "006");
		String[] result = chkData.split(",");
		List<String> data = Arrays.asList(result);

		// 회원 정보
		Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");
		Map<String, Object> personnel = personnelService.selectUserInfo(userInfoMap);

		try {
			Map<String, Object> careerInfo = personnelService.careerInfo(userInfoMap);
			List<Map<String, Object>> skillInfo = personnelService.chkPrintBtn(data); // check한 id값들
			// 엑셀 다운로드 요청
			excelDownloadService.excelDownload(response, personnel, careerInfo, skillInfo, type);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//양식다운로드 버튼
	@RequestMapping(value = "/papyrus/pesonnel/download", produces = "application/vnd.ms-excel", method = RequestMethod.GET)
	public void uploadExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model)
			throws Exception {

			// 회원 정보
			Map<String, Object> userInfoMap = (Map<String, Object>) session.getAttribute("userInfo");
			Map<String, Object> personnel = personnelService.selectUserInfo(userInfoMap);
			model.addAttribute("menuCode", "006");

		try {
			Map<String, Object> careerInfo = personnelService.careerInfo(userInfoMap);
			List<Map<String, Object>> skillInfoUpload = personnelService.skillInfoUpload(personnel); // 해당 id값
			// 엑셀 다운로드 요청
			excelDownloadService.downExcel(response, personnel, careerInfo, skillInfoUpload);
			System.out.println("############엑셀 다운로드");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	
	
}