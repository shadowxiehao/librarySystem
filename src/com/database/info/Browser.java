package com.database.info;
/**
 * 用于存储浏览历史对象
 * @author XieHao
 */
public class Browser {
	private String browser_reader_username;
	private String browser_bookname;

	public String getBrowser_reader_username() {
		return browser_reader_username;
	}

	public void setBrowser_reader_username(String browser_reader_username) {
		this.browser_reader_username = browser_reader_username;
	}

	public String getBrowser_bookname() {
		return browser_bookname;
	}

	public void setBrowser_bookname(String browser_bookname) {
		this.browser_bookname = browser_bookname;
	}
}
