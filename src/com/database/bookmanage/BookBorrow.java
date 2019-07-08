package com.database.bookmanage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import com.database.info.Book;
import com.database.info.Borrow;
import com.database.info.History;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.jdbc.DatabaseHandlerBorrow;
import com.database.jdbc.DatabaseHistory;
import com.database.util.ImageLabel;
import com.database.util.UseUtil;

public class BookBorrow {
	DatabaseHandlerBorrow databaseHandlerBorrow = new DatabaseHandlerBorrow();
	DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();
	private int booknum;

	public void CreatUI(String booknumber, String bookname,int booknum) {
		this.booknum=booknum;
		final JFrame jframe = new JFrame("图书出借");
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
		JLabel jl_borrow_booknumber = new JLabel("图书号 *:");
		final JTextField jt_borrow_booknumber = new JTextField(15);
		jt_borrow_booknumber.setText(booknumber);
		JLabel jl_borrow_bookname = new JLabel("书名     *:");
		final JTextField jt_borrow_bookname = new JTextField(15);
		jt_borrow_bookname.setText(bookname);
		JLabel jl_borrow_readerusername = new JLabel("读者号    :");
		final JTextField jt_borrow_readerusername = new JTextField(15);
		// jt_borrow_readerusername.setText("张三");
		JLabel jl_borrow_time = new JLabel("时间  :");
		final JTextField jt_borrow_time = new JTextField(15);
		UseUtil useUtil = new UseUtil();
		jt_borrow_time.setText(UseUtil.GetTime());
		/*** 确认键 ***/
		JButton jb_confirm = new JButton("出借");
		jb_confirm.setSize(15, 5);

		/*** 添加组件 **/
		jframe.add(jl_borrow_booknumber);
		jframe.add(jt_borrow_booknumber);
		jframe.add(jl_borrow_bookname);
		jframe.add(jt_borrow_bookname);
		jframe.add(jl_borrow_readerusername);
		jframe.add(jt_borrow_readerusername);
		jframe.add(jl_borrow_time);
		jframe.add(jt_borrow_time);
		jframe.add(jb_confirm);
		jframe.setVisible(true);
		jframe.setSize(250, 320);
		jframe.setLocation(100, 260);
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {//借书动作
				// TODO Auto-generated method  //不好意思,这里扩展空间很大,还望后人接着搬砖
				DatabaseHandlerBorrow databaseHandlerBorrow = new DatabaseHandlerBorrow();
				Borrow borrow = new Borrow();
				borrow.setBorrow_book_number(jt_borrow_booknumber.getText().trim());
				borrow.setBorrow_book_name(jt_borrow_bookname.getText().trim());
				borrow.setBorrow_reader_username(jt_borrow_readerusername.getText().trim());
				borrow.setBorrow_time(jt_borrow_time.getText());

				//检查借书是否满足条件
				if(databaseHandlerBorrow.IFSame(borrow.getBorrow_book_number(),borrow.getBorrow_reader_username())==false){
					JOptionPane.showMessageDialog(null, "同一人不能借多本相同书或其它错误", "错误",
							JOptionPane.INFORMATION_MESSAGE);
				}else {//开始借书

					DatabaseHistory databaseHistory = new DatabaseHistory();
					History history = new History();
					history.setHistory_username(jt_borrow_readerusername.getText().trim());
					history.setHistory_book_number(jt_borrow_booknumber.getText().trim());
					history.setHistory_book_name(jt_borrow_bookname.getText().trim());
					history.setHistory_borrow_time(jt_borrow_time.getText());

					//插入到借书表中
					databaseHandlerBorrow.insert_into_borrowtable(borrow);

					//插入到个人借书历史表中，以便于以后查询个人的借书历史
					databaseHistory.insert_into_historytable(history);
					/****** 插入成功后将book表中对应的存存量修改为n-1 *******/
					String book_number = jt_borrow_booknumber.getText();
					RefreshBookAmount(book_number);
					jframe.dispose();
				}
			}
		});
	}
/**
 * 用于将书本详情里面的库存量设置为n-1，这样就不可以再借出这本书了
 * @param book_number 已经借出的书本的书本号码
 */
	public void RefreshBookAmount(String book_number) {
		// TODO Auto-generated method stub
		Book book1 = new Book();
		book1.setBook_amount(booknum-1);
		book1.setBook_number(book_number);
		databaseHandlerBook.updateBookToSetNum(book1);
	}
}
