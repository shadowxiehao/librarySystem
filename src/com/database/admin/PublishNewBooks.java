package com.database.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

import com.database.info.Book;
import com.database.jdbc.DatabaseHandler;

public class PublishNewBooks {
	public void createUI() {
		final JFrame jframe = new JFrame("��¼");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setLayout(new FlowLayout());
		/**** ��¼���˺����� ****/
		JLabel jl_booknumber = new JLabel("�鱾�� *:");
		final JTextField jt_booknumber = new JTextField(15);
		jt_booknumber.setText("0003");
		JLabel jl_bookname = new JLabel("����     *:");
		final JTextField jt_bookname = new JTextField(15);
		jt_bookname.setText("Χ��1");
		JLabel jl_bookauthor = new JLabel("����       :");
		final JTextField jt_bookauthor = new JTextField(15);
		jt_bookauthor.setText("kyle");
		JLabel jl_bookpublishtime = new JLabel("����ʱ��:");
		final JTextField jt_bookpublishtime = new JTextField(15);
		jt_bookpublishtime.setText("01/1/1");
		JLabel jl_bookamount = new JLabel("�����  *:");
		final JTextField jt_bookamount = new JTextField(15);
		jt_bookamount.setText("4");
		JLabel jl_adminusername = new JLabel("������  *:");
		final JTextField jt_adminusername = new JTextField(15);
		jt_adminusername.setText("wuhao");

		/*** ȷ�ϼ� ***/
		JButton jb_confirm = new JButton("���");
		jb_confirm.setSize(15, 5);

		/*** ������ **/
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
		// ȷ�ϼ��ĵ���¼�
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (jt_booknumber.getText().equals("")
						|| jt_bookname.getText().equals("")
						|| jt_bookamount.getText().equals("")
						|| jt_adminusername.equals("")) {
					JOptionPane.showMessageDialog(null, "��*�Ĳ���Ϊ��", "����",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// ִ�в������ݿ����
					DatabaseHandler databaseHandler = new DatabaseHandler();
					Book book = new Book();
					book.setBook_name(jt_bookname.getText().trim());
					book.setBook_number(jt_booknumber.getText()
							.trim());
					book.setBook_author(jt_bookauthor.getText()
							.trim());
					book.setBook_publishtime(jt_bookpublishtime.getText());
					// ��string ת����int
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
