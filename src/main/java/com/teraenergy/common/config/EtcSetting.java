package com.teraenergy.common.config;

public class EtcSetting {
	public final static String LOCALFILEPATH = "C:/filepath";
	public final static String STAGINGFILEPATH = "C:/filepath";
	public final static String PRODUCTIONFILEPATH = "/home/papyrus/papyrus_files";

	public final static String STATIC_URI_NAME = "filepath";

	public final static String LOCALPORT = "8080";
	public final static String PRODUCTION_PORT = "8090";

	public final static String LOCALADDR = "localhost";
	public static String PRODUCTION_ADDR = "121.179.87.17";

	
	
	// local = 개인 컴퓨터
	// staging = 배포서버랑 비슷한 환경의 또다른 서버
	// production = 진짜 서버
	public final static String ENVMODE = "production";

}
