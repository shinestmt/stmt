package com.hanghang.codestore.mina.cmd.impl;

import org.apache.mina.core.session.IoSession;

import com.hanghang.codestore.mina.cmd.Commander;

public class ExitCommand extends Commander {

	@Override
	public void execute(IoSession session, String message) {
		session.close(false);
	}

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		
	}

}
