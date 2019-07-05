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
			// System.out.println("��������"+e.getMessage());
		}
	}

	/***
	 * �������ݿ⺯��
	 * 
	 * @return �������Ӷ���
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(url, username,
					password);
			System.out.println("�������ݿ�ɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;

	}

	/****
	 * �ر����ݿ�
	 * 
	 * @param rs
	 *            �����ݿ��м�¼������ɵļ��ϡ�
	 * @param conn
	 *            ����Java���ݿ��JavaӦ�ó���֮�����Ҫ���󲢴������е�Statement����
	 * @param stmt
	 *            SQL���Ķ���
	 */
	public static void free(ResultSet rs, Connection conn, Statement stmt) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			System.out.println("�ر�resultsetʧ��");
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();

				}
			} catch (SQLException e) {
				System.out.println("�ر�connectionʧ��");
				e.printStackTrace();

			} finally {
				try {
					if (stmt != null) {
						stmt.close();

					}
				} catch (SQLException e) {
					System.out.println("�ر�statementʧ��");
					e.printStackTrace();
				}
			}
		}
	}
}
