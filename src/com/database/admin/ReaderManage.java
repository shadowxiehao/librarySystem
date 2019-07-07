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

import com.database.info.History;
import com.database.info.Reader;
import com.database.jdbc.DatabaseHandler;
import com.database.jdbc.DatabaseHistory;


public class ReaderManage implements ActionListener {
	private JButton jb_reader_add, jb_reader_change, jb_reader_delete,
			jb_reader_search,jb_reader_browser;
	private JTextField jt_reader_search;
	private JTextArea jt_show_detail;
	DatabaseHandler databaseHandler = new DatabaseHandler();

	public void createUI() {
		JFrame jf_reader = new JFrame("读者管理");
		jf_reader.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 安全退出
		jf_reader.setLayout(new BorderLayout());// 设置BorderLayout布局
		/***** 以下部分就是界面的第一个功能面板 ****/
		JPanel jp_title = new JPanel();
		JLabel jl = new JLabel("读者登录号");
		jt_reader_search = new JTextField(20);
		jb_reader_search = new JButton("搜索");
		jp_title.add(jl);
		jp_title.add(jt_reader_search);// 搜索条
		jp_title.add(jb_reader_search);// 确定搜索键
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
		jb_reader_add = new JButton("添加读者");
		jb_reader_change = new JButton("修改信息");
		jb_reader_delete = new JButton("删除读者");
		jb_reader_browser=new JButton("借书历史");
		jp_function.add(jb_reader_add);
		jp_function.add(jb_reader_change);
		jp_function.add(jb_reader_delete);
		jp_function.add(jb_reader_browser);
		jf_reader.add(jp_function, BorderLayout.EAST);
		jf_reader.setVisible(true);
		jf_reader.setSize(700, 400);
		jf_reader.setLocation(100, 100);
		jf_reader.setResizable(false);

		jb_reader_search.addActionListener(this);
		jb_reader_add.addActionListener(this);
		jb_reader_change.addActionListener(this);
		jb_reader_delete.addActionListener(this);
		jb_reader_browser.addActionListener(this);

	}

	@SuppressWarnings("null")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();
		if (event.equals("搜索")) {
			System.out.println("成功按下搜索键");
			String reader_username = jt_reader_search.getText();
			System.out.println(reader_username);
			Reader reader = databaseHandler
					.queryByreaderusername(reader_username);
			jt_show_detail.append("账号\t " + "密码\t " + "姓名\t " + "权限号\t "
					+ "所在系\t " + "已借数\t " + "学历\t ");
			if (reader != null) {

				jt_show_detail.append("\n");
				jt_show_detail.append(reader.getReader_username() + "\t"
						+ reader.getReader_password() + "\t"
						+ reader.getReader_name() + "\t"
						+ reader.getAuthority() + "\t"
						+ reader.getReader_dept() + "\t"
						+ reader.getReader_borrow() + "\t"
						+ reader.getReader_degree() + "\t");

			} else {
				System.out.println(reader_username + "不存在");
				JOptionPane.showMessageDialog(null, "不存在", "错误",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (event.equals("添加读者")) {
			// 返回登录界面
			AddReader addReader = new AddReader();
			addReader.createUI();

		} else if (event.equals("修改信息")) {
			String reader_username = jt_reader_search.getText();

			Reader reader = databaseHandler
					.queryByreaderusername(reader_username);
			if (reader != null) {
				String username = reader.getReader_username();
				String password = reader.getReader_password();
				String name = reader.getReader_name();
				int authority = reader.getAuthority();
				String dept = reader.getReader_dept();
				int borrow = reader.getReader_borrow();
				String degree = reader.getReader_degree();
				UpdateReader updateReader = new UpdateReader();
				updateReader.createUI(username, password, name, authority,
						dept, borrow, degree);
			} else {
				jt_show_detail.append("用户不存在\n");
			}

		} else if (event.equals("删除读者")) {
			Reader reader = new Reader();
			String reader_username = jt_reader_search.getText();
			reader = databaseHandler.queryByreaderusername(reader_username);
			if (reader != null) {

				int confirm_delete = JOptionPane.showConfirmDialog(null,
						"确定删除？", "删除读者", JOptionPane.YES_NO_OPTION);
				if (confirm_delete == JOptionPane.YES_OPTION) {
					System.out.println("确定删除");
					databaseHandler.deleteReader(reader_username);
					JOptionPane.showMessageDialog(null, "成功删除", "成功",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "该用户名不存在", "错误",
						JOptionPane.ERROR_MESSAGE);
			}
		}else if(event.equals("借书历史")){
			reader_borrow_history();
		}

	}
	public void reader_borrow_history(){
		String readerusername = jt_reader_search.getText();
		System.out.println(readerusername);
		jt_show_detail.setText("");
		DatabaseHistory databaseHistory = new DatabaseHistory();
		List<History> history = databaseHistory
				.QueryHistorytable(readerusername);
		jt_show_detail.append("读者号\t" + "书本号\t " + "书名\t " + "借阅时间\t ");
		if (history != null) {

			jt_show_detail.append("\n");
			for (int i = 0; i < history.size(); i++) {
				jt_show_detail.append(history.get(i).getHistory_username()
						+ "\t" + history.get(i).getHistory_book_number() + "\t"
						+ history.get(i).getHistory_book_name() + "\t"
						+ history.get(i).getHistory_borrow_time() + "\t");
				jt_show_detail.append("\n");
			}

		} else {
			System.out.println(readerusername + "不存在");
			JOptionPane.showMessageDialog(null, "书名不存在", "错误",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
