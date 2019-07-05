package com.database.bean;

/**
 * 用于存储个人的借书历史
 * 
 * @author wuhao
 * 
 */
public class History {
	private String history_username;
	private String history_book_number;
	private String history_book_name;
	private String history_borrow_time;

	public String getHistory_username() {
		return history_username;
	}

	public void setHistory_username(String history_username) {
		this.history_username = history_username;
	}

	public String getHistory_book_number() {
		return history_book_number;
	}

	public void setHistory_book_number(String history_book_number) {
		this.history_book_number = history_book_number;
	}

	public String getHistory_book_name() {
		return history_book_name;
	}

	public void setHistory_book_name(String history_book_name) {
		this.history_book_name = history_book_name;
	}

	public String getHistory_borrow_time() {
		return history_borrow_time;
	}

	public void setHistory_borrow_time(String history_borrow_time) {
		this.history_borrow_time = history_borrow_time;
	};

}
