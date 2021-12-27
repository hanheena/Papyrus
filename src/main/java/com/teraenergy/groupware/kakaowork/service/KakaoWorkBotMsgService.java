package com.teraenergy.groupware.kakaowork.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.teraenergy.groupware.kakaowork.mapper.KakaoworkBotMapper;
import com.teraenergy.groupware.kakaowork.vo.KakaoworBotVO;

@Service
public class KakaoWorkBotMsgService {
	@Autowired
	private KakaoworkBotMapper kakaoworkBotMapper;

	/**
	 * 카카오 워크에 등록된 맴머목록 전체를 가져오는 함수.
	 * KakaoworBotVO 객체 안에 id 값이 open_bot_chatroom 의 인자값으로 사용된다.
	 * 조회 성공시 List<KakaoworBotVO> 에 값이 채워져서 return
	 * api 문제가 생길시 null return
	 * @return
	 */
	public List<KakaoworBotVO> getKakaowork_user_list() {
		// db안에 kakaowork_bot_key 테이블에서 카카오워크봇 appkey 가져오기
		String appkey=kakaoworkBotMapper.getKakaoworkBotKey();
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<KakaoworBotVO> kakaoworkUserList=new ArrayList<>();

		try {
			/*
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000); // 타임아웃 설정 5초
			factory.setReadTimeout(5000);// 타임아웃 설정 5초
			*/
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders header = new HttpHeaders();
			// 헤더에 appkey 토큰 담기
			header.add("Authorization", "Bearer "+appkey);
			HttpEntity<?> entity = new HttpEntity<>(header);

			//api 주소
			String url = "https://api.kakaowork.com/v1/users.list";

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
			
			/* api 요청이 문제가 있으면 _success 가 false로 나옴 */
			Boolean _success=(Boolean) lm.get("success");
			if(!_success) {
				return null;
			}
			
			/* 아래는 json으로 받은 데이터를 vo로 변환하는 코드 */
			List<Map<String,Object>> itemList = (List<Map<String, Object>>) lm.get("users");
			
			for (Map item : itemList) {
				String email=null;
				KakaoworBotVO kakaoworkBotVO=new KakaoworBotVO();
				List<Map<String, Object>> identifications = (List<Map<String, Object>>) item.get("identifications");
				for (Map e : identifications) {
					String type=(String) e.get("type");
					if("email".equals(type.toLowerCase())) {
						email=(String) e.get("value");
					}
				}
				kakaoworkBotVO.setEmail(email);
				
				String avatar_url=(String) item.get("avatar_url");
				kakaoworkBotVO.setAvatar_url(avatar_url);
				String department= (String) item.get("department");
				kakaoworkBotVO.setDepartment(department);
				String display_name=(String) item.get("display_name");
				kakaoworkBotVO.setDisplay_name(display_name);
				String id=(String)item.get("id");
				kakaoworkBotVO.setId(id);
				
				kakaoworkBotVO.setName((String)item.get("name"));
				kakaoworkBotVO.setNickname((String)item.get("nickname"));
				kakaoworkBotVO.setPosition((String)item.get("position"));
				kakaoworkBotVO.setResponsibility((String)item.get("responsibility"));
				kakaoworkBotVO.setSpace_id((String)item.get("space_id"));
				kakaoworkBotVO.setVacation_end_time((String)item.get("vacation_end_time"));
				kakaoworkBotVO.setVacation_start_time((String)item.get("vacation_start_time"));
				kakaoworkBotVO.setWork_start_time((String)item.get("work_start_time"));
				kakaoworkUserList.add(kakaoworkBotVO);
				
			}


		} catch (Exception e) {
			result.put("statusCode", "999");
			result.put("body", "excpetion오류");
			System.out.println(e.toString());
		}

		return kakaoworkUserList;
	}
	
	/**
	 * 유저의 이메일을 이용하여 카카오워크 user_id를 가여오는 함수.
	 * KakaoworBotVO 객체 안에 id 값이 send_bot_msg_to_user 의 인자값으로 사용된다.
	 * 유저 데이터 조회 성공시 KakaoworBotVO에 값이 채워져서 return.
	 * api 문제가 생길시 null return.
	 * @param email
	 * @return
	 */
	public KakaoworBotVO getKakaowork_user_by_email(String email) {
		// db안에 kakaowork_bot_key 테이블에서 카카오워크봇 appkey 가져오기
		String appkey=kakaoworkBotMapper.getKakaoworkBotKey();
		
		Map<String, Object> result = new HashMap<String, Object>();
		KakaoworBotVO kakaoworkBotVO=new KakaoworBotVO();

		try {
			/*
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000); // 타임아웃 설정 5초
			factory.setReadTimeout(5000);// 타임아웃 설정 5초
			*/
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders header = new HttpHeaders();
			// 헤더에 카카오워크봇 토큰 담기
			header.add("Authorization", "Bearer "+appkey);
			HttpEntity<?> entity = new HttpEntity<>(header);

			//api 주소
			String url = "https://api.kakaowork.com/v1/users.find_by_email?email=";

			// 이게 urlencode 까지 다해줌
			UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+email).build();

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
			 */
			LinkedHashMap lm = (LinkedHashMap) resultMap.getBody();
			Boolean _success=(Boolean) lm.get("success");
			//api 요청에 문제가 생기면 _success 가 false로 온다
			if(!_success) {
				return null;
			}
			
			/* json으로 된 데이터를 vo로 변환하는 코드 */
			Map<String,Object> kakaoUserMap=(Map<String, Object>) lm.get("user");
			kakaoworkBotVO.setAvatar_url((String) kakaoUserMap.get("avatar_url")) ;
			kakaoworkBotVO.setDepartment((String)kakaoUserMap.get("department"));
			kakaoworkBotVO.setDisplay_name((String)kakaoUserMap.get("display_name"));
			kakaoworkBotVO.setId((String)kakaoUserMap.get("id"));
			kakaoworkBotVO.setName((String)kakaoUserMap.get("name"));
			kakaoworkBotVO.setNickname((String)kakaoUserMap.get("nickname"));
			kakaoworkBotVO.setPosition((String)kakaoUserMap.get("position"));
			kakaoworkBotVO.setResponsibility((String)kakaoUserMap.get("responsibility"));
			kakaoworkBotVO.setSpace_id((String)kakaoUserMap.get("space_id"));
			kakaoworkBotVO.setVacation_end_time((String)kakaoUserMap.get("vacation_end_time"));
			kakaoworkBotVO.setVacation_start_time((String)kakaoUserMap.get("vacation_start_time"));
			kakaoworkBotVO.setWork_end_time((String)kakaoUserMap.get("work_end_time"));
			kakaoworkBotVO.setWork_start_time((String)kakaoUserMap.get("work_start_time"));


		} catch (Exception e) {
			result.put("statusCode", "999");
			result.put("body", "excpetion오류");
			System.out.println(e.toString());
		}
		return kakaoworkBotVO;
	}
	
	/**
	 * 카카오 봇과 유저간의 1:1 채팅방을 생성하는 코드.
	 * KakaoworBotVO 객체 안에 id 값이 open_bot_chatroom 의 인자값으로 사용된다.
	 * api 를 통해서 방을 생성하고,
	 * 이 api를 10번정도 또 호출한다고해서 방이 10개 생성되지는 않는다.
	 * 1번을 호출하든 100일에 걸쳐 100번을 호출하든 봇과 유저간의 방은 딱 하나만 생성된다.
	 * 방 생성 성공시 kakaoworbotkVO 에 값이 채워져서 return
	 * api 문제가 생길시 null return 
	 * @param kakaowork_user_id
	 * @return
	 */
	public KakaoworBotVO open_bot_chatroom(String kakaowork_user_id) {
		// db안에 kakaowork_bot_key 테이블에서 카카오워크봇 appkey 가져오기
		String appkey=kakaoworkBotMapper.getKakaoworkBotKey();
		
		Map<String, Object> result = new HashMap<String, Object>();
		KakaoworBotVO kakaoworkBotVO=new KakaoworBotVO();

		try {
			/*
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000); // 타임아웃 설정 5초
			factory.setReadTimeout(5000);// 타임아웃 설정 5초
			*/
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders header = new HttpHeaders();
			//보낼 데이터 형식을 json으로 설정
			header.setContentType(MediaType.APPLICATION_JSON);
			// 헤더에 카카오워크봇 토큰을 담는다
			header.add("Authorization", "Bearer "+appkey);
			
			/* body에 실을 data 만들기 */
			Map<String,Object> reqBody=new HashMap<>();
			reqBody.put("user_id", kakaowork_user_id);
			
			/* HttpEntity<>(reqBody     이 부분이 body data 첨가 해주는 코드 */
			HttpEntity<?> entity = new HttpEntity<>(reqBody,header);

			// api 주소
			String url = "https://api.kakaowork.com/v1/conversations.open";

			// 이게 urlencode 까지 다해줌
			UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

			// 이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
			ResponseEntity<Map> resultMap =null;
			try {
				resultMap = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			result.put("statusCode", resultMap.getStatusCodeValue()); // http status code를 확인
			result.put("header", resultMap.getHeaders()); // 헤더 정보 확인
			result.put("body", resultMap.getBody()); // 실제 데이터 정보 확인
			
			/*
			 * Map<String,Object> 로 안받은 이유?? 몰?루
			 */
			LinkedHashMap lm = (LinkedHashMap) resultMap.getBody();
			Boolean _success=(Boolean) lm.get("success");
			
			// api 요청에 문제가 생기면 _success 가 false로 온다
			if(!_success) {
				return null;
			}
			
			/* json 데이터를 vo로 변환하는 코드*/
			Map<String,Object> conversationMap=(Map<String, Object>) lm.get("conversation");
			
			kakaoworkBotVO.setAvatar_url((String) conversationMap.get("avatar_url"));
			kakaoworkBotVO.setId((String)conversationMap.get("id"));
			kakaoworkBotVO.setName((String)conversationMap.get("name"));
			kakaoworkBotVO.setType((String)conversationMap.get("type"));
			kakaoworkBotVO.setUsers_count(conversationMap.get("users_count")+"");

		} catch (Exception e) {
			result.put("statusCode", "999");
			result.put("body", "excpetion오류");
			System.out.println(e.toString());
		}
		return kakaoworkBotVO;
	}
	
	/**
	 * 유저에게 실제 메세지를 보내는 코드
	 * 해당 함수를 사용하면 메세지 전송이 끝나기 때문에 추가로 해줄 작업은 없다.
	 * 메세지 보내기 성공시 KakaoworBotVO 에 값이 채워져서 return.
	 * api 문제가 생길시 null return
	 * @param kakaowork_bot_room_id
	 * @param msg
	 * @return
	 */
	public KakaoworBotVO send_bot_msg_to_user(String kakaowork_bot_room_id,String msg) {
		// db안에 kakaowork_bot_key 테이블에서 카카오워크봇 appkey 가져오기
		String appkey=kakaoworkBotMapper.getKakaoworkBotKey();
		
		Map<String, Object> result = new HashMap<String, Object>();
		KakaoworBotVO kakaoworkBotVO=new KakaoworBotVO();

		try {
			/*
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000); // 타임아웃 설정 5초
			factory.setReadTimeout(5000);// 타임아웃 설정 5초
			*/
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders header = new HttpHeaders();
			// 보낼 데이터 형식을 json으로 설정
			header.setContentType(MediaType.APPLICATION_JSON);
			// 카카오워크봇 토큰을 헤더에 담는다
			header.add("Authorization", "Bearer "+appkey);
			
			/* body에 실을 data 만들기 */
			Map<String,Object> reqBody=new HashMap<>();
			reqBody.put("conversation_id", kakaowork_bot_room_id);
			reqBody.put("text", msg);
			
			/* HttpEntity<>(reqBody     이 부분이 body data 첨가 해주는 코드 */
			HttpEntity<?> entity = new HttpEntity<>(reqBody,header);

			// api 주소
			String url = "https://api.kakaowork.com/v1/messages.send";

			// 이게 urlencode 까지 다해줌
			UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

			// 이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
			ResponseEntity<Map> resultMap =null;
			try {
				resultMap = restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			result.put("statusCode", resultMap.getStatusCodeValue()); // http status code를 확인
			result.put("header", resultMap.getHeaders()); // 헤더 정보 확인
			result.put("body", resultMap.getBody()); // 실제 데이터 정보 확인
			
			/*
			 * Map<String,Object> 로 안받은 이유?? 몰?루
			 */
			LinkedHashMap lm = (LinkedHashMap) resultMap.getBody();
			Boolean _success=(Boolean) lm.get("success");
			
			// api 요청이 잘못되면 _success 가 false로 온다
			if(!_success) {
				System.out.println("##!! bot msg api request failed");
				return null;
			}
			
			/* json 데이터를 vo형태로 바꾸는 코드 */
			Map<String,Object> messageMap=(Map<String, Object>) lm.get("message");
			kakaoworkBotVO.setConversation_id( (String) messageMap.get("conversation_id"));
			kakaoworkBotVO.setId( (String)messageMap.get("id"));
			kakaoworkBotVO.setSend_time( messageMap.get("send_time")+"");
			kakaoworkBotVO.setText( (String)messageMap.get("text"));
			kakaoworkBotVO.setUpdate_time( messageMap.get("update_time")+"");
			kakaoworkBotVO.setUser_id( (String)messageMap.get("user_id"));

		} catch (Exception e) {
			result.put("statusCode", "999");
			result.put("body", "excpetion오류");
			System.out.println(e.toString());
		}
		return kakaoworkBotVO;
	}
	
}
