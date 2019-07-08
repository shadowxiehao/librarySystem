package com.database.info;

/**
 * 用于存储还书历史对象
 * @author XieHao
 */
public class Return {
    private String return_reader_username;
    private String return_borrow_book_name;
    private String return_time;
    private String return_money;
    private int return_overtime;
    private String return_borrow_time;

    public String getReturn_reader_username() {
        return return_reader_username;
    }

    public void setReturn_reader_username(String return_reader_username) {
        this.return_reader_username = return_reader_username;
    }

    public String getReturn_borrow_book_name() {
        return return_borrow_book_name;
    }

    public void setReturn_borrow_book_name(String return_borrow_book_name) {
        this.return_borrow_book_name = return_borrow_book_name;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getReturn_money() {
        return return_money;
    }

    public void setReturn_money(String return_money) {
        this.return_money = return_money;
    }

    public int getReturn_overtime() {
        return return_overtime;
    }

    public void setReturn_overtime(int return_overtime) {
        this.return_overtime = return_overtime;
    }

    public String getReturn_borrow_time() {
        return return_borrow_time;
    }

    public void setReturn_borrow_time(String return_borrow_time) {
        this.return_borrow_time = return_borrow_time;
    }
}
