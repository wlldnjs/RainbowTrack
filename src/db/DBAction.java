package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAction {
	private static DBAction instance;
	public Connection conn;

	public DBAction() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@210.123.255.149:1521:orcl";
		String user = "hr";
		String user_pw = "hr";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "hr", "hr");
			System.out.println("Connection Successful");
		} catch (ClassNotFoundException e) {
			System.out.println("Error : " + e.getMessage());
		} catch (SQLException ex) {
			System.out.println("SQLError : " + ex.getMessage());
		}
	}

	public static DBAction getInstance() {
		if (instance == null)
			instance = new DBAction();
		return instance;
	}

	public Connection getConnection() {
		return this.conn;
	}
}