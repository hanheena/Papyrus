package com.teraenergy.common.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teraenergy.common.config.EtcSetting;

public class SummerNoteSample {
	/**STEP 1. 섬머노트에서 이미지 첨부 할시 첫번째로 요청을 받는 컨트롤러
	 * 이미지를 일단은 temp폴더로 업로드 시킨다
	 * 섬머노트에서는 <src="/file/... 이런 이미지가 들어있는 static resource 경로가 필요하기 때문에 static resource url을 리턴해준다
	 * 리턴하는 url은 임시폴더에 저장된 이미지 url이다 
	 * @param img
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/papyrus/sample/sample_uploadSummernoteImg")
	public String summerimage(@RequestParam("file") MultipartFile img, HttpServletRequest request) throws IOException {

		String filePath;
		// os환경에 따른 디렉터리 경로 설정 기능
		if ("local".equals(EtcSetting.ENVMODE)) {
			filePath = EtcSetting.LOCALFILEPATH;
		} else if ("staging".equals(EtcSetting.STAGINGFILEPATH)) {
			filePath = EtcSetting.STAGINGFILEPATH;
		} else {
			filePath = EtcSetting.PRODUCTIONFILEPATH;
		}


		String real_filePath = filePath + "/temp";

		// 파일 경로 확인 후 없으면 폴더 생성
		File dir = new File(real_filePath);
		if (!dir.exists())
			dir.mkdirs();

		String tempName = UUID.randomUUID().toString() + "_" + img.getOriginalFilename();
		tempName = URLEncoder.encode(tempName, "UTF-8").replace("\\+", "%20");

		File file = new File(real_filePath, tempName);

		try {
			img.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// temp static resource url 주소
		String url = "/" + EtcSetting.STATIC_URI_NAME + "/temp/" + tempName;

		return url;
	}

	/**
	 * STEP 2. 이미지 실제 폴더로 이동시키는 함수 섬머노트 게시글 작성중에 이미지를 첨부하면 temp폴더로 올라간다. 등록을 눌렀을시
	 * 이미지를 temp폴더에서 진짜 이미지 경로로 옮기는 코드. 섬머노트로 작성한 게시글은 초기엔 <src="/filepath/temp/...
	 * 이렇게 되있다. <src="/filepath/temp/... 부분을 <src="/filepath/summernote/... 이렇게 이미지
	 * url 문자열 경로도 수정해준다
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public String replace_temp_imgsrc_to_real_imgsrc(String content) throws IOException {
		String filePath;
		// os환경에 따른 디렉터리 경로 설정 기능
		if ("local".equals(EtcSetting.ENVMODE)) {
			filePath = EtcSetting.LOCALFILEPATH;
		} else if ("staging".equals(EtcSetting.STAGINGFILEPATH)) {
			filePath = EtcSetting.STAGINGFILEPATH;
		} else {
			filePath = EtcSetting.PRODUCTIONFILEPATH;
		}

		String startWord = "src=\"/" + EtcSetting.STATIC_URI_NAME + "/temp/"; // 찾을 첫번째 단어
		String endWord = "\""; // 찾을 두번째 단어
		List<String> fileName = new ArrayList<String>();
		if (content.contains(startWord)) {
			int startIndex = content.indexOf(startWord); // 첫번째 인덱스
			int endIndex = content.indexOf(endWord, startIndex + 5); // 두번째 인덱스

			while (startIndex > 0 && endIndex > 0) { // indexOf는 지정할 값을 찾을 수 없다면 -1를 반환
				String file = content.substring(startIndex + 20, endIndex);
				fileName.add(file);
				try {
					startIndex = content.indexOf(startWord, startIndex + startWord.length());
					endIndex = content.indexOf(endWord, startIndex + 5);
				} catch (Exception e) {
					startIndex = -1;
					endIndex = -1;
				} // try
			} // while
		} // if

		// 첨부된 파일을 임시적으로 저장했던 폴더 경로
		String temp_filePath = filePath + "/temp";
		// 실질적으로 저장될 파일 경로
		String savedFilePath = filePath + "/summernote";

		File dir = new File(savedFilePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 파일 복사하기
		byte[] buf = new byte[1024];
		FileInputStream fin = null;
		FileOutputStream fout = null;

		if (fileName.size() != 0) {
			for (int i = 0; i < fileName.size(); i++) {
				File tempFile = new File(temp_filePath + "/" + fileName.get(i));
				File saveFile = new File(savedFilePath + "/" + fileName.get(i));
				if (!tempFile.renameTo(saveFile)) {
					buf = new byte[1024];
					fin = new FileInputStream(tempFile);
					fout = new FileOutputStream(saveFile);

					int read = 0;
					while ((read = fin.read(buf, 0, buf.length)) != -1) {
						fout.write(buf, 0, read);
					} // while
					fin.close();
					fout.close();
					tempFile.delete(); // 기존 파일 삭제
				} // if
			} // for
		} // if

		//temp src 경로를 실제 저장 경로로 바꿔줌
		content = content.replaceAll(startWord, "src=\"/" + EtcSetting.STATIC_URI_NAME + "/summernote/");
		return content;
	}

}
