package com.database.util;

import com.database.admin.PublishNewBooks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ResultPanel {
    private JPanel resultpanel = new JPanel();
    private JScrollPane scrollPanel = new JScrollPane();//������
    private JTable table = new JTable();
    private DefaultTableModel mm = null; //new DefaultTableModel(col, 0);

    private String[] tableHead=null;//���嶥����ʾ��
    private Object[][] tableData=null;//�����������

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
        resultpanel.setName("��ѯ���");
        resultpanel.setVisible(true);//��Ϊ��ʾ
        resultpanel.add(scrollPanel);//������
        resultpanel.setOpaque(false);//͸��

        return resultpanel;
    }

}
