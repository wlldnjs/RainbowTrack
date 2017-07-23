package boardService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BoardBean;
import bean.ReplyBean;
import bean.ServiceBean;
import dao.BoardDao;
import dao.ReplyDao;

public class BoardReadService implements BoardService {

	private static BoardReadService instance;
	private static final int BOARD_COUNT_PER_PAGE = 10;

	/*public static BoardReadService getInstance() {

		if (instance == null) {
			instance = new BoardReadService();
		}
		return instance;
	}

	public BoardReadService() {	}

	public BoardListView BoardReadService(int pageNumber) {
		
		int currentPageNum = pageNumber;
		BoardDao boardDao = BoardDao.getInstance();
		ReplyDao replyDao = ReplyDao.getInstance();
		int boardTotalCount = boardDao.selectBoardCount();

		List<BoardBean> boardList = null;
		int firstRow = 0;
		int endRow = 0;

		if (boardTotalCount > 0) {
			firstRow = (pageNumber - 1) * BOARD_COUNT_PER_PAGE + 1;
			endRow = firstRow + BOARD_COUNT_PER_PAGE - 1;
			boardList = boardDao.getBoardList(firstRow, endRow);
		}
		
		// reply를 가져와서 담는 부분.
		for (int i = 0; i < boardList.size(); i++) {
			BoardBean temBean = boardList.get(i);
			
			List<ReplyBean> replyBeanList = replyDao.getAllReply(temBean.getIdx());
			temBean.setReplyBeanList(replyBeanList);
			
		}
		
		return new BoardListView(boardList, boardTotalCount, currentPageNum, BOARD_COUNT_PER_PAGE, firstRow, endRow);
	}*/

	@Override
	public ServiceBean process(HttpServletRequest request, HttpServletResponse response) {
		
		int pageNumber = 1; // TODO test code. 더보기가 되면 바뀔 수 있다.
//		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int currentPageNum = pageNumber;
		BoardDao boardDao = BoardDao.getInstance();
		ReplyDao replyDao = ReplyDao.getInstance();
		int boardTotalCount = boardDao.selectBoardCount();

		List<BoardBean> boardList = null;
		int firstRow = 0;
		int endRow = 0;

		if (boardTotalCount > 0) {
			firstRow = (pageNumber - 1) * BOARD_COUNT_PER_PAGE + 1;
			endRow = firstRow + BOARD_COUNT_PER_PAGE - 1;
			boardList = boardDao.getBoardList(firstRow, endRow);
		}
		
		// reply를 가져와서 담는 부분.
		for (int i = 0; i < boardList.size(); i++) {
			BoardBean temBean = boardList.get(i);
			
			List<ReplyBean> replyBeanList = replyDao.getAllReply(temBean.getIdx());
			temBean.setReplyBeanList(replyBeanList);
			
		}
		
		BoardListView listView = new BoardListView(boardList, boardTotalCount, currentPageNum, BOARD_COUNT_PER_PAGE, firstRow, endRow);
		ServiceBean serviceBean = new ServiceBean();
		serviceBean.setListView(listView);
//		serviceBean.setViewPage("jsp/main/main.jsp");
		serviceBean.setViewPage("main.jsp");

		return serviceBean;
	}

}
