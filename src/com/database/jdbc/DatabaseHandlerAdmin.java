package com.database.jdbc;

import java.sql.SQLException;

import com.database.info.Admin;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


/***
 * ������û���admin�������ص����ݿ����
 * @author XieHao
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
		try{//С��ʻ�����괬
			conn=JDBC_Connection.getConnection();

			//���漸���ָ������Ա��������Ϣ,Ȼ�����Ա�����޸�֮���,û��
			String sql_select_admintable="select * from admintable where admin_username=?";
			pstm= conn.prepareStatement(sql_select_admintable);//?�ȴ�����ֵ
			pstm.setString(1, admin_username);//��?��ֵ����Ϊ���������,�������������,�϶����ǹ���Ա������
			rs= pstm.executeQuery();//ִ�в�ѯ�
			//���ҵ���ÿ�е�ֵŪ��ȥ,��,�㶮����˼
			while(rs.next()){
				admin=new Admin();
				admin.setAdmin_username(rs.getString("admin_username"));
				admin.setAdmin_password(rs.getString("admin_password"));
				admin.setAuthority(rs.getInt("authority"));//Ȩ���,����Ա����1
				admin.setAdmin_name(rs.getString("admin_name"));
				admin.setAdmin_dept(rs.getString("admin_dept"));//department����"ϵ"����˼,�����������ϵ
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			JDBC_Connection.free(rs, conn, pstm);//������ͷ�,��
		}
		return admin;//��ֵ�ͻ�ȥ
	}
}
