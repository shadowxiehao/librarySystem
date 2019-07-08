package com.database.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import com.database.info.Book;
import com.database.jdbc.DatabaseHandler;
import com.database.util.ImageLabel;

public class PublishNewBooks {
	public void createUI() {
		final JFrame jframe = new JFrame("登录");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setLayout(new FlowLayout());

		//设置刚开始显示的大小
		Dimension dimension = new Dimension(250, 320);
		jframe.setMinimumSize(dimension);

		//设置窗口图标
		ImageIcon imageIcon = new ImageIcon("src\\com\\database\\util\\c.jpg");// 这是图标 .png .jpg .gif 等格式的图片都可以
		jframe.setIconImage(imageIcon.getImage());

		//背景图片
		try {
			Image image = new ImageIcon("src\\com\\database\\util\\d.png").getImage();// 这是背景图片 .png .jpg .gif 等格式的图片都可以
			JLabel imgLabel = new ImageLabel(image, jframe);// 将背景图放在"标签"里。
			jframe.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// 注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
			Container cp = jframe.getContentPane();
			((JPanel) cp).setOpaque(false); // 注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。
			imgLabel.setBounds(0, 0, jframe.getWidth(), jframe.getHeight());// 设置背景标签的位置

			jframe.addComponentListener(new ComponentAdapter() {//监听窗口大小改变,然后改变jlabel大小
				@Override
				public void componentResized(ComponentEvent e) {
					imgLabel.setSize(jframe.getWidth(), jframe.getHeight());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}


		/**** 登录的账号密码 ****/
		JLabel jl_booknumber = new JLabel("书本号 *:");
		final JTextField jt_booknumber = new JTextField(15);
		jt_booknumber.setText("0003");
		JLabel jl_bookname = new JLabel("书名     *:");
		final JTextField jt_bookname = new JTextField(15);
		jt_bookname.setText("围城1");
		JLabel jl_bookauthor = new JLabel("作者       :");
		final JTextField jt_bookauthor = new JTextField(15);
		jt_bookauthor.setText("kyle");
		JLabel jl_bookpublishtime = new JLabel("出版时间:");
		final JTextField jt_bookpublishtime = new JTextField(15);
		jt_bookpublishtime.setText("01/1/1");
		JLabel jl_bookamount = new JLabel("库存量  *:");
		final JTextField jt_bookamount = new JTextField(15);
		jt_bookamount.setText("4");
		JLabel jl_adminusername = new JLabel("发布者  *:");
		final JTextField jt_adminusername = new JTextField(15);
		jt_adminusername.setText("wuhao");

		/*** 确认键 ***/
		JButton jb_confirm = new JButton("添加");
		jb_confirm.setSize(15, 5);

		/*** 添加组件 **/
		jframe.add(jl_booknumber);
		jframe.add(jt_booknumber);
		jframe.add(jl_bookname);
		jframe.add(jt_bookname);
		jframe.add(jl_bookauthor);
		jframe.add(jt_bookauthor);
		jframe.add(jl_bookpublishtime);
		jframe.add(jt_bookpublishtime);
		jframe.add(jl_bookamount);
		jframe.add(jt_bookamount);
		jframe.add(jl_adminusername);
		jframe.add(jt_adminusername);

		jframe.add(jb_confirm);
		jframe.setVisible(true);
		jframe.setSize(250, 250);
		jframe.setLocation(100, 150);
		// 确认键的点击事件
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (jt_booknumber.getText().equals("")
						|| jt_bookname.getText().equals("")
						|| jt_bookamount.getText().equals("")
						|| jt_adminusername.equals("")) {
					JOptionPane.showMessageDialog(null, "带*的不能为空", "警告",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// 执行插入数据库语句
					DatabaseHandler databaseHandler = new DatabaseHandler();
					Book book = new Book();
					book.setBook_name(jt_bookname.getText().trim());
					book.setBook_number(jt_booknumber.getText()
							.trim());
					book.setBook_author(jt_bookauthor.getText()
							.trim());
					book.setBook_publishtime(jt_bookpublishtime.getText());
					// 将string 转换成int
					String amount = jt_bookamount.getText();
					int bookamount = Integer.parseInt(amount);
					book.setBook_amount(bookamount);
					book.setAdmin_username(jt_adminusername.getText());
					databaseHandler.insert_into_booktable(book);
				}
			}
		});
	}
}
