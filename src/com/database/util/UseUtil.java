package com.database.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/***
 * ������ڷ�װϵͳ�õ��ķ���
 * @author XieHao
 *
 */
public class UseUtil {
	/**
	 * ��ȡϵͳ��ǰ������
	 * @return ϵͳ��ǰʱ�䣬��ȷ����
	 */
	public String GetTime() {
		Date dt = new Date();		
		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println(matter1.format(dt));
		String time = matter1.format(dt);
		return time;
	}
}

