package com.teraenergy.common.vo;


public class Page {
	private int row_per_page;	//페이지에 보여줄 게시글의 개수
	private int curPage;			//현재 페이지
	private int startPageNum = 1;	//페이지에 보여줄 첫번째 페이지 번호 : 1
	private int lastPageNum = 10;	//처음 보여줄 마지막 페이지 번호 : 10
	private int startRow;			//MySQL에서 사용할 LIMIT 
	private double workReportCnt;	//전체 게시물 개수
	private int lastPage;			//마지막 페이지 번호
	
	public int getRow_per_page() {
		return row_per_page;
	}
	public void setRow_per_page(int row_per_page) {
		this.row_per_page = row_per_page;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getStartPageNum() {
		return startPageNum;
	}
	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}
	public int getLastPageNum() {
		return lastPageNum;
	}
	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public double getWorkReportCnt() {
		return workReportCnt;
	}
	public void setWorkReportCnt(double workReportCnt) {
		this.workReportCnt = workReportCnt;
		pagingData();
	}
	
	private void pagingData() {
		if(curPage > (row_per_page/2)) {
			startPageNum = curPage -((lastPageNum/2)-1);
			//보여지는 첫번재 페이지 번호는 현재페이지 ((마지막페이지번호/2)-1) > 현재 페이지가 6이라면 첫번째 페이지는 2가 뜬다
			lastPageNum += (startPageNum-1);
		}
		
		startRow = (curPage-1) * row_per_page;
		//마지막 페이지 번호 : 게시물 전체 개수 / 페이지당 보여지는 게시물의 개수 > 올림처리 > lastPage에 대입
		lastPage = (int) (Math.ceil(workReportCnt/row_per_page));
		if(lastPage<=10) lastPageNum = lastPage;
		//현재페이지가 (마지막페이지 -4)보다 같거나 작을 경우
		if(curPage >= (lastPage-4)) lastPageNum = lastPage;
	}
	
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	
	@Override
	public String toString() {
		return "Page [row_per_page=" + row_per_page + ", curPage=" + curPage + ", startPageNum=" + startPageNum
				+ ", lastPageNum=" + lastPageNum + ", startRow=" + startRow + ", workReportCnt=" + workReportCnt
				+ ", lastPage=" + lastPage + "]";
	}
	
}
