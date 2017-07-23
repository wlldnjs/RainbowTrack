package jsonService;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import dao.FollowDao;

public class FollowingService implements JsonService {

	@Override
	public String process(HttpServletRequest request) {

		String id = request.getParameter("id");		//현재 사용자의 아이디
		String follow = request.getParameter("follow");  //내가 팔로잉 할 사람의 아이디
		
		FollowDao dao = FollowDao.getInstance();
		boolean check = dao.checkFollowing(id, follow);
		String result = "";
		if(check){
			dao.deleteFollow(id, follow);
			result = "{\"result\" : \"cancle\"}";
		} else{
			dao.insertFollow(id, follow);
			result = "{\"result\" : \"follow\"}";
		}
		return result;
	}

}
