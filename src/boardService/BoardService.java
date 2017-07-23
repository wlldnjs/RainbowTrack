package boardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ServiceBean;

public interface BoardService {
	
	public ServiceBean process(HttpServletRequest request, HttpServletResponse response);
	
} 
