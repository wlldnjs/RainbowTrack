package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ServiceBean;
import boardService.BoardService;
import util.JdbcUtil;

/**
 * Servlet implementation class BoardController
 */
@WebServlet(urlPatterns = "*.bd", initParams = {
		@WebInitParam(name = "configFile", value = "WEB-INF/BoardService.properties") // �ʱ�
																						// �Ķ����
																						// ����.
})
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// BoardService�� ���� <���ɾ�, ���� �ν��Ͻ�> ���� ������ boardServiceMap�� ����
	private Map<String, BoardService> boardServiceMap = new HashMap<String, BoardService>();

	// �ʱ� ����(������ �Ǵ� init)
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
			System.out.println("boardService property command : " + command);
			String serviceClassName = prop.getProperty(command);

			try {
				Class<?> serviceClass = Class.forName(serviceClassName);
				BoardService boardServiceInstance = (BoardService) serviceClass.newInstance();
				boardServiceMap.put(command, boardServiceInstance);
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

//		String command = request.getParameter("command");
		
		String command = request.getRequestURI(); 
		if (command.indexOf(request.getContextPath()) == 0) { 
			System.out.println("contextPath : " + request.getContextPath());
			command = command.substring(request.getContextPath().length()); 
			System.out.println(command);
		}
		 
		
		BoardService service = boardServiceMap.get(command);
		
		if (service == null) {
			System.out.println("�ش� Boardservice ��ü ����");
		}
		
		ServiceBean serviceBean = null;
		serviceBean = service.process(request, response);
		
		if (serviceBean.getListView() != null) {
			request.setAttribute("listView", serviceBean.getListView());
//			response.sendRedirect(serviceBean.getViewPage());
			RequestDispatcher rd = request.getRequestDispatcher(serviceBean.getViewPage());
			rd.forward(request, response);
		} else {
			response.sendRedirect(serviceBean.getViewPage());
		}
		
		
//		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
//		rd.forward(request, response);

		// �ּ�â�� get��� �Ķ���� ����� �ڵ��� ��
		/*
		 * String command = request.getRequestURI(); if
		 * (command.indexOf(request.getContextPath()) == 0) { command =
		 * command.substring(request.getContextPath().length()); }
		 */
	}

}