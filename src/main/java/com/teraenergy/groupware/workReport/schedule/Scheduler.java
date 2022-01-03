package com.teraenergy.groupware.workReport.schedule;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teraenergy.common.config.EtcSetting;

@Component
public class Scheduler {
	
	static final Logger log = LoggerFactory.getLogger(Scheduler.class);
	
	private String filePath;	//파일 경로
	
	@Scheduled(cron = "* 1 * * * ?")
	public void deleteGarbageFile() throws Exception{
		//파일을 삭제하는 로직을 생각해봐야함
		//1. 시간 -> 매일 오후 7시마다면 좋겠다 : 퇴근한 후에 확인해야하니까
		//2. 파일의 수정한 날자를 현재 시간 대비 경과시간을 받아
		//3. 정해진 시간(여기서 1일이라고 하겠음)이 지난 파일을 삭제한다.
		
		//캘린더 객체 생성
		Calendar calendar = Calendar.getInstance();
		long today = calendar.getTimeInMillis();	//현재 시간 : 밀리세컨드 단위
		long day = 24*60*60*1000;	//일 단위
		
		Calendar file_calendar = Calendar.getInstance();	//파일에 대한 캘린더 객체 생성
		Date fileDate = null;
		
		// os환경에 따른 디렉터리 경로 설정 기능
		if("local".equals(EtcSetting.ENVMODE)) {
			filePath=EtcSetting.LOCALFILEPATH;
		}else if("staging".equals(EtcSetting.STAGINGFILEPATH)) {
			filePath=EtcSetting.STAGINGFILEPATH;
		}else {
			filePath=EtcSetting.PRODUCTIONFILEPATH;
		}
		
		//삭제할 폴더의 경로 : garbageFilePath
		File garbageFilePath = new File(filePath + "/temp");
		File[] fileList = garbageFilePath.listFiles();	//파일 리스트 가져오기
		
		if(fileList == null || fileList.length < 1) {
			return;
		}
		for(int i = 0; i< fileList.length; i++) {
			//파일의 마지막 수정 시간 가져오기
			fileDate = new Date(fileList[i].lastModified());
			//현재 시간과 파일 수정 시간과의 시간차를 계산한다
			file_calendar.setTime(fileDate);
			long difMil = today - file_calendar.getTimeInMillis();
			
			//날짜로 계산한다.
			int diffDay = (int)(difMil/day);
			
			if(diffDay > 3 && fileList[i].exists()) {
				fileList[i].delete();
				System.out.println("파일 삭제 완료");
			}
		}
		
		
	}
	
	
	
	
}