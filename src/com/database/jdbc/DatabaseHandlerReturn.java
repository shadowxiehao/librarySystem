package com.database.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.database.info.Return;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * ��return�������ز���
 *
 * @author XieHao
 *
 */
public class DatabaseHandlerReturn {
    /**
     * ��һ��list����ʾ�����ڽ���鱾
     *
     * @param
     * @return ���ص�Ȼ����Ϣ�б���,������Կ�����
     */
    public List<Return> queryBookOnReturn() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Return> list_books = new ArrayList<Return>();
        try {
            conn = JDBC_Connection.getConnection();
            //���ϲ�ѯ,�������������������
            String sql_select_bookname = "select * from returntable";
            pstm = conn.prepareStatement(sql_select_bookname);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Return returner = new Return();
                returner.setReturn_reader_username(rs.getString("reader_username"));
                returner.setReturn_borrow_book_name(rs.getString("borrow_book_name"));
                returner.setReturn_time(rs.getString("return_time"));
                returner.setReturn_money(rs.getString("return_money"));
                returner.setReturn_overtime(rs.getInt("return_overtime"));
                returner.setReturn_borrow_time(rs.getString("borrow_time"));
                list_books.add(returner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBC_Connection.free(rs, conn, pstm);
        }
        return list_books;
    }
}
