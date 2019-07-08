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
		final JFrame jframe = new JFrame("ͼ�����");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setLayout(new FlowLayout());

		//���øտ�ʼ��ʾ�Ĵ�С
		Dimension dimension = new Dimension(250, 320);
		jframe.setMinimumSize(dimension);

		//���ô���ͼ��
		ImageIcon imageIcon = new ImageIcon("src\\com\\database\\util\\c.jpg");// ����ͼ�� .png .jpg .gif �ȸ�ʽ��ͼƬ������
		jframe.setIconImage(imageIcon.getImage());

		//����ͼƬ
		try {
			Image image = new ImageIcon("src\\com\\database\\util\\d.png").getImage();// ���Ǳ���ͼƬ .png .jpg .gif �ȸ�ʽ��ͼƬ������
			JLabel imgLabel = new ImageLabel(image, jframe);// ������ͼ����"��ǩ"�
			jframe.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����
			Container cp = jframe.getContentPane();
			((JPanel) cp).setOpaque(false); // ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������
			imgLabel.setBounds(0, 0, jframe.getWidth(), jframe.getHeight());// ���ñ�����ǩ��λ��

			jframe.addComponentListener(new ComponentAdapter() {//�������ڴ�С�ı�,Ȼ��ı�jlabel��С
				@Override
				public void componentResized(ComponentEvent e) {
					imgLabel.setSize(jframe.getWidth(), jframe.getHeight());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		jt_borrow_time.setText(UseUtil.GetTime());
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
		jframe.setSize(250, 320);
		jframe.setLocation(100, 260);
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {//���鶯��
				// TODO Auto-generated method  //������˼,������չ�ռ�ܴ�,�������˽��Ű�ש
				DatabaseHandlerBorrow databaseHandlerBorrow = new DatabaseHandlerBorrow();
				Borrow borrow = new Borrow();
				borrow.setBorrow_book_number(jt_borrow_booknumber.getText().trim());
				borrow.setBorrow_book_name(jt_borrow_bookname.getText().trim());
				borrow.setBorrow_reader_username(jt_borrow_readerusername.getText().trim());
				borrow.setBorrow_time(jt_borrow_time.getText());

				//�������Ƿ���������
				if(databaseHandlerBorrow.IFSame(borrow.getBorrow_book_number(),borrow.getBorrow_reader_username())==false){
					JOptionPane.showMessageDialog(null, "ͬһ�˲��ܽ�౾��ͬ�����������", "����",
							JOptionPane.INFORMATION_MESSAGE);
				}else {//��ʼ����

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
					/****** ����ɹ���book���ж�Ӧ�Ĵ�����޸�Ϊn-1 *******/
					String book_number = jt_borrow_booknumber.getText();
					RefreshBookAmount(book_number);
					jframe.dispose();
				}
			}
		});
	}
/**
 * ���ڽ��鱾��������Ŀ��������Ϊn-1�������Ͳ������ٽ���Ȿ����
 * @param book_number �Ѿ�������鱾���鱾����
 */
	public void RefreshBookAmount(String book_number) {
		// TODO Auto-generated method stub
		Book book1 = new Book();
		book1.setBook_amount(booknum-1);
		book1.setBook_number(book_number);
		databaseHandlerBook.updateBookToSetNum(book1);
	}
}
