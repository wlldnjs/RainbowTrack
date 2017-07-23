package userService;


import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import dao.UserDao;

public class RegistService implements UserService {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id").trim();
		String pw = request.getParameter("pw").trim();
		String name = request.getParameter("name").trim();
		String phoneNum = request.getParameter("phoneNum").trim();
		String email = request.getParameter("email").trim();
		String birth = request.getParameter("birth").trim();
		
		UserBean bean = new UserBean();
		bean.setId(id);
		bean.setPw(pw);
		bean.setName(name);
		bean.setPhoneNum(phoneNum);
		bean.setEmail(email);
		bean.setBirth(birth);
		
		UserDao dao = new UserDao();
		boolean check = dao.insertUser(bean);
		String viewPage = null;
		ServletContext application = request.getServletContext();
		Hashtable<String, String> idList = (Hashtable) application.getAttribute("idList");	
		
		if (check) {
			idList.put(id, id);
			application.setAttribute("idList", idList);
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("bean", bean);
			viewPage = "jsp/main/boardRead.bd";
		} else {
			//TODO 회원가입 실패
		}
		
		return viewPage;
	}

}
