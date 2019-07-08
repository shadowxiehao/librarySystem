package com.database.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

import com.database.info.Book;
import com.database.info.Borrow;
import com.database.bookmanage.BookBorrow;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.jdbc.DatabaseHandlerBorrow;
import com.database.util.ImageLabel;
import com.database.util.UseUtil;

public class BorrowOrReturn implements ActionListener {
	private JTextField jt_book_search;
	DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();
	DatabaseHandlerBorrow databaseHandlerBorrow = new DatabaseHandlerBorrow();
	private JTextArea jt_show_detail;

	public void createUI() {
		JFrame jf_reader = new JFrame("ͼ�����/�黹");
		jf_reader.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// ��ȫ�˳�
		jf_reader.setLayout(new BorderLayout());// ����BorderLayout����

		//���øտ�ʼ��ʾ�Ĵ�С
		Dimension dimension = new Dimension(650,400);
		jf_reader.setMinimumSize(dimension);

		//���ô���ͼ��
		ImageIcon imageIcon = new ImageIcon("src\\com\\database\\util\\c.jpg");// ����ͼ�� .png .jpg .gif �ȸ�ʽ��ͼƬ������
		jf_reader.setIconImage(imageIcon.getImage());

		//����ͼƬ
		try {
			Image image = new ImageIcon("src\\com\\database\\util\\b.png").getImage();// ���Ǳ���ͼƬ .png .jpg .gif �ȸ�ʽ��ͼƬ������
			JLabel imgLabel = new ImageLabel(image,jf_reader);// ������ͼ����"��ǩ"�
			jf_reader.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����
			Container cp = jf_reader.getContentPane();
			((JPanel) cp).setOpaque(false); // ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������
			imgLabel.setBounds(0, 0, jf_reader.getWidth(), jf_reader.getHeight());// ���ñ�����ǩ��λ��

			jf_reader.addComponentListener(new ComponentAdapter(){//�������ڴ�С�ı�,Ȼ��ı�jlabel��С
				@Override public void componentResized(ComponentEvent e){
					imgLabel.setSize(jf_reader.getWidth(), jf_reader.getHeight());
				}});
		}catch (Exception e){
			e.printStackTrace();
		}

		/***** ���²��־��ǽ���ĵ�һ��������� ****/
		JPanel jp_title = new JPanel();
		JLabel jl = new JLabel("ͼ�����");
		jt_book_search = new JTextField(20);
		JButton jb_book_search = new JButton("����");
		jp_title.add(jl);
		jp_title.add(jt_book_search);// ������
		jp_title.add(jb_book_search);// ȷ��������
		// �˳���
		jp_title.setOpaque(false);//͸��
		jf_reader.add(jp_title, BorderLayout.NORTH);// ���ò�����������
		// һ������������ʾ������
		jt_show_detail = new JTextArea();
		jt_show_detail.setEditable(false);
		jt_show_detail.setOpaque(false);//͸��
		jf_reader.add(jt_show_detail, BorderLayout.CENTER);
		// ���߽������Ҫ����
		JPanel jp_function = new JPanel();
		BoxLayout bl_function = new BoxLayout(jp_function, BoxLayout.Y_AXIS);// ���ù��ܰ�ť��ʾΪBoxLayout
		jp_function.setLayout(bl_function);
		JButton jb_borrow = new JButton("ͼ�����");
		JButton jb_return = new JButton("ͼ��黹");
		JButton jb_owemoney = new JButton("Ƿ�ѹ���");
		jp_function.add(jb_borrow);
		jp_function.add(jb_return);
		//jp_function.add(jb_owemoney);//Ƿ�ѹ��������ò��ɼ������Ժ�����
		jp_function.setOpaque(false);//͸��
		jf_reader.add(jp_function, BorderLayout.EAST);
		jf_reader.setVisible(true);
		jf_reader.setSize(650, 400);
		jf_reader.setLocation(100, 240);
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
		if (event.equals("����")) {

			System.out.println("�ɹ�����������");
			jt_show_detail.setText("");
			// String book_number = jt_book_search.getText().toString();
			System.out.println(book_number);
			searchbooks(book_number);
			// Book book =
			// databaseHandlerBook.queryBookBybooknumber(book_number);

			/*
			 * jt_show_detail.append("�鱾��\t " + "����\t " + "����\t " + "����ʱ��\t " +
			 * "�����\t " + "����Ա\t "); if (book != null) {
			 * 
			 * jt_show_detail.append("\n");
			 * jt_show_detail.append(book.getBook_number() + "\t" +
			 * book.getBook_name() + "\t" + book.getBook_author() + "\t" +
			 * book.getBook_publishtime() + "\t" + book.getBook_amount() + "\t"
			 * + book.getAdmin_username() + "\t");
			 * 
			 * } else { System.out.println(book_number + "������");
			 * JOptionPane.showMessageDialog(null, "������", "����",
			 * JOptionPane.ERROR_MESSAGE); }
			 */

		} else if (event.equals("ͼ�����")) {
			// String book_number = jt_book_search.getText().toString();
			Book book = databaseHandlerBook.queryBookBybooknumber(book_number);
			if (book != null) {
				String booknumber = book.getBook_number();
				String bookname = book.getBook_name();
				int bookamount = book.getBook_amount();
				if (bookamount > 0) {
					BookBorrow bookBorrow = new BookBorrow();
					bookBorrow.CreatUI(booknumber, bookname,bookamount);
				} else {
					JOptionPane.showMessageDialog(null, "�鱾�Ѿ�ȫ������", "����",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				System.out.println(book_number + "������");
				JOptionPane.showMessageDialog(null, "������", "����",
						JOptionPane.ERROR_MESSAGE);
			}

			/*
			 * BookBorrow bookBorrow = new BookBorrow(); bookBorrow.CreatUI();
			 */
		} else if (event.equals("ͼ��黹")) {
			String borrwow_name=JOptionPane.showInputDialog("������黹���û���:");//borrwow_name���ǵõ��������������Ϣ
			searchAndShowBorrowInfo(book_number,borrwow_name);

		} else if (event.equals("Ƿ�ѹ���")) {//ûʱ������,������

		}
	}

	/**
	 * ����ͨ���鱾������ҳ��鱾��Ϣ����ʾ
	 * 
	 * @param book_number
	 *            �鱾��
	 */
	public void searchbooks(String book_number) {
		Book book = databaseHandlerBook.queryBookBybooknumber(book_number);

		jt_show_detail.append("�鱾��\t " + "����\t " + "����\t " + "����ʱ��\t "
				+ "�����\t " + "����Ա\t ");
		if (book != null) {

			jt_show_detail.append("\n");
			jt_show_detail.append(book.getBook_number() + "\t"
					+ book.getBook_name() + "\t" + book.getBook_author() + "\t"
					+ book.getBook_publishtime() + "\t" + book.getBook_amount()
					+ "\t" + book.getAdmin_username() + "\t");

		} else {
			System.out.println(book_number + "������");
			JOptionPane.showMessageDialog(null, "������", "����",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * ����borrow�����Ƿ���Ҫ�黹��ͼ����룬����о���ʾ��Ӧ����Ϣ����ɾ����borrow�еĶ�Ӧ�ļ�¼
	 * 
	 * @param book_number
	 *            �黹��ͼ���
	 */
	public void searchAndShowBorrowInfo(String book_number,String borrwow_name) {
		long day=0;//���ڼ��㳬����ʱ��
		jt_show_detail.setText("");
		//����Ӧ���鱾�����Ƿ��Ѿ����
		Borrow borrow = databaseHandlerBorrow.QueryBorrowTable(book_number,borrwow_name);
		jt_show_detail.append("ͼ���\t " + "����\t " + "������\t " + "����ʱ��\t ");
		if (borrow != null) {
			jt_show_detail.append("\n");
			jt_show_detail.append(borrow.getBorrow_book_number() + "\t"
					+ borrow.getBorrow_book_name() + "\t"
					+ borrow.getBorrow_reader_username() + "\t"
					+ borrow.getBorrow_time());
			/*****�������ڼ����Ƿ�Ƿ��***********/
			UseUtil useutil = new UseUtil();
			SimpleDateFormat format =new  SimpleDateFormat("yyyy-MM-dd");   
			String str_return_time = (UseUtil.GetTime());
			String str_borrow_time=borrow.getBorrow_time();
			//������������������
			try{
			Date startDay=format.parse(str_borrow_time);
			Date endDay=format.parse(str_return_time);
			//��ʱ�仯Ϊ����
			day= (endDay.getTime()-startDay.getTime())/(24*60*60*1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			//Ĭ��30�켴Ϊ��ʱ
			if(day>30){
				String money_pay=(day-30)*0.1+"Ԫ";
				jt_show_detail.append("\n");
				jt_show_detail.append("**********************************\n");
				jt_show_detail.append("��������Ϊ��"+(day-30+"��"+"\n"));
				jt_show_detail.append("��Ҫ�黹"+money_pay+"\n");
			}else{
				jt_show_detail.append("\n");
				jt_show_detail.append("**********************************\n");
				jt_show_detail.append("����ʱ��Ϊ��"+(day+"��,û��Ƿ��"));
			}
			/***************����ȷ���Ƿ�黹ͼ��******************/
			int confirm_delete = JOptionPane.showConfirmDialog(null,
					"ȷ���黹��", "�黹ͼ��", JOptionPane.YES_NO_OPTION);
			if (confirm_delete == JOptionPane.YES_OPTION) {
				System.out.println("ȷ���黹");				
				databaseHandlerBorrow.DeleteBorrow(book_number,borrwow_name);
				RefreshBookAmounToNum(book_number);
				JOptionPane.showMessageDialog(null, "�ɹ��黹", "�ɹ�",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null, "ͼ�黹û�г������", "����",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * ���鱾���������Ϊn+1
	 * @param book_number
	 */
	public void RefreshBookAmounToNum(String book_number) {
		// TODO Auto-generated method stub
		//��¼��ǰ��ʣ������
		Book book = databaseHandlerBook.queryBookBybooknumber(book_number);

		Book book1 = new Book();
		book1.setBook_amount(book.getBook_amount()+1);
		book1.setBook_number(book_number);
		databaseHandlerBook.updateBookToSetOne(book1);
	}

}
