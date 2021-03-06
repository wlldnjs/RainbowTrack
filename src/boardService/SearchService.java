package boardService;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.BoardBean;
import bean.ReplyBean;
import bean.ServiceBean;
import bean.UserBean;
import dao.BoardDao;
import dao.FollowDao;
import dao.ReplyDao;
import dao.UserDao;

public class SearchService implements BoardService {

	private static final int BOARD_COUNT_PER_PAGE = 10;

	@Override
	public ServiceBean process(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

		int pageNumber = 1;
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		UserDao userDao = UserDao.getInstance();
		BoardDao boardDao = BoardDao.getInstance();
		ReplyDao replyDao = ReplyDao.getInstance();
		List<BoardBean> boardList = null;
		ServiceBean serviceBean = new ServiceBean();

		System.out.println("SearchService search : " + request.getParameter("search"));
		String search = request.getParameter("search").trim();
		char hash = search.charAt(0);

		if (hash != 35) {

			if (userDao.selectUser(search) != null) {

				int currentPageNum = 1;
				int boardTotalCount = boardDao.selectBoardCount();

				int firstRow = 0;
				int endRow = 0;

				if (boardTotalCount > 0) {
					firstRow = (pageNumber - 1) * BOARD_COUNT_PER_PAGE + 1;
					endRow = firstRow + BOARD_COUNT_PER_PAGE - 1;
					boardList = boardDao.getBoardListFromId(search, firstRow, endRow);

				}

				if (boardList != null) {

					// reply를 가져와서 담는 부분.
					for (int i = 0; i < boardList.size(); i++) {
						BoardBean temBean = boardList.get(i);

						List<ReplyBean> replyBeanList = replyDao.getAllReply(temBean.getIdx());
						temBean.setReplyBeanList(replyBeanList);

					}
				} else {
					
					// TODO 검색한 아이디의 유저가 쓴 글이 하나도 없다.
				}
				
				UserBean searchUser = userDao.selectUser(search);
				BoardListView viewList = new BoardListView(boardList, boardTotalCount, currentPageNum,
						BOARD_COUNT_PER_PAGE, firstRow, endRow);
				//검색한 유저가 팔로우 상태인지 검사
				FollowDao followDao = FollowDao.getInstance();
				boolean follow = followDao.checkFollowing(id, search);
				System.out.println("SearchService follow : " + follow);
				
				serviceBean.setListView(viewList);
//				serviceBean.setViewPage("jsp/main/main.jsp?uri=userBoard.jsp&check="+search); // TODO url pattern 적용 중.
//				serviceBean.setViewPage("main.jsp?check="+search+"&follow=" +follow);
				serviceBean.setViewPage("main.jsp?check="+search+"&follow=" +follow);
				request.setAttribute("searchUser", searchUser);

				return serviceBean;
			} else {
//				serviceBean.setViewPage("jsp/main/main.jsp");
				serviceBean.setViewPage("main.jsp?check=noId");
				return serviceBean; // TODO 검색한 아이디와 일치하는 아이디가 없다.
			}

		} else {
			
			search = search.substring(1); 
					
			BoardListView viewList = new BoardListView();
			
			boardList = boardDao.getBoardListFromHashtag(search);
			
			if (boardList != null) {
				viewList.setBoardList(boardList);
				
				serviceBean.setListView(viewList);
//				serviceBean.setViewPage("jsp/main/main.jsp?uri=userBoard.jsp");
				serviceBean.setViewPage("main.jsp");
				
				return serviceBean;
			} else {
				System.out.println("searchService에서 boardList가 null.");
				serviceBean.setViewPage("main.jsp?check=noTag");
				return serviceBean; // TODO 검색한 해쉬택과 일치하는 해쉬택이 없다.
			}

		}

	}

}
