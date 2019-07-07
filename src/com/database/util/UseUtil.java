package com.database.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/***
 * 这个用于封装系统用到的方法
 * @author XieHao
 *
 */
public class UseUtil {
	/**
	 * 获取系统当前的日期
	 * @return 系统当前时间，精确到天
	 */
	public String GetTime() {
		Date dt = new Date();		
		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println(matter1.format(dt));
		String time = matter1.format(dt);
		return time;
	}
}

