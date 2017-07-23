package userService;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import dao.UserDao;
import util.MultiPart;

public class ImageService implements UserService {
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response){
//		String viewPage="jsp/main/main.jsp";
		String viewPage="jsp/main/boardRead.bd";
		UserBean userBean = new UserBean();
		UserDao userDao = UserDao.getInstance();
		try{
			MultiPart multiPart = new MultiPart(request);
			String user_id = multiPart.getParameter("user_id");
			StringTokenizer st = new StringTokenizer(multiPart.getFileName("upload_image"),".");
			st.nextToken(); //이미지 파을의 이름을 가져오기 위함
			String fileExtension = st.nextToken(); //이미지 파일의 확장자만 가져오기 위함.
			String newPath = "c://Java/Tomcat9/webapps/rt_img/" + (user_id) + "." + fileExtension;
			String saveFilePath = "http://210.123.255.149:8080/rt_img/" + (user_id) + "." + fileExtension;
			multiPart.saveFile("upload_image", newPath);
			System.out.println();
			System.out.println(newPath);
			
			userBean.setId(user_id);
			userBean.setImage(saveFilePath);
			userDao.imageUser(userBean);
			
			HttpSession session = request.getSession(false);
			session.setAttribute("bean", userBean);
			System.out.println("ImageService : 프로필 사진 수정 성공");
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("ImageService 실패");
		}
		
		return viewPage;
	}
}
