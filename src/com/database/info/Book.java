package com.database.info;
/**
 * 书本的info对象
 * @author xiehao
 *
 */
public class Book {
	private String book_number;
	private String book_author;
	private String book_publishtime;
	private String book_name;
	private String admin_username;
	private int book_amount;

	public String getBook_number() {
		return book_number;
	}

	public void setBook_number(String book_number) {
		this.book_number = book_number;
	}

	public String getBook_author() {
		return book_author;
	}

	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}

	public String getBook_publishtime() {
		return book_publishtime;
	}

	public void setBook_publishtime(String book_publishtime) {
		this.book_publishtime = book_publishtime;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAdmin_username() {
		return admin_username;
	}

	public void setAdmin_username(String admin_username) {
		this.admin_username = admin_username;
	}

	public int getBook_amount() {
		return book_amount;
	}

	public void setBook_amount(int book_amount) {
		this.book_amount = book_amount;
	}
}
