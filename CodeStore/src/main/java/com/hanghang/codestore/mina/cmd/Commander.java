package com.hanghang.codestore.mina.cmd;

import org.apache.commons.cli.Options;
import org.apache.mina.core.session.IoSession;

public abstract class Commander {
	
	protected String command = "";
	protected String[] args = {};
	protected Options options = new Options();
	
	public Commander() {
		
	}
	
	public Commander(String command, int index) {
		this.command = command;
		String arg = command.substring(0, index).trim();
		args = arg.split(" ");
	}
	
	public void init() {
		
	}
	
	public void parse() {
		
	}
	
	public void execute(IoSession session, String message) {
		
	}

}
