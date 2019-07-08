package com.database.jdbc;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.database.info.Borrow;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


/***
 * 对borrow表的相关操作
 * @author XieHao
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
			pstm = conn.prepareStatement(sql_insert_into_borrow);
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
			pstm = conn.prepareStatement(sql_select_booktable);
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
	 * 这个用于查询borrow表
	 * @param borrow_book_number 已经借出的书本号
	 * @return 返回borrow对象
	 */
	public Borrow QueryBorrowTable(String borrow_book_number,String borrwow_name){
		Borrow borrow = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_select_booktable = "select * from borrowtable where borrow_book_number=? and borrow_reader_username=?";
			pstm = conn.prepareStatement(sql_select_booktable);
			pstm.setString(1, borrow_book_number);
			pstm.setString(2, borrwow_name);
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
	public void DeleteBorrow(String borrow_book_number,String borrwow_name){
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_delete_borrow = "delete from borrowtable where borrow_book_number=? and borrow_reader_username=?";
			pstm = conn.prepareStatement(sql_delete_borrow);
			pstm.setString(1, borrow_book_number);
			pstm.setString(2, borrwow_name);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(null, conn, pstm);
		}
	}
	/***
	 * 这个用于查询borrow表中是否已经有相同人借了同一本书,有的话不能多借
	 * @param borrow_book_number 将要借出的书本号
	 * @param borrow_reader_username 借书者名字
	 * @return 满足同一人没有借同一本书>0本
	 */
	public boolean IFSame(String borrow_book_number,String borrow_reader_username){
		Borrow borrow = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_select_booktable = "select count(*) from borrowtable where borrow_book_number=? and borrow_reader_username=?";
			pstm = conn.prepareStatement(sql_select_booktable);
			pstm.setString(1, borrow_book_number);
			pstm.setString(2, borrow_reader_username);
			rs = pstm.executeQuery();
			if(rs.next()){
				if(Integer.valueOf(rs.getString(1))==0){
					return true;
				}
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(rs, conn, pstm);
			System.out.println("成功关闭数据库链接");
		}
		return false;
	}
	/**
	 * 用一个list来显示所有在借的书本
	 *
	 * @param
	 * @return 返回当然是信息列表啦,具体可以看代码
	 */
	public List<Borrow> queryBookOnBorrow() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Borrow> list_books = new ArrayList<Borrow>();
		try {
			conn = JDBC_Connection.getConnection();
			//联合查询,可以搜索到书名或序号
			String sql_select_bookname = "select * from borrowtable";
			pstm = conn.prepareStatement(sql_select_bookname);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Borrow borrow = new Borrow();
				borrow.setBorrow_reader_username(rs.getString("borrow_reader_username"));
				borrow.setBorrow_book_number(rs.getString("borrow_book_number"));
				borrow.setBorrow_book_name(rs.getString("borrow_book_name"));
				borrow.setBorrow_time(rs.getString("borrow_time"));
				list_books.add(borrow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(rs, conn, pstm);
		}
		return list_books;
	}
}
