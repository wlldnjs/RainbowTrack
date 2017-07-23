package userService;

import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutService implements UserService {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		String id = (String) session.getAttribute("id"); 
		
		ServletContext application = request.getServletContext();
		Hashtable<String, String> idList = (Hashtable) application.getAttribute("idList");
		idList.remove(id);
		
		session.invalidate();
		
		String viewPage = "jsp/index/index.jsp";
		
		return viewPage; 
	}

}
