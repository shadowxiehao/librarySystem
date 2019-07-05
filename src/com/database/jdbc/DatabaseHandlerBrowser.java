package com.database.jdbc;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.database.bean.Browser;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

/**
 * 对浏览历史表的相关查询
 * 
 * @author wuhao
 * 
 */
public class DatabaseHandlerBrowser {
	/**
	 * 对浏览历史表进行查询
	 * 
	 * @param browser_reader_username
	 *            读者号
	 * @return 浏览对象
	 */
	public List<Browser> QueryBrowserTable(String browser_reader_username) {
		List<Browser> list_browser = new ArrayList<Browser>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_select_browserhistorytable = "select * from browserhistorytable where browser_reader_username=?";
			pstm = (PreparedStatement) conn
					.prepareStatement(sql_select_browserhistorytable);
			pstm.setString(1, browser_reader_username);
			rs = (ResultSet) pstm.executeQuery();
			while (rs.next()) {
				Browser browser = new Browser();
				browser.setBrowser_reader_username(rs
						.getString("browser_reader_username"));
				
				browser.setBrowser_bookname(rs.getString("browser_bookname"));
				list_browser.add(browser);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Connection.free(rs, conn, pstm);
			System.out.println("成功关闭数据库链接");
		}
		return list_browser;
	}

	/**
	 * 插入数据到浏览历史表中，当执行搜索的时候就调用这个函数
	 * 
	 * @param browser
	 *            浏览的结果
	 */
	public void insert_into_browsertable(Browser browser) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = JDBC_Connection.getConnection();
			String sql_insert_into_browser = "INSERT INTO browserhistorytable(browser_reader_username,browser_bookname)Values(?,?)";
			pstm = (PreparedStatement) conn
					.prepareStatement(sql_insert_into_browser);
			pstm.setString(1, browser.getBrowser_reader_username());			
			pstm.setString(2, browser.getBrowser_bookname());
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
}
