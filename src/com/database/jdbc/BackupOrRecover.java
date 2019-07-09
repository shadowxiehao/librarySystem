package com.database.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class BackupOrRecover {
    static String file= "";
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
//    public static boolean recover(String filepath) {
//        /*IP地址
//          database 数据库名称
//          userName 用户名
//          password 密码
//         */
//        String ip = "127.0.0.1";
//        String database = "librarydb";
//        String userName = "root";
//        String password = "xiehao@799522476";
//        String port = "3306";
//
////        String stmt0 = "mysqladmin -h " + ip + " -u " + userName + " -p" + password + " drop " + database;
//
////        String stmt1 = "mysqladmin -h " + ip + " -u " + userName + " -p" + password + " create " + database;
//
//        String stmt2 = "mysql -h " + ip + " -u " + userName + " -p "  + " -P" + port +" "+ database + " <" + filepath ;
//        String[] doit={stmt2,password};
//
//        String[] cmd = {"cmd", "/c"};
//        try {
////            Runtime.getRuntime().exec(stmt0);
////            Runtime.getRuntime().exec(stmt1);
//            Runtime.getRuntime().exec(cmd,doit,null);
////            Runtime.getRuntime().exec(password);
//
////            Runtime.getRuntime().exec(password);
//            System.out.println("数据已从 " + filepath + " 导入到数据库中");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
    /**
     * @param filepath 数据库备份的脚本路径
     * @return
     */
    public static boolean recover(String filepath) throws Exception{

        file = filepath;
        Runtime rt = Runtime.getRuntime();
        Process pro = rt.exec(getCommand());
        BufferedReader br = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
        String errorLine = null;
        while ((errorLine = br.readLine()) != null) {
            System.out.println(errorLine);
        }
        br.close();
        int result = pro.waitFor();
        if (result != 0) {
            throw new Exception("数据库恢复失败！ ");
        }else {
            return true;
        }
    }

    static String[] getCommand() {
        String database = "librarydb";
        String userName = "root";
        String password = "xiehao@799522476";
        String[] cmd = new String[3];
        String os = System.getProperties().getProperty("os.name");
        if (os.startsWith("Win")) {
            cmd[0] = "cmd.exe";
            cmd[1] = "/c";
        } else {
            cmd[0] = "/bin/sh";
            cmd[1] = "-c";
        }
        StringBuilder arg = new StringBuilder();
        arg.append("mysql ");
        arg.append("-u"+userName+" ");
        arg.append("-p"+password+" ");
        arg.append(database+" ");
        arg.append("< ");
        arg.append(file);
        cmd[2] = arg.toString();
        return cmd;
    }

}
