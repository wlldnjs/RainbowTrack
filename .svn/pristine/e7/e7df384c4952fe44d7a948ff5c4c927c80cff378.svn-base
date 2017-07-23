package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import bean.ReplyBean;
import db.DBAction;
import util.JdbcUtil;

public class ReplyDao {

	DBAction db = DBAction.getInstance();
	Connection conn = db.getConnection();
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql = null;

	private static ReplyDao instance;

	public static ReplyDao getInstance() {
		if (instance == null) {
			instance = new ReplyDao();
		}
		return instance;
	}

	/*
	 * 게시글을 데이터베이스에 저장하는 메소드. ReplyBean 객체를 파라미터 값으로 받고 그 내용을 데이터베이스에 저장한다.
	 */
	public boolean insertReply(ReplyBean bean) {

		sql = "insert into rt_reply (user_id, reply, replydate, board_idx, reply_idx) "
				+ "values (?, ?, sysdate, ?, ?)";
		boolean check = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getUser_id());
			pstmt.setString(2, bean.getReply());
			pstmt.setInt(3, bean.getBoard_idx());
			pstmt.setInt(4, bean.getReply_idx());
			int result = pstmt.executeUpdate();
			if (result < 1) {
				System.out.println("insertReply method 에러 발생.");
			} else {
				check = true;
				return check;
			}
		} catch (SQLException e) {
			System.out.println("insertReply method 에러 발생.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return check;
	}

	/*
	 * ResultSet을 통해 ReplyBean 객체를 생성하는 메소드. ResultSet 객체를 파라미터 값으로 받는다. 생성한
	 * ReplyBean 객체를 리턴한다.
	 */
	protected ReplyBean getReplyBeanFromResultSet(ResultSet rs) {

		ReplyBean bean = new ReplyBean();
		try {
			bean.setUser_id(rs.getString("user_id"));
			bean.setReply(rs.getString("reply"));
			bean.setReplyDate(rs.getString("replydate"));
			bean.setBoard_idx(rs.getInt("board_idx"));
			bean.setReply_idx(rs.getInt("reply_idx"));
		} catch (SQLException e) {
			System.out.println("getReplyBeanFromResultSet method 에러 발생.");
			e.printStackTrace();
		}
		// finally {
		// JdbcUtil.close(rs);
		// JdbcUtil.close(pstmt);
		// }

		return bean;
	}

	/*
	 * 해당 board_idx에 있는 댓글을 collection(vector)에 담아서 리턴한다.
	 */
	public List<ReplyBean> getAllReply(int board_idx) {

		sql = "select user_id, reply, replydate, board_idx, reply_idx from rt_reply " + "where board_idx = ? "
				+ "order by reply_idx";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				List<ReplyBean> replyList = new Vector<ReplyBean>();
				do {
					replyList.add(getReplyBeanFromResultSet(rs));
				} while (rs.next());
				return replyList;
			}
		} catch (SQLException e) {
			System.out.println("getAllReply method 에러 발생.");
			e.printStackTrace();
		}
		// finally {
		// JdbcUtil.close(rs);
		// JdbcUtil.close(pstmt);
		// }

//		return Collections.emptyList();
		return null;
	}

	public int getLastReplyIdx(int board_idx) {

		sql = "select max(reply_idx) from rt_reply where board_idx = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			rs = pstmt.executeQuery();

			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			System.out.println("getLastReplyIdx method 에러 발생.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return 0;
	}

	public boolean deleteReply(int board_idx, int reply_idx) {

		sql = "delete from rt_reply where board_idx = ? and reply_idx = ?";
		boolean check = false;
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setInt(2, reply_idx);
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				check = true;
				return check;
			}
		} catch (SQLException e) {
			System.out.println("deleteReply method 에러 발생.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return check;
	}

	// 게시글을 삭제하면 모든 답글이 삭제된다.
	public void deleteAllReplyByIdx(int board_idx) {

		sql = "delete from rt_reply where board_idx = ?";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("deleteAllReplyByIdx method 에러 발생.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	public void deleteAllReplyById(String id) {

		sql = "delete from rt_reply where user_id = ?";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("deleteAllReplyById method 에러 발생.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public ReplyBean getOneReply(int boardIdx, int replyIdx) {
		
		sql = "select * from rt_reply where board_idx = ? and reply_idx = ?";
		
		ReplyBean replyBean = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardIdx);
			pstmt.setInt(2, replyIdx);
			rs = pstmt.executeQuery();
			rs.next();
			replyBean = getReplyBeanFromResultSet(rs);
			
			return replyBean;
			
		} catch (SQLException e) {
			System.out.println("getOneReply method 에러 발생.");
			e.printStackTrace();
		}
		return null;
	}
}
