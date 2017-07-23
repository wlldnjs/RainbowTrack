package userService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FollowBean;
import dao.FollowDao;

public class FollowListService implements UserService{
	
	private static FollowListService instance;
	
	public static FollowListService getInstance(){
		if(instance == null){
			instance = new FollowListService();
		}
		return instance;
	}
	public FollowListService(){ }
	
	public FollowListView FollowingListService(String myId){
		List<FollowBean> followList = null;
		FollowDao followDao = FollowDao.getInstance();
		followList = followDao.selectFollowingList(myId);
		return new FollowListView(followList);
	}
	
	public FollowListView FollowerListService(String myId){
		
		FollowDao followDao = FollowDao.getInstance();
		List<FollowBean> followList = null;
		followList = followDao.selectFollowerList(myId);
		
		return new FollowListView(followList);
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		
		return null;
	}

}
