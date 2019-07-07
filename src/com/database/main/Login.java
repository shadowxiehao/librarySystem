package com.database.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.database.admin.AdminMain;
import com.database.info.Admin;
import com.database.info.Reader;
import com.database.jdbc.DatabaseHandler;
import com.database.jdbc.DatabaseHandlerAdmin;
import com.database.reader.ReaderMain;

/***
 * ������½ע�����
 * 
 * @author XieHao
 * 
 */
public class Login {
	private JTextField jt_username, jt_password;

	public void createUI() {
		final JFrame jframe = new JFrame("��¼");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setLayout(new FlowLayout());
		/**** ��¼���˺����� ****/
		JLabel jl_username = new JLabel("�˺�:");
		jt_username = new JTextField(15);
		JLabel jl_password = new JLabel("����:");
		jt_password = new JTextField(15);
		/** ѡ���¼���ģ�� **/
		JLabel jl_identity = new JLabel("ѡ�����");
		final JRadioButton jr_admin = new JRadioButton("����Ա");
		final JRadioButton jr_reader = new JRadioButton("����");
		ButtonGroup login_identity = new ButtonGroup();
		login_identity.add(jr_admin);
		login_identity.add(jr_reader);
		jr_admin.setSelected(true); // ����Ĭ��Ϊ����Ա

		/*** ȷ�ϼ� ***/
		JButton jb_confirm = new JButton("��¼");
		jb_confirm.setSize(15, 5);

		/*** ������ **/
		jframe.add(jl_username);
		jframe.add(jt_username);
		jframe.add(jl_password);
		jframe.add(jt_password);
		jframe.add(jl_identity);
		jframe.add(jr_admin);
		jframe.add(jr_reader);
		jframe.add(jb_confirm);
		jframe.setVisible(true);
		jframe.setSize(250, 250);
		jframe.setLocation(100, 100);
		// jframe.setResizable(false);
		// ȷ�ϼ��ĵ���¼�
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String Login_username = jt_username.getText();
				String Login_password = jt_password.getText();
				// ���ѡ����ǹ���Ա��ݣ���ô��ת������Ա�Ľ���adminMain
				if (jr_admin.isSelected() == true) {
					DatabaseHandlerAdmin databaseHandlerAdmin = new DatabaseHandlerAdmin();
					Admin admin = databaseHandlerAdmin
							.queryAdmin(Login_username);

					if (admin != null) {
						String admin_password = admin.getAdmin_password();
						if (Login_password.equals(admin_password)) {
							AdminMain adminMain = new AdminMain();
							adminMain.createUI();
							jframe.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "�������", "����",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "�û��������������", "����",
								JOptionPane.ERROR_MESSAGE);
						System.out.println("�û��������������1");
					}
					// ������֤�˺�����

					/*
					 * AdminMain adminMain = new AdminMain();
					 * adminMain.createUI();
					 */

				} else if (jr_reader.isSelected() == true) {
					// ���ѡ����Ƕ�����ݣ���ô��ת�����ߵĽ���
					DatabaseHandler databaseHandler = new DatabaseHandler();
					Reader reader = databaseHandler
							.queryByreaderusername(Login_username);
					if (reader != null) {
						String reader_password = reader.getReader_password();
						if (Login_password.equals(reader_password)) {
							ReaderMain readerMain = new ReaderMain();
							readerMain.creatUI(Login_username);
							jframe.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "�������", "����",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "�û��������������", "����",
								JOptionPane.ERROR_MESSAGE);
						System.out.println("�û��������������1");
					}
					/*
					 * ReaderMain readerMain = new ReaderMain();
					 * readerMain.creatUI();
					 */
				}

			}
		});
	}

}
