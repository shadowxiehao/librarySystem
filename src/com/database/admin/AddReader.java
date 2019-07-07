package com.database.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.database.info.Reader;
import com.database.jdbc.DatabaseHandler;

public class AddReader {
	public void createUI() {
		final JFrame jframe = new JFrame("添加读者");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setLayout(new FlowLayout());
		/**** 登录的账号密码 ****/
		JLabel jl_readerusername = new JLabel("登录号 *:");
		final JTextField jt_readerusername = new JTextField(15);
		jt_readerusername.setText("300001");
		JLabel jl_readerpassword = new JLabel("密码     *:");
		final JTextField jt_readerpassword = new JTextField(15);
		jt_readerpassword.setText("123");
		JLabel jl_readername = new JLabel("姓名      :");
		final JTextField jt_readername = new JTextField(15);
		jt_readername.setText("张三");
		JLabel jl_readerauthority = new JLabel("权限号  :");
		final JTextField jt_readerauthority = new JTextField(15);
		jt_readerauthority.setText("2");
		JLabel jl_readersedp = new JLabel("所在系  *:");
		final JTextField jt_readerdept = new JTextField(15);
		jt_readerdept.setText("计算机");

		JLabel jl_borrowamount = new JLabel("已借数  *:");
		final JTextField jt_readerborrowamount = new JTextField(15);
		jt_readerborrowamount.setText("0");
		JLabel jl_readerdegree = new JLabel("学历   *:");
		final JTextField jt_readerdegree = new JTextField(15);
		jt_readerdegree.setText("本科");

		/*** 确认键 ***/
		JButton jb_confirm = new JButton("添加读者");
		jb_confirm.setSize(15, 5);

		/*** 添加组件 **/
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
		/*****执行添加读者数据操作******/
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (jt_readerusername.getText().equals("")
						|| jt_readerpassword.getText().equals("")
						|| jt_readername.getText().equals("")
						|| jt_readerauthority.equals("")
						|| jt_readerdept.equals("")
						|| jt_readerborrowamount.equals("")
						|| jt_readerdegree.equals("")) {
					JOptionPane.showMessageDialog(null, "检查是否有没有填的地方", "警告",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// 执行插入数据库语句
					DatabaseHandler databaseHandler = new DatabaseHandler();
					Reader reader = new Reader();
					reader.setReader_username(jt_readerusername.getText().trim());
					reader.setReader_password(jt_readerpassword.getText()
							.trim());
					reader.setReader_name(jt_readername.getText()
							.trim());
					// 将string 转换成int
					String str_authority = jt_readerauthority.getText();
					int authority = Integer.parseInt(str_authority);
					reader.setAuthority(authority);					
					reader.setReader_dept(jt_readerdept.getText());
					String str_borrow = jt_readerborrowamount.getText();
					int borrow_amount = Integer.parseInt(str_borrow);
					reader.setReader_borrow(borrow_amount);
					reader.setReader_degree(jt_readerdegree.getText());
					databaseHandler.insert_into_readertable(reader);
				}

			}
		});
	}
}
