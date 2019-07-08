package com.database.jdbc;

import java.io.File;
import java.io.IOException;

public class BackupOrRecover {
    /**
     * @param savePath 备份的路径
     * @return
     */
    public static boolean backup(String savePath) {
        /*
         * @param hostIP ip地址，可以是本机也可以是远程
         * @param userName 数据库的用户名
         * @param password 数据库的密码
         * @param fileName 备份的文件名
         * @param databaseName 需要备份的数据库的名称
         */
        String hostIP = "127.0.0.1";
        String userName = "root";
        String password = "xiehao@799522476";
        String fileName = "backup";
        String databaseName = "librarydb";

        fileName += ".sql";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }

        //拼接命令行的命令
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump").append(" --opt").append(" -h").append(hostIP);
        stringBuilder.append(" --user=").append(userName).append(" --password=").append(password)
                .append(" --lock-all-tables=true");
        stringBuilder.append(" --result-file=").append(savePath + fileName).append(" --default-character-set=utf8 ")
                .append(databaseName);
        try {
            //调用外部执行exe文件的javaAPI
            Process process = Runtime.getRuntime().exec(stringBuilder.toString());
            if (process.waitFor() == 0) {// 0 表示线程正常终止。
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
     * @param filepath 数据库备份的脚本路径
     * @return
     */
    public static boolean recover(String filepath) {
        /*IP地址
          database 数据库名称
          userName 用户名
          password 密码
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
            System.out.println("数据已从 " + filepath + " 导入到数据库中");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
