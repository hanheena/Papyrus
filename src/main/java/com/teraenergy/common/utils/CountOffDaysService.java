package com.teraenergy.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teraenergy.common.vo.HoliDayApiVO;

@Service
public class CountOffDaysService {
	

	// {"response":{"header":{"resultCode":"00","resultMsg":"NORMAL
	// SERVICE."},"body":{"items":{"item":[{"dateKind":"01","dateName":"추석","isHoliday":"Y","locdate":20210920,"seq":1},{"dateKind":"01","dateName":"추석","isHoliday":"Y","locdate":20210921,"seq":1},{"dateKind":"01","dateName":"추석","isHoliday":"Y","locdate":20210922,"seq":1}]},"numOfRows":10,"pageNo":1,"totalCount":3}}}
	public static int cntDaysOffsV2(String strStartDate, String strEndDate) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=krChd937teHPjSn2G7Dp2RdyYKj11wGszFLbTbXT9S%2BUXO7mbajOQSR5%2BHiVBX1AnRFrO3bo6Vb69ID9EnpXug%3D%3D"); 
		urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "="
				+ URLEncoder.encode(strStartDate.split("-")[0], "UTF-8")); /* 연 */
		urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "="
				+ URLEncoder.encode(strStartDate.split("-")[1], "UTF-8")); /* 월 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 월 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		String strJson = sb.toString();

		int cntHoliday = 0;
		List<HoliDayApiVO> holiDayList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> mapWhole = mapper.readValue(strJson, Map.class);
		Map<String, Object> mapResponse = (Map<String, Object>) mapWhole.get("response");
		Map<String, Object> mapBody = (Map<String, Object>) mapResponse.get("body");
		Object objItems=mapBody.get("items");
		if("".equals(objItems.toString().trim())) {
			return 0;
		}
		Map<String, Object> mapItems = (Map<String, Object>) mapBody.get("items");
		if (mapItems.toString().contains("[") && mapItems.toString().contains("]")) {
			List<Map<String, Object>> mapItem = (List<Map<String, Object>>) mapItems.get("item");
			// ## mapItem: [{dateKind=01, dateName=추석, isHoliday=Y, locdate=20210920,
			// seq=1},
			for (Map<String, Object> map : mapItem) {
				/*
				 * HoliDayApiVO holiDayApiVO=new HoliDayApiVO();
				 * holiDayApiVO.setDateKind(map.get("dateKind").toString());
				 * holiDayApiVO.setDateName(map.get("dateName").toString());
				 * holiDayApiVO.setIsHoliday(map.get("isHoliday").toString());
				 * holiDayApiVO.setLocdate(map.get("locdate").toString());
				 * holiDayApiVO.setSeq(map.get("seq").toString());
				 * holiDayList.add(holiDayApiVO);
				 */
				String locdate = map.get("locdate").toString();
				System.out.println("## locdate: " + locdate);
				String strDate = locdate.substring(0, 4) + "-" + locdate.subSequence(4, 6) + "-"
						+ locdate.subSequence(6, 8);
				int startdateCompare = strDate.compareToIgnoreCase(strStartDate);
				int enddateCompare = strDate.compareToIgnoreCase(strEndDate);
				Boolean isWeekend = isWeekend(strDate);
				if (!isWeekend && startdateCompare > -1 && enddateCompare<1) {
					cntHoliday++;
				}
			}
		} else {
			Map<String, Object> mapItem = (Map<String, Object>) mapItems.get("item");
			String locdate = mapItem.get("locdate").toString();
			System.out.println("## locdate in else: "+locdate);
			String strDate = locdate.substring(0, 4) + "-" + locdate.subSequence(4, 6) + "-"
					+ locdate.subSequence(6, 8);
			int startdateCompare = strDate.compareToIgnoreCase(strStartDate);
			int enddateCompare = strDate.compareToIgnoreCase(strEndDate);
			Boolean isWeekend = isWeekend(strDate);
			if (!isWeekend && startdateCompare > -1 && enddateCompare<1) {
				cntHoliday++;
			}
		}

		return cntHoliday;
	}

	public static int detectWeekends(String strStartDate, String strEndDate, List<HoliDayApiVO> holiDayList) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = formatter.parse(strStartDate);
			Date d2 = formatter.parse(strEndDate);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);

			Calendar c2 = Calendar.getInstance();
			c2.setTime(d2);

			int sundays = 0;
			int saturday = 0;

			while (!c1.after(c2)) {
				System.out.println("## c1: " + c1.toString());
				if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
					saturday++;
				}
				if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					sundays++;
				}

				c1.add(Calendar.DATE, 1);
			}

			return saturday + sundays;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("!!! detectWeekends exception: " + e.getMessage());
			return -999;
		}

	}

	public static int compareStr(String strStartDate, String strEndDate) {
		return strStartDate.compareTo(strEndDate);
	}

	public static boolean isWeekend(String strDate) {
		LocalDate date = LocalDate.parse(strDate, DateTimeFormatter.ISO_DATE);
		DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
		return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
	}

}
