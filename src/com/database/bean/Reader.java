package com.database.bean;
/***
 *读者的对象
 * @author wuhao
 *
 */
public class Reader {
	private String reader_username;
	private String reader_password;
	private String reader_name;
	private String reader_dept;
	private String reader_degree;
	private int authority;
	private int reader_borrow;

	public String getReader_username() {
		return reader_username;
	}

	public void setReader_username(String reader_username) {
		this.reader_username = reader_username;
	}

	public String getReader_password() {
		return reader_password;
	}

	public void setReader_password(String reader_password) {
		this.reader_password = reader_password;
	}

	public String getReader_name() {
		return reader_name;
	}

	public void setReader_name(String reader_name) {
		this.reader_name = reader_name;
	}

	public String getReader_dept() {
		return reader_dept;
	}

	public void setReader_dept(String reader_dept) {
		this.reader_dept = reader_dept;
	}

	public String getReader_degree() {
		return reader_degree;
	}

	public void setReader_degree(String reader_degree) {
		this.reader_degree = reader_degree;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public int getReader_borrow() {
		return reader_borrow;
	}

	public void setReader_borrow(int reader_borrow) {
		this.reader_borrow = reader_borrow;
	}

}
