package boardService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BoardBean;
import bean.ServiceBean;
import dao.BoardDao;

public class IndexTopTenService implements BoardService {
	
	private static IndexTopTenService instance;
	
	public static IndexTopTenService getInstance(){
		if(instance == null) { 
			instance = new IndexTopTenService();
		}
		return instance;
	}
	
	public IndexTopTenService() {  }
	
	public IndexTopTenArrayView IndexTopTenService(){
		
		List<BoardBean> toptenArray = null;
		BoardDao boardDao = BoardDao.getInstance();
		toptenArray = boardDao.getTopTenList();
	
		return new IndexTopTenArrayView(toptenArray);
	}
	
	@Override
	public ServiceBean process(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}
