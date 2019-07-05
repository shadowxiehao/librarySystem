package com.database.bookmanage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.database.admin.UpdateReader;
import com.database.bean.Book;
import com.database.bean.Reader;
import com.database.jdbc.DatabaseHandlerBook;

public class BookMain implements ActionListener {
	private JTextField jt_book_search;
	DatabaseHandlerBook databaseHandlerBook=new DatabaseHandlerBook();
	private JTextArea jt_show_detail;

	public void createUI() {
		JFrame jf_book = new JFrame("ͼ�����");
		jf_book.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// ��ȫ�˳�
		jf_book.setLayout(new BorderLayout());// ����BorderLayout����
		/***** ���²��־��ǽ���ĵ�һ��������� ****/
		JPanel jp_title = new JPanel();
		JLabel jl = new JLabel("��Ҫ�����ͼ�����");
		jt_book_search = new JTextField(20);
		JButton jb_book_search = new JButton("����");
		jp_title.add(jl);
		jp_title.add(jt_book_search);// ������
		jp_title.add(jb_book_search);// ȷ��������
		jf_book.add(jp_title, BorderLayout.NORTH);// ���ò�����������
		jt_show_detail = new JTextArea();
		jt_show_detail.setEditable(false);
		jf_book.add(jt_show_detail, BorderLayout.CENTER);

		JPanel jp_function = new JPanel();
		BoxLayout bl_function = new BoxLayout(jp_function, BoxLayout.Y_AXIS);// ���ù��ܰ�ť��ʾΪBoxLayout
		jp_function.setLayout(bl_function);
		JButton jb_deletebooks = new JButton("ɾ��ͼ��");
		JButton jb_changebooks = new JButton("�޸�ͼ��");
		jp_function.add(jb_deletebooks);
		jp_function.add(jb_changebooks);
		jf_book.add(jp_function, BorderLayout.EAST);
		jf_book.setVisible(true);
		jf_book.setSize(650, 400);
		jf_book.setLocation(100, 100);
		jb_book_search.addActionListener(this);
		jb_deletebooks.addActionListener(this);
		jb_changebooks.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();
		if (event.equals("ɾ��ͼ��")) {
			System.out.println("ɾ��ͼ��");
			Book book = new Book();
			String book_number = jt_book_search.getText().toString();
			book = databaseHandlerBook.queryBookBybooknumber(book_number);
			if (book != null) {

				int confirm_delete = JOptionPane.showConfirmDialog(null,
						"ȷ��ɾ����", "ɾ������", JOptionPane.YES_NO_OPTION);
				if (confirm_delete == JOptionPane.YES_OPTION) {
					System.out.println("ȷ��ɾ��");
					databaseHandlerBook.delete_Booktable(book_number);
					JOptionPane.showMessageDialog(null, "�ɹ�ɾ��", "�ɹ�",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "������������", "����",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (event.equals("�޸�ͼ��")) {
			System.out.println("�޸�ͼ��");			
			String book_number = jt_book_search.getText().toString();
			Book book= databaseHandlerBook.queryBookBybooknumber(book_number);					
			if (book != null) {
				String number = book.getBook_number().toString();				
				String name = book.getBook_name().toString();
				String  author = book.getBook_author().toString();
				String publishtime = book.getBook_publishtime().toString();
				int amount = book.getBook_amount();
				String admin= book.getAdmin_username().toString();
				UpdateBooks updataBooks = new UpdateBooks();
				updataBooks.createUI(number, name, author, publishtime,
						amount, admin);
			} else {
				jt_show_detail.append("�û�������\n");
			}
			

		} else if (event.equals("����")) {
			System.out.println("�ɹ�����������");
			jt_show_detail.setText("");
			String book_number = jt_book_search.getText().toString();
			System.out.println(book_number);
			Book book = databaseHandlerBook.queryBookBybooknumber(book_number);
					
			jt_show_detail.append("�鱾��\t " + "����\t " + "����\t " + "����ʱ��\t "
					+ "�����\t " + "����Ա\t ");
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
				System.out.println(book_number + "������");
				JOptionPane.showMessageDialog(null, "������", "����",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
