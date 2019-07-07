package com.database.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;

/*
* �����������ӵĽ���������
*/
public class JDBC_Connection {
	static String drivername = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/librarydb?useSSL=false";
	static String username = "root";
	static String password = "xi"+"eh"+"a"+"o@7"+"9952"+"2476";

	static {
		try {
			Class.forName(drivername);// ע�� JDBC ����
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
	public static Connection getConnection() {// ������
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
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
