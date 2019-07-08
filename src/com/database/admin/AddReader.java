package com.database.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import com.database.info.Reader;
import com.database.jdbc.DatabaseHandler;
import com.database.util.ImageLabel;

public class AddReader {
	public void createUI() {
		final JFrame jframe = new JFrame("��Ӷ���");
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
		JLabel jl_readerusername = new JLabel("��¼�� *:");
		final JTextField jt_readerusername = new JTextField(15);
		jt_readerusername.setText("300001");
		JLabel jl_readerpassword = new JLabel("����     *:");
		final JTextField jt_readerpassword = new JTextField(15);
		jt_readerpassword.setText("123");
		JLabel jl_readername = new JLabel("����      :");
		final JTextField jt_readername = new JTextField(15);
		jt_readername.setText("����");
		JLabel jl_readerauthority = new JLabel("Ȩ�޺�  :");
		final JTextField jt_readerauthority = new JTextField(15);
		jt_readerauthority.setText("2");
		JLabel jl_readersedp = new JLabel("����ϵ  *:");
		final JTextField jt_readerdept = new JTextField(15);
		jt_readerdept.setText("�����");

		JLabel jl_borrowamount = new JLabel("�ѽ���  *:");
		final JTextField jt_readerborrowamount = new JTextField(15);
		jt_readerborrowamount.setText("0");
		JLabel jl_readerdegree = new JLabel("ѧ��   *:");
		final JTextField jt_readerdegree = new JTextField(15);
		jt_readerdegree.setText("����");

		/*** ȷ�ϼ� ***/
		JButton jb_confirm = new JButton("��Ӷ���");
		jb_confirm.setSize(15, 5);

		/*** ������ **/
		jframe.add(jl_readerusername);
		jframe.add(jt_readerusername);
		jframe.add(jl_readerpassword);
		jframe.add(jt_readerpassword);
		jframe.add(jl_readername);
		jframe.add(jt_readername);
		jframe.add(jl_readerauthority);
		jframe.add(jt_readerauthority);
		jframe.add(jl_readersedp);
		jframe.add(jt_readerdept);
		jframe.add(jl_borrowamount);
		jframe.add(jt_readerborrowamount);
		jframe.add(jl_readerdegree);
		jframe.add(jt_readerdegree);

		jframe.add(jb_confirm);
		jframe.setVisible(true);
		jframe.setSize(250, 320);
		jframe.setLocation(100, 200);
		/*****ִ����Ӷ������ݲ���******/
		jb_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (jt_readerusername.getText().equals("")
						|| jt_readerpassword.getText().equals("")
						|| jt_readername.getText().equals("")
						|| jt_readerauthority.equals("")
						|| jt_readerdept.equals("")
						|| jt_readerborrowamount.equals("")
						|| jt_readerdegree.equals("")) {
					JOptionPane.showMessageDialog(null, "����Ƿ���û����ĵط�", "����",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// ִ�в������ݿ����
					DatabaseHandler databaseHandler = new DatabaseHandler();
					Reader reader = new Reader();
					reader.setReader_username(jt_readerusername.getText().trim());
					reader.setReader_password(jt_readerpassword.getText()
							.trim());
					reader.setReader_name(jt_readername.getText()
							.trim());
					// ��string ת����int
					String str_authority = jt_readerauthority.getText();
					int authority = Integer.parseInt(str_authority);
					reader.setAuthority(authority);					
					reader.setReader_dept(jt_readerdept.getText());
					String str_borrow = jt_readerborrowamount.getText();
					int borrow_amount = Integer.parseInt(str_borrow);
					reader.setReader_borrow(borrow_amount);
					reader.setReader_degree(jt_readerdegree.getText());
					databaseHandler.insert_into_readertable(reader);
				}

			}
		});
	}
}
