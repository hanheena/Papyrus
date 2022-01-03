package com.teraenergy.common.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.teraenergy.common.config.EtcSetting;
import com.teraenergy.common.interceptor.LoginInterceptor;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	
	 @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 String filePath="";
		// os환경에 따른 디렉터리 경로 설정 기능
			if ("local".equals(EtcSetting.ENVMODE)) {
				filePath = EtcSetting.LOCALFILEPATH;
			} else if ("staging".equals(EtcSetting.STAGINGFILEPATH)) {
				filePath = EtcSetting.STAGINGFILEPATH;
			} else {
				filePath = EtcSetting.PRODUCTIONFILEPATH;
			}
			
			
	 registry .addResourceHandler("/"+EtcSetting.STATIC_URI_NAME+"/**")
	 .addResourceLocations("file:///"+filePath+"/");
	 }
	 
	
	/* 인터셉터 설정 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/papyrus/dashboard")
				.addPathPatterns("/papyrus/dashboard/*").addPathPatterns("/papyrus/personnel")
				.addPathPatterns("/papyrus/commonCode").addPathPatterns("/papyrus/workReport")
				.addPathPatterns("/papyrus/workReport/*");

	}
}