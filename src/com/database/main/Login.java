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
 * 创建登陆注册界面
 * 
 * @author XieHao
 * 
 */
public class Login {
	private JTextField jt_username, jt_password;

	public void createUI() {
		final JFrame jframe = new JFrame("登录");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setLayout(new FlowLayout());
		/**** 登录的账号密码 ****/
		JLabel jl_username = new JLabel("账号:");
		jt_username = new JTextField(15);
		JLabel jl_password = new JLabel("密码:");
		jt_password = new JTextField(15);
		/** 选择登录身份模块 **/
		JLabel jl_identity = new JLabel("选择身份");
		final JRadioButton jr_admin = new JRadioButton("管理员");
		final JRadioButton jr_reader = new JRadioButton("读者");
		ButtonGroup login_identity = new ButtonGroup();
		login_identity.add(jr_admin);
		login_identity.add(jr_reader);
		jr_admin.setSelected(true); // 设置默认为管理员

		/*** 确认键 ***/
		JButton jb_confirm = new JButton("登录");
		jb_confirm.setSize(15, 5);

		/*** 添加组件 **/
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
		// 确认键的点击事件
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String Login_username = jt_username.getText();
				String Login_password = jt_password.getText();
				// 如果选择的是管理员身份，那么跳转到管理员的界面adminMain
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
							JOptionPane.showMessageDialog(null, "密码错误", "警告",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "用户名或者密码错误", "警告",
								JOptionPane.ERROR_MESSAGE);
						System.out.println("用户名或者密码错误1");
					}
					// 不用验证账号密码

					/*
					 * AdminMain adminMain = new AdminMain();
					 * adminMain.createUI();
					 */

				} else if (jr_reader.isSelected() == true) {
					// 如果选择的是读者身份，那么跳转到读者的界面
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
							JOptionPane.showMessageDialog(null, "密码错误", "警告",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "用户名或者密码错误", "警告",
								JOptionPane.ERROR_MESSAGE);
						System.out.println("用户名或者密码错误1");
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
