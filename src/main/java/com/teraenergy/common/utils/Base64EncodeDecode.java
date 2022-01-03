package com.teraenergy.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

/*
 * 한글, 특수문자(= $ ) 같은것들을 jsp 로 넘기고 컨트롤러로 받는과정에서 문제가 생깁니다.
 * 한글, 특수문자들을 안전한 방법으로 문자열 변환, 문자열 해독 시켜주는 서비스입니다
 * */
@Service
public class Base64EncodeDecode {

	/*
	 * 원본 문자열을 안전한 문자열로 변환 합니다 (base64 url로 변환)
	 * */
	public String base64Encoder(String originalStr) {
		String encodeResult = System.currentTimeMillis() + "";
		try {
			encodeResult = URLEncoder.encode(originalStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encodeResult;
	}

	/*
	 * base64 url(변환됬던) 문자열을 원본 문자열로 복호화 시켜줍니다
	 * */
	public String base64Decoder(String base64Str) {
		String decoded=System.currentTimeMillis() + "";
		try {
			decoded = URLDecoder.decode(base64Str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decoded;
	}
	
}
