package jsonService;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.ReplyBean;
import dao.ReplyDao;
import net.sf.json.JSONObject;

public class ReplyWriteService implements JsonService {

	@Override
	public String process(HttpServletRequest request) {
		
		String result = "{\"result\" : \"fail\"}";
		
		try {
			request.setCharacterEncoding("utf-8");
			ReplyDao replyDao = ReplyDao.getInstance();
			HttpSession session = request.getSession();

			String user_id = (String) session.getAttribute("id");
			String reply = request.getParameter("reply").trim();
			int board_idx = Integer.parseInt(request.getParameter("boardIdx").trim());
			int reply_idx = replyDao.getLastReplyIdx(board_idx) + 1;

			ReplyBean bean = new ReplyBean();
			bean.setUser_id(user_id);
			bean.setReply(reply);
			bean.setBoard_idx(board_idx);
			bean.setReply_idx(reply_idx);

			boolean check = replyDao.insertReply(bean);
			
			if (check) {
				ReplyBean replyBean = replyDao.getOneReply(board_idx, reply_idx); 
				JSONObject jsonReply = new JSONObject();
				jsonReply.put("user_id", replyBean.getUser_id());
				jsonReply.put("reply", replyBean.getReply());
				jsonReply.put("replyDate", replyBean.getReplyDate());
				jsonReply.put("boardIdx", replyBean.getBoard_idx());
				jsonReply.put("replyIdx", replyBean.getReply_idx());
						
				result = jsonReply.toString();
			} 
		} catch (UnsupportedEncodingException e) {
			System.out.println("ReplyWriteService ¿¡·¯ : " + e.getMessage());
		}

		return result;
	}

}
