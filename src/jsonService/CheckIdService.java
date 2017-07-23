package jsonService;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import dao.UserDao;

public class CheckIdService implements JsonService {

	@Override
	public String process(HttpServletRequest request) {
		
		String id = request.getParameter("id").trim();
		System.out.println("CheckIdService id : " + id);
		
		String result = "";

		if (id == "") {
			result = "{\"result\" : \"false\"}";
			return result;
		}
		
		UserDao userDao = UserDao.getInstance();
		if (userDao.idCheck(id)) {
			result = "{\"result\" : \"true\"}";
		} else {
			result = "{\"result\" : \"false\"}";
		}
		
		return result;
	}

}
