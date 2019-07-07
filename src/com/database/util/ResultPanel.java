package com.database.util;

import com.database.admin.PublishNewBooks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ResultPanel {
    private JPanel resultpanel = new JPanel();
    private JScrollPane scrollPanel = new JScrollPane();//滚动栏
    private JTable table = new JTable();
    private DefaultTableModel mm = null; //new DefaultTableModel(col, 0);

    private String[] tableHead=null;//定义顶端显示栏
    private Object[][] tableData=null;//表格下面内容

    public ResultPanel(){
    }
    public ResultPanel(String[] tableHead){
        this.tableHead=tableHead;
        mm = new DefaultTableModel(tableHead, 0);
        table.setModel(mm);
    }
    public void sethead(String[] tableHead){
        this.tableHead=tableHead;
        mm = new DefaultTableModel(tableHead, 0);
        table.setModel(mm);
    }

    public JPanel getResultpanel(){

        scrollPanel.add(table);
        resultpanel.setBackground(Color.LIGHT_GRAY);
        resultpanel.setForeground(Color.WHITE);
        resultpanel.setName("查询结果");
        resultpanel.setVisible(true);//设为显示
        resultpanel.add(scrollPanel);//添加组件
        resultpanel.setOpaque(false);//透明

        return resultpanel;
    }

}
