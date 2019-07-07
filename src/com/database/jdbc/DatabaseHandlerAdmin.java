package com.database.jdbc;

import java.sql.SQLException;

import com.database.info.Admin;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


/***
 * 这个类用户对admin表进行相关的数据库操作
 * @author XieHao
 *
 */
public class DatabaseHandlerAdmin {
	/***
	 * 根据admin表的username进行数据的相关查找
	 * @param admin_username admin的登录账号
	 * @return Admin的对象
	 */
	public Admin queryAdmin(String admin_username){
		Admin admin=null;
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try{//小心驶得万年船
			conn=JDBC_Connection.getConnection();

			//下面几句查指定管理员的所有信息,然后管理员可以修改之类的,没错
			String sql_select_admintable="select * from admintable where admin_username=?";
			pstm= conn.prepareStatement(sql_select_admintable);//?等待输入值
			pstm.setString(1, admin_username);//将?的值设置为传入的名字,至于这个名字嘛,肯定就是管理员名字啦
			rs= pstm.executeQuery();//执行查询喽
			//把找到的每列的值弄进去,嗯,你懂我意思
			while(rs.next()){
				admin=new Admin();
				admin.setAdmin_username(rs.getString("admin_username"));
				admin.setAdmin_password(rs.getString("admin_password"));
				admin.setAuthority(rs.getInt("authority"));//权限喽,管理员就是1
				admin.setAdmin_name(rs.getString("admin_name"));
				admin.setAdmin_dept(rs.getString("admin_dept"));//department就是"系"的意思,比如软件工程系
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			JDBC_Connection.free(rs, conn, pstm);//查完就释放,嗯
		}
		return admin;//把值送回去
	}
}
