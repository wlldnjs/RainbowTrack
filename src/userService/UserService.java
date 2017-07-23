package userService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
	
	public String process(HttpServletRequest request, HttpServletResponse response);
	
}
