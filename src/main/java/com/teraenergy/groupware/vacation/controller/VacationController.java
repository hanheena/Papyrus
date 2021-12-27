package com.teraenergy.groupware.vacation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teraenergy.common.utils.UserMapToUserVO;
import com.teraenergy.common.vo.UserVO;
import com.teraenergy.groupware.elecapproval.mapper.ElectApprovMapper;
import com.teraenergy.groupware.elecapproval.service.ElectApprovService;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovCategoryVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovLineVO;
import com.teraenergy.groupware.elecapproval.vo.ElectApprovVO;
import com.teraenergy.groupware.vacation.mapper.VacationMapper;
import com.teraenergy.groupware.vacation.service.VacationService;

@Controller
public class VacationController {

	@Autowired
	private VacationService vcationService;
	@Autowired
	private ElectApprovMapper electApprovMapper;
	@Autowired
	private ElectApprovService electAppService;
	@Autowired
	private VacationMapper vacationMapper;


	  // 휴가신청 페이지
	   @RequestMapping(value = "/papyrus/dayOff")
	   public String dayOff(@RequestParam(defaultValue = "-1") int categoryId,@RequestParam(defaultValue = "-1") int electapprovId, String formTableName, HttpServletRequest request,
				HttpServletResponse response, Model model) throws Exception {
		 
			HttpSession session = request.getSession();
			Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
			if (mapUserInfo == null) {
				return "redirect:/papyrus/login";
			}
			
			UserVO userId = UserMapToUserVO.userMaptoUserVO(mapUserInfo);
			
								
			//ElectApprovCategoryVO electApprovCategoryVO = electApprovMapper.getACategoryById(categoryId);
			ElectApprovCategoryVO electApprovCategoryVO = electApprovMapper.getACategoryById(1);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			String strNowDate = dateFormat.format(new Date());

			
			ElectApprovVO electApprov = null;
			if (electapprovId < 1) {
				electApprov = new ElectApprovVO();
				electApprov.setStrNowDate(strNowDate);
				electApprov.setFormTableName(formTableName);
				electApprov.setApprovId(1);
				electApprov.setStatus("1");
				electApprov.setFormTableName("day_off_mng");	
			} else {
				
				electApprov=electAppService.getElectApprovWithFormInfos(electapprovId,userId.getUserId(),formTableName);
			}
				 

			List<UserVO> refUserList = electApprovMapper.getRefUsers(electApprov.getId());
			List<ElectApprovLineVO> elctApprovLineList = electApprovMapper.getLineUsers(electApprov);

			model.addAttribute("electApprov", electApprov);
			model.addAttribute("userInfo", userId);
			model.addAttribute("electApprovCategoryVO", electApprovCategoryVO);
			model.addAttribute("refUserList", refUserList);
			model.addAttribute("elctApprovLineList", elctApprovLineList);
			
			model.addAttribute("menuCode" , "004");
	      
	      return "groupware/vacation/dayOff";
	      
	   }
	   

	   
	   //휴가신청
	   @RequestMapping(value = "/papyrus/dayOffWrite",method = RequestMethod.POST) 
	   @ResponseBody 
	   public  Map<String, Object> dayOffWrite(ElectApprovVO electApprov,
				@RequestParam(name = "refUserId", required = false) List<String> refUserId,
				ElectApprovLineVO electApprovLineVO, HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam Map<String, Object> reqParam) throws Exception {
		   
				Map<String, Object> result = new HashMap<String, Object>();
			   	   
		   		System.out.println("================ dayOffWriteController");
		   		System.out.println("================ refUserId  :  "+ refUserId);
		   		System.out.println("================ reqParam  :  " + reqParam);
		   		System.out.println("================ electApprov  :  " + electApprov);
		 
				HttpSession session = request.getSession();
				Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");				
				UserVO userId = UserMapToUserVO.userMaptoUserVO(mapUserInfo);
							
				System.out.println("================ userId  :  " + userId);
				
				int use = userId.getUserId();  	  
				Map<String, Object> eId;
				if (electApprov.getId() < 1) {
					 electAppService.insertElectApprovService(electApprov, refUserId, electApprovLineVO);
					 eId = vcationService.electApproId(use);	
					 reqParam.put("electApprovId", eId.get("electApprovId"));
				} else {
					electAppService.updateElectApprovService(electApprov, refUserId, electApprovLineVO);
					eId = vcationService.electApproId(use);
					 System.out.println(eId);
					 reqParam.put("electApprovId", eId.get("electApprovId"));					
				}
				
				
				try { 			
					 vcationService.dayOffWrite(reqParam);
				      result.put("result", "success");
				      System.out.println("======dayOffWrite 성공");
			      
			      }catch(Exception e) {
			    	  e.printStackTrace();
			    	  result.put("result","fail");
			      }
			      return result;
	   }
	   
   
	
		  // 휴가신청 리스트
	   @RequestMapping(value = "/papyrus/dayOffList",method = RequestMethod.GET)
	   public String dayOffList(@RequestParam(value = "status", defaultValue = "1") String status, Model model,HttpServletRequest request ) throws Exception {
			HttpSession session = request.getSession();
			Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
			if (mapUserInfo == null) {
				return "redirect:/papyrus/login";
			}
			
			UserVO userId = UserMapToUserVO.userMaptoUserVO(mapUserInfo);
			List<Map<String,Object>> list = vcationService.VacationList(userId);
			model.addAttribute("vacationlist", list);	
			
			System.out.println("=====list"+list);
			
			ElectApprovVO electApprov = new ElectApprovVO();
			electApprov.setUserId(userId.getUserId());
			electApprov.setStatus(status);
           
			List<ElectApprovVO> electApprovList = electApprovMapper.getAllElectApprovByStatus(electApprov);
			model.addAttribute("userInfo", userId);
			model.addAttribute("electApprovList", electApprovList);
			model.addAttribute("status", status);
		
			model.addAttribute("menuCode" , "004");
		
			return "groupware/vacation/dayOffList";    
					
	   }

		  // 휴가승인 리스트
	   @RequestMapping(value = "/papyrus/dayOffListApprove",method = RequestMethod.GET)
	   public String dayOffListApprove( Model model,HttpServletRequest request ) throws Exception {
			
			int userId = Integer.parseInt( request.getParameter("userId"));
			List<Map<String,Object>> list = vcationService.dayOffListApprove(userId);
			
			model.addAttribute("vacationlist", list);	
			 
			 return "groupware/vacation/dayOffList";
		      

	   }
	   
		// 휴가신청 상세페이지
	    @RequestMapping(value="/papyrus/dayOffDetail",method = RequestMethod.GET)
	    public String dayOffDetail(@RequestParam(defaultValue = "-1") int id,HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
	    	HttpSession session = request.getSession();
			Map<String, Object> mapUserInfo = (Map<String, Object>) session.getAttribute("userInfo");
			if (mapUserInfo == null) {
				return "redirect:/papyrus/login";
			}

			String formTableName = "day_off_mng";	
		 	UserVO userId = UserMapToUserVO.userMaptoUserVO(mapUserInfo);
			
			int dayOffid =Integer.parseInt( request.getParameter("id"));
			Map<String, Object> detail = vcationService.electApprovDetail(dayOffid);
			int eAId = (int) detail.get("id");
			
			//System.out.println("=============== dayOffid :"+id+"=============== eAId :"+eAId+"====detail"+detail);	
			
			ElectApprovVO electApprovVO = vcationService.selectDayOffDetail(eAId , userId.getUserId(),formTableName);

			//System.out.println("=============== electApprovVO :"+electApprovVO);	
			
			List<UserVO> refUserList =  vacationMapper.getRefUsers_for_detail_page(electApprovVO.getId());
			List<ElectApprovLineVO> elctApprovLineList = vacationMapper.getLineUsers(electApprovVO);
		 
			model.addAttribute("electApprovVO", electApprovVO);
			model.addAttribute("refUserList", refUserList);
			model.addAttribute("elctApprovLineList", elctApprovLineList);
			model.addAttribute("userInfo", userId);
	        
			Map<String, Object> dayOff = vcationService.dayOffDetail(id);   
	    	model.addAttribute("dayOff", dayOff);	

	    	return "groupware/vacation/dayOffDetail";
	    }
	    
	    
	    
		//휴가신청 승인된 상세페이지
	    @RequestMapping(value="/papyrus/dayOffDetailApprove",method = RequestMethod.GET)
	    public String dayOffDetailApprove(HttpServletRequest request , Model model) throws Exception {
	    	int id = Integer.parseInt( request.getParameter("id"));
	    
	    	Map<String, Object> dayOff = vcationService.dayOffDetail(id);

	    	model.addAttribute("dayOff", dayOff);	
	       
	        return "groupware/vacation/dayOffDetailApprove";    
	    }
	    
		   //휴가 승인  (승잉="Y", 반려="N",대기중="W")
		   @RequestMapping(value = "/papyrus/approve",method = RequestMethod.POST)
		   @ResponseBody 
		   public  Map<String, Object> approve(HttpServletRequest request ,@RequestParam Map<String, Object> reqParam,Model model) throws Exception {
			   //	System.out.println("========entered approve");
			   	//System.out.println("========reqParam : "+reqParam);
			   	  Map<String, Object> result = new HashMap<String, Object>();

			      try {
			      
			      vcationService.approve(reqParam);
			      result.put("result", "success");
			     // System.out.println("======approve");
			      
			      }catch(Exception e) {
			    	  e.printStackTrace();
			    	  result.put("result","fail");
			      }
			      return result;
		   }
		   
		   //휴가 반려  (승잉="Y", 반려="N",대기중="W")
		   @RequestMapping(value = "/papyrus/companion",method = RequestMethod.POST)
		   @ResponseBody 
		   public  Map<String, Object> Companion(HttpServletRequest request ,@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
			   		//System.out.println("========entered companion");
			   		//System.out.println("========reqParam : "+reqParam);
			   	  Map<String, Object> result = new HashMap<String, Object>();

			      try {     
			      vcationService.companion(reqParam);
			      result.put("result", "success");
			     // System.out.println("======Companion");    
			      }catch(Exception e) {
			    	  e.printStackTrace();
			    	  result.put("result","fail");
			      }
			      return result;
		   }
		   
		   //휴가 취소
		   @RequestMapping(value = "/papyrus/cancel",method = RequestMethod.POST)
		   @ResponseBody 
		   public  Map<String, Object> cancel(HttpServletRequest request ,@RequestParam Map<String, Object> reqParam, Model model) throws Exception {
		   		//System.out.println("========entered cancel");
		   		//System.out.println("========reqParam : "+reqParam);
			   	  Map<String, Object> result = new HashMap<String, Object>();

			      try {			      
			      vcationService.cancel(reqParam);
			      result.put("result", "success");
			     // System.out.println("======cancel");     
			      }catch(Exception e) {
			    	  e.printStackTrace();
			    	  result.put("result","fail");
			      }
			      return result;
		   }
	   

	
	



}
