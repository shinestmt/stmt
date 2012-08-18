package com.hanghang.codestore.other;

import java.sql.SQLException;


/**
 * SQL对象
 * Company: 深圳市康索特软件有限公司
 * Copyright: Copyright (c) 2010
 * version 3.0.0.0
 * @author 樊航
 */
public class SQL {
	
	private StringBuffer sql = null;
	
	public SQL() throws SQLException {
		sql = new StringBuffer();
	}
	
	public SQL delete(int start, int end){
		sql.delete(start, end);
		return this;
	}
	
	public SQL clear() {
		this.delete(0, sql.length());
		return this;
	}
	
	public SQL append(String str){
		sql.append(str);
		return this;
	}
	
	public SQL appendln(String str){
		sql.append(str).append("\n");
		return this;
	}
	
	public SQL where(String key, String value){
		if(value==null || value.equals("")){
			this.appendln("where 1=1 ");
		}else{
			this.append("where ").append(key).append("='").append(value).appendln("'");
		}
		return this;
	}
	
	public SQL equals(String key, String value){
		this.add("=", key, "'", value, "'");
		return this;
	}
	
	public SQL in(String key, String... strs){
		this.add("in", key, "(", connect(strs).toString(), ")");
		return this;
	}
	
	public SQL greatEquals(String key, String value){
		this.add(">=", key, "'", value, "'");
		return this;
	}
	
	public SQL lessEquals(String key, String value){
		this.add("<=", key, "'", value, "'");
		return this;
	}
	
	public SQL notEquals(String key, String value){
		this.add("<>", key, "'", value, "'");
		return this;
	}
	
	public SQL like(String key, String value){
		this.add("like", key, "%", value, "%");
		return this;
	}
	
	public SQL notLike(String key, String value){
		this.add("not like", key, "%", value, "%");
		return this;
	}
	
	private void add(String flag, String key, String prefix,String value, String suffix){
		if(value!=null && !value.equals("")){
			sql.append(" and ").append(key).append(" ").append(flag).append(prefix).append(value).append(suffix).append(" \n");
		}
	}
	
	/**
	 * 把字符串列表每一项作为SQL中的数字格式连接成一串字符，可用于SQL中的in条件语句。
	 * @param list
	 * @return
	 */
	private String connect(String... strs) {
		if(strs==null || strs.length==0){
			return "''";
		}
		
		StringBuffer s = new StringBuffer();
		for (int i = 0, ii = strs.length; i < ii; i++) {
			s.append("'").append(strs[i]).append("',");
		}
		if (s.length() > 0) {
			s.deleteCharAt(s.length() - 1);
		}
		return s.toString();
	}
	
	public SQL setVar(Object... vars){
		String temp = String.format(this.toString(), vars);
		return this.clear().append(temp);
	}
	
	public String toString(){
		return this.getSQL();
	}
	
	public boolean equals(Object obj){
		if(!(obj instanceof SQL)){
			return false;
		}
		return sql.equals(obj);
	}
	
	public String getSQL(){
		return sql.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String[] menuIds = {"10", "20", "30", "40", "50", "60", "70"};
		SQL sql = new SQL();
		sql.appendln("select * from stat_menu ")
		.where("name", "")
		.in("menuid", menuIds)
		.like("menuname", "分析");
		
		System.out.println(sql);
	}
}
