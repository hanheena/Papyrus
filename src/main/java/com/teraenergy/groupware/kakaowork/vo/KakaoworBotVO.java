package com.teraenergy.groupware.kakaowork.vo;

public class KakaoworBotVO {
	/* 카카오워크 봇 api 전체 공용 field */
	private String avatar_url;
	private String department;
	private String display_name;
	private String id;
	private String email;
	private String name;
	private String nickname;
	private String position;
	private String responsibility;
	private String space_id;
	private String vacation_end_time;
	private String vacation_start_time;
	private String work_start_time;
	private String work_end_time;
	
	/* 봇 채팅방 생성용 추가 파라미터 */
	private String type; 
	private String users_count;
	
	/* 봇이 채팅용 추가 파라미터 */
	private String conversation_id;
	private String send_time;
	private String text;
	private String update_time;
	private String user_id;
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	public String getSpace_id() {
		return space_id;
	}
	public void setSpace_id(String space_id) {
		this.space_id = space_id;
	}
	public String getVacation_end_time() {
		return vacation_end_time;
	}
	public void setVacation_end_time(String vacation_end_time) {
		this.vacation_end_time = vacation_end_time;
	}
	public String getVacation_start_time() {
		return vacation_start_time;
	}
	public void setVacation_start_time(String vacation_start_time) {
		this.vacation_start_time = vacation_start_time;
	}
	public String getWork_start_time() {
		return work_start_time;
	}
	public void setWork_start_time(String work_start_time) {
		this.work_start_time = work_start_time;
	}
	public String getWork_end_time() {
		return work_end_time;
	}
	public void setWork_end_time(String work_end_time) {
		this.work_end_time = work_end_time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsers_count() {
		return users_count;
	}
	public void setUsers_count(String users_count) {
		this.users_count = users_count;
	}
	public String getConversation_id() {
		return conversation_id;
	}
	public void setConversation_id(String conversation_id) {
		this.conversation_id = conversation_id;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}

