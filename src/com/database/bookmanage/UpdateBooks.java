package com.database.bookmanage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import com.database.info.Book;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.util.ImageLabel;

public class UpdateBooks {
	public void createUI(final String number, final String name,
			final String author, final String publishtime, final int amount,
			final String admin) {
		final JFrame jframe = new JFrame("������Ϣ");
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
		jt_booknumber.setText(number);
		JLabel jl_bookname = new JLabel("����     *:");
		final JTextField jt_bookname = new JTextField(15);
		jt_bookname.setText(name);
		JLabel jl_bookauthor = new JLabel("����      :");
		final JTextField jt_bookauthor = new JTextField(15);
		jt_bookauthor.setText(author);
		JLabel jl_bookpublishtime = new JLabel("����ʱ�� :");
		final JTextField jt_bookpublishtime = new JTextField(15);
		// ��intת����String
		
		jt_bookpublishtime.setText(publishtime);

		JLabel jl_bookamount = new JLabel("�����  *:");
		final JTextField jt_bookamount = new JTextField(15);		
		jt_bookamount.setText(amount+"");
		JLabel jl_bookadmin = new JLabel("������  *:");
		final JTextField jt_bookadmin = new JTextField(15);
		jt_bookadmin.setText(admin);
		/*** ȷ�ϼ� ***/
		JButton jb_confirm = new JButton("�޸�");
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
		jframe.add(jl_bookadmin);
		jframe.add(jt_bookadmin);
		jframe.add(jb_confirm);
		jframe.setVisible(true);
		jframe.setSize(250, 320);
		jframe.setLocation(100, 200);
		jb_confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DatabaseHandlerBook databaseHandlerBook=new DatabaseHandlerBook();
				Book book=new Book();
				/**��������Ķ����final ���������ԸĻ�������**/
				String booknumber1= jt_booknumber.getText();
				book=databaseHandlerBook.queryBookBybooknumber(booknumber1);
				if(book!=null){
					Book book1=new Book();
					book1.setBook_number(jt_booknumber.getText());
					book1.setBook_name(jt_bookname.getText());
					book1.setBook_author(jt_bookauthor.getText());
					book1.setBook_publishtime(jt_bookpublishtime.getText());
					int int_amount=Integer.parseInt(jt_bookamount.getText());
					book1.setBook_amount(int_amount);
					book1.setAdmin_username(jt_bookadmin.getText());
					int confirm_delete = JOptionPane.showConfirmDialog(null,
							"ȷ���޸ģ�", "�޸�", JOptionPane.YES_NO_OPTION);
					if (confirm_delete == JOptionPane.YES_OPTION) {
						
						databaseHandlerBook.update_booktable(book1);
						JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "�ɹ�",
								JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				else{
					JOptionPane.showMessageDialog(null, "�鱾�Ų�����", "����",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
}
