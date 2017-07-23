package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.jasper.tagplugins.jstl.core.Out;

import bean.FollowBean;
import db.DBAction;
import util.JdbcUtil;

public class FollowDao {

	DBAction db = DBAction.getInstance();
	Connection conn = db.getConnection();
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql = null;

	private static FollowDao instance;

	public static FollowDao getInstance() {
		if (instance == null) {
			instance = new FollowDao();
		}
		return instance;
	}

	/**
	 * 팔로잉,팔로워를 추가하는 메소드
	 */
	public void insertFollow(String myId, String follow) {
		try {
			sql = "insert into rt_follow values(?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myId);
			pstmt.setString(2, follow);
			pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println("insertFollow 에러 : " + e.getMessage());
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	/**
	 * 팔로잉, 팔로워를 취소해 주는 메소드
	 */
	public void deleteFollow(String myId, String follow){
		try{
			sql = "delete from rt_follow where myId=? and follow=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myId);
			pstmt.setString(2, follow);
			pstmt.executeQuery();
			
			pstmt.executeQuery();
		} catch(SQLException e){
			System.out.println("deleteFollow 에러 : " +e.getMessage());
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	/**
	 * 내가 Following하는 사람들의 List메소드의 ResultSet을 반환해주는 메소드
	 */
	protected FollowBean getFollowingFromResultSet(ResultSet rs){
		FollowBean followBean = new FollowBean();
		try{
			followBean.setFollow(rs.getString("following"));
			followBean.setImage(rs.getString("image"));
		} catch(SQLException e){
			System.out.println("getFollowingFromResultSet error : " +e.getMessage());
		}
		return followBean;
	}
	
	/**
	 * 내가 Following하는 사람들을 리스트에 담아 리턴하는 메소드.
	 */
	public List<FollowBean> selectFollowingList(String myId){
		sql = "select f.follow as following, u.image from (select follow, myid from rt_follow) f inner join" +
			  " (select image, id from rt_user) u on f.follow = u.id where f.myid= ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				List<FollowBean> followingList = new Vector<FollowBean>();
				do {
					followingList.add(getFollowingFromResultSet(rs));
				}while(rs.next());
				return followingList;
			}
		} catch(SQLException e){
			System.out.println("selectFollowingList error : "+e.getMessage());		
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return Collections.emptyList();
	}
	
	/**
	 * 나를 Follower한 사람들의 List메소드의 ResultSet을 반환해주는 메소드
	 */
	protected FollowBean getFollowerFromResultSet(ResultSet rs){
		FollowBean followBean = new FollowBean();
		try{
			followBean.setFollow(rs.getString("follower"));
			followBean.setImage(rs.getString("image"));
		} catch(SQLException e){
			System.out.println("getFollowingFromResultSet error : " +e.getMessage());
		}
		return followBean;
	}
	
	/**
	 * 나를 Follower한 사람들을 리스트에 담아 리턴하는 메소드.
	 */
	public List<FollowBean> selectFollowerList(String myId){
		sql = "select f.myid as follower, u.image from (select follow, myid from rt_follow) f inner join"
			+" (select image, id from rt_user) u on f.myid = u.id where f.follow= ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				List<FollowBean> followingList = new Vector<FollowBean>();
				do {
					
					followingList.add(getFollowerFromResultSet(rs));
				}while(rs.next());
				return followingList;
			}
		} catch(SQLException e){
			System.out.println("selectFollowingList error : "+e.getMessage());		
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return Collections.emptyList();
	}
	
	/**
	 * 현재 팔로잉중인지 확인하는 메소드.
	 * true를 반환하면 이미 팔로잉중, false를 반환하면 팔로잉중이 아님.
	 */
	public boolean checkFollowing(String myId, String follow){
		sql = "select * from rt_follow where myId=? and follow=?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myId);
			pstmt.setString(2, follow);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch(SQLException e){
			System.out.println("checkFollowing error : " +e.getMessage());
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return false;
	}
	
}
