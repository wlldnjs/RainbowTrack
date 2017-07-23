package boardService;

import java.util.List;

import bean.BoardBean;

public class BoardListView {
	private int boardTotalCount;		//��ü �Խù� ��
	private int currentPageNum;			//��û �Խù� ��ȣ
	private List<BoardBean> boardList;  //�Խù� ���
	private int pageTotalCount;			//������ ��
	private int boardCountPerPage;		//�������� ������ �Խù� ����
	private int firstRow;				//���� �Խù� ��ȣ
	private int endRow;					//�� �Խù� ��ȣ
	
	
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
	 * ��ü �������� ���� ���ϴ� �޼ҵ�
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
