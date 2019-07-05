package com.database.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.database.bean.Reader;
import com.database.jdbc.DatabaseHandler;

public class UpdateReader {
/**
 * ���UI������ʾ��Ӧ����Ϣ��ͬʱ����������Ļ����Ͻ��и���
 * @param username
 * @param passwoid
 * @param name
 * @param authority
 * @param dept
 * @param borrow
 * @param degree
 */
	public void createUI(final String username, final String password, final String name,
			final int authority, final String dept, final int borrow, final String degree) {
		final JFrame jframe = new JFrame("������Ϣ");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setLayout(new FlowLayout());
		/**** ��¼���˺����� ****/
		JLabel jl_readerusername = new JLabel("��¼�� *:");
		final JTextField jt_readerusername = new JTextField(15);
		jt_readerusername.setText(username);
		jt_readerusername.setEditable(false);
		JLabel jl_readerpassword = new JLabel("����     *:");
		final JTextField jt_readerpassword = new JTextField(15);
		jt_readerpassword.setText(password);
		JLabel jl_readername = new JLabel("����      :");
		final JTextField jt_readername = new JTextField(15);
		jt_readername.setText(name);
		JLabel jl_readerauthority = new JLabel("Ȩ�޺�  :");
		final JTextField jt_readerauthority = new JTextField(15);
		//��intת����String
		String str_authority=authority+"";
		jt_readerauthority.setText(str_authority);		

		JLabel jl_readersedp = new JLabel("����ϵ  *:");
		final JTextField jt_readerdept = new JTextField(15);
		jt_readerdept.setText(dept);
		JLabel jl_borrowamount = new JLabel("�ѽ���  *:");
		final JTextField jt_readerborrowamount = new JTextField(15);
		// ���Ѿ��ӵ�������ת����String
		String str_borrow=borrow+"";
		jt_readerborrowamount.setText(str_borrow);
		JLabel jl_readerdegree = new JLabel("ѧ��   *:");
		final JTextField jt_readerdegree = new JTextField(15);
		jt_readerdegree.setText(degree);
		/*** ȷ�ϼ� ***/
		JButton jb_confirm = new JButton("�޸�");
		jb_confirm.setSize(15, 5);

		/*** ������ **/
		jframe.add(jl_readerusername);
		jframe.add(jt_readerusername);
		jframe.add(jl_readerpassword);
		jframe.add(jt_readerpassword);
		jframe.add(jl_readername);
		jframe.add(jt_readername);
		jframe.add(jl_readerauthority);
		jframe.add(jt_readerauthority);
		jframe.add(jl_readersedp);
		jframe.add(jt_readerdept);
		jframe.add(jl_borrowamount);
		jframe.add(jt_readerborrowamount);
		jframe.add(jl_readerdegree);
		jframe.add(jt_readerdegree);
		jframe.add(jb_confirm);
		jframe.setVisible(true);
		jframe.setSize(250, 350);
		jframe.setLocation(100, 200);
		
		jb_confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DatabaseHandler databaseHandler=new DatabaseHandler();
				Reader reader=new Reader();
				/**��������Ķ����final ���������ԸĻ�������**/
				String username1=jt_readerusername.getText().toString();
				reader=databaseHandler.queryByreaderusername(username1);
				if(reader!=null){
					Reader reader1=new Reader();
					reader1.setReader_username(jt_readerusername.getText().toString());
					reader1.setReader_password(jt_readerpassword.getText().toString());
					reader1.setReader_name(jt_readername.getText().toString());
					
					int reader_authority=Integer.parseInt(jt_readerauthority.getText().toString());
					reader1.setAuthority(reader_authority);
					
					reader1.setReader_dept(jt_readerdept.getText().toString());
					int int_borrow=Integer.parseInt(jt_readerborrowamount.getText().toString());
					reader1.setReader_borrow(int_borrow);
					reader1.setReader_degree(jt_readerdegree.getText().toString());
					//databaseHandler.updateReaderTable(reader1);
					int confirm_delete = JOptionPane.showConfirmDialog(null,
							"ȷ���޸ģ�", "�޸�", JOptionPane.YES_NO_OPTION);
					if (confirm_delete == JOptionPane.YES_OPTION) {
						
						databaseHandler.updateReaderTable(reader1);
						JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "�ɹ�",
								JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				else{
					JOptionPane.showMessageDialog(null, "���û���������", "����",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
}
