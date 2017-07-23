package jsonService;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import dao.BoardDao;
import dao.ReplyDao;

public class DeleteBoardService implements JsonService {

	@Override
	public String process(HttpServletRequest request) {

		int board_idx = Integer.parseInt(request.getParameter("boardIdx").trim());
		BoardDao boardDao = BoardDao.getInstance();
		ReplyDao replyDao = ReplyDao.getInstance();
		
		replyDao.deleteAllReplyByIdx(board_idx);
		boardDao.deleteBoardByIdx(board_idx);
		
		return "{\"result\" : \"success\"}";
	}

}
