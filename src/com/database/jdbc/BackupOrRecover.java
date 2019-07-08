package com.database.jdbc;

import java.io.File;
import java.io.IOException;

public class BackupOrRecover {
    /**
     * @param savePath ���ݵ�·��
     * @return
     */
    public static boolean backup(String savePath) {
        /*
         * @param hostIP ip��ַ�������Ǳ���Ҳ������Զ��
         * @param userName ���ݿ���û���
         * @param password ���ݿ������
         * @param fileName ���ݵ��ļ���
         * @param databaseName ��Ҫ���ݵ����ݿ������
         */
        String hostIP = "127.0.0.1";
        String userName = "root";
        String password = "xiehao@799522476";
        String fileName = "backup";
        String databaseName = "librarydb";

        fileName += ".sql";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// ���Ŀ¼������
            saveFile.mkdirs();// �����ļ���
        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }

        //ƴ�������е�����
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump").append(" --opt").append(" -h").append(hostIP);
        stringBuilder.append(" --user=").append(userName).append(" --password=").append(password)
                .append(" --lock-all-tables=true");
        stringBuilder.append(" --result-file=").append(savePath + fileName).append(" --default-character-set=utf8 ")
                .append(databaseName);
        try {
            //�����ⲿִ��exe�ļ���javaAPI
            Process process = Runtime.getRuntime().exec(stringBuilder.toString());
            if (process.waitFor() == 0) {// 0 ��ʾ�߳�������ֹ��
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param filepath ���ݿⱸ�ݵĽű�·��
     * @return
     */
    public static boolean recover(String filepath) {
        /*IP��ַ
          database ���ݿ�����
          userName �û���
          password ����
         */
        String ip = "127.0.0.1";
        String database = "librarydb";
        String userName = "root";
        String password = "xiehao@799522476";

        String stmt1 = "mysqladmin -h " + ip + " -u " + userName + " -p" + password + " create " + database;

        String stmt2 = "mysql -h " + ip + " -u " + userName + " -p " + password + " " + database + " < " + filepath;

        String[] cmd = {"cmd", "/c", stmt2};

        try {
            Runtime.getRuntime().exec(stmt1);
            Runtime.getRuntime().exec(cmd);
            System.out.println("�����Ѵ� " + filepath + " ���뵽���ݿ���");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
