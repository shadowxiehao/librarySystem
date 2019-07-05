package com.database.jdbc;

import java.sql.SQLException;

import com.database.bean.Admin;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

/***
 * ������û���admin�������ص����ݿ����
 * @author wuhao
 *
 */
public class DatabaseHandlerAdmin {
	/***
	 * ����admin���username�������ݵ���ز���
	 * @param admin_username admin�ĵ�¼�˺�
	 * @return Admin�Ķ���
	 */
	public Admin queryAdmin(String admin_username){
		Admin admin=null;
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try{
			conn=JDBC_Connection.getConnection();
			String sql_select_admintable="select * from admintable where admin_username=?";
			pstm=(PreparedStatement) conn.prepareStatement(sql_select_admintable);
			pstm.setString(1, admin_username);
			rs=(ResultSet) pstm.executeQuery();
			while(rs.next()){
				admin=new Admin();
				admin.setAdmin_username(rs.getString("admin_username"));
				admin.setAdmin_password(rs.getString("admin_password"));
				admin.setAuthority(rs.getInt("authority"));
				admin.setAdmin_name(rs.getString("admin_name"));
				admin.setAdmin_dept(rs.getString("admin_dept"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			JDBC_Connection.free(rs, conn, pstm);
		}
		return admin;
	}
}
