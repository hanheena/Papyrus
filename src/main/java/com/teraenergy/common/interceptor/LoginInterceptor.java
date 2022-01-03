package com.teraenergy.common.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userInfo") == null) {
			
			response.setContentType("text/html; charset=UTF-8");
			
			PrintWriter printwriter = response.getWriter();
			
			printwriter.println("<html>");
			printwriter.println("<head>");
			printwriter.println("</head>");
			printwriter.println("<body>");
			printwriter.println("<script>");
			printwriter.println("alert('로그인 후 이용해주세요.')");
			printwriter.println("location.href='/papyrus/'");
			printwriter.println("</script>");
			printwriter.println("</body>");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
			
			return false;
			
		} else {
			
			return true;
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		//postHandle(request, response, handler, modelAndView);
	}

}
