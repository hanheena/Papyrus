package com.teraenergy.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleCodeController {

	/* 베이스 레이아웃 페이지 */
	@GetMapping(value = "/papyrus/sample_basePage")
	public String basePage() {
		return "common/sampleCode/basePage";
	}
	
	/* 풀캘린더 샘플 페이지 */
	@GetMapping(value = "/papyrus/sample_fullCalender")
	public String sample_fullCalender() {
		return "common/sampleCode/fullcalender/fullcalender";
	}
	
	/* 날씨 api 샘플 페이지 */
	@GetMapping(value = "/papyrus/sample_weather")
	public String sample_weather() {
		return "common/sampleCode/api_weather/api_weather";
	}
	
	/* 투야 api 샘플 페이지 */
	@GetMapping(value = "/papyrus/sample_tuya")
	public String sample_tuya() {
		return "common/sampleCode/api_tuya/api_tuya";
	}
}