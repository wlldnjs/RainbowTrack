package userService;

import java.util.List;

import bean.FollowBean;

public class FollowListView {
	private List<FollowBean> followList;
	
	public List<FollowBean> getFollowList(){
		return followList;
	}
	
	public FollowListView(List<FollowBean> followList){
		this.followList = followList;
	}
}
