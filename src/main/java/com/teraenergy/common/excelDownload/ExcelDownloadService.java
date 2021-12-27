package com.teraenergy.common.excelDownload;

import org.springframework.stereotype.Service;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service
public class ExcelDownloadService {
	public void excelDownload(HttpServletResponse response, Map<String, Object> personnel,
			Map<String, Object> careerInfo, List<Map<String, Object>> skillInfo, String type) throws Exception {
		OutputStream outs = response.getOutputStream();
		//다운로드 될 파일 이름, 인코딩(한글 안 깨지게)
		String fileName = "";
		if(type.equals("career")) {
			fileName = personnel.get("krName") + "_경력사항";
		}else if(type.equals("skill")) {
			fileName = personnel.get("krName") + "_스킬_인벤토리";
		}
		fileName = URLEncoder.encode(fileName, "UTF-8").replace("\\+", "");
		
		//엑셀 다운로드 요청
		try {
			XSSFWorkbook workbook = null;	//xlsx 확장자를 가진 엑셀 파일로 다운로드
			workbook =  excelDownloadXSSF(personnel, careerInfo, skillInfo, type);
			//컨텐츠 타입과 파일명 지정
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
			
			workbook.write(outs);
		} catch (Exception e) {
			e.printStackTrace();
			
			response.reset();
			PrintWriter pout = response.getWriter();
			pout.println("<script type=\"text/javascript\">");
            pout.println("alert('[IOException] 엑셀 다운로드 도중 오류가 발생했습니다.\n시스템 관리자에게 문의 바랍니다.');history.go(-1);");
            pout.println("</script>");
            pout.flush();


		}finally {
        	//엑셀 다운로드 종료
        	if(outs != null) outs.close();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }  
	}
	
	//엑셀 다운로드 구현
	@SuppressWarnings("deprecation")
	private XSSFWorkbook excelDownloadXSSF(Map<String, Object> personnel, 
			Map<String, Object> careerInfo, List<Map<String, Object>> skillInfo,String type) throws Exception {
		//엑셀 다운로드 시작
		XSSFWorkbook workbook = new XSSFWorkbook();
		//엑셀 시트명
		Sheet sheet = workbook.createSheet((String) personnel.get("krName"));
		//행, 열
		Row row = null;
		Cell cell = null;
		int rowCnt = 0;
		int cellCnt = 0;
			
		//스타일 지정
		//1. 헤더 셀 스타일 지정 : 투입인력 경력 사항 등
		CellStyle styleOfHeader = workbook.createCellStyle();
		styleOfHeader.setAlignment(HorizontalAlignment.CENTER);	//가운데 정렬
		styleOfHeader.setVerticalAlignment(VerticalAlignment.CENTER);	//높이 가운데 정렬
		
		//폰트 설정
		Font fontOfHeader = workbook.createFont();
		fontOfHeader.setFontHeight((short) (20*20)); //폰트 사이즈
		fontOfHeader.setBold(true);	//굵게
		styleOfHeader.setFont(fontOfHeader);
		
		/*타이틀 스타일*/
		CellStyle styleOfTitle = workbook.createCellStyle();
		styleOfTitle.setAlignment(HorizontalAlignment.CENTER);	//가운데 정렬
		styleOfTitle.setVerticalAlignment(VerticalAlignment.CENTER);	//높이 가운데 정렬
		styleOfTitle.setWrapText(true);
		//배경색 지정
		styleOfTitle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		styleOfTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//테두리 선 (상하좌우)
		styleOfTitle.setBorderTop(BorderStyle.THIN);
		styleOfTitle.setBorderBottom(BorderStyle.THIN);
		styleOfTitle.setBorderLeft(BorderStyle.THIN);
		styleOfTitle.setBorderRight(BorderStyle.THIN);
		
		/*일반 텍스트*/
		CellStyle styleOfText = workbook.createCellStyle();
		styleOfText.setAlignment(HorizontalAlignment.CENTER);	//가운데 정렬
		styleOfText.setVerticalAlignment(VerticalAlignment.CENTER); //높이 가운데 정렬
		styleOfText.setWrapText(true);	//자동 줄바꿈처리
		//테두리 선 (상하좌우)
		styleOfText.setBorderTop(BorderStyle.THIN);
		styleOfText.setBorderBottom(BorderStyle.THIN);
		styleOfText.setBorderLeft(BorderStyle.THIN);
		styleOfText.setBorderRight(BorderStyle.THIN);
		//폰트 설정
		Font fontOfText = workbook.createFont();
		fontOfText.setFontName("맑은 고딕");
		fontOfText.setFontHeight((short) (10*20));
		styleOfText.setFont(fontOfText);
		
		if(type.equals("career")) {
			//헤더 : 투입인력 경력사항
			//셀 병합
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
			row = sheet.createRow(rowCnt++);
			row.setHeight((short)1600);
			cell = row.createCell(cellCnt++);
			cell.setCellStyle(styleOfHeader);	//헤더 스타일 적용
			cell.setCellValue("투입인력 경력사항");
			
			//2번째 줄 작성
			row = sheet.createRow(rowCnt++);
			row.setHeight((short)700);
			cellCnt = 0;	//셀 초기화
			for (int i = cellCnt; i <= 7; i++) {
				cell = row.createCell(i);
				if(i % 2 == 0) {
					cell.setCellStyle(styleOfTitle);
					if(i == 0) {
						cell.setCellValue("성명");
						sheet.setColumnWidth(0, 4000);
					}
					if(i == 2) cell.setCellValue("소속");
					if(i == 4) {
						cell.setCellValue("직위");
						sheet.setColumnWidth(4, 3600);
					}
					if(i == 6) cell.setCellValue("생년월일");
				}else if(i % 2 != 0) {
					cell.setCellStyle(styleOfText);
					if(i == 1) cell.setCellValue(personnel.get("krName").toString());
					if(i == 3) {
						cell.setCellValue("주식회사\n테라에너지");
						sheet.setColumnWidth(3, 3300);
					}
					if(i == 5) cell.setCellValue(personnel.get("positionName").toString());
					if(i == 7) {
						if(personnel.get("birthDt") != null) cell.setCellValue(personnel.get("birthDt").toString() );
					}
				}
			}//for
			
			//세번째출 ~ 여섯번째 줄 셋 병합이 똑같음 -> for문으로 병합
			for(int i = rowCnt; i <=5; i ++) {
				row = sheet.createRow(rowCnt++);
				row.setHeight((short) 500);
				sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));
				sheet.addMergedRegion(new CellRangeAddress(i,i,4,7));
				
				if(i % 2 == 0) {	//세번째 줄, 다섯번째 줄
					for (int j = 0; j < 8; j++) {
						cell = row.createCell(j);
						cell.setCellStyle(styleOfTitle);
						if(i == 2) {
							if(j == 0) cell.setCellValue("최종학력(학교)");
							if(j == 3) cell.setCellValue("졸업 연도");
							if(j == 4) cell.setCellValue("전 공");
						}else if(i == 4) {
							if(j == 0) cell.setCellValue("자격증 종류");
							if(j == 3) cell.setCellValue("취득연도");
							if(j == 4) cell.setCellValue("기 타");
						}
					}
				}else if(i % 2 !=0) {	//네번째 줄, 여섯번째줄
					for (int j = 0; j < 8; j++) {
						cell = row.createCell(j);
						cell.setCellStyle(styleOfText);
						if(i == 3) {
							if(j == 0) cell.setCellValue( careerInfo.get("final_school").toString() );
							if(j == 3) cell.setCellValue( careerInfo.get("grad_dt").toString() );
							if(j == 4) cell.setCellValue(careerInfo.get("major").toString());
						}else if(i == 5) {
							if(careerInfo.get("certificate_kind").toString() != null ) {
								if(j == 0) cell.setCellValue( careerInfo.get("certificate_kind").toString() );	
								if(j == 3) cell.setCellValue( careerInfo.get("certificate_dt").toString() );
							}
							if(careerInfo.get("etc").toString() != null) {
								if(j == 4) cell.setCellValue( careerInfo.get("etc").toString() );
							}
						}
					}//for
				}//if
			}//for
			
			//7번째 줄
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 3, 7));
			row = sheet.createRow(rowCnt++);
			row.setHeight((short) 500);
			for (int i = 0; i < 3; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(styleOfTitle);
				if(i == 0) cell.setCellValue("기술등급");
			}
			cell = row.createCell(3);
			for(int i = 3; i < 8; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(styleOfText);
				if(i ==3) {
					if( careerInfo.get("techlvl").toString() != null) cell.setCellValue( careerInfo.get("techlvl").toString() );
				}
			}
			
			//8번째 줄
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 7));
			row = sheet.createRow(rowCnt++);
			row.setHeight((short) 500);
			for(int i = 0; i < 8; i ++) {
				cell = row.createCell(i);
				cell.setCellStyle(styleOfTitle);
				if(i == 0) cell.setCellValue("경 력 사 항");
			}
			
			//9번째 줄 
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 3, 7));
			row = sheet.createRow(rowCnt++);
			row.setHeight((short) 500);
			for (int i = 0; i < 3; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(styleOfTitle);
				if(i == 0) cell.setCellValue("해당분야 업무경력");
			}
			cell = row.createCell(3);
			for(int i = 3; i < 8; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(styleOfText);
				if(i ==3) {
					//지금까지 했던 경력 모두가 더해져서 ~~년 ~~ 개월 로 표시되어야함
					int sum = 0;
					for (int j = 0; j < skillInfo.size(); j++) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date startDate =  format.parse((String) skillInfo.get(j).get("work_start_dt"));
						Date endDate = format.parse((String) skillInfo.get(j).get("work_end_dt"));
						
						int startMonth = (startDate.getYear()*12) + startDate.getMonth();
						int endMonth = (endDate.getYear()*12) + endDate.getMonth();
						
						sum +=  endMonth - startMonth;
					}
					
					int year = (int)sum/12;
					int month = (sum % 12) + 1;
					if(month == 12) {
						year += year;
						month = 0;
					}
					
					cell.setCellValue( careerInfo.get("work_start_dt").toString() + " ~ " + careerInfo.get("work_end_dt").toString() 
							+ " ("+ year + " 년 "+ month + " 개월)");
				}
			}
			
			
			//10번째줄 >> 셀 병합
			for (int i = 0; i < 8; i++) {
				if(i == 4) {
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, i, i+1));
				}else if(i == 5){
				}else {
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt+1, i, i));
				}
			}//for	
			for(int i = 0; i <2; i++) {
				row = sheet.createRow(rowCnt++);
				row.setHeight((short) 1000);
				for (int j = 0; j < 8; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(styleOfTitle);
					if(j==0) cell.setCellValue("사업명\n(해당분야\n관련업무)");
					if(j==1) cell.setCellValue("사업기간");
					if(j==2) cell.setCellValue("담당 업무");
					if(j==3) cell.setCellValue("참여 기간");
					if(j==4) cell.setCellValue("참여 당시");
					if(j==6) cell.setCellValue("발주처");
					if(j==7) cell.setCellValue("개발\n상세내역\n(OS,\nDBMS,\n개발언어 등)");
					if(i == 1) {
						if(j ==4) cell.setCellValue("소속회사");
						if(j ==5) cell.setCellValue("직위");
					}
				}//for
			}//if
			
			//11번째 줄 > 리스트 
			for (int i = 0; i < skillInfo.size(); i++) {
				row = sheet.createRow(rowCnt++);
				row.setHeight((short) 800);
				for (int j = 0; j < 8; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(styleOfText);
					if(j == 0) cell.setCellValue( skillInfo.get(i).get("work_name").toString() );
					if(j == 1) cell.setCellValue( skillInfo.get(i).get("work_start_dt") + "\n ~ \n" + skillInfo.get(i).get("work_end_dt") );
					if(j == 2) cell.setCellValue( skillInfo.get(i).get("role").toString());
					if(j == 3) cell.setCellValue( skillInfo.get(i).get("work_start_dt") + "\n ~ \n" + skillInfo.get(i).get("work_end_dt") );
					if(j == 4) cell.setCellValue( skillInfo.get(i).get("working_company").toString() );
					if(j == 5) cell.setCellValue( skillInfo.get(i).get("position").toString() );
					if(j == 6) cell.setCellValue( skillInfo.get(i).get("client_name").toString() );
					if(j == 7) cell.setCellValue( skillInfo.get(i).get("dev_specific").toString() );
				}
			}
			
		}else if(type.equals("skill")) {
			//헤더 : skill inventory
			//헤더 셀 별합
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,10));
			row = sheet.createRow(rowCnt++);
			row.setHeight((short)1600);
			cell = row.createCell(cellCnt);
			cell.setCellStyle(styleOfHeader);
			cell.setCellValue("SKILL INVENTORY");
			
			//업체명 / 성명 -> 두번째 줄
			sheet.addMergedRegion(new CellRangeAddress(1,1,0,3));
			sheet.addMergedRegion(new CellRangeAddress(1,1,4,10));
			row = sheet.createRow(rowCnt++);
			row.setHeight((short)400);
			cell = row.createCell(cellCnt);
			cell.setCellValue("업체명 : 주식회사 테라에너지");
			cell = row.createCell(cellCnt + 4);
			cell.setCellValue("성명 : " + personnel.get("krName"));
			
			//3~4번째 줄 : title >> 셀 병합
			for(int i = 0; i < 11; i++) {
				if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt+1, i, i));
				}else if(i == 5) {
					sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, i, i+5));
				}else {}
			}
			for(int i = 0; i < 2; i++) {
				row = sheet.createRow(rowCnt++);
				row.setHeight((short)600);
				for (int j = 0; j < 11; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(styleOfTitle);
					if(j == 0) cell.setCellValue("업무명");
					if(j == 1) cell.setCellValue("참여기간");
					if(j == 2) cell.setCellValue("고객사");
					if(j == 3) cell.setCellValue("역할");
					if(j == 4) cell.setCellValue("근무회사");
					if(j == 5) cell.setCellValue("개발 및 운영");
					if(i == 1) {
						if(j == 5) cell.setCellValue("①언어");
						if(j == 6) cell.setCellValue("②RDBMS");
						if(j == 7) cell.setCellValue("③프레임워크");
						if(j == 8) cell.setCellValue("④내부\n기술");
						if(j == 9) cell.setCellValue("⑤OS");
						if(j == 10) cell.setCellValue("⑥개발\nTool");
					}
				}
			}//for
			//5번째 줄 > 리스트
			for (int i = 0; i < skillInfo.size(); i++) {
				row = sheet.createRow(rowCnt++);
				row.setHeight((short) 1000);
				for(int j = 0; j < 11; j ++) {
					cell = row.createCell(j);
					cell.setCellStyle(styleOfText);
					if(j == 0) cell.setCellValue( skillInfo.get(i).get("work_name").toString() );
					if(j == 1) cell.setCellValue( skillInfo.get(i).get("work_start_dt") + "\n ~ \n" + skillInfo.get(i).get("work_end_dt") );
					if(j == 2) cell.setCellValue( skillInfo.get(i).get("client_name").toString() );
					if(j == 3) cell.setCellValue( skillInfo.get(i).get("position").toString() );
					if(j == 4) cell.setCellValue( skillInfo.get(i).get("working_company").toString() );
					if(j == 5) cell.setCellValue( skillInfo.get(i).get("code_language").toString() );
					if(j == 6) cell.setCellValue( skillInfo.get(i).get("rdbms").toString() );
					if(j == 7) cell.setCellValue( skillInfo.get(i).get("framework").toString() );
					if(j == 8) cell.setCellValue( skillInfo.get(i).get("internal_skill").toString() );
					if(j == 9) cell.setCellValue( skillInfo.get(i).get("os").toString() );
					if(j == 10) cell.setCellValue( skillInfo.get(i).get("dev_tool").toString() );
						
				}
			}
			
			
			
		}//if
		
		return workbook;
	}
}
