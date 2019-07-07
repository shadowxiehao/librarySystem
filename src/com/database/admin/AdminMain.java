package com.database.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.database.info.Book;
import com.database.bookmanage.BookMain;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.main.Login;

/**
 * 这个是管理员的界面
 * 
 * @author XieHao
 * 
 */
public class AdminMain implements ActionListener {
	JFrame jf_admin;
	JButton jb_admin_search, jb_admin_exit, jb_admin_borrow,
			jb_admin_reader_manage, jb_admin_book_manager,
			jb_admin_publish_newbook;
	private JTextArea jt_admin_show_detail;
	private JTextField jt_admin_search;

	public void createUI() {
		jf_admin = new JFrame("管理员登录");
		jf_admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 安全退出
		jf_admin.setLayout(new BorderLayout());// 设置BorderLayout布局
		/***** 以下部分就是界面的第一个功能面板 ****/
		JPanel jp_admin_title = new JPanel();
		jt_admin_search = new JTextField(20);
		jt_admin_search.setText("请输入书名");
		jb_admin_search = new JButton("搜索");
		JLabel jt_admin_space1 = new JLabel("  ");
		JLabel jt_admin_welcome = new JLabel("欢迎您：");
		JLabel jt_admin_space = new JLabel("   ");
		JButton jb_admin_exit = new JButton("退出");
		jp_admin_title.add(jt_admin_search);// 搜索条
		jp_admin_title.add(jb_admin_search);// 确定搜索键
		jp_admin_title.add(jt_admin_space1);
		jp_admin_title.add(jt_admin_welcome);// 欢迎XX
		jp_admin_title.add(jt_admin_space);
		jp_admin_title.add(jb_admin_exit);
		// 退出键
		jf_admin.add(jp_admin_title, BorderLayout.NORTH);// 设置布局在最上面
		// 一下是搜索后显示的内容
		jt_admin_show_detail = new JTextArea();
		jt_admin_show_detail.setEditable(false);
		jf_admin.add(jt_admin_show_detail, BorderLayout.CENTER);
		// 读者界面的主要功能
		JPanel jp_admin_function = new JPanel();
		BoxLayout bl_function = new BoxLayout(jp_admin_function,
				BoxLayout.Y_AXIS);// 设置功能按钮显示为BoxLayout
		jp_admin_function.setLayout(bl_function);
		jb_admin_borrow = new JButton("出借/归还");

		jb_admin_publish_newbook = new JButton("发布新书");
		jb_admin_book_manager = new JButton("图书管理");

		jb_admin_reader_manage = new JButton("读者管理");
		jp_admin_function.add(jb_admin_borrow);

		jp_admin_function.add(jb_admin_publish_newbook);
		jp_admin_function.add(jb_admin_book_manager);

		jp_admin_function.add(jb_admin_reader_manage);
		jf_admin.add(jp_admin_function, BorderLayout.EAST);
		jf_admin.setVisible(true);
		jf_admin.setSize(610, 400);
		jf_admin.setLocation(100, 100);
		/***** 设置各按钮的点击事件 ******/
		jb_admin_search.addActionListener(this);// 搜索按钮
		jb_admin_exit.addActionListener(this);// 退出登录事件
		jb_admin_borrow.addActionListener(this);// 出借图书事件

		jb_admin_book_manager.addActionListener(this);// 图书删除
		jb_admin_publish_newbook.addActionListener(this);
		// 修改图书信息
		jb_admin_reader_manage.addActionListener(this);// 读者信息管理

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();

		if (event.equals("搜索")) {
			// System.out.println("search");
			search_book_name();

		} else if (event.equals("退出")) {
			// 返回登录界面
			Login login = new Login();
			login.createUI();
			jf_admin.dispose();

		} else if (event.equals("发布新书")) {
			System.out.println("search2");
			PublishNewBooks publishNewBooks = new PublishNewBooks();
			publishNewBooks.createUI();
		} else if (event.equals("出借/归还")) {
			BorrowOrReturn borrowOrReturn = new BorrowOrReturn();
			borrowOrReturn.createUI();
		} else if (event.equals("图书管理")) {
			System.out.println("search5");
			BookMain bookMain=new BookMain();
			bookMain.createUI();
			
			
		}  else if (event.equals("读者管理")) {
			System.out.println("search7");
			ReaderManage readerManage = new ReaderManage();
			readerManage.createUI();
		}

	}

	/**
	 * 用搜索书本名字进行搜索！同时实现模糊搜索
	 */

	public void search_book_name() {
		System.out.println("成功按下搜索键");
		jt_admin_show_detail.setText("");
		String bookname = jt_admin_search.getText();
		System.out.println(bookname);
		DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();

		List<Book> book = databaseHandlerBook.queryBook(bookname);
		jt_admin_show_detail.append("书本号\t " + "书名\t " + "作者\t " + "发布时间\t "
				+ "库存量\t " + "发布者\t ");
		if (book != null) {

			jt_admin_show_detail.append("\n");
			for (int i = 0; i < book.size(); i++) {
				jt_admin_show_detail.append(book.get(i).getBook_number() + "\t"
						+ book.get(i).getBook_name() + "\t"
						+ book.get(i).getBook_author() + "\t"
						+ book.get(i).getBook_publishtime() + "\t"
						+ book.get(i).getBook_amount() + "\t"
						+ book.get(i).getAdmin_username() + "\t");
				jt_admin_show_detail.append("\n");
			}

		} else {
			System.out.println(bookname + "不存在");
			JOptionPane.showMessageDialog(null, "书名不存在", "错误",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
