package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import bean.BoardBean;
import bean.UserBean;
import db.DBAction;
import util.JdbcUtil;

public class BoardDao {

	DBAction db = DBAction.getInstance();
	Connection conn = db.getConnection();
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql = null;

	private static BoardDao instance;

	public static BoardDao getInstance() {
		if (instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}

	/*
	 * �Խñ��� �����ͺ��̽��� �����ϴ� �޼ҵ�. BoardBean ��ü�� �Ķ���� ������ �ް� �� ������ �����ͺ��̽��� �����Ѵ�.
	 */
	public void insertBoard(BoardBean bean) {

		sql = "insert into rt_board (user_id, board, musicfilepath, boarddate, hashtags, idx) "
				+ "values (?, ?, ?, sysdate, ?, board_idx_inc.NEXTVAL)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getUser_id());
			pstmt.setString(2, bean.getBoard());
			pstmt.setString(3, bean.getMusicFilePath());
			pstmt.setString(4, bean.getHashTags());
			int result = pstmt.executeUpdate();
			if (result < 1) {
				System.out.println("insert method ���� �߻�.");
			}
		} catch (SQLException e) {
			System.out.println("insert method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}

	}

	/*
	 * ResultSet�� ���� BoardBean ��ü�� �����ϴ� �޼ҵ�. ResultSet ��ü�� �Ķ���� ������ �޴´�. ������
	 * BoardBean ��ü�� �����Ѵ�.
	 */
	protected BoardBean getBoardBeanFromResultSet(ResultSet rs) {
		
		UserDao userDao = UserDao.getInstance();
		
		BoardBean boardBean = new BoardBean();
		try {
			String id = rs.getString("user_id");
			UserBean userBean = userDao.selectUser(id);
			
			boardBean.setUser_id(id);
			boardBean.setBoard(rs.getString("board"));
			boardBean.setMusicFilePath(rs.getString("musicfilepath"));
			boardBean.setBoardDate(rs.getString("boarddate"));
			boardBean.setHashTags(rs.getString("hashtags"));
			boardBean.setLikeUser(rs.getString("likeusers"));
			boardBean.setIdx(rs.getInt("idx"));
			boardBean.setImage(userBean.getImage());
			boardBean.setLikeCount(rs.getInt("likecount"));
			
		} catch (SQLException e) {
			System.out.println("getBoardBeanFromResultSet method ���� �߻�.");
			e.printStackTrace();
		}

		return boardBean;
	}

	/*
	 * ��ü �Խù��� ���� �����ϴ� �޼ҵ�.
	 */
	public int selectBoardCount() {

		sql = "select count(*) from rt_board";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("selectCount method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}

		return 0;
	}

	/*
	 * �־��� ����(firstRow, endRow)�� �ִ� �Խù��� collection(vector)�� ��Ƽ� �����Ѵ�.
	 */
	public List<BoardBean> getBoardList(int firstRow, int endRow) {

		/*
		 * sql =
		 * "select user_id, board, musicfile_path, boarddate, hashtags, likeuser, idx from "
		 * +
		 * "(select rownum rnum, user_id, board, musicfile_path, boarddate, hashtags, likeuser, idx from "
		 * +
		 * "(select * from im_board a order by a.idx desc) where rownum <= ? ) where rnum >= ?"
		 * ;
		 */

		sql = "select * from (select rownum rnum, user_id, board, musicfilepath, boarddate, hashtags, likeusers, idx, likecount"
				+ " from(select * from rt_board m order by m.boarddate desc) " + "where rownum <= ?) where rnum >= ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, firstRow);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				List<BoardBean> boardList = new Vector<BoardBean>();
				do {
					boardList.add(getBoardBeanFromResultSet(rs));
				} while (rs.next());
				return boardList;
			}
			return Collections.emptyList();
		} catch (SQLException e) {
			System.out.println("getBoardList method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return Collections.emptyList();
	}

	/*
	 * im_board ���̺��� ������ idx ���� ��ȯ�Ѵ�.
	 */
	public int getLastIdx() {

		sql = "select max(idx) from rt_board";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("getLastIdx method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}

		return 0;
	}

	public void deleteBoardByIdx(int idx) {

		sql = "delete from rt_board where idx = ?";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("deleteBoardByIdx method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stmt);
		}
	}

	public void deleteBoardById(String id) {

		sql = "delete from rt_board where user_id = ?";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("deleteBoardById method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(stmt);
		}
	}

	public List<BoardBean> getBoardListFromId(String id, int firstRow, int endRow) {

		sql = "select * from (select rownum rnum, user_id, board, musicfilepath, boarddate, hashtags, likeusers, idx, likecount "
				+ "from (select * from rt_board where user_id = ?) where rownum <= ? )"
				+ "where rnum >= ? order by boarddate desc";

		List<BoardBean> boardList = new Vector<BoardBean>();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, endRow);
			pstmt.setInt(3, firstRow);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				do {
					boardList.add(getBoardBeanFromResultSet(rs));
				} while (rs.next());
				return boardList;
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.out.println("getBoardListFromId method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}

		return null;
	}

	public List<BoardBean> getBoardListFromHashtag(String search) {

		sql = "select * from rt_board order by boarddate desc";
		List<BoardBean> boardList = new Vector<BoardBean>();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String hashtags = rs.getString("hashtags");

				if (hashtags != null) {

					StringTokenizer st = new StringTokenizer(hashtags, "#");

					while (st.hasMoreTokens()) {
						if (st.nextToken().trim().equals(search)) {
							boardList.add(getBoardBeanFromResultSet(rs));
						}
					}
				}
			}
			if (boardList.size() >= 1) {
				return boardList;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("getBoardListFromHashtag method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}

		return null;
	}

	
	public int updateLikeUser(String id, int idx, int likeCount) {
		
		sql = "update rt_board set likeusers = ?, likecount = ? where idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, likeCount);
			pstmt.setInt(3, idx);
			int result = pstmt.executeUpdate();
			System.out.println("BoardDao addLikeUser method result: " + result);
			return result;
		} catch (SQLException e) {
			System.out.println("addLikeUser method ���� �߻�.");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return 0;
	}
	
	public BoardBean getOneBoardBean(int idx) {
		
		sql = "select * from rt_board where idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs= pstmt.executeQuery();
			
			if (rs.next()) {
				BoardBean boardBean = null;
				boardBean = getBoardBeanFromResultSet(rs);
				return boardBean;
			}
			
		} catch (SQLException e) {
			System.out.println("getOneBoardBean method ���� �߻�.");
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return  null;
	}
	
	public String getLikeUsers(int idx) {
		
		sql = "select likeusers from rt_board where idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			System.out.println("getLikeUsers method ���� �߻�.");
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return null;
	}
	
	public List<BoardBean> getTopTenList(){
		sql = "select * from (select * from rt_board order by likecount desc) where rownum <=10";
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				List<BoardBean> boardList = new Vector<BoardBean>();
				do {
					boardList.add(getBoardBeanFromResultSet(rs));
				} while (rs.next());
				return boardList;
			}
			return Collections.emptyList();
		} catch(SQLException e){
			System.out.println("getTopTenList error : " +e.getMessage());
		} finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		
		return Collections.emptyList();
	}
}