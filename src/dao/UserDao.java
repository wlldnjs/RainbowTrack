package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.UserBean;
import db.DBAction;
import util.JdbcUtil;

public class UserDao {
	DBAction db = DBAction.getInstance();
	Connection conn = db.getConnection();
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql = "";

	private static UserDao instance;

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	/**
	 * ȸ�����Խ� ������ ������ �����ϴ� ����. true�� ��ȯ�ϸ� insert ����, false�� ��ȯ�ϸ� insert ���� �߻�
	 */
	public boolean insertUser(UserBean bean) {
		sql = "insert into rt_user(id,pw,name,phoneNum,email,birth) values(?,?,?,?,?,?)";
		boolean check = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getId());
			pstmt.setString(2, bean.getPw());
			pstmt.setString(3, bean.getName());
			pstmt.setString(4, bean.getPhoneNum());
			pstmt.setString(5, bean.getEmail());
			pstmt.setString(6, bean.getBirth());
			int result = pstmt.executeUpdate();
			if (result < 1) {
				System.out.println("insert ���� �߻�.");
			} else {
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return check;
	}

	/**
	 * �α��� �� ������ id�� pw�� ��ġ�ϴ��� üũ�ϴ� ����. return���� ���� 0-����α���, 1-id ����, 2-pw Ʋ��
	 */
	public int loginUser(String id, String pw) {
		sql = "select pw from rt_user where id='" + id + "'";
		boolean check = false;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				// id ����
				if (pw.equals(rs.getString("pw"))) {
					// �α��� ����
					return 0;
				} else {
					// pw Ʋ��
					System.out.println("LoginUser : pw�� Ʋ���ϴ�.");
					return 2;
				}
			} else {
				// id ����� ���� �α��� ����
				System.out.println("LoginUser : id�� �����ϴ�.");
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		return 0;
	}

	/**
	 * �α��� �� ������ ������ select �Ͽ� bean�� �����͸� set ���ִ� ����. return���� true�� �� ����,
	 * false�� �� ����.
	 */
	public UserBean selectUser(String id) {

		sql = "select * from rt_user where id='" + id + "'";
		UserBean bean = new UserBean();

		boolean check = false;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				bean.setId(rs.getString("id"));
				bean.setPw(rs.getString("pw"));
				bean.setName(rs.getString("name"));
				bean.setPhoneNum(rs.getString("phoneNum"));
				bean.setEmail(rs.getString("email"));
				bean.setBirth(rs.getString("birth"));
				bean.setImage(rs.getString("image"));
				bean.setFollower(rs.getString("follower"));
				bean.setFollowing(rs.getString("following"));
				check = true;
			} else {
				// select ���� �߻�
				System.out.println("selectUser : ���� ������ �ҷ��� �� �����ϴ�.");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		return bean;
	}

	/**
	 * ȸ������ ��, ������ ID�� �ߺ��Ǵ��� Ȯ�����ִ� ����. true�� ��ȯ�ϸ� ��� ����, false�� ��ȯ�ϸ� ���Ұ�.
	 */
	public boolean idCheck(String id) {
		sql = "select id from rt_user where id='" + id + "'";
		boolean check = false;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				// id �ߺ�
			} else {
				// id �ߺ����� ����.
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		return check;
	}

	/**
	 * ȸ������ ���� ����.(������Ʈ)
	 */
	public boolean modifyUser(UserBean bean) {

		sql = "update rt_user set pw=?,phoneNum=?,email=? where id=?";
		boolean check = false;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getPw());
			pstmt.setString(2, bean.getPhoneNum());
			pstmt.setString(3, bean.getEmail());
			pstmt.setString(4, bean.getId());

			int result = pstmt.executeUpdate();
			if (result < 1) {
				System.out.println("ȸ������ ���� �޼ҵ� ����");
			} else {
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return check;
	}

	public void resignUser(String id) {

		sql = "delete from rt_user where id = ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("resignUser method error");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}

	}

	/**
	 * ������ ���� ���� ����.(������Ʈ)
	 */
	public boolean imageUser(UserBean bean) {
		sql = "update rt_user set image=? where id=?";
		boolean check = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getImage());
			pstmt.setString(2, bean.getId());

			int result = pstmt.executeUpdate();
			if (result < 1) {
				System.out.println("������ ���� ���� �޼ҵ� ����");
			} else {
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return check;
	}

//	public void addFollower(String myId, String user) {
//
//		sql = "update rt_user set follower = ? where id = ?";
//
//		try {
//			
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, myId);
//			pstmt.setString(2, user);
//			pstmt.executeUpdate();
//
//		} catch (SQLException e) {
//			System.out.println("UserDao addFoller method error.");
//			e.printStackTrace();
//		} finally {
//			JdbcUtil.close(pstmt);
//		}
//
//	}
//
//	public void addFollowing(String myId, String user) {
//
//		sql = "update rt_user set following = ? where id = ?";
//
//		try {
//			
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, user);
//			pstmt.setString(2, myId);
//			pstmt.executeUpdate();
//
//		} catch (SQLException e) {
//			System.out.println("UserDao addFollwing method error.");
//			e.printStackTrace();
//		} finally {
//			JdbcUtil.close(pstmt);
//		}
//	}
	
	/**
	 * id�� ã�� �޼ҵ�
	 */
	public String searchId(String email){
		sql = "select id from rt_user where email=?";
		String id = "";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next()){
				id = rs.getString("id");
				return id;
			} else{
				return null;
			}
		} catch(SQLException e){
			System.out.println("SearchId error : " +e.getMessage());
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return null;
	}
	
	/**
	 * pw�� ã�� �޼ҵ�
	 */
	public String searchPw(String id, String email){
		sql = "select pw from rt_user where id=? and email=?";
		String pw = "";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			if(rs.next()){
				pw = rs.getString("pw");
				return pw;
			} else{
				return null;
			}
		} catch(SQLException e){
			System.out.println("SearchId error : " +e.getMessage());
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return null;
	}
	
}