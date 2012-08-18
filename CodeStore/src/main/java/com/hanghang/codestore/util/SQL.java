package com.hanghang.codestore.util;

import java.util.List;

public class SQL<T> {
	
	private StringBuffer sql = new StringBuffer();
	
	public SQL(){
		
	}
	
	public SQL(String sql){
		this.sql.append(sql);
	}
	
	public static SQL create(){
		return new SQL();
	}
	
	public SQL(StringBuffer sql){
		this.sql.append(sql);
	}

	public SQL(String sql, Object... args){
		this(String.format(sql, args));
		
	}
	
	public SQL delete(int start, int end){
		this.sql.delete(start, end);
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
	
	public SQL append(String str, Object... args){
		sql.append(String.format(str, args));
		return this;
	}
	
	public SQL appendln(String str){
		sql.append(str).append("\n");
		return this;
	}
	
	public SQL addEqules(String key, String value){
		this.sql.append(" and ").append(key).append("='").append(value).append("' \n");
		return this;
	}
	
	public T executeQuery(){
		return null;
	}
	
	public String toString(){
		return sql.toString();
	}
	
	public static void main(String[] args) {
		
		
		String str = "String.format(sql, args) where id='%s' and name='%s' ";
		SQL<List<String[]>> sql = new SQL<List<String[]>>(str, "myid", "myname");
		sql.append("and id='%s' and name='%s'", "fanhang", "hang");
		sql.clear();
		sql.executeQuery();
		
		System.out.println(sql);
	}
}
