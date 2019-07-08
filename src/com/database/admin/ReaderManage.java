package com.database.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.*;

import com.database.info.History;
import com.database.info.Reader;
import com.database.jdbc.DatabaseHandler;
import com.database.jdbc.DatabaseHistory;
import com.database.util.ImageLabel;


public class ReaderManage implements ActionListener {
	private JButton jb_reader_add, jb_reader_change, jb_reader_delete,
			jb_reader_search,jb_reader_browser;
	private JTextField jt_reader_search;
	private JTextArea jt_show_detail;
	DatabaseHandler databaseHandler = new DatabaseHandler();

	public void createUI() {
		JFrame jf_reader = new JFrame("���߹���");
		jf_reader.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// ��ȫ�˳�
		jf_reader.setLayout(new BorderLayout());// ����BorderLayout����

		//���øտ�ʼ��ʾ�Ĵ�С
		Dimension dimension = new Dimension(700,400);
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
		JLabel jl = new JLabel("���ߵ�¼��");
		jt_reader_search = new JTextField(20);
		jb_reader_search = new JButton("����");
		jp_title.add(jl);
		jp_title.add(jt_reader_search);// ������
		jp_title.add(jb_reader_search);// ȷ��������
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
		jb_reader_add = new JButton("��Ӷ���");
		jb_reader_change = new JButton("�޸���Ϣ");
		jb_reader_delete = new JButton("ɾ������");
		jb_reader_browser=new JButton("������ʷ");
		jp_function.add(jb_reader_add);
		jp_function.add(jb_reader_change);
		jp_function.add(jb_reader_delete);
		jp_function.add(jb_reader_browser);
		jp_function.setOpaque(false);//͸��
		jf_reader.add(jp_function, BorderLayout.EAST);
		jf_reader.setVisible(true);
		jf_reader.setSize(700, 400);
		jf_reader.setLocation(100, 240);
		jf_reader.setResizable(false);

		jb_reader_search.addActionListener(this);
		jb_reader_add.addActionListener(this);
		jb_reader_change.addActionListener(this);
		jb_reader_delete.addActionListener(this);
		jb_reader_browser.addActionListener(this);

	}

	@SuppressWarnings("null")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();
		if (event.equals("����")) {
			System.out.println("�ɹ�����������");
			String reader_username = jt_reader_search.getText();
			System.out.println(reader_username);
			Reader reader = databaseHandler
					.queryByreaderusername(reader_username);
			jt_show_detail.append("�˺�\t " + "����\t " + "����\t " + "Ȩ�޺�\t "
					+ "����ϵ\t " + "�ѽ���\t " + "ѧ��\t ");
			if (reader != null) {

				jt_show_detail.append("\n");
				jt_show_detail.append(reader.getReader_username() + "\t"
						+ reader.getReader_password() + "\t"
						+ reader.getReader_name() + "\t"
						+ reader.getAuthority() + "\t"
						+ reader.getReader_dept() + "\t"
						+ reader.getReader_borrow() + "\t"
						+ reader.getReader_degree() + "\t");

			} else {
				System.out.println(reader_username + "������");
				JOptionPane.showMessageDialog(null, "������", "����",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (event.equals("��Ӷ���")) {
			// ���ص�¼����
			AddReader addReader = new AddReader();
			addReader.createUI();

		} else if (event.equals("�޸���Ϣ")) {
			String reader_username = jt_reader_search.getText();

			Reader reader = databaseHandler
					.queryByreaderusername(reader_username);
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
						dept, borrow, degree,1);
			} else {
				JOptionPane.showMessageDialog(null, "�û�������", "�ɹ�",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} else if (event.equals("ɾ������")) {
			Reader reader = new Reader();
			String reader_username = jt_reader_search.getText();
			reader = databaseHandler.queryByreaderusername(reader_username);
			if (reader != null) {

				int confirm_delete = JOptionPane.showConfirmDialog(null,
						"ȷ��ɾ����", "ɾ������", JOptionPane.YES_NO_OPTION);
				if (confirm_delete == JOptionPane.YES_OPTION) {
					System.out.println("ȷ��ɾ��");
					databaseHandler.deleteReader(reader_username);
					JOptionPane.showMessageDialog(null, "�ɹ�ɾ��", "�ɹ�",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "���û���������", "����",
						JOptionPane.ERROR_MESSAGE);
			}
		}else if(event.equals("������ʷ")){
			reader_borrow_history();
		}

	}
	public void reader_borrow_history(){
		String readerusername = jt_reader_search.getText();
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
}
