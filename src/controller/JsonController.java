package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonService.JsonService;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class JsonController
 */
@WebServlet(urlPatterns = "/JsonController/*", initParams = {
		@WebInitParam(name = "configFile", value = "WEB-INF/JsonService.properties") // 초기
																						// 파라미터
																						// 설정.
})
public class JsonController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// <명령어, 서비스 인스턴스> 매핑 정보 저장.
	private Map<String, JsonService> jsonServiceMap = new HashMap<String, JsonService>();

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
				} catch (IOException ex) {
				}
			}
		}

		Iterator keyIterator = prop.keySet().iterator();
		while (keyIterator.hasNext()) {
			String command = (String) keyIterator.next();
			System.out.println(command); // test code
			String serviceClassName = prop.getProperty(command);
			try {
				Class<?> serviceClass = Class.forName(serviceClassName);
				JsonService jsonServiceInstance = (JsonService) serviceClass.newInstance();
				jsonServiceMap.put(command, jsonServiceInstance);
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException : " + e.getMessage());
			} catch (InstantiationException e) {
				System.out.println("InstantiationException : " + e.getMessage());
			} catch (IllegalAccessException e) {
				System.out.println("IllegalAccessException : " + e.getMessage());
			}

		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String command = uri.split("/JsonController/")[1];
		 
		JsonService service = jsonServiceMap.get(command);
		if (service == null) {
			System.out.println("해당 service 객체 없음");
		}

		String jsonData = service.process(request);

		response.setContentType("application/x-json; charset=UTF-8"); // HttpServletResponse response
		PrintWriter out = response.getWriter();
		out.print(jsonData);
		out.flush();
		
		System.out.println("JsonController process. flush complete!");

	}

}
