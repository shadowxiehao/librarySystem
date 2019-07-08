package com.database.bookmanage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import com.database.info.Book;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.util.ImageLabel;

public class BookMain implements ActionListener {
	private JTextField jt_book_search;
	DatabaseHandlerBook databaseHandlerBook=new DatabaseHandlerBook();
	private JTextArea jt_show_detail;

	public void createUI() {
		JFrame jf_book = new JFrame("图书管理");
		jf_book.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 安全退出
		jf_book.setLayout(new BorderLayout());// 设置BorderLayout布局

		//设置刚开始显示的大小
		Dimension dimension = new Dimension(650,400);
		jf_book.setMinimumSize(dimension);

		//设置窗口图标
		ImageIcon imageIcon = new ImageIcon("src\\com\\database\\util\\c.jpg");// 这是图标 .png .jpg .gif 等格式的图片都可以
		jf_book.setIconImage(imageIcon.getImage());

		//背景图片
		try {
			Image image = new ImageIcon("src\\com\\database\\util\\b.png").getImage();// 这是背景图片 .png .jpg .gif 等格式的图片都可以
			JLabel imgLabel = new ImageLabel(image,jf_book);// 将背景图放在"标签"里。
			jf_book.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// 注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
			Container cp = jf_book.getContentPane();
			((JPanel) cp).setOpaque(false); // 注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。
			imgLabel.setBounds(0, 0, jf_book.getWidth(), jf_book.getHeight());// 设置背景标签的位置

			jf_book.addComponentListener(new ComponentAdapter(){//监听窗口大小改变,然后改变jlabel大小
				@Override public void componentResized(ComponentEvent e){
					imgLabel.setSize(jf_book.getWidth(), jf_book.getHeight());
				}});
		}catch (Exception e){
			e.printStackTrace();
		}
		
		/***** 以下部分就是界面的第一个功能面板 ****/
		JPanel jp_title = new JPanel();
		JLabel jl = new JLabel("需要管理的图书书号");
		jt_book_search = new JTextField(20);
		JButton jb_book_search = new JButton("搜索");
		jp_title.add(jl);
		jp_title.add(jt_book_search);// 搜索条
		jp_title.add(jb_book_search);// 确定搜索键
		jp_title.setOpaque(false);//透明
		jf_book.add(jp_title, BorderLayout.NORTH);// 设置布局在最上面
		jt_show_detail = new JTextArea();
		jt_show_detail.setEditable(false);
		jt_show_detail.setOpaque(false);//透明
		jf_book.add(jt_show_detail, BorderLayout.CENTER);

		JPanel jp_function = new JPanel();
		BoxLayout bl_function = new BoxLayout(jp_function, BoxLayout.Y_AXIS);// 设置功能按钮显示为BoxLayout
		jp_function.setLayout(bl_function);
		JButton jb_deletebooks = new JButton("删除图书");
		JButton jb_changebooks = new JButton("修改图书");
		jp_function.add(jb_deletebooks);
		jp_function.add(jb_changebooks);
		jp_function.setOpaque(false);//透明
		jf_book.add(jp_function, BorderLayout.EAST);
		jf_book.setVisible(true);
		jf_book.setSize(650, 400);
		jf_book.setLocation(100, 240);
		jb_book_search.addActionListener(this);
		jb_deletebooks.addActionListener(this);
		jb_changebooks.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();
		if (event.equals("删除图书")) {
			System.out.println("删除图书");
			Book book = new Book();
			String book_number = jt_book_search.getText();
			book = databaseHandlerBook.queryBookBybooknumber(book_number);
			if (book != null) {

				int confirm_delete = JOptionPane.showConfirmDialog(null,
						"确定删除？", "删除读者", JOptionPane.YES_NO_OPTION);
				if (confirm_delete == JOptionPane.YES_OPTION) {
					System.out.println("确定删除");
					databaseHandlerBook.delete_Booktable(book_number);
					JOptionPane.showMessageDialog(null, "成功删除", "成功",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "该书名不存在", "错误",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (event.equals("修改图书")) {
			System.out.println("修改图书");			
			String book_number = jt_book_search.getText();
			Book book= databaseHandlerBook.queryBookBybooknumber(book_number);					
			if (book != null) {
				String number = book.getBook_number();
				String name = book.getBook_name();
				String  author = book.getBook_author();
				String publishtime = book.getBook_publishtime();
				int amount = book.getBook_amount();
				String admin= book.getAdmin_username();
				UpdateBooks updataBooks = new UpdateBooks();
				updataBooks.createUI(number, name, author, publishtime,
						amount, admin);
			} else {
				JOptionPane.showMessageDialog(null, "无此图书", "成功",
						JOptionPane.INFORMATION_MESSAGE);
			}
			

		} else if (event.equals("搜索")) {
			System.out.println("成功按下搜索键");
			jt_show_detail.setText("");
			String book_number = jt_book_search.getText();
			System.out.println(book_number);
			Book book = databaseHandlerBook.queryBookBybooknumber(book_number);
					
			jt_show_detail.append("书本号\t " + "书名\t " + "作者\t " + "发布时间\t "
					+ "库存量\t " + "管理员\t ");
			if (book != null) {

				jt_show_detail.append("\n");
				jt_show_detail.append(book.getBook_number()+ "\t"
						+ book.getBook_name() + "\t"
						+ book.getBook_author() + "\t"
						+ book.getBook_publishtime() + "\t"
						+ book.getBook_amount() + "\t"
						+ book.getAdmin_username() + "\t"
						);

			} else {
				System.out.println(book_number + "不存在");
				JOptionPane.showMessageDialog(null, "不存在", "错误",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
