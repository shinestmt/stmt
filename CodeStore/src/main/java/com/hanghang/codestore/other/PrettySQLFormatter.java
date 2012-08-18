package com.hanghang.codestore.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;

public class PrettySQLFormatter {
	/**
	 * ��ӡƯ����SQL��� Τ����
	 * 
	 * @since 2011-05-27
	 * @param sql
	 *            SQL���
	 */
	public static void print(String sql) {
		System.out.println(getPerttySql(sql));
	}

	/**
	 * ��ӡƯ����SQL��� Τ����
	 * 
	 * @since 2011-05-27
	 * @param remark
	 *            ��ӡǰ��˵����Ϣ
	 * @param sql
	 *            SQL���
	 */
	public static void print(String remark, String sql) {
		System.out.println(getPerttySql(remark, sql));
	}

	/**
	 * ��ȡƯ����SQL��� Τ����
	 * 
	 * @since 2011-05-27
	 * @param sql
	 *            SQL���
	 */
	public static String getPerttySql(String sql) {
//		FormatStyle.BASIC.getFormatter().format(sql);
		return null;
	}

	/**
	 * ��ȡƯ����SQL��� Τ����
	 * 
	 * @since 2011-05-27
	 * @param remark
	 *            ��ӡǰ��˵����Ϣ
	 * @param sql
	 *            SQL���
	 */
	public static String getPerttySql(String remark, String sql) {
		return remark + getPerttySql(sql);
	}
	
	@Test
	public void testPrintSQL() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.zip_code,a.login_name,a.xnhm,b.areaname,  \n")
		.append("case a.sts when '1' then '����' when '2' then '����ͣ��' when '3' then 'Ƿ��ͣ��' when '5' then '���' end, \n")
		.append(" a.ktsj_date,a.sfsj_date,a.kfsj_date,a.end_date,a.name,a.pay_rate,a.pay_date,a.pay_jf,a.pay_ye_before,a.pay_kf,a.pay_ye_after, \n")
		.append("case a.type when '1' then '����' when '2' then '����' end,a.ykf \n")
		.append(" from pan_byjf_ft_1109 as a left join stat_area as  b on  a.zgs = b.areaid \n")
		.append("where a.area_comp in (select a.areaid from stat_arearights a, stat_area b where a.areaid=b.areaid and b.areatype=2 and a.roleid=1 ) \n")
		.append(" and a.area_comp='900002' \n")
		.append("order by 1\n");
		print("--��ע", sql.toString());
	}
	
	@Test
	public void testPrintSQLFromFile() throws Exception {
		StringBuffer sql = new StringBuffer();
		
		File file = new File("SQL.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			sql.append(line).append("\n");
		}
		String[] sqls = sql.toString().split(";");
		for (int i = 0; i < sqls.length; i++) {
			print(sqls[i]+";");
			
			for (int j = 0; j < 15; j++) { System.out.print("-----"); }
			System.out.println();
		}
		
	}
}
