package com.hanghang.codestore.util.operation;

public enum Op {
	plus("plus", '+'), 
	minus("minus", '-'), 
	multiply("multiply", '*'), 
	divide("divide", '/'), 
	bool("bool", '=', '=');
	
	private String opName;
	private char[] chars;
	
	Op(String opName, char... chars){
		this.opName = opName;
		this.chars = chars;
	}
	
	public char[] chars() {
		return chars;
	}

	public String opName() {
		return opName;
	}
}
