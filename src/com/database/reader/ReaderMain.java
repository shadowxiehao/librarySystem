package com.database.reader;

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

import com.database.admin.UpdateReader;
import com.database.info.Book;
import com.database.info.Browser;
import com.database.info.History;
import com.database.info.Reader;
import com.database.jdbc.DatabaseHandler;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.jdbc.DatabaseHandlerBrowser;
import com.database.jdbc.DatabaseHistory;
import com.database.main.Login;

/***
 * ����Ƕ��ߵĽ���
 * 
 * @author XieHao
 * 
 */
public class ReaderMain implements ActionListener {
	private JTextField jt_search;
	private JTextArea jt_show_detail;
	private JLabel jt_reader_username;
	private JFrame jf_reader;

	public void creatUI(String Login_username) {
		jf_reader = new JFrame("���ߵ�¼");
		jf_reader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ��ȫ�˳�
		jf_reader.setLayout(new BorderLayout());// ����BorderLayout����
		/***** ���²��־��ǽ���ĵ�һ��������� ****/
		JPanel jp_title = new JPanel();
		jt_search = new JTextField(20);
		JButton jb_search = new JButton("����");
		JLabel jt_space1 = new JLabel("  ");
		JLabel jt_welcome = new JLabel("��ӭ����");
		jt_reader_username = new JLabel("");
		// �����ǩ������ʾ��¼�����Ķ��ߵĶ��ߺ�
		jt_reader_username.setText(Login_username);
		JButton jb_exit = new JButton("�˳�");
		jp_title.add(jt_search);// ������
		jp_title.add(jb_search);// ȷ��������
		jp_title.add(jt_space1);
		jp_title.add(jt_welcome);// ��ӭXX
		jp_title.add(jt_reader_username);
		jp_title.add(jb_exit);
		// �˳���
		jf_reader.add(jp_title, BorderLayout.NORTH);// ���ò�����������
		// һ������������ʾ������
		jt_show_detail = new JTextArea();
		jt_show_detail.setEditable(false);
		jf_reader.add(jt_show_detail, BorderLayout.CENTER);
		// ���߽������Ҫ����
		JPanel jp_function = new JPanel();
		BoxLayout bl_function = new BoxLayout(jp_function, BoxLayout.Y_AXIS);// ���ù��ܰ�ť��ʾΪBoxLayout
		jp_function.setLayout(bl_function);
		JButton jb_borrow = new JButton("����");
		JButton jb_already_borrow = new JButton("�ѽ��鱾");
		JButton jb_already_search = new JButton("������ʷ");
		JButton jb_change_information = new JButton("�޸���Ϣ");
		//jp_function.add(jb_borrow);
		jp_function.add(jb_already_borrow);
		jp_function.add(jb_already_search);
		jp_function.add(jb_change_information);
		jf_reader.add(jp_function, BorderLayout.EAST);
		jf_reader.setVisible(true);
		jf_reader.setSize(600, 600);
		jf_reader.setLocation(100, 100);
		// ������ü���
		jb_search.addActionListener(this);
		jb_borrow.addActionListener(this);
		jb_already_borrow.addActionListener(this);
		jb_already_search.addActionListener(this);
		jb_change_information.addActionListener(this);
		jb_exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();
		if (event.equals("����")) {
			search_book_name();

		} else if (event.equals("�˳�")) {
			Login login = new Login();
			login.createUI();
			jf_reader.dispose();
		} else if (event.equals("����")) {

		} else if (event.equals("�ѽ��鱾")) {
			search_borrow_history();
		} else if (event.equals("������ʷ")) {
			search_history();
		} else if (event.equals("�޸���Ϣ")) {
			String str_reader_username= jt_reader_username.getText();
			//String reader_username = jt_reader_search.getText().toString();
			DatabaseHandler databaseHandler=new DatabaseHandler();
			Reader reader = databaseHandler
					.queryByreaderusername(str_reader_username);
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
				jt_show_detail.append("�û�������\n");
			}
		}

	}

	/**
	 * ���߽�����ʾ��ص�ͼ����Ϣ
	 */
	public void search_book_name() {
		System.out.println("�ɹ�����������");
		jt_show_detail.setText("");
		String bookname = jt_search.getText();
		System.out.println(bookname);
		DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();
		List<Book> book = databaseHandlerBook.queryBook(bookname);
		jt_show_detail.append("�鱾��\t " + "����\t " + "����\t " + "����ʱ��\t "
				+ "�����\t " + "������\t ");
		if (book != null) {

			jt_show_detail.append("\n");
			for (int i = 0; i < book.size(); i++) {
				jt_show_detail.append(book.get(i).getBook_number() + "\t"
						+ book.get(i).getBook_name() + "\t"
						+ book.get(i).getBook_author() + "\t"
						+ book.get(i).getBook_publishtime() + "\t"
						+ book.get(i).getBook_amount() + "\t"
						+ book.get(i).getAdmin_username() + "\t");
				jt_show_detail.append("\n");
			}

		} else {
			System.out.println(bookname + "������");
			JOptionPane.showMessageDialog(null, "����������", "����",
					JOptionPane.ERROR_MESSAGE);
		}
		/**** ����������������browser���� *********/
		String jt_reader_username1 = jt_reader_username.getText();
		DatabaseHandlerBrowser databaseHandlerBrowser = new DatabaseHandlerBrowser();
		Browser browser = new Browser();
		browser.setBrowser_bookname(bookname);
		browser.setBrowser_reader_username(jt_reader_username1);
		databaseHandlerBrowser.insert_into_browsertable(browser);
	}

	/**
	 * ��������ʾ�Ѿ��赽���鱾
	 */
	public void search_borrow_history() {
		String readerusername = jt_reader_username.getText();
		System.out.println(readerusername);
		jt_show_detail.setText("");
		DatabaseHistory databaseHistory = new DatabaseHistory();
		List<History> history = databaseHistory
				.QueryHistorytable(readerusername);
		jt_show_detail.append("���ߺ�\t" + "�鱾��\t " + "����\t " + "����ʱ��\t ");
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
			System.out.println(readerusername + "������");
			JOptionPane.showMessageDialog(null, "����������", "����",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * ������ʷ
	 */
	public void search_history() {
		String readerusername = jt_reader_username.getText();

		jt_show_detail.setText("");
		DatabaseHandlerBrowser databaseHandlerBrowser = new DatabaseHandlerBrowser();
		List<Browser> browser = databaseHandlerBrowser
				.QueryBrowserTable(readerusername);
		jt_show_detail.append("���ߺ�\t" + "����\t " );
		if (browser != null) {

			jt_show_detail.append("\n");
			for (int i = 0; i < browser.size(); i++) {
				jt_show_detail.append(browser.get(i)
						.getBrowser_reader_username()+ "\t"
						+ browser.get(i).getBrowser_bookname() + "\t");
				jt_show_detail.append("\n");
			}

		} else {
			System.out.println(readerusername + "������");
			JOptionPane.showMessageDialog(null, "����������", "����",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
