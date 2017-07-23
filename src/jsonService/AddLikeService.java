package jsonService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import bean.BoardBean;
import bean.ReplyBean;
import dao.BoardDao;
import dao.ReplyDao;
import net.sf.json.JSONObject;

public class AddLikeService implements JsonService {

	@Override
	public String process(HttpServletRequest request) {
		
		int boardIdx = Integer.parseInt(request.getParameter("boardIdx").trim());
		String user_id = request.getParameter("user_id").trim() + "/";
		
		BoardDao boardDao = BoardDao.getInstance();
		ReplyDao replyDao = ReplyDao.getInstance();
		
		String likeUsers = boardDao.getLikeUsers(boardIdx);
		int likeCount = 0;
		
		if (likeUsers != null && !likeUsers.equals("null")) {
			user_id = likeUsers + user_id;
			StringTokenizer st = new StringTokenizer(likeUsers, "/");
			likeCount = st.countTokens();
		}
		
		int result = boardDao.updateLikeUser(user_id, boardIdx, likeCount+1);
		if (result == 1) {
			BoardBean boardBean = boardDao.getOneBoardBean(boardIdx);
			List<ReplyBean> replyBeanList = replyDao.getAllReply(boardIdx);
			
			JSONObject jsonBoard = new JSONObject();
			JSONObject jsonReply = new JSONObject();
			JSONObject jsonAll = new JSONObject();
			
			jsonBoard.put("user_id", boardBean.getUser_id());
			jsonBoard.put("board", boardBean.getBoard());
			jsonBoard.put("boarddate", boardBean.getBoardDate());
			jsonBoard.put("hashtags", boardBean.getHashTags());
			jsonBoard.put("idx", boardBean.getIdx());
			jsonBoard.put("musicfilepath", boardBean.getMusicFilePath());
			jsonBoard.put("likeusers", boardBean.getLikeUser());
			jsonBoard.put("image", boardBean.getImage());
			
			jsonAll.put("jsonBoard", jsonBoard);
			if (replyBeanList != null) {
				for (int i = 0; i < replyBeanList.size(); i++) {
					ReplyBean replyBean = replyBeanList.get(i);
					jsonReply.put("user_id" + i, replyBean.getUser_id());
					jsonReply.put("reply" + i, replyBean.getReply());
					jsonReply.put("replydate" + i, replyBean.getReplyDate());
					jsonReply.put("boardIdx" + i, replyBean.getBoard_idx());
					jsonReply.put("replyIdx" + i, replyBean.getReply_idx());
				}
				jsonAll.put("jsonReply", jsonReply);
			}
			String tem = jsonAll.toString();
			return tem;
		}
		return null;
	}

}
