package com.database.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class JDBC_Connection {
	static String drivername = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/librarydb";
	static String username = "root";
	static String password = "123";

	static {
		try {
			Class.forName(drivername);
		} catch (Exception e) {
			// TODO: handle exception
			// System.out.println("加载驱动"+e.getMessage());
		}
	}

	/***
	 * 连接数据库函数
	 * 
	 * @return 返回连接对象
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(url, username,
					password);
			System.out.println("连接数据库成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;

	}

	/****
	 * 关闭数据库
	 * 
	 * @param rs
	 *            是数据库中记录或行组成的集合。
	 * @param conn
	 *            连接Java数据库和Java应用程序之间的主要对象并创建所有的Statement对象
	 * @param stmt
	 *            SQL语句的对象
	 */
	public static void free(ResultSet rs, Connection conn, Statement stmt) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			System.out.println("关闭resultset失败");
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();

				}
			} catch (SQLException e) {
				System.out.println("关闭connection失败");
				e.printStackTrace();

			} finally {
				try {
					if (stmt != null) {
						stmt.close();

					}
				} catch (SQLException e) {
					System.out.println("关闭statement失败");
					e.printStackTrace();
				}
			}
		}
	}
}
