package userService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import dao.UserDao;

public class ModifyService implements UserService {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String pw = request.getParameter("pw").trim();
		String phoneNum = request.getParameter("phoneNum").trim();
		String email = request.getParameter("email").trim();
		
		UserBean bean = new UserBean();
		bean.setId(id);
		bean.setPw(pw);
		bean.setPhoneNum(phoneNum);
		bean.setEmail(email);
		
		UserDao dao = new UserDao();
		boolean check = dao.modifyUser(bean);

		String viewPage = null;

		if (check) {
			viewPage = "jsp/main/boardRead.bd";
		} else {
			// TODO 회원정보수정 실패
		}

		return viewPage;
	}
}
