package com.database.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.database.bean.History;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

/**
 * 对于历史表的相关操作
 * 
 * @author wuhao
 * 
 */
public class DatabaseHistory {
	/**
	 * 插入到history表中
	 * 
	 * @param history
	 */
	public void insert_into_historytable(History history) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_insert_into_history = "INSERT INTO historytable(history_username,history_book_number,history_book_name,history_borrow_time)Values(?,?,?,?)";
			pstm = (PreparedStatement) conn
					.prepareStatement(sql_insert_into_history);
			pstm.setString(1, history.getHistory_username());
			pstm.setString(2, history.getHistory_book_number());
			pstm.setString(3, history.getHistory_book_name());
			pstm.setString(4, history.getHistory_borrow_time());
			pstm.executeUpdate();// 提交数据到表中
			System.out.println("添加成功");
			// JOptionPane.showMessageDialog(null, "添加成功", "提醒",
			// JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println("插入异常");
			e.printStackTrace();

		} finally {
			JDBC_Connection.free(rs, conn, pstm);
		}
	}

	public List<History> QueryHistorytable(String history_username) {

		List<History> list_history = new ArrayList<History>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_select_booktable = "select * from historytable where history_username=?";
			pstm = (PreparedStatement) conn
					.prepareStatement(sql_select_booktable);
			pstm.setString(1, history_username);
			rs = (ResultSet) pstm.executeQuery();
			while (rs.next()) {
				History history = new History();
				history.setHistory_username(rs.getString("history_username"));
				history.setHistory_book_number(rs
						.getString("history_book_number"));
				history.setHistory_book_name(rs.getString("history_book_name"));
				history.setHistory_borrow_time(rs
						.getString("history_borrow_time"));
				list_history.add(history);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(rs, conn, pstm);
			System.out.println("成功关闭数据库链接");
		}
		return list_history;

	}

}
