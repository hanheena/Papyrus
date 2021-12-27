package com.teraenergy.groupware.workReport.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teraenergy.common.config.EtcSetting;
import com.teraenergy.common.vo.Page;
import com.teraenergy.groupware.workReport.service.WorkReportService;

@Controller
public class WorkReportController {
	private String filePath;
	
	@Autowired private WorkReportService workReportService;
	
	public String filePathRoute() {
		if("local".equals(EtcSetting.ENVMODE)) {
			return filePath=EtcSetting.LOCALFILEPATH;
		}else if("staging".equals(EtcSetting.STAGINGFILEPATH)) {
			return filePath=EtcSetting.STAGINGFILEPATH;
		}else {
			return filePath=EtcSetting.PRODUCTIONFILEPATH;
		}
	}

	public Map<String, Object> fileSearchSave(Map<String, Object> map) throws IOException {
		//System.out.println("## fileSearchSaveSaveSaveSave Map : " + map);
		String content = (String) map.get("content");
		String startWord = "src=\"/filepath/temp/";	//찾을 첫번째 단어
		String endWord = "\"";	//찾을 두번째 단어
		List<String> fileName = new ArrayList<String>();
		if(content.contains(startWord)) {
			int startIndex = content.indexOf(startWord);	//첫번째 인덱스
			int endIndex = content.indexOf(endWord, startIndex + 5);	//두번째 인덱스
			
			while(startIndex > 0 && endIndex > 0) {	//indexOf는 지정할 값을 찾을 수 없다면 -1를 반환
				String file = content.substring(startIndex + 20, endIndex);
				fileName.add(file);
				try {
					startIndex = content.indexOf(startWord, startIndex+ startWord.length());
					endIndex = content.indexOf(endWord, startIndex + 5);
				} catch (Exception e) {
					startIndex = -1;
					endIndex = -1;
				}//try
			}//while
		}//if
		
		//첨부된 파일을 임시적으로 저장했던 폴더 경로
		String temp_filePath = filePath + "/temp";
		//실질적으로 저장될 파일 경로
		String savedFilePath = filePath + "/filepath";
		//파일 복사하기
		byte[] buf = new byte[1024];
		FileInputStream fin = null;
		FileOutputStream fout = null;
		
		if(fileName.size() != 0) {
			for (int i = 0; i < fileName.size(); i++) {
				File tempFile = new File(temp_filePath + "/" + fileName.get(i));
				File saveFile = new File(savedFilePath + "/" + fileName.get(i));
				if(!tempFile.renameTo(saveFile)) {
					buf = new byte[1024];
					fin = new FileInputStream(tempFile);
					fout = new FileOutputStream(saveFile);
					
					int read = 0;
					while((read = fin.read(buf,0,buf.length)) != -1) {
						fout.write(buf,0,read);
					}//while
					fin.close();
					fout.close();
					tempFile.delete();	//기존 파일 삭제
				}//if
			}//for
		}//if
		
		File dir = new File(savedFilePath);
		if(!dir.exists()) dir.mkdirs();
		
		content = content.replaceAll(startWord, "src=\"/filepath/filepath/");
		map.put("content", content);
		return map;
	}
	//업무 버튼 클릭 시 리스트 페이지 
	@RequestMapping(value = "/papyrus/workReport/workReportList", method= {RequestMethod.GET, RequestMethod.POST})
	public String workReport(@RequestParam Map<String, Object> map, @RequestParam(defaultValue = "1") int curPage, 
			@RequestParam(defaultValue = "DAY,WEEK") String array, @RequestParam(defaultValue = "recent") String sort, Model model) throws Exception {
		if((String) map.get("row_per_page") == null || (String) map.get("row_per_page") == "") map.put("row_per_page", "10");
		//게시물 정렬 순서
		map.put("sort", sort);
		
		//전체 게시물 개수
		double WorkReportcnt = workReportService.workReportCnt(map);
		
		//페이지 처리
		Page page = new Page();
		page.setRow_per_page( Integer.parseInt((String) map.get("row_per_page")));	//한 페이지에 보여줄 게시물 개수 
		page.setCurPage(curPage);	//편재 페이지
		page.setWorkReportCnt(WorkReportcnt);	//전체 게시물 개수
		
		model.addAttribute("page", page);
		
		//리스트 처리
		map.put("array", array);
		map.put("curPage", curPage);
		map.put("page", page);
		
		List<Map<String, Object>> list = workReportService.workReportList(map);
		
		model.addAttribute("list", list);
		model.addAttribute("map", map);
		
		model.addAttribute("menuCode" , "002");
		return "groupware/workReport/workReportList";
	}
	
	//일일보고/주간보고 버튼 클릭 시 작성 화면으로 이동
	@RequestMapping(value = "/papyrus/workReport/workReportWrite", method= {RequestMethod.GET, RequestMethod.POST})
	public String workReportWrite(@RequestParam Map<String, Object> map, Model model, HttpSession session) throws Exception {
		// 로그인 session 값 불러오기		
		model.addAttribute("userInfo", session.getAttribute("userInfo"));
		map.put("userInfo", session.getAttribute("userInfo"));
		model.addAttribute("map", map);
		// 부서 불러오기
		model.addAttribute("dept", workReportService.getDept(map));
		
		model.addAttribute("menuCode" , "002");
		return "groupware/workReport/workReportWrite";
	}
	
	//일일보고/주간보고 작성 -> DB 저장
	@RequestMapping(value = "/papyrus/workReport/workReportSave", method= {RequestMethod.GET, RequestMethod.POST})
	public String wordReportSave(@RequestParam Map<String, Object> map, Model model) throws Exception {
		
		fileSearchSave(map);
		
		filePathRoute();
		
		workReportService.workReportSave(map);
		 
		model.addAttribute("map", map);
		
		return "redirect:/papyrus/workReport/workReportList";
	}
	
	
	//보고 게시글 상세보기
	@RequestMapping(value = "/papyrus/workReport/workReportDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public String workReportDetail(@RequestParam Map<String, Object> map, Model model) throws Exception {
		workReportService.workReportDetail(map);
		model.addAttribute("map", map);
		
		model.addAttribute("menuCode" , "002");
		
		return "groupware/workReport/workReportDetail";
	}
	
	//보고 게시글 수정 -> 화면 전환
	@RequestMapping(value = "/papyrus/workReport/workReportModify", method= {RequestMethod.GET, RequestMethod.POST})
	public String workReportModify(@RequestParam Map<String, Object> map, Model model, HttpSession session) throws Exception {
		map.put("userInfo", session.getAttribute("userInfo"));	//로그인한 유저 정보를 불러온다.
		workReportService.workReportDetail(map);
		model.addAttribute("map", map);
		
		model.addAttribute("menuCode" , "002");
		
		return "groupware/workReport/workReportModify";
	}
	
	//보고 게시글 수정 -> DB 반영
	@ResponseBody
	@RequestMapping(value = "/papyrus/workReport/workReportChange", method = {RequestMethod.GET, RequestMethod.POST})
	public boolean workReportChange(@RequestParam Map<String, Object> map, int id) throws Exception {
		
		//1. DB에 저장되어있는 content의 값을 가져온다.
		String dbContent = workReportService.getModifyReport(id);
		
		//1-2 content에 있는 파일 이름을 가져온다.
		String startWord = "src=\"/filepath/filepath/";	//찾을 첫번째 단어
		String endWord = "\"";	//찾을 두번째 단어
		//DB에 저장된 파일 이름을 저장할 리스트
		List<String> dbFileName = new ArrayList<String>();
		if(dbContent.contains(startWord)) {	//기존 DB content에 이미지 파일이 들어가있으면
			//파일 이름 가지고 오는지 확인 잘 해야함
			int startIndex = dbContent.indexOf(startWord);	//첫번째 인덱스
			int endIndex = dbContent.indexOf(endWord, startIndex + 9);	//두번째 인덱스
			
			while(startIndex > 0 && endIndex > 0) {
				String file = dbContent.substring(startIndex +24, endIndex);
				dbFileName.add(file);
				try {
					startIndex = dbContent.indexOf(startWord, startIndex + startWord.length());
					endIndex = dbContent.indexOf(endWord, startIndex+9);
				} catch (Exception e) {
					startIndex = -1;
					endIndex = -1;
				}//try
			}//while
		}//if
		//2. map에 저장된 content의 값을 가져온다.
		String content = (String) map.get("content");
		//2-1.두가지를 고려해야함 filepath/filepath 인지 filepath/temp 인지
		List<String> contentFileName  = new ArrayList<String>();	//수정된 글의 파일 리스트
		if(content.contains(startWord)) {	//기존에 있는 파일을 그대로 쓴 경우
			int startIndex = content.indexOf(startWord);
			int endIndex = content.indexOf(endWord, startIndex + 9);
			
			while(startIndex > 0 && endIndex > 0) {
				String file = content.substring(startIndex + 24, endIndex);
				contentFileName.add(file);
				try {
					startIndex = content.indexOf(startWord, startIndex + startWord.length());
					endIndex = content.indexOf(endWord, startIndex + 9);
				} catch (Exception e) {
					startIndex = -1;
					endIndex = -1;
				}
			}//while
		}
		//중복값 제거
		dbFileName.removeAll(contentFileName);
		//dbFileName에 있는 데이터 제거
		for(int i = 0; i < dbFileName.size(); i++) {
			String file = filePath + "/filepath/" + dbFileName.get(i);
			File delFile = new File(file);
			if(delFile.exists()) delFile.delete();
		}

		fileSearchSave(map);
		
		return workReportService.workReportChange(map);
	}


	// 보고 게시판 삭제
	@RequestMapping(value = "/papyrus/workReport/workReportDelete", method= {RequestMethod.GET, RequestMethod.POST})
	public String workReportDelete(@RequestParam Map<String, Object> map, HttpSession session) throws NumberFormatException, Exception {
		
		filePathRoute();
		
		//파일 삭제 먼저 들어가야함
		String content = workReportService.getModifyReport(Integer.parseInt((String) map.get("id")));
		String startWord = "src=\"/filepath/filepath/";	//찾을 첫번째 단어
		String endWord = "\"";	//찾을 두번째 단어
		
		List<String> fileName = new ArrayList<String>();
		if(content.contains(startWord)) {	//파일이 있다면 파일 삭제
			int startIndex = content.indexOf(startWord);
			int endIndex = content.indexOf(endWord, startIndex + 9);
			
			while(startIndex > 0 && endIndex > 0) {
				String file = content.substring(startIndex+24, endIndex);
				fileName.add(file);
				try {
					startIndex = content.indexOf(startWord, startIndex + startWord.length());
					endIndex = content.indexOf(endWord, startIndex + 9);
				} catch (Exception e) {
					startIndex = -1;
					endIndex = -1;
				}
			}//while
		}//if
		//파일 삭제
		if(fileName.size() != 0) {
			for(int i = 0; i < fileName.size(); i ++) {
				String file = filePath + "/filepath/" + fileName.get(i);
				File delFile = new File(file);
				if(delFile.exists()) delFile.delete();
			}
		}
		
		map.put("userInfo", session.getAttribute("userInfo"));
		workReportService.workReportDelete(map);
		return "redirect:/papyrus/workReport/workReportList";
	}

	
	// Summernote 이미지 경로 저장
	@ResponseBody
	@RequestMapping(value="/papyrus/workReport/uploadSummernoteImg")
	public String summerimage(@RequestParam("file") MultipartFile img, HttpServletRequest request) throws IOException {
		
		filePathRoute();
		
		String real_filePath = filePath + "/temp";
		
		//파일 경로 확인 후 없으면 폴더 생성
		File dir = new File(real_filePath);
		if(!dir.exists()) dir.mkdirs();
		
		String tempName = UUID.randomUUID().toString() + "_" + img.getOriginalFilename();
		
		File file = new File(real_filePath, tempName);
		
		try {
			img.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = "/filepath/temp/" +tempName; 

		return url;
	}
}
