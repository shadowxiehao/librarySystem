package com.database.bookmanage;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.database.info.Book;
import com.database.info.Borrow;
import com.database.info.History;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.jdbc.DatabaseHandlerBorrow;
import com.database.jdbc.DatabaseHistory;
import com.database.util.UseUtil;

public class BookBorrow {
	DatabaseHandlerBorrow databaseHandlerBorrow = new DatabaseHandlerBorrow();
	DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();

	public void CreatUI(String booknumber, String bookname) {
		final JFrame jframe = new JFrame("ͼ�����");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setLayout(new FlowLayout());
		/**** ��¼���˺����� ****/
		JLabel jl_borrow_booknumber = new JLabel("ͼ��� *:");
		final JTextField jt_borrow_booknumber = new JTextField(15);
		jt_borrow_booknumber.setText(booknumber);
		JLabel jl_borrow_bookname = new JLabel("����     *:");
		final JTextField jt_borrow_bookname = new JTextField(15);
		jt_borrow_bookname.setText(bookname);
		JLabel jl_borrow_readerusername = new JLabel("���ߺ�    :");
		final JTextField jt_borrow_readerusername = new JTextField(15);
		// jt_borrow_readerusername.setText("����");
		JLabel jl_borrow_time = new JLabel("ʱ��  :");
		final JTextField jt_borrow_time = new JTextField(15);
		UseUtil useUtil = new UseUtil();
		jt_borrow_time.setText(useUtil.GetTime());
		/*** ȷ�ϼ� ***/
		JButton jb_confirm = new JButton("����");
		jb_confirm.setSize(15, 5);

		/*** ������ **/
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
		jframe.setSize(280, 350);
		jframe.setLocation(100, 200);
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {//���鶯��
				// TODO Auto-generated method stub
				DatabaseHandlerBorrow databaseHandlerBorrow = new DatabaseHandlerBorrow();
				Borrow borrow = new Borrow();
				borrow.setBorrow_book_number(jt_borrow_booknumber.getText().trim());
				borrow.setBorrow_book_name(jt_borrow_bookname.getText().trim());
				borrow.setBorrow_reader_username(jt_borrow_readerusername.getText().trim());
				borrow.setBorrow_time(jt_borrow_time.getText());
				
				DatabaseHistory databaseHistory = new DatabaseHistory();
				History history = new History();
				history.setHistory_username(jt_borrow_readerusername.getText().trim());
				history.setHistory_book_number(jt_borrow_booknumber.getText().trim());
				history.setHistory_book_name(jt_borrow_bookname.getText().trim());
				history.setHistory_borrow_time(jt_borrow_time.getText());
				
				//���뵽�������
				databaseHandlerBorrow.insert_into_borrowtable(borrow);
				//���뵽���˽�����ʷ���У��Ա����Ժ��ѯ���˵Ľ�����ʷ
				databaseHistory.insert_into_historytable(history);
				/****** ����ɹ���book���ж�Ӧ�Ĵ�����޸�Ϊ0 *******/
				String book_number= jt_borrow_booknumber.getText();
				RefreshBookAmount(book_number);
				jframe.dispose();

			}
		});
	}
/**
 * ���ڽ��鱾��������Ŀ��������Ϊ0�������Ͳ������ٽ���Ȿ����
 * @param book_number �Ѿ�������鱾���鱾����
 */
	public void RefreshBookAmount(String book_number) {
		// TODO Auto-generated method stub
		Book book1 = new Book();
		book1.setBook_amount(0);
		book1.setBook_number(book_number);
		databaseHandlerBook.updateBookToSetZero(book1);

	}

}
