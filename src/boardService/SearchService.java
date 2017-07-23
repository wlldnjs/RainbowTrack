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

					// reply�� �����ͼ� ��� �κ�.
					for (int i = 0; i < boardList.size(); i++) {
						BoardBean temBean = boardList.get(i);

						List<ReplyBean> replyBeanList = replyDao.getAllReply(temBean.getIdx());
						temBean.setReplyBeanList(replyBeanList);

					}
				} else {
					
					// TODO �˻��� ���̵��� ������ �� ���� �ϳ��� ����.
				}
				
				UserBean searchUser = userDao.selectUser(search);
				BoardListView viewList = new BoardListView(boardList, boardTotalCount, currentPageNum,
						BOARD_COUNT_PER_PAGE, firstRow, endRow);
				//�˻��� ������ �ȷο� �������� �˻�
				FollowDao followDao = FollowDao.getInstance();
				boolean follow = followDao.checkFollowing(id, search);
				System.out.println("SearchService follow : " + follow);
				
				serviceBean.setListView(viewList);
//				serviceBean.setViewPage("jsp/main/main.jsp?uri=userBoard.jsp&check="+search); // TODO url pattern ���� ��.
//				serviceBean.setViewPage("main.jsp?check="+search+"&follow=" +follow);
				serviceBean.setViewPage("main.jsp?check="+search+"&follow=" +follow);
				request.setAttribute("searchUser", searchUser);

				return serviceBean;
			} else {
//				serviceBean.setViewPage("jsp/main/main.jsp");
				serviceBean.setViewPage("main.jsp?check=noId");
				return serviceBean; // TODO �˻��� ���̵�� ��ġ�ϴ� ���̵� ����.
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
				System.out.println("searchService���� boardList�� null.");
				serviceBean.setViewPage("main.jsp?check=noTag");
				return serviceBean; // TODO �˻��� �ؽ��ð� ��ġ�ϴ� �ؽ����� ����.
			}

		}

	}

}