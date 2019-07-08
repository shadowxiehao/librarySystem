package com.database.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import com.database.info.Book;
import com.database.jdbc.DatabaseHandler;
import com.database.util.ImageLabel;

public class PublishNewBooks {
	public void createUI() {
		final JFrame jframe = new JFrame("��¼");
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
