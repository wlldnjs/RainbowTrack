package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import userService.UserService;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns="/UserController", initParams = {
		@WebInitParam(name="configFile", value="WEB-INF/UserService.properties") // 초기 파라미터 설정.
})
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// <명령어, 서비스 인스턴스> 매핑 정보 저장. 
	private Map<String, UserService> userServiceMap = new HashMap<String, UserService>();
    
	// 초기 설정
    public void init() throws ServletException {
    	String configFile = getInitParameter("configFile");
    	Properties prop = new Properties();
    	FileInputStream fis = null;
    	
    	try {
    		String configFilePath = getServletContext().getRealPath(configFile);
    		fis = new FileInputStream(configFilePath);
    		prop.load(fis);
    	} catch (IOException e) {
    		System.out.println("IOException : " + e.getMessage());
    	} finally {
    		if (fis != null) {
    			try {
    				fis.close();
    			} catch (IOException ex) { }
    		}
    	}
    	
    	
    	Iterator keyIterator = prop.keySet().iterator();
    	while (keyIterator.hasNext()) {
    		String command = (String) keyIterator.next();
    		System.out.println(command); // test code
    		String serviceClassName = prop.getProperty(command);
    		try {
    			Class<?> serviceClass = Class.forName(serviceClassName);
    			UserService userServiceInstance = (UserService) serviceClass.newInstance();
    			userServiceMap.put(command, userServiceInstance);
    		} catch (ClassNotFoundException e) {
    			System.out.println("ClassNotFoundException : " + e.getMessage());
    		} catch (InstantiationException e) {
    			System.out.println("InstantiationException : " + e.getMessage());
    		} catch (IllegalAccessException e) {
    			System.out.println("IllegalAccessException : " + e.getMessage());
    		}
    				
    	}
    	
    	System.out.println(userServiceMap.size());
    	
    	
    	// 로그인 중인 사용자의 id를 저장하는 리스트 생성후 application에 속성 추가.
    	ServletContext application = getServletContext();
    	Map<String, String> idList = new Hashtable<String, String>();
    	application.setAttribute("idList", idList);
    	
    	
//    	HttpServletResponse response = application.
//    	response.setHeader("Pragma", "No-cache");
//    	response.setHeader("Cache-Control", "no-cache");
//    	response.setHeader("Cache-Control", "no-store");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String command = request.getParameter("command");
		System.out.println("process test code : " + command); // test code
		UserService service = userServiceMap.get(command);
		if (service == null) {
			System.out.println("해당 service 객체 없음");
		}
		
		/*String command = request.getRequestURI();
		if (command.indexOf(request.getContextPath()) == 0) {
			command = command.substring(request.getContextPath().length());
		}*/
		
		String viewPage = null;
		viewPage = service.process(request, response);
		response.sendRedirect(viewPage);
//		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
//		rd.forward(request, response);
				
	}
	
}
