package boardService;

import java.util.List;

import bean.BoardBean;

public class BoardListView {
	private int boardTotalCount;		//전체 게시물 수
	private int currentPageNum;			//요청 게시물 번호
	private List<BoardBean> boardList;  //게시물 목록
	private int pageTotalCount;			//페이지 수
	private int boardCountPerPage;		//페이지당 보여줄 게시물 개수
	private int firstRow;				//시작 게시물 번호
	private int endRow;					//끝 게시물 번호
	
	
	public BoardListView() {}
	
	public BoardListView(List<BoardBean> boardList, int boardTotalCount,
			int currentPageNum, int boardCountPerPage, int startRow, int endrow){
		this.boardList = boardList;
		this.boardTotalCount = boardTotalCount;
		this.currentPageNum = currentPageNum;
		this.boardCountPerPage = boardCountPerPage;
		this.firstRow = startRow;
		this.endRow = endrow;
		
		calculatePageTotalCount();
	}
	
	/**
	 * 전체 페이지의 수를 구하는 메소드
	 * 
	 */
	public void calculatePageTotalCount(){
		if(boardTotalCount == 0){
			pageTotalCount = 0;
		} else{
			pageTotalCount = boardTotalCount / boardCountPerPage;
			if(boardTotalCount % boardCountPerPage > 0){
				pageTotalCount++;
			}
		}
	}

	public int getBoardTotalCount() {
		return boardTotalCount;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public List<BoardBean> getBoardList() {
		return boardList;
	}
	
	public void setBoardList(List<BoardBean> boardList) {
		this.boardList = boardList;
	}

	public int getPageTotalCount() {
		return pageTotalCount;
	}

	public int getBoardCountPerPage() {
		return boardCountPerPage;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public int getEndRow() {
		return endRow;
	}
	
	public boolean isEmpty(){
		return boardTotalCount == 0;
	}
	
}
