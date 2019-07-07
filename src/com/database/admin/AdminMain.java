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
 * ����ǹ���Ա�Ľ���
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
		jf_admin = new JFrame("����Ա��¼");
		jf_admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ��ȫ�˳�
		jf_admin.setLayout(new BorderLayout());// ����BorderLayout����
		/***** ���²��־��ǽ���ĵ�һ��������� ****/
		JPanel jp_admin_title = new JPanel();
		jt_admin_search = new JTextField(20);
		jt_admin_search.setText("����������");
		jb_admin_search = new JButton("����");
		JLabel jt_admin_space1 = new JLabel("  ");
		JLabel jt_admin_welcome = new JLabel("��ӭ����");
		JLabel jt_admin_space = new JLabel("   ");
		JButton jb_admin_exit = new JButton("�˳�");
		jp_admin_title.add(jt_admin_search);// ������
		jp_admin_title.add(jb_admin_search);// ȷ��������
		jp_admin_title.add(jt_admin_space1);
		jp_admin_title.add(jt_admin_welcome);// ��ӭXX
		jp_admin_title.add(jt_admin_space);
		jp_admin_title.add(jb_admin_exit);
		// �˳���
		jf_admin.add(jp_admin_title, BorderLayout.NORTH);// ���ò�����������
		// һ������������ʾ������
		jt_admin_show_detail = new JTextArea();
		jt_admin_show_detail.setEditable(false);
		jf_admin.add(jt_admin_show_detail, BorderLayout.CENTER);
		// ���߽������Ҫ����
		JPanel jp_admin_function = new JPanel();
		BoxLayout bl_function = new BoxLayout(jp_admin_function,
				BoxLayout.Y_AXIS);// ���ù��ܰ�ť��ʾΪBoxLayout
		jp_admin_function.setLayout(bl_function);
		jb_admin_borrow = new JButton("����/�黹");

		jb_admin_publish_newbook = new JButton("��������");
		jb_admin_book_manager = new JButton("ͼ�����");

		jb_admin_reader_manage = new JButton("���߹���");
		jp_admin_function.add(jb_admin_borrow);

		jp_admin_function.add(jb_admin_publish_newbook);
		jp_admin_function.add(jb_admin_book_manager);

		jp_admin_function.add(jb_admin_reader_manage);
		jf_admin.add(jp_admin_function, BorderLayout.EAST);
		jf_admin.setVisible(true);
		jf_admin.setSize(610, 400);
		jf_admin.setLocation(100, 100);
		/***** ���ø���ť�ĵ���¼� ******/
		jb_admin_search.addActionListener(this);// ������ť
		jb_admin_exit.addActionListener(this);// �˳���¼�¼�
		jb_admin_borrow.addActionListener(this);// ����ͼ���¼�

		jb_admin_book_manager.addActionListener(this);// ͼ��ɾ��
		jb_admin_publish_newbook.addActionListener(this);
		// �޸�ͼ����Ϣ
		jb_admin_reader_manage.addActionListener(this);// ������Ϣ����

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();

		if (event.equals("����")) {
			// System.out.println("search");
			search_book_name();

		} else if (event.equals("�˳�")) {
			// ���ص�¼����
			Login login = new Login();
			login.createUI();
			jf_admin.dispose();

		} else if (event.equals("��������")) {
			System.out.println("search2");
			PublishNewBooks publishNewBooks = new PublishNewBooks();
			publishNewBooks.createUI();
		} else if (event.equals("����/�黹")) {
			BorrowOrReturn borrowOrReturn = new BorrowOrReturn();
			borrowOrReturn.createUI();
		} else if (event.equals("ͼ�����")) {
			System.out.println("search5");
			BookMain bookMain=new BookMain();
			bookMain.createUI();
			
			
		}  else if (event.equals("���߹���")) {
			System.out.println("search7");
			ReaderManage readerManage = new ReaderManage();
			readerManage.createUI();
		}

	}

	/**
	 * �������鱾���ֽ���������ͬʱʵ��ģ������
	 */

	public void search_book_name() {
		System.out.println("�ɹ�����������");
		jt_admin_show_detail.setText("");
		String bookname = jt_admin_search.getText();
		System.out.println(bookname);
		DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();

		List<Book> book = databaseHandlerBook.queryBook(bookname);
		jt_admin_show_detail.append("�鱾��\t " + "����\t " + "����\t " + "����ʱ��\t "
				+ "�����\t " + "������\t ");
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
			System.out.println(bookname + "������");
			JOptionPane.showMessageDialog(null, "����������", "����",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
