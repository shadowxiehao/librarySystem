package com.database.reader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.database.admin.UpdateReader;
import com.database.info.Book;
import com.database.info.Browser;
import com.database.info.History;
import com.database.info.Reader;
import com.database.jdbc.DatabaseHandler;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.jdbc.DatabaseHandlerBrowser;
import com.database.jdbc.DatabaseHistory;
import com.database.main.Login;
import com.database.util.ImageLabel;//���챳��ͼ��
import com.database.util.ResultPanel;//���������
import com.database.util.UseUtil;

/***
 * ����Ƕ��ߵĽ���
 *
 * @author XieHao
 *
 */
public class ReaderMain implements ActionListener {
    private JTextField jt_search;
    private JTextArea jt_show_detail;
    private JLabel jt_reader_username;
    private JFrame jf_reader;
    private JTable table = new JTable();//��ʾ����ı��
    private DefaultTableModel mm = null;//��Ĭ�ϸ�ʽ
    String[] tablehead = {"�鱾��", "����", "����", "����ʱ��", "�����", "������"};//��ͷ����

    public void creatUI(String Login_username) {
        jf_reader = new JFrame("���ߵ�¼");
        jf_reader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ��ȫ�˳�
        jf_reader.setLayout(new BorderLayout());// ����BorderLayout����

        //���øտ�ʼ��ʾ�Ĵ�С
        Dimension dimension = new Dimension(600, 600);
        jf_reader.setMinimumSize(dimension);

        //���ô���ͼ��
        ImageIcon imageIcon = new ImageIcon("src\\com\\database\\util\\c.jpg");// ����ͼ�� .png .jpg .gif �ȸ�ʽ��ͼƬ������
        jf_reader.setIconImage(imageIcon.getImage());

        //����ͼƬ
        try {
            Image image = new ImageIcon("src\\com\\database\\util\\b.png").getImage();// ���Ǳ���ͼƬ .png .jpg .gif �ȸ�ʽ��ͼƬ������
            JLabel imgLabel = new ImageLabel(image, jf_reader);// ������ͼ����"��ǩ"�
            jf_reader.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����
            Container cp = jf_reader.getContentPane();
            ((JPanel) cp).setOpaque(false); // ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������
            imgLabel.setBounds(0, 0, jf_reader.getWidth(), jf_reader.getHeight());// ���ñ�����ǩ��λ��

            jf_reader.addComponentListener(new ComponentAdapter() {//�������ڴ�С�ı�,Ȼ��ı�jlabel��С
                @Override
                public void componentResized(ComponentEvent e) {
                    imgLabel.setSize(jf_reader.getWidth(), jf_reader.getHeight());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***** ���²��־��ǽ���ĵ�һ��������� ****/
        JPanel jp_title = new JPanel();
        jt_search = new JTextField(20);
        jt_search.setText("�������������");
        JButton jb_search = new JButton("����");
        JLabel jt_space1 = new JLabel("  ");
        JLabel jt_welcome = new JLabel("��ӭ����");
        jt_reader_username = new JLabel("");
        // �����ǩ������ʾ��¼�����Ķ��ߵĶ��ߺ�
        jt_reader_username.setText(Login_username);
        JButton jb_exit = new JButton("�˳�");
        jp_title.add(jt_search);// ������
        jp_title.add(jb_search);// ȷ��������
        jp_title.add(jt_space1);
        jp_title.add(jt_welcome);// ��ӭXX
        jp_title.add(jt_reader_username);
        jp_title.add(jb_exit);    // �˳���

        jp_title.setOpaque(false);//͸��
        jf_reader.add(jp_title, BorderLayout.NORTH);// ���ò�����������

        // ��������������ʾ������
        mm = new DefaultTableModel(tablehead, 0);//˳��ѱ�ͷ�ӽ�ȥ
        table.setModel(mm);
        table.setOpaque(false);//͸��
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setOpaque(false);//͸��
        jScrollPane.getViewport().setOpaque(false);//͸��
        jf_reader.add(jScrollPane, BorderLayout.CENTER);
        search_book_all();//�����Ȱ�������Ϣ��ʾ����,������

//		������,����������,������
//		jt_show_detail = new JTextArea();
//		jt_show_detail.setEditable(false);
//
//		jt_show_detail.setOpaque(false);//͸��
//		jf_reader.add(jt_show_detail, BorderLayout.CENTER);

        // ���߽������Ҫ����
        JPanel jp_function = new JPanel();
        BoxLayout bl_function = new BoxLayout(jp_function, BoxLayout.Y_AXIS);// ���ù��ܰ�ť��ʾΪBoxLayout
        jp_function.setLayout(bl_function);
        JButton jb_borrow = new JButton("����");
        JButton jb_already_borrow = new JButton("�ѽ��鱾");
        JButton jb_already_search = new JButton("������ʷ");
        JButton jb_change_information = new JButton("�޸���Ϣ");

        //jp_function.add(jb_borrow);
        jp_function.add(jb_already_borrow);
        jp_function.add(jb_already_search);
        jp_function.add(jb_change_information);

        jp_function.setOpaque(false);//͸��
        jf_reader.add(jp_function, BorderLayout.WEST);

        jf_reader.setVisible(true);
        jf_reader.setSize(800, 600);
        jf_reader.setLocation(100, 100);
        // ������ü���
        jb_search.addActionListener(this);
        jb_borrow.addActionListener(this);
        jb_already_borrow.addActionListener(this);
        jb_already_search.addActionListener(this);
        jb_change_information.addActionListener(this);
        jb_exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String event = e.getActionCommand();
        if (event.equals("����")) {
            search_book_name();

        } else if (event.equals("�˳�")) {
            Login login = new Login();
            login.createUI();
            jf_reader.dispose();
        } else if (event.equals("����")) {

        } else if (event.equals("�ѽ��鱾")) {
            search_borrow_history();
        } else if (event.equals("������ʷ")) {
            search_history();
        } else if (event.equals("�޸���Ϣ")) {
            String str_reader_username = jt_reader_username.getText();
            //String reader_username = jt_reader_search.getText().toString();
            DatabaseHandler databaseHandler = new DatabaseHandler();
            Reader reader = databaseHandler.queryByreaderusername(str_reader_username);

            if (reader != null) {
                String username = reader.getReader_username();
                String password = reader.getReader_password();
                String name = reader.getReader_name();
                int authority = reader.getAuthority();
                String dept = reader.getReader_dept();
                int borrow = reader.getReader_borrow();
                String degree = reader.getReader_degree();
                UpdateReader updateReader = new UpdateReader();
                updateReader.createUI(username, password, name, authority, dept, borrow, degree, 2);
            } else {
                JOptionPane.showMessageDialog(null, "�û�������", "�ɹ�",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    /**
     * ���߽�����ʾ��ص�ͼ����Ϣ
     */
    public void search_book_name() {
        List<Book> book = null;
        mm.setColumnIdentifiers(tablehead);
        mm.setRowCount(0);//��ʾ���
        System.out.println("�ɹ�����������");
//        jt_show_detail.setText("");
        String bookname = jt_search.getText();
        System.out.println(bookname);
        DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();
        if (bookname.trim() == "") {
            search_book_all();
        } else {
            book = databaseHandlerBook.queryBook(bookname);
            if (book != null) {
                for (int i = 0; i < book.size(); i++) {
                    String[] row = {book.get(i).getBook_number(), book.get(i).getBook_name(),
                            book.get(i).getBook_author(), book.get(i).getBook_publishtime(),
                            book.get(i).getBook_amount() + "", book.get(i).getAdmin_username()};
                    mm.addRow(row);
//			jt_show_detail.append("\n");
//			for (int i = 0; i < book.size(); i++) {
//				jt_show_detail.append(book.get(i).getBook_number() + "\t"
//						+ book.get(i).getBook_name() + "\t"
//						+ book.get(i).getBook_author() + "\t"
//						+ book.get(i).getBook_publishtime() + "\t"
//						+ book.get(i).getBook_amount() + "\t"
//						+ book.get(i).getAdmin_username() + "\t");
//				jt_show_detail.append("\n");
//			}
                    /**** �������ļ�¼(����,��,ʱ��)����browser(��ʷ����)���� *********/
                    String jt_reader_username1 = jt_reader_username.getText();
                    DatabaseHandlerBrowser databaseHandlerBrowser = new DatabaseHandlerBrowser();
                    Browser browser = new Browser();
                    browser.setBrowser_bookname(book.get(i).getBook_name());//��Ӽ���������
                    browser.setBrowser_reader_username(jt_reader_username1);//�����μ������û���
                    browser.setBorrow_time(UseUtil.GetTime());//��μ�����ʱ��
                    databaseHandlerBrowser.insert_into_browsertable(browser);
                }

            } else {
                System.out.println(bookname + "������");
                JOptionPane.showMessageDialog(null, "����������", "����",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
//		List<Book> book = databaseHandlerBook.queryBook(bookname);
//		jt_show_detail.append("�鱾��\t " + "����\t " + "����\t " + "����ʱ��\t "
//				+ "�����\t " + "������\t ");


    }

    /**
     * ��������ʾ�Ѿ��赽���鱾
     */
    public void search_borrow_history() {
        String[] newtablehead={"���ߺ�" , "�鱾��" , "����" , "����ʱ��"};
        mm.setColumnIdentifiers(newtablehead);
        mm.setRowCount(0);
        String readerusername = jt_reader_username.getText();
        System.out.println(readerusername);
//        jt_show_detail.setText("");
        DatabaseHistory databaseHistory = new DatabaseHistory();
        List<History> history = databaseHistory.QueryHistorytable(readerusername);
//        jt_show_detail.append("���ߺ�\t" + "�鱾��\t " + "����\t " + "����ʱ��\t ");
        if (history != null) {
            for (int i = 0; i < history.size(); i++) {
                String[] row = {history.get(i).getHistory_username(), history.get(i).getHistory_book_number(),
                        history.get(i).getHistory_book_name(), history.get(i).getHistory_borrow_time(),
                        };
                mm.addRow(row);
            }
//            jt_show_detail.append("\n");
//            for (int i = 0; i < history.size(); i++) {
//                jt_show_detail.append(history.get(i).getHistory_username()
//                        + "\t" + history.get(i).getHistory_book_number() + "\t"
//                        + history.get(i).getHistory_book_name() + "\t"
//                        + history.get(i).getHistory_borrow_time() + "\t");
//                jt_show_detail.append("\n");
//            }

        } else {
            System.out.println(readerusername + "������");
            JOptionPane.showMessageDialog(null, "����������", "����",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * ������ʷ
     */
    public void search_history() {
        String[] newtablehead={"���ߺ�" , "����" , "����" };
        mm.setColumnIdentifiers(newtablehead);
        mm.setRowCount(0);
        String readerusername = jt_reader_username.getText();

//        jt_show_detail.setText("");
        DatabaseHandlerBrowser databaseHandlerBrowser = new DatabaseHandlerBrowser();
        List<Browser> browser = databaseHandlerBrowser
                .QueryBrowserTable(readerusername);
//        jt_show_detail.append("���ߺ�\t" + "����\t " + "����\t ");
        if (browser != null) {
            for (int i = 0; i < browser.size(); i++) {
                String[] row = {browser.get(i).getBrowser_reader_username(), browser.get(i).getBrowser_bookname(),
                        browser.get(i).getBorrow_time()};
                mm.addRow(row);
//            jt_show_detail.append("\n");
//            for (int i = 0; i < browser.size(); i++) {
//                jt_show_detail.append(browser.get(i).getBrowser_reader_username() + "\t"
//                        + browser.get(i).getBrowser_bookname() + "\t" +
//                        browser.get(i).getBorrow_time() + "\t");
//                jt_show_detail.append("\n");
            }

        } else {
            System.out.println(readerusername + "������");
            JOptionPane.showMessageDialog(null, "����������", "����",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void search_book_all() {
        List<Book> book = null;
        mm.setRowCount(0);//��ʾ���
        DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();
        book = databaseHandlerBook.queryBook();//��������

        if (book != null) {
            for (int i = 0; i < book.size(); i++) {
                String[] row = {book.get(i).getBook_number(), book.get(i).getBook_name(),
                        book.get(i).getBook_author(), book.get(i).getBook_publishtime(),
                        book.get(i).getBook_amount() + "", book.get(i).getAdmin_username()};
                mm.addRow(row);
            }

        } else {
            System.out.println("������");
            JOptionPane.showMessageDialog(null, "���ݿ�Ŀǰû����¼��", "����",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
