package com.database.jdbc;

import java.sql.SQLException;
import javax.swing.JOptionPane;

import com.database.info.Book;
import com.database.info.Reader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/***
 * 数据库的相关操作类
 *
 * @author XieHao
 *
 */
public class DatabaseHandler {
    /***
     * 插入书本表中
     *
     * @param book
     * 书本的对象
     */
    public void insert_into_booktable(Book book) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = JDBC_Connection.getConnection();
            String sql_insert_into_book = "INSERT INTO booktable(book_number,book_name,book_author,book_publishtime," +
                    "book_amount,admin_username)Values(?,?,?,?,?,?)";
            pstm = conn.prepareStatement(sql_insert_into_book);
            pstm.setString(1, book.getBook_number());
            pstm.setString(2, book.getBook_name());
            pstm.setString(3, book.getBook_author());
            pstm.setString(4, book.getBook_publishtime());
            pstm.setInt(5, book.getBook_amount());
            pstm.setString(6, book.getAdmin_username());

            pstm.executeUpdate();// 提交数据到表中
            System.out.println("添加成功");
            JOptionPane.showMessageDialog(null, "添加成功",
                    "提醒", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.out.println("插入异常");
            e.printStackTrace();
        } finally {
            JDBC_Connection.free(rs, conn, pstm);//随手关门哈
        }
    }

    /***
     * 插入数据到reader表中
     *
     * @param reader
     *            读者reader对象
     */
    public void insert_into_readertable(Reader reader) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = JDBC_Connection.getConnection();
            String sql_insert_into_reader = "INSERT INTO readertable(reader_username,reader_password,reader_name," +
                    "authority,reader_dept,reader_borrow,reader_degree)Values(?,?,?,?,?,?,?)";
            pstm = conn
                    .prepareStatement(sql_insert_into_reader);
            pstm.setString(1, reader.getReader_username());
            pstm.setString(2, reader.getReader_password());
            pstm.setString(3, reader.getReader_name());
            pstm.setInt(4, reader.getAuthority());
            pstm.setString(5, reader.getReader_dept());
            pstm.setInt(6, reader.getReader_borrow());
            pstm.setString(7, reader.getReader_degree());

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

    /**
     * 通过读者的登录号吗查询读者信息
     *
     * @param reader_username 读者的登录号码
     * @return 返回reader对象
     */
    public Reader queryByreaderusername(String reader_username) {
        Reader reader = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = JDBC_Connection.getConnection();
            String sql_select_readertable = "select * from readertable where reader_username=?";
            pstm = conn
                    .prepareStatement(sql_select_readertable);
            pstm.setString(1, reader_username);
            rs = pstm.executeQuery();
            while (rs.next()) {
                reader = new Reader();
                reader.setReader_username(rs.getString("reader_username"));
                reader.setReader_password(rs.getString("reader_password"));
                reader.setReader_name(rs.getString("reader_name"));
                reader.setAuthority(rs.getInt("authority"));
                reader.setReader_dept(rs.getString("reader_dept"));
                reader.setReader_borrow(rs.getInt("reader_borrow"));
                reader.setReader_degree(rs.getString("reader_degree"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBC_Connection.free(rs, conn, pstm);
            System.out.println("成功关闭数据库链接");
        }
        return reader;

    }

    /**
     * 删除指定登录号的读者的信息
     *
     * @param reader_username 对着的登录号码
     */
    public void deleteReader(String reader_username) {
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = JDBC_Connection.getConnection();
            String sql_delete_reader = "delete from readertable where reader_username=?";
            pstm = conn.prepareStatement(sql_delete_reader);
            pstm.setString(1, reader_username);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBC_Connection.free(null, conn, pstm);
        }
    }

    /**
     * 用于修改读者信息
     *
     * @param reader
     */
    public void updateReaderTable(Reader reader) {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql_update_reader = "UPDATE readertable set reader_username=? ,reader_password=? , " +
                "reader_name=? ,authority=? ,reader_dept=?,reader_borrow=?,reader_degree=? WHERE reader_username=?";
        try {
            conn = JDBC_Connection.getConnection();
            pstm = conn.prepareStatement(sql_update_reader);
            pstm.setString(1, reader.getReader_username());
            pstm.setString(2, reader.getReader_password());
            pstm.setString(3, reader.getReader_name());
            pstm.setInt(4, reader.getAuthority());
            pstm.setString(5, reader.getReader_dept());
            pstm.setInt(6, reader.getReader_borrow());
            pstm.setString(7, reader.getReader_degree());
            pstm.setString(8, reader.getReader_username());
            pstm.executeUpdate();// 提交数据到表中
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBC_Connection.free(null, conn, pstm);

        }
    }

}
