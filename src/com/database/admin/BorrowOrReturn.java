package com.database.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.database.info.Book;
import com.database.info.Borrow;
import com.database.bookmanage.BookBorrow;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.jdbc.DatabaseHandlerBorrow;
import com.database.util.UseUtil;

public class BorrowOrReturn implements ActionListener {
	private JTextField jt_book_search;
	DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();
	DatabaseHandlerBorrow databaseHandlerBorrow = new DatabaseHandlerBorrow();
	private JTextArea jt_show_detail;

	public void createUI() {
		JFrame jf_reader = new JFrame("图书出借/归还");
		jf_reader.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 安全退出
		jf_reader.setLayout(new BorderLayout());// 设置BorderLayout布局
		/***** 以下部分就是界面的第一个功能面板 ****/
		JPanel jp_title = new JPanel();
		JLabel jl = new JLabel("图书书号");
		jt_book_search = new JTextField(20);
		JButton jb_book_search = new JButton("搜索");
		jp_title.add(jl);
		jp_title.add(jt_book_search);// 搜索条
		jp_title.add(jb_book_search);// 确定搜索键
		// 退出键
		jf_reader.add(jp_title, BorderLayout.NORTH);// 设置布局在最上面
		// 一下是搜索后显示的内容
		jt_show_detail = new JTextArea();
		jt_show_detail.setEditable(false);
		jf_reader.add(jt_show_detail, BorderLayout.CENTER);
		// 读者界面的主要功能
		JPanel jp_function = new JPanel();
		BoxLayout bl_function = new BoxLayout(jp_function, BoxLayout.Y_AXIS);// 设置功能按钮显示为BoxLayout
		jp_function.setLayout(bl_function);
		JButton jb_borrow = new JButton("图书出借");
		JButton jb_return = new JButton("图书归还");
		JButton jb_owemoney = new JButton("欠费管理");
		jp_function.add(jb_borrow);
		jp_function.add(jb_return);
		//jp_function.add(jb_owemoney);//欠费管理先设置不可见，待以后完善
		jf_reader.add(jp_function, BorderLayout.EAST);
		jf_reader.setVisible(true);
		jf_reader.setSize(650, 400);
		jf_reader.setLocation(100, 100);
		jb_book_search.addActionListener(this);
		jb_borrow.addActionListener(this);
		jb_return.addActionListener(this);
		jb_owemoney.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();
		String book_number = jt_book_search.getText();
		if (event.equals("搜索")) {

			System.out.println("成功按下搜索键");
			jt_show_detail.setText("");
			// String book_number = jt_book_search.getText().toString();
			System.out.println(book_number);
			searchbooks(book_number);
			// Book book =
			// databaseHandlerBook.queryBookBybooknumber(book_number);

			/*
			 * jt_show_detail.append("书本号\t " + "书名\t " + "作者\t " + "发布时间\t " +
			 * "库存量\t " + "管理员\t "); if (book != null) {
			 * 
			 * jt_show_detail.append("\n");
			 * jt_show_detail.append(book.getBook_number() + "\t" +
			 * book.getBook_name() + "\t" + book.getBook_author() + "\t" +
			 * book.getBook_publishtime() + "\t" + book.getBook_amount() + "\t"
			 * + book.getAdmin_username() + "\t");
			 * 
			 * } else { System.out.println(book_number + "不存在");
			 * JOptionPane.showMessageDialog(null, "不存在", "错误",
			 * JOptionPane.ERROR_MESSAGE); }
			 */

		} else if (event.equals("图书出借")) {
			// String book_number = jt_book_search.getText().toString();
			Book book = databaseHandlerBook.queryBookBybooknumber(book_number);
			if (book != null) {
				String booknumber = book.getBook_number();
				String bookname = book.getBook_name();
				int bookamount = book.getBook_amount();
				if (bookamount > 0) {
					BookBorrow bookBorrow = new BookBorrow();
					bookBorrow.CreatUI(booknumber, bookname);
				} else {
					JOptionPane.showMessageDialog(null, "书本已经出借", "错误",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				System.out.println(book_number + "不存在");
				JOptionPane.showMessageDialog(null, "不存在", "错误",
						JOptionPane.ERROR_MESSAGE);
			}

			/*
			 * BookBorrow bookBorrow = new BookBorrow(); bookBorrow.CreatUI();
			 */
		} else if (event.equals("图书归还")) {
			searchAndShowBorrowInfo(book_number);

		} else if (event.equals("欠费管理")) {

		}
	}

	/**
	 * 用于通过书本号码查找出书本信息并显示
	 * 
	 * @param book_number
	 *            书本号
	 */
	public void searchbooks(String book_number) {
		Book book = databaseHandlerBook.queryBookBybooknumber(book_number);

		jt_show_detail.append("书本号\t " + "书名\t " + "作者\t " + "发布时间\t "
				+ "库存量\t " + "管理员\t ");
		if (book != null) {

			jt_show_detail.append("\n");
			jt_show_detail.append(book.getBook_number() + "\t"
					+ book.getBook_name() + "\t" + book.getBook_author() + "\t"
					+ book.getBook_publishtime() + "\t" + book.getBook_amount()
					+ "\t" + book.getAdmin_username() + "\t");

		} else {
			System.out.println(book_number + "不存在");
			JOptionPane.showMessageDialog(null, "不存在", "错误",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * 查找borrow表中是否有要归还的图书号码，如果有就显示对应的信息并且删除掉borrow中的对应的记录
	 * 
	 * @param book_number
	 *            归还的图书号
	 */
	public void searchAndShowBorrowInfo(String book_number) {
		long day=0;//用于计算超过的时间
		jt_show_detail.setText("");
		//检查对应的书本号码是否已经借出
		Borrow borrow = databaseHandlerBorrow.QueryBorrowTable(book_number);
		jt_show_detail.append("图书号\t " + "书名\t " + "借阅者\t " + "出借时间\t ");
		if (borrow != null) {
			jt_show_detail.append("\n");
			jt_show_detail.append(borrow.getBorrow_book_number() + "\t"
					+ borrow.getBorrow_book_name() + "\t"
					+ borrow.getBorrow_reader_username() + "\t"
					+ borrow.getBorrow_time());
			/*****以下用于计算是否欠费***********/
			UseUtil useutil = new UseUtil();
			SimpleDateFormat format =new  SimpleDateFormat("yyyy-MM-dd");   
			String str_return_time = (useutil.GetTime());		
			String str_borrow_time=borrow.getBorrow_time();
			//借下来用于剖析日期
			try{
			Date startDay=format.parse(str_borrow_time);
			Date endDay=format.parse(str_return_time);
			//将时间化为天数
			day= (endDay.getTime()-startDay.getTime())/(24*60*60*1000);
			}catch(Exception e){
				e.printStackTrace();
			}			
			if(day>30){
				String money_pay=(day-30)*0.1+"元";
				jt_show_detail.append("\n");
				jt_show_detail.append("**********************************\n");
				jt_show_detail.append("超过天数为："+(day-30+"天"+"\n"));
				jt_show_detail.append("需要归还"+money_pay+"\n");
			}else{
				jt_show_detail.append("\n");
				jt_show_detail.append("**********************************\n");
				jt_show_detail.append("借阅时间为："+(day+"天,没有欠费"));
			}
			/***************重新确定是否归还图书******************/
			int confirm_delete = JOptionPane.showConfirmDialog(null,
					"确定归还？", "归还图书", JOptionPane.YES_NO_OPTION);
			if (confirm_delete == JOptionPane.YES_OPTION) {
				System.out.println("确定归还");				
				databaseHandlerBorrow.DeleteBorrow(book_number);
				RefreshBookAmounToOne(book_number);
				JOptionPane.showMessageDialog(null, "成功归还", "成功",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null, "图书还没有出借", "错误",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * 将书本库存量设置为1
	 * @param book_number
	 */
	public void RefreshBookAmounToOne(String book_number) {
		// TODO Auto-generated method stub
		Book book1 = new Book();
		book1.setBook_amount(1);
		book1.setBook_number(book_number);
		databaseHandlerBook.updateBookToSetOne(book1);
	}

}
