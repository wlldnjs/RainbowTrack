package boardService;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BoardBean;
import bean.ServiceBean;
import dao.BoardDao;
import util.MultiPart;

public class BoardWriteService implements BoardService {

	@Override
	public ServiceBean process(HttpServletRequest request, HttpServletResponse response) {
		
		BoardBean boardBean = new BoardBean();
		BoardDao boardDao = BoardDao.getInstance();
		ServiceBean serviceBean = new ServiceBean();
//		serviceBean.setViewPage("jsp/main/main.jsp"); // TODO url pattern ���� ��.
		serviceBean.setViewPage("boardRead.bd");
		
		try{
			request.setCharacterEncoding("utf-8");
			MultiPart multiPart = new MultiPart(request);
			String user_id = multiPart.getParameter("user_id");
			String board = multiPart.getParameter("board");
			String hashTags = multiPart.getParameter("hashTags");
			int boardIdx = boardDao.getLastIdx();
			StringTokenizer st = new StringTokenizer(multiPart.getFileName("upload_file"),".");
			st.nextToken(); // �����̸��� �������� ����
			String fileExtension = st.nextToken(); //������ Ȯ���ڸ� ������
			System.out.println("������ Ȯ���� : " +fileExtension);
//			String newPath = "c:/Java/Tomcat9/webapps/musicFile/" +(boardIdx+1) +"." +fileExtension;
			String newPath = "c:/Java/Tomcat9/webapps/musicFile/" +(boardIdx+1) +"." +fileExtension;
			String saveFilePath = "http://210.123.255.149:8080/musicFile/" +(boardIdx+1) +"." +fileExtension;
			multiPart.saveFile("upload_file", newPath);
			System.out.println();
			System.out.println(user_id +newPath +board +hashTags);
			
			boardBean.setUser_id(user_id);
			boardBean.setBoard(board);
			boardBean.setMusicFilePath(saveFilePath);
			boardBean.setHashTags(hashTags);
			boardDao.insertBoard(boardBean);
			System.out.println("BoardWriteService ������ ���Լ���");
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("BoardWriteService ����");
		}

		return serviceBean;
	}

}