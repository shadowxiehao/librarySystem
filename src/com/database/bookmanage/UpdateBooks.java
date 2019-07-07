package com.database.bookmanage;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.database.info.Book;
import com.database.jdbc.DatabaseHandlerBook;

public class UpdateBooks {
	public void createUI(final String number, final String name,
			final String author, final String publishtime, final int amount,
			final String admin) {
		final JFrame jframe = new JFrame("读者信息");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setLayout(new FlowLayout());
		/**** 登录的账号密码 ****/
		JLabel jl_booknumber = new JLabel("书本号 *:");
		final JTextField jt_booknumber = new JTextField(15);
		jt_booknumber.setText(number);
		JLabel jl_bookname = new JLabel("书名     *:");
		final JTextField jt_bookname = new JTextField(15);
		jt_bookname.setText(name);
		JLabel jl_bookauthor = new JLabel("作者      :");
		final JTextField jt_bookauthor = new JTextField(15);
		jt_bookauthor.setText(author);
		JLabel jl_bookpublishtime = new JLabel("发布时间 :");
		final JTextField jt_bookpublishtime = new JTextField(15);
		// 将int转换成String
		
		jt_bookpublishtime.setText(publishtime);

		JLabel jl_bookamount = new JLabel("库存量  *:");
		final JTextField jt_bookamount = new JTextField(15);		
		jt_bookamount.setText(amount+"");
		JLabel jl_bookadmin = new JLabel("发布者  *:");
		final JTextField jt_bookadmin = new JTextField(15);
		jt_bookadmin.setText(admin);
		/*** 确认键 ***/
		JButton jb_confirm = new JButton("修改");
		jb_confirm.setSize(15, 5);

		/*** 添加组件 **/
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
		jframe.setSize(280, 350);
		jframe.setLocation(100, 200);
		jb_confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DatabaseHandlerBook databaseHandlerBook=new DatabaseHandlerBook();
				Book book=new Book();
				/**下面的语句改动添加final 如果出错可以改回来即可**/
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
							"确定修改？", "修改", JOptionPane.YES_NO_OPTION);
					if (confirm_delete == JOptionPane.YES_OPTION) {
						
						databaseHandlerBook.update_booktable(book1);
						JOptionPane.showMessageDialog(null, "修改成功", "成功",
								JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				else{
					JOptionPane.showMessageDialog(null, "书本号不存在", "错误",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
}
