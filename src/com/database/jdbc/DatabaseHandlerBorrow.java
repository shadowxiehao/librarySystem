package com.database.jdbc;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.database.bean.Book;
import com.database.bean.Borrow;
import java.sql.*;


/***
 * ��borrow�����ز���
 * @author wuhao
 *
 */
public class DatabaseHandlerBorrow {
	/**
	 * ִ�г��蹦��ʱ����������ܣ����ڽ����ݲ���borrow���У����ڶ��߿��Բ�ѯ�Լ��Ľ�����ʷ
	 * @param borrow ����borrow����
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
			pstm.executeUpdate();// �ύ���ݵ�����
			System.out.println("��ӳɹ�");
			JOptionPane.showMessageDialog(null, "��ӳɹ�", "����",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			System.out.println("�����쳣");
			e.printStackTrace();

		} finally {
			JDBC_Connection.free(rs, conn, pstm);
		}
	}
	/***
	 * ������ڲ�ѯborrow��
	 * @param borrow_book_number �Ѿ�������鱾��
	 * @return ����borrow����
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
			System.out.println("�ɹ��ر����ݿ�����");
		}
		return borrow;
		
	}
	/***
	 * ����ɾ��borrow���е����ݣ���ͼ��黹��ʱ�����
	 * @param borrow_book_number �黹���鱾��
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
