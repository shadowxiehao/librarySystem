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
 * ��borrow�����ز���
 * @author XieHao
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
			pstm = conn.prepareStatement(sql_insert_into_borrow);
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
			System.out.println("�ɹ��ر����ݿ�����");
		}
		return borrow;
	}

	/***
	 * ������ڲ�ѯborrow��
	 * @param borrow_book_number �Ѿ�������鱾��
	 * @return ����borrow����
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
			System.out.println("�ɹ��ر����ݿ�����");
		}
		return borrow;
	}

	/***
	 * ����ɾ��borrow���е����ݣ���ͼ��黹��ʱ�����
	 * @param borrow_book_number �黹���鱾��
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
	 * ������ڲ�ѯborrow�����Ƿ��Ѿ�����ͬ�˽���ͬһ����,�еĻ����ܶ��
	 * @param borrow_book_number ��Ҫ������鱾��
	 * @param borrow_reader_username ����������
	 * @return ����ͬһ��û�н�ͬһ����>0��
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
			System.out.println("�ɹ��ر����ݿ�����");
		}
		return false;
	}
	/**
	 * ��һ��list����ʾ�����ڽ���鱾
	 *
	 * @param
	 * @return ���ص�Ȼ����Ϣ�б���,������Կ�����
	 */
	public List<Borrow> queryBookOnBorrow() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Borrow> list_books = new ArrayList<Borrow>();
		try {
			conn = JDBC_Connection.getConnection();
			//���ϲ�ѯ,�������������������
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
