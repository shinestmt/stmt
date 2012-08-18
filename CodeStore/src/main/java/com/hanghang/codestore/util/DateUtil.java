package com.hanghang.codestore.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	SimpleDateFormat sdf = null;
	
	private static SimpleDateFormat createShortYMFormat(){
		return new SimpleDateFormat("yyyyMM");
	}
	
	private static SimpleDateFormat createLongYMFormat(){
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	private static SimpleDateFormat createShortYMDFormat(){
		return new SimpleDateFormat("yyyyMMdd");
	}
	
	private static SimpleDateFormat createLongYMDFormat(){
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	private int getDaysBetweenShortYMD(String startYMD, String endYMD) throws ParseException {
		sdf = createShortYMDFormat();
		long start = sdf.parse(startYMD).getTime();
		long end = sdf.parse(endYMD).getTime();
		return (int)(end-start)/1000/60/60/24;
	}
	
	public String getYearDiffYear(int diffYear){
		String year = "";
		
		return year;
	}
	
	/**
	 * ��ȡ����������������
	 * @param shortYMD1 ����1(yyyyMMdd)
	 * @param shortYMD2 ����2(yyyyMMdd)
	 * @return �������
	 * @throws ParseException
	 */
	public static int diffShortYMD(String shortYMD1, String shortYMD2) throws ParseException {
		SimpleDateFormat sdf = createShortYMDFormat();
		long start = sdf.parse(shortYMD1).getTime();
		long end = sdf.parse(shortYMD2).getTime();
		return (int)((start-end)/1000/60/60/24);
	}
	
	/**
	 * ��ȡ�������������·���
	 * @param shortYM1 ����1(yyyyMM)
	 * @param shortYM2 ����2(yyyyMM)
	 * @return ����·���(����1-����2)
	 * @throws ParseException
	 */
	public static int diffShortYM(String shortYM1, String shortYM2) throws ParseException {
		SimpleDateFormat sdf = createShortYMFormat();
		
		Calendar c = sdf.getCalendar();
		;
		c.setTime(sdf.parse(shortYM1));
		int start = c.get(Calendar.DAY_OF_MONTH);
		
		c.setTime(new Date(shortYM2));
		int end = sdf.getCalendar().get(Calendar.DAY_OF_MONTH);
		
		int ym1 = Integer.parseInt(shortYM1);
		int ym2 = Integer.parseInt(shortYM2);
		return ym1-ym2;
	}
	
	public static void main(String[] args) {
		try {
			int size = diffShortYM("201012", "201101");
			System.out.println(size);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
