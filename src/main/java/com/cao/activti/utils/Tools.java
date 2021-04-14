package com.cao.activti.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
	
	/**
	 * 获取当前的年月日的时间, 返回的格式形如pattern指定的格式, 默认的类型为yyyy-MM, 即返回 年-月<br/>
	 * pattern的格式参考jdk手册中的说明, 诸如yyyy-MM-dd
	 * @return
	 */
	public static String getDate(String pattern) {
		if(pattern==null || "".equals(pattern))
			pattern = "yyyy-MM";
		return new SimpleDateFormat(pattern).format(new Date());
	}
	
	/**
	 * 字符串转成日期
	 * @throws ParseException 
	 */
	public static Date getDateStr(String pattern,String str) {
		SimpleDateFormat sdf =   new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
		
	}
	
	/**
	 * 获取timestamp时间戳
	 */
	public static Timestamp getTimestamp(String pattern) {
		if(pattern==null || "".equals(pattern))
			pattern = "yyyy-MM-dd HH:mm:ss";
		return Timestamp.valueOf(new SimpleDateFormat(pattern).format(new Date()));
	}
	/**
	 * 获取timestamp时间戳
	 */
	public static Timestamp getTimes(String str,String pattern) {
		//if(pattern==null || "".equals(pattern))
			//pattern = "yyyy-MM-dd HH:mm:ss";
		Timestamp ts = new Timestamp(System.currentTimeMillis());   
		 ts = Timestamp.valueOf(str);
		 System.err.println("你好："+ts);
		return ts;
	}
	
}
