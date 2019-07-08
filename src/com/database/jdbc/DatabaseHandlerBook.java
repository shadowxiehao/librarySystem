package com.database.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.database.info.Book;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * ��book�������ز���
 * 
 * @author XieHao
 * 
 */
public class DatabaseHandlerBook {
	/**
	 * ��һ��list����ʾ�������������鱾����ʾģ������
	 * 
	 * @param book_name ��Ȼ��һ��ʼд��name,���Ǻ���֧������ŵ�����,������Ҳ���������,��,�����������벻���õ�,�����Ȳ���������
	 * @return ���ص�Ȼ����Ϣ�б���,������Կ�����
	 */
	public List<Book> queryBook(String book_name) {
		// Book book=null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Book> list_books = new ArrayList<Book>();
		try {
			conn = JDBC_Connection.getConnection();
			//���ϲ�ѯ,�������������������
			String sql_select_bookname = "select * from booktable where book_name LIKE ? UNION select * from booktable where book_number LIKE ?";
			pstm = conn.prepareStatement(sql_select_bookname);
			pstm.setString(1, "%" + book_name + "%");
			pstm.setString(2, "%" + book_name + "%");
			rs = pstm.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setBook_number(rs.getString("book_number"));
				book.setBook_name(rs.getString("book_name"));
				book.setBook_author(rs.getString("book_author"));
				book.setBook_publishtime(rs.getString("book_publishtime"));
				book.setBook_amount(rs.getInt("book_amount"));
				book.setAdmin_username(rs.getString("admin_username"));
				list_books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(rs, conn, pstm);
		}
		return list_books;
	}

		/**
		 * �տ�ʼֱ����ʾ�������������鱾
		 * @param
		 * @return ���ص�Ȼ����Ϣ�б���,������Կ�����
		 */
		public List<Book> queryBook() {
			// Book book=null;
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			List<Book> list_books = new ArrayList<Book>();
			try {
				conn = JDBC_Connection.getConnection();
				//���ϲ�ѯ,�������������������
				String sql_select_bookname = "select * from booktable";
				pstm = conn.prepareStatement(sql_select_bookname);
				rs = pstm.executeQuery();
				while (rs.next()) {
					Book book = new Book();
					book.setBook_number(rs.getString("book_number"));
					book.setBook_name(rs.getString("book_name"));
					book.setBook_author(rs.getString("book_author"));
					book.setBook_publishtime(rs.getString("book_publishtime"));
					book.setBook_amount(rs.getInt("book_amount"));
					book.setAdmin_username(rs.getString("admin_username"));
					list_books.add(book);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Connection.free(rs, conn, pstm);
			}
			return list_books;
		}

	/**
	 * ����ɾ���鱾
	 * 
	 * @param book_number
	 *            �鱾��
	 */
	public void delete_Booktable(String book_number) {

		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_delete_books = "delete from booktable where book_number=?";
			pstm = conn.prepareStatement(sql_delete_books);
			pstm.setString(1, book_number);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(null, conn, pstm);
		}

	}

	public Book queryBookBybooknumber(String book_number) {
		Book book = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_select_booktable = "select * from booktable where book_number=?";
			pstm = conn
					.prepareStatement(sql_select_booktable);
			pstm.setString(1, book_number);
			rs = pstm.executeQuery();
			while (rs.next()) {
				book = new Book();
				book.setBook_number(rs.getString("book_number"));
				book.setBook_name(rs.getString("book_name"));
				book.setBook_author(rs.getString("book_author"));
				book.setBook_publishtime(rs.getString("book_publishtime"));
				book.setBook_amount(rs.getInt("book_amount"));
				book.setAdmin_username(rs.getString("admin_username"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(rs, conn, pstm);
			System.out.println("�ɹ��ر����ݿ�����");
		}
		return book;

	}

	/**
	 * �����޸�ͼ����Ϣ
	 * 
	 * @param book
	 * @return
	 */
	public Book update_booktable(Book book) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql_update_reader = "UPDATE booktable set book_number=? ,book_name=? ,book_author=? ,book_publishtime=? ,book_amount=? ,admin_username=? WHERE book_number=?";
		try {
			conn = JDBC_Connection.getConnection();
			pstm = conn.prepareStatement(sql_update_reader);
			pstm.setString(1, book.getBook_number());
			pstm.setString(2, book.getBook_name());
			pstm.setString(3, book.getBook_author());
			pstm.setString(4, book.getBook_publishtime());
			pstm.setInt(5, book.getBook_amount());
			pstm.setString(6, book.getAdmin_username());
			pstm.setString(7, book.getBook_number());
			pstm.executeUpdate();// �ύ���ݵ�����
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(null, conn, pstm);

		}

		return book;

	}

	/***
	 * ���ڽ�ͼ���еĿ��������Ϊ0
	 * 
	 * @param book
	 * @return
	 */
	public Book updateBookToSetNum(Book book) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql_update_reader = "UPDATE booktable set book_amount=? WHERE book_number=?";
		try {
			conn = JDBC_Connection.getConnection();
			pstm = conn.prepareStatement(sql_update_reader);
			pstm.setInt(1, book.getBook_amount());
			pstm.setString(2, book.getBook_number());
			pstm.executeUpdate();// �ύ���ݵ�����
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(null, conn, pstm);

		}

		return book;
	}
	/**
	 * ִ�й黹�����󣬽���Ӧ���鱾�Ŀ��������Ϊ1���Ա�����������ٽ��
	 * @param book
	 * @return
	 */
	public Book updateBookToSetOne(Book book) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql_update_reader = "UPDATE booktable set book_amount=? WHERE book_number=?";
		try {
			conn = JDBC_Connection.getConnection();
			pstm = conn.prepareStatement(sql_update_reader);
			pstm.setInt(1, book.getBook_amount());
			pstm.setString(2, book.getBook_number());
			pstm.executeUpdate();// �ύ���ݵ�����
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(null, conn, pstm);

		}

		return book;
	}

}