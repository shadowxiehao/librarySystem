package com.database.info;

public class Borrow {
	private String borrow_reader_username;
	private String borrow_book_number;
	private String borrow_book_name;
	private String borrow_time;

	public String getBorrow_reader_username() {
		return borrow_reader_username;
	}

	public void setBorrow_reader_username(String borrow_reader_username) {
		this.borrow_reader_username = borrow_reader_username;
	}

	public String getBorrow_book_number() {
		return borrow_book_number;
	}

	public void setBorrow_book_number(String borrow_book_number) {
		this.borrow_book_number = borrow_book_number;
	}

	public String getBorrow_book_name() {
		return borrow_book_name;
	}

	public void setBorrow_book_name(String borrow_book_name) {
		this.borrow_book_name = borrow_book_name;
	}

	public String getBorrow_time() {
		return borrow_time;
	}

	public void setBorrow_time(String borrow_time) {
		this.borrow_time = borrow_time;
	}

}
