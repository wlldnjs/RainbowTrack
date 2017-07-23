package jsonService;

import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import dao.UserDao;

public class LoginService implements JsonService {

	@Override
	public String process(HttpServletRequest request) {
		
		String id = request.getParameter("id").trim();
		String pw = request.getParameter("pw").trim();
		
		UserDao dao = UserDao.getInstance();
		int check = dao.loginUser(id, pw);
		HttpSession session = request.getSession();
		ServletContext application = request.getServletContext();
		String result = null;
		
		if (check == 0) {
			Hashtable<String, String> idList = (Hashtable) application.getAttribute("idList");	
			if (idList == null) {
				idList = new Hashtable<String, String>();
			}
			if (!idList.isEmpty()) {
				if (idList.containsKey(id)) {
					System.out.println("LoginService error : �̹� �������� ������Դϴ�.");
					result = "{\"result\" : \"already\"}"; // �̹� ���� ���� ����� ���̵�� �� �α����� �Ϸ��� �� �� .
				} else {
					idList.put(id, id);
					UserBean bean = dao.selectUser(id);
					session.setAttribute("id", id);
					session.setAttribute("bean", bean);
					application.setAttribute("idList", idList);
					result = "{\"result\" : \"true\"}";
				}
			} else {
				idList.put(id, id);
				UserBean bean = dao.selectUser(id);
				session.setAttribute("id", id);
				session.setAttribute("bean", bean);
				application.setAttribute("idList", idList);
				result = "{\"result\" : \"true\"}";
			}
		} else if (check == 1) { // id ����
			System.out.println("LoginService error : id����.");
			result = "{\"result\" : \"idFail\"}";
		} else { // pw ����
			System.out.println("LoginService error : pw����.");
			result = "{\"result\" : \"pwFail\"}";
		}
		
		return result;
	}

}