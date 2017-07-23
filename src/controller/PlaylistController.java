package controller;

import java.io.FileInputStream;
import java.io.IOException;
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

import boardService.BoardService;
import playlistService.PlaylistService;
import util.JdbcUtil;

/**
 * Servlet implementation class PlaylistController
 */
@WebServlet(urlPatterns = "/PlaylistController", initParams = {
		@WebInitParam(name = "configFile", value = "WEB-INF/PlaylistService.properties") // 초기
																							// 파라미터
}) // 설정.
public class PlaylistController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// PlaylistService에 대한 <명령어, 서비스 인스턴스> 매핑 정보를 playlistServiceMap에 저장
	private Map<String, PlaylistService> playlistServiceMap = new HashMap<String, PlaylistService>();

	// 초기 설정(생성자 또는 init)
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
			JdbcUtil.close(fis);
		}

		Iterator keyItorator = prop.keySet().iterator();
		while (keyItorator.hasNext()) {
			String command = (String) keyItorator.next();
			String serviceClassName = prop.getProperty(command);

			try {
				Class<?> serviceClass = Class.forName(serviceClassName);
				PlaylistService playlistServiceInstance = (PlaylistService) serviceClass.newInstance();
				playlistServiceMap.put(command, playlistServiceInstance);
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
		String command = request.getParameter("command");
		PlaylistService service = playlistServiceMap.get(command);
		if (service == null) {
			System.out.println("PlaylistService 객체 없음");
		}
		String viewPage = null;
		viewPage = service.process(request, response);
		response.sendRedirect(viewPage);
		// RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		// rd.forward(request, response);
	}
}
