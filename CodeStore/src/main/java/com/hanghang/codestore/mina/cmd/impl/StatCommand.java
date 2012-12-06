package com.hanghang.codestore.mina.cmd.impl;

import org.apache.mina.core.session.IoSession;

import com.hanghang.codestore.mina.cmd.Commander;

public class StatCommand extends Commander {

	
	
	@Override
	public void init() {
		options.addOption("a", "all", false, "all status");
	}

	@Override
	public void parse() {
		
	}

	@Override
	public void execute(IoSession session, String message) {
		// TODO Auto-generated method stub
		
	}

}
