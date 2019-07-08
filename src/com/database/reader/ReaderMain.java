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
import com.database.util.ImageLabel;//构造背景图用
import com.database.util.ResultPanel;//搜索结果用
import com.database.util.UseUtil;

/***
 * 这个是读者的界面
 *
 * @author XieHao
 *
 */
public class ReaderMain implements ActionListener {
    private JTextField jt_search;
    private JTextArea jt_show_detail;
    private JLabel jt_reader_username;
    private JFrame jf_reader;
    private JTable table = new JTable();//显示结果的表格
    private DefaultTableModel mm = null;//表默认格式
    String[] tablehead = {"书本号", "书名", "作者", "发布时间", "库存量", "发布者"};//表头内容

    public void creatUI(String Login_username) {
        jf_reader = new JFrame("读者登录");
        jf_reader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 安全退出
        jf_reader.setLayout(new BorderLayout());// 设置BorderLayout布局

        //设置刚开始显示的大小
        Dimension dimension = new Dimension(600, 600);
        jf_reader.setMinimumSize(dimension);

        //设置窗口图标
        ImageIcon imageIcon = new ImageIcon("src\\com\\database\\util\\c.jpg");// 这是图标 .png .jpg .gif 等格式的图片都可以
        jf_reader.setIconImage(imageIcon.getImage());

        //背景图片
        try {
            Image image = new ImageIcon("src\\com\\database\\util\\b.png").getImage();// 这是背景图片 .png .jpg .gif 等格式的图片都可以
            JLabel imgLabel = new ImageLabel(image, jf_reader);// 将背景图放在"标签"里。
            jf_reader.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// 注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
            Container cp = jf_reader.getContentPane();
            ((JPanel) cp).setOpaque(false); // 注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。
            imgLabel.setBounds(0, 0, jf_reader.getWidth(), jf_reader.getHeight());// 设置背景标签的位置

            jf_reader.addComponentListener(new ComponentAdapter() {//监听窗口大小改变,然后改变jlabel大小
                @Override
                public void componentResized(ComponentEvent e) {
                    imgLabel.setSize(jf_reader.getWidth(), jf_reader.getHeight());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***** 以下部分就是界面的第一个功能面板 ****/
        JPanel jp_title = new JPanel();
        jt_search = new JTextField(20);
        jt_search.setText("搜索书名或序号");
        JButton jb_search = new JButton("搜索");
        JLabel jt_space1 = new JLabel("  ");
        JLabel jt_welcome = new JLabel("欢迎您：");
        jt_reader_username = new JLabel("");
        // 这个标签用于显示登录进来的读者的读者号
        jt_reader_username.setText(Login_username);
        JButton jb_exit = new JButton("退出");
        jp_title.add(jt_search);// 搜索条
        jp_title.add(jb_search);// 确定搜索键
        jp_title.add(jt_space1);
        jp_title.add(jt_welcome);// 欢迎XX
        jp_title.add(jt_reader_username);
        jp_title.add(jb_exit);    // 退出键

        jp_title.setOpaque(false);//透明
        jf_reader.add(jp_title, BorderLayout.NORTH);// 设置布局在最上面

        // 以下是搜索后显示的内容
        mm = new DefaultTableModel(tablehead, 0);//顺便把表头加进去
        table.setModel(mm);
        table.setOpaque(false);//透明
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setOpaque(false);//透明
        jScrollPane.getViewport().setOpaque(false);//透明
        jf_reader.add(jScrollPane, BorderLayout.CENTER);
        search_book_all();//刚来先把所有信息显示出来,方便找

//		表格化设计,将这里抛弃,呜呜呜
//		jt_show_detail = new JTextArea();
//		jt_show_detail.setEditable(false);
//
//		jt_show_detail.setOpaque(false);//透明
//		jf_reader.add(jt_show_detail, BorderLayout.CENTER);

        // 读者界面的主要功能
        JPanel jp_function = new JPanel();
        BoxLayout bl_function = new BoxLayout(jp_function, BoxLayout.Y_AXIS);// 设置功能按钮显示为BoxLayout
        jp_function.setLayout(bl_function);
        JButton jb_borrow = new JButton("借书");
        JButton jb_already_borrow = new JButton("已借书本");
        JButton jb_already_search = new JButton("检索历史");
        JButton jb_change_information = new JButton("修改信息");

        //jp_function.add(jb_borrow);
        jp_function.add(jb_already_borrow);
        jp_function.add(jb_already_search);
        jp_function.add(jb_change_information);

        jp_function.setOpaque(false);//透明
        jf_reader.add(jp_function, BorderLayout.WEST);

        jf_reader.setVisible(true);
        jf_reader.setSize(800, 600);
        jf_reader.setLocation(100, 100);
        // 组件设置监听
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
        if (event.equals("搜索")) {
            search_book_name();

        } else if (event.equals("退出")) {
            Login login = new Login();
            login.createUI();
            jf_reader.dispose();
        } else if (event.equals("借书")) {

        } else if (event.equals("已借书本")) {
            search_borrow_history();
        } else if (event.equals("检索历史")) {
            search_history();
        } else if (event.equals("修改信息")) {
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
                JOptionPane.showMessageDialog(null, "用户不存在", "成功",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    /**
     * 读者界面显示相关的图书信息
     */
    public void search_book_name() {
        List<Book> book = null;
        mm.setColumnIdentifiers(tablehead);
        mm.setRowCount(0);//显示清空
        System.out.println("成功按下搜索键");
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
                    /**** 将搜索的记录(书名,人,时间)存入browser(历史搜索)表中 *********/
                    String jt_reader_username1 = jt_reader_username.getText();
                    DatabaseHandlerBrowser databaseHandlerBrowser = new DatabaseHandlerBrowser();
                    Browser browser = new Browser();
                    browser.setBrowser_bookname(book.get(i).getBook_name());//添加检索的书名
                    browser.setBrowser_reader_username(jt_reader_username1);//添加这次检索的用户名
                    browser.setBorrow_time(UseUtil.GetTime());//这次检索的时间
                    databaseHandlerBrowser.insert_into_browsertable(browser);
                }

            } else {
                System.out.println(bookname + "不存在");
                JOptionPane.showMessageDialog(null, "书名不存在", "错误",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
//		List<Book> book = databaseHandlerBook.queryBook(bookname);
//		jt_show_detail.append("书本号\t " + "书名\t " + "作者\t " + "发布时间\t "
//				+ "库存量\t " + "发布者\t ");


    }

    /**
     * 搜索并显示已经借到的书本
     */
    public void search_borrow_history() {
        String[] newtablehead={"读者号" , "书本号" , "书名" , "借阅时间"};
        mm.setColumnIdentifiers(newtablehead);
        mm.setRowCount(0);
        String readerusername = jt_reader_username.getText();
        System.out.println(readerusername);
//        jt_show_detail.setText("");
        DatabaseHistory databaseHistory = new DatabaseHistory();
        List<History> history = databaseHistory.QueryHistorytable(readerusername);
//        jt_show_detail.append("读者号\t" + "书本号\t " + "书名\t " + "借阅时间\t ");
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
            System.out.println(readerusername + "不存在");
            JOptionPane.showMessageDialog(null, "书名不存在", "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 搜索历史
     */
    public void search_history() {
        String[] newtablehead={"读者号" , "书名" , "日期" };
        mm.setColumnIdentifiers(newtablehead);
        mm.setRowCount(0);
        String readerusername = jt_reader_username.getText();

//        jt_show_detail.setText("");
        DatabaseHandlerBrowser databaseHandlerBrowser = new DatabaseHandlerBrowser();
        List<Browser> browser = databaseHandlerBrowser
                .QueryBrowserTable(readerusername);
//        jt_show_detail.append("读者号\t" + "书名\t " + "日期\t ");
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
            System.out.println(readerusername + "不存在");
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
