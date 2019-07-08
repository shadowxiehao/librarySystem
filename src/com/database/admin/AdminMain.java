package com.database.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.database.info.Admin;
import com.database.info.Book;
import com.database.bookmanage.BookMain;
import com.database.jdbc.DatabaseHandlerBook;
import com.database.main.Login;
import com.database.util.ImageLabel;//���챳��ͼ��


/**
 * ����ǹ���Ա�Ľ���
 *
 * @author XieHao
 */
public class AdminMain implements ActionListener {
    JFrame jf_admin;
    JButton jb_admin_search, jb_admin_exit, jb_admin_borrow,
            jb_admin_reader_manage, jb_admin_book_manager,
            jb_admin_publish_newbook;
    //private JTextArea jt_admin_show_detail;
    private JTable table = new JTable();//��ʾ����ı��
    private DefaultTableModel mm = null;//��Ĭ�ϸ�ʽ
    String[] tablehead = {"�鱾��", "����", "����", "����ʱ��", "�����", "������"};//��ͷ����

    private JTextField jt_admin_search;
    private Admin admin;

    public AdminMain(Admin admin) {
        this.admin = admin;
    }

    public void createUI() {
        jf_admin = new JFrame("����Ա " + admin.getAdmin_name() + " ����");
        jf_admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ��ȫ�˳�
        jf_admin.setLayout(new BorderLayout());// ����BorderLayout����

        //���øտ�ʼ��ʾ�Ĵ�С
        Dimension dimension = new Dimension(610, 400);
        jf_admin.setMinimumSize(dimension);

        //���ô���ͼ��
        ImageIcon imageIcon = new ImageIcon("src\\com\\database\\util\\c.jpg");// ����ͼ�� .png .jpg .gif �ȸ�ʽ��ͼƬ������
        jf_admin.setIconImage(imageIcon.getImage());

        //����ͼƬ
        try {
            Image image = new ImageIcon("src\\com\\database\\util\\b.png").getImage();// ���Ǳ���ͼƬ .png .jpg .gif �ȸ�ʽ��ͼƬ������
            JLabel imgLabel = new ImageLabel(image, jf_admin);// ������ͼ����"��ǩ"�
            jf_admin.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����
            Container cp = jf_admin.getContentPane();
            ((JPanel) cp).setOpaque(false); // ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������
            imgLabel.setBounds(0, 0, jf_admin.getWidth(), jf_admin.getHeight());// ���ñ�����ǩ��λ��

            jf_admin.addComponentListener(new ComponentAdapter() {//�������ڴ�С�ı�,Ȼ��ı�jlabel��С
                @Override
                public void componentResized(ComponentEvent e) {
                    imgLabel.setSize(jf_admin.getWidth(), jf_admin.getHeight());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***** ���²��־��ǽ���ĵ�һ��������� ****/
        JPanel jp_admin_title = new JPanel();
        jt_admin_search = new JTextField(20);
        jt_admin_search.setText("����������������");
        jb_admin_search = new JButton("����");
        JLabel jt_admin_space1 = new JLabel("  ");
        JLabel jt_admin_welcome = new JLabel("��ӭ����");
        JButton jb_admin_exit = new JButton("�˳�");
        jp_admin_title.add(jt_admin_search);// ������
        jp_admin_title.add(jb_admin_search);// ȷ��������
        jp_admin_title.add(jt_admin_space1);
        // ��ӭXX
        jp_admin_title.add(jt_admin_welcome);
        JLabel welcome_name = new JLabel();
        welcome_name.setText(admin.getAdmin_name() + "  ");
        welcome_name.setSize(100, 22);
        jp_admin_title.add(welcome_name);

        // �˳���
        jp_admin_title.add(jb_admin_exit);

        jf_admin.add(jp_admin_title, BorderLayout.NORTH);// ���ò�����������
        jp_admin_title.setOpaque(false);//͸��

        // ��������ʾ������(��ѯ�����)
        mm = new DefaultTableModel(tablehead, 0);//˳��ѱ�ͷ�ӽ�ȥ
        table.setModel(mm);
        table.setOpaque(false);//͸��
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setOpaque(false);//͸��
        jScrollPane.getViewport().setOpaque(false);//͸��
        jf_admin.add(jScrollPane, BorderLayout.CENTER);
        search_book_all();//�����Ȱ�������Ϣ��ʾ����,������

//        ������ԭ����,���ڲ����˱�����,����ɾ��,�ٺ�(������...)
//        JPanel text_panel = new JPanel();
//        jt_admin_show_detail = new JTextArea();
//        jt_admin_show_detail.setEditable(false);
//        text_panel.add(jt_admin_show_detail);
//        text_panel.setOpaque(false);
//        jf_admin.add(text_panel, BorderLayout.CENTER);

        // ���߽������Ҫ����
        JPanel jp_admin_function = new JPanel();
        BoxLayout bl_function = new BoxLayout(jp_admin_function, BoxLayout.Y_AXIS);// ���ù��ܰ�ť��ʾΪBoxLayout
        jp_admin_function.setLayout(bl_function);

        jb_admin_borrow = new JButton("����/�黹");
        jb_admin_publish_newbook = new JButton("��������");
        jb_admin_book_manager = new JButton("ͼ�����");
        jb_admin_reader_manage = new JButton("���߹���");

        jp_admin_function.add(jb_admin_borrow);
        jp_admin_function.add(jb_admin_publish_newbook);
        jp_admin_function.add(jb_admin_book_manager);
        jp_admin_function.add(jb_admin_reader_manage);

        jp_admin_function.setOpaque(false);
        jf_admin.add(jp_admin_function, BorderLayout.WEST);

        jf_admin.setVisible(true);
        jf_admin.setSize(610, 400);
        jf_admin.setLocation(100, 100);


        /***** ���ø���ť�ĵ���¼� ******/
        jb_admin_search.addActionListener(this);// ������ť
        jb_admin_exit.addActionListener(this);// �˳���¼�¼�
        jb_admin_borrow.addActionListener(this);// ����ͼ���¼�

        jb_admin_book_manager.addActionListener(this);// ͼ��ɾ��
        jb_admin_publish_newbook.addActionListener(this);
        // �޸�ͼ����Ϣ
        jb_admin_reader_manage.addActionListener(this);// ������Ϣ����

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String event = e.getActionCommand();

        if (event.equals("����")) {
            // System.out.println("search");
            search_book_name();

        } else if (event.equals("�˳�")) {
            // ���ص�¼����
            Login login = new Login();
            login.createUI();
            jf_admin.dispose();

        } else if (event.equals("��������")) {
            System.out.println("search2");
            PublishNewBooks publishNewBooks = new PublishNewBooks();
            publishNewBooks.createUI();
        } else if (event.equals("����/�黹")) {
            BorrowOrReturn borrowOrReturn = new BorrowOrReturn();
            borrowOrReturn.createUI();
        } else if (event.equals("ͼ�����")) {
            System.out.println("search5");
            BookMain bookMain = new BookMain();
            bookMain.createUI();


        } else if (event.equals("���߹���")) {
            System.out.println("search7");
            ReaderManage readerManage = new ReaderManage();
            readerManage.createUI();
        }

    }

    /**
     * �������鱾���ֽ���������ͬʱʵ��ģ������
     */

    public void search_book_name() {
        List<Book> book = null;
        System.out.println("�ɹ�����������");
        mm.setRowCount(0);//��ʾ���
        String bookname = jt_admin_search.getText();
        System.out.println(bookname);
        DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();
        if (bookname.trim() == "") {
            book = databaseHandlerBook.queryBook();
        } else {
            book = databaseHandlerBook.queryBook(bookname);
        }

        if (book != null) {
//            jt_admin_show_detail.append("\n");
            for (int i = 0; i < book.size(); i++) {
                String[] row = {book.get(i).getBook_number(), book.get(i).getBook_name(),
                        book.get(i).getBook_author(), book.get(i).getBook_publishtime(),
                        book.get(i).getBook_amount() + "", book.get(i).getAdmin_username()};
                mm.addRow(row);
//                jt_admin_show_detail.append(book.get(i).getBook_number() + "\t"
//                        + book.get(i).getBook_name() + "\t"
//                        + book.get(i).getBook_author() + "\t"
//                        + book.get(i).getBook_publishtime() + "\t"
//                        + book.get(i).getBook_amount() + "\t"
//                        + book.get(i).getAdmin_username() + "\t");
//                jt_admin_show_detail.append("\n");
            }

        } else {
            System.out.println(bookname + "������");
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
