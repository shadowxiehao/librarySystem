package com.database.jdbc;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.database.bean.Book;
import com.database.bean.Borrow;
import java.sql.*;


/***
 * 对borrow表的相关操作
 * @author wuhao
 *
 */
public class DatabaseHandlerBorrow {
	/**
	 * 执行出借功能时调用这个功能，用于将数据插入borrow表中，用于读者可以查询自己的借阅历史
	 * @param borrow 返回borrow对象
	 */
	public void insert_into_borrowtable(Borrow borrow) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_insert_into_borrow = "INSERT INTO borrowtable(borrow_reader_username,borrow_book_number,borrow_book_name,borrow_time)Values(?,?,?,?)";
			pstm = conn
					.prepareStatement(sql_insert_into_borrow);
			pstm.setString(1, borrow.getBorrow_reader_username());
			pstm.setString(2, borrow.getBorrow_book_number());
			pstm.setString(3, borrow.getBorrow_book_name());
			pstm.setString(4, borrow.getBorrow_time());		
			pstm.executeUpdate();// 提交数据到表中
			System.out.println("添加成功");
			JOptionPane.showMessageDialog(null, "添加成功", "提醒",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println("插入异常");
			e.printStackTrace();

		} finally {
			JDBC_Connection.free(rs, conn, pstm);
		}
	}
	/***
	 * 这个用于查询borrow表
	 * @param borrow_book_number 已经借出的书本号
	 * @return 返回borrow对象
	 */
	public Borrow QueryBorrowTable(String borrow_book_number){
		Borrow borrow = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_select_booktable = "select * from borrowtable where borrow_book_number=?";
			pstm = conn
					.prepareStatement(sql_select_booktable);
			pstm.setString(1, borrow_book_number);
			rs = pstm.executeQuery();
			while (rs.next()) {
				borrow = new Borrow();
				borrow.setBorrow_reader_username(rs.getString("borrow_reader_username"));
				borrow.setBorrow_book_number(rs.getString("borrow_book_number"));
				borrow.setBorrow_book_name(rs.getString("borrow_book_name"));
				borrow.setBorrow_time(rs.getString("borrow_time"));				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(rs, conn, pstm);
			System.out.println("成功关闭数据库链接");
		}
		return borrow;
		
	}
	/***
	 * 用于删除borrow表中的内容，当图书归还的时候调用
	 * @param borrow_book_number 归还的书本号
	 */
	public void DeleteBorrow(String borrow_book_number){
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_delete_borrow = "delete from borrowtable where borrow_book_number=?";
			pstm = conn.prepareStatement(sql_delete_borrow);
			pstm.setString(1, borrow_book_number);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(null, conn, pstm);
		}
	}

}
