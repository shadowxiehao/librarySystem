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
import com.database.util.ImageLabel;//构造背景图用


/**
 * 这个是管理员的界面
 *
 * @author XieHao
 */
public class AdminMain implements ActionListener {
    JFrame jf_admin;
    JButton jb_admin_search, jb_admin_exit, jb_admin_borrow,
            jb_admin_reader_manage, jb_admin_book_manager,
            jb_admin_publish_newbook;
    //private JTextArea jt_admin_show_detail;
    private JTable table = new JTable();//显示结果的表格
    private DefaultTableModel mm = null;//表默认格式
    String[] tablehead = {"书本号", "书名", "作者", "发布时间", "库存量", "发布者"};//表头内容

    private JTextField jt_admin_search;
    private Admin admin;

    public AdminMain(Admin admin) {
        this.admin = admin;
    }

    public void createUI() {
        jf_admin = new JFrame("管理员 " + admin.getAdmin_name() + " 界面");
        jf_admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 安全退出
        jf_admin.setLayout(new BorderLayout());// 设置BorderLayout布局

        //设置刚开始显示的大小
        Dimension dimension = new Dimension(610, 400);
        jf_admin.setMinimumSize(dimension);

        //设置窗口图标
        ImageIcon imageIcon = new ImageIcon("src\\com\\database\\util\\c.jpg");// 这是图标 .png .jpg .gif 等格式的图片都可以
        jf_admin.setIconImage(imageIcon.getImage());

        //背景图片
        try {
            Image image = new ImageIcon("src\\com\\database\\util\\b.png").getImage();// 这是背景图片 .png .jpg .gif 等格式的图片都可以
            JLabel imgLabel = new ImageLabel(image, jf_admin);// 将背景图放在"标签"里。
            jf_admin.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// 注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
            Container cp = jf_admin.getContentPane();
            ((JPanel) cp).setOpaque(false); // 注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。
            imgLabel.setBounds(0, 0, jf_admin.getWidth(), jf_admin.getHeight());// 设置背景标签的位置

            jf_admin.addComponentListener(new ComponentAdapter() {//监听窗口大小改变,然后改变jlabel大小
                @Override
                public void componentResized(ComponentEvent e) {
                    imgLabel.setSize(jf_admin.getWidth(), jf_admin.getHeight());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***** 以下部分就是界面的第一个功能面板 ****/
        JPanel jp_admin_title = new JPanel();
        jt_admin_search = new JTextField(20);
        jt_admin_search.setText("请输入书名或书编号");
        jb_admin_search = new JButton("搜索");
        JLabel jt_admin_space1 = new JLabel("  ");
        JLabel jt_admin_welcome = new JLabel("欢迎您：");
        JButton jb_admin_exit = new JButton("退出");
        jp_admin_title.add(jt_admin_search);// 搜索条
        jp_admin_title.add(jb_admin_search);// 确定搜索键
        jp_admin_title.add(jt_admin_space1);
        // 欢迎XX
        jp_admin_title.add(jt_admin_welcome);
        JLabel welcome_name = new JLabel();
        welcome_name.setText(admin.getAdmin_name() + "  ");
        welcome_name.setSize(100, 22);
        jp_admin_title.add(welcome_name);

        // 退出键
        jp_admin_title.add(jb_admin_exit);

        jf_admin.add(jp_admin_title, BorderLayout.NORTH);// 设置布局在最上面
        jp_admin_title.setOpaque(false);//透明

        // 搜索后显示的内容(查询结果表)
        mm = new DefaultTableModel(tablehead, 0);//顺便把表头加进去
        table.setModel(mm);
        table.setOpaque(false);//透明
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setOpaque(false);//透明
        jScrollPane.getViewport().setOpaque(false);//透明
        jf_admin.add(jScrollPane, BorderLayout.CENTER);
        search_book_all();//刚来先把所有信息显示出来,方便找

//        这里是原方法,由于采用了表格化设计,所以删了,嘿嘿(呜呜呜...)
//        JPanel text_panel = new JPanel();
//        jt_admin_show_detail = new JTextArea();
//        jt_admin_show_detail.setEditable(false);
//        text_panel.add(jt_admin_show_detail);
//        text_panel.setOpaque(false);
//        jf_admin.add(text_panel, BorderLayout.CENTER);

        // 读者界面的主要功能
        JPanel jp_admin_function = new JPanel();
        BoxLayout bl_function = new BoxLayout(jp_admin_function, BoxLayout.Y_AXIS);// 设置功能按钮显示为BoxLayout
        jp_admin_function.setLayout(bl_function);

        jb_admin_borrow = new JButton("出借/归还");
        jb_admin_publish_newbook = new JButton("发布新书");
        jb_admin_book_manager = new JButton("图书管理");
        jb_admin_reader_manage = new JButton("读者管理");

        jp_admin_function.add(jb_admin_borrow);
        jp_admin_function.add(jb_admin_publish_newbook);
        jp_admin_function.add(jb_admin_book_manager);
        jp_admin_function.add(jb_admin_reader_manage);

        jp_admin_function.setOpaque(false);
        jf_admin.add(jp_admin_function, BorderLayout.WEST);

        jf_admin.setVisible(true);
        jf_admin.setSize(610, 400);
        jf_admin.setLocation(100, 100);


        /***** 设置各按钮的点击事件 ******/
        jb_admin_search.addActionListener(this);// 搜索按钮
        jb_admin_exit.addActionListener(this);// 退出登录事件
        jb_admin_borrow.addActionListener(this);// 出借图书事件

        jb_admin_book_manager.addActionListener(this);// 图书删除
        jb_admin_publish_newbook.addActionListener(this);
        // 修改图书信息
        jb_admin_reader_manage.addActionListener(this);// 读者信息管理

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String event = e.getActionCommand();

        if (event.equals("搜索")) {
            // System.out.println("search");
            search_book_name();

        } else if (event.equals("退出")) {
            // 返回登录界面
            Login login = new Login();
            login.createUI();
            jf_admin.dispose();

        } else if (event.equals("发布新书")) {
            System.out.println("search2");
            PublishNewBooks publishNewBooks = new PublishNewBooks();
            publishNewBooks.createUI();
        } else if (event.equals("出借/归还")) {
            BorrowOrReturn borrowOrReturn = new BorrowOrReturn();
            borrowOrReturn.createUI();
        } else if (event.equals("图书管理")) {
            System.out.println("search5");
            BookMain bookMain = new BookMain();
            bookMain.createUI();


        } else if (event.equals("读者管理")) {
            System.out.println("search7");
            ReaderManage readerManage = new ReaderManage();
            readerManage.createUI();
        }

    }

    /**
     * 用搜索书本名字进行搜索！同时实现模糊搜索
     */

    public void search_book_name() {
        List<Book> book = null;
        System.out.println("成功按下搜索键");
        mm.setRowCount(0);//显示清空
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
            System.out.println(bookname + "不存在");
            JOptionPane.showMessageDialog(null, "书名不存在", "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void search_book_all() {
        List<Book> book = null;
        mm.setRowCount(0);//显示清空
        DatabaseHandlerBook databaseHandlerBook = new DatabaseHandlerBook();
        book = databaseHandlerBook.queryBook();//查所有书

        if (book != null) {
            for (int i = 0; i < book.size(); i++) {
                String[] row = {book.get(i).getBook_number(), book.get(i).getBook_name(),
                        book.get(i).getBook_author(), book.get(i).getBook_publishtime(),
                        book.get(i).getBook_amount() + "", book.get(i).getAdmin_username()};
                mm.addRow(row);
            }

        } else {
            System.out.println("不存在");
            JOptionPane.showMessageDialog(null, "数据库目前没有书录入", "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
