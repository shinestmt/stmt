package com.hanghang.codestore.mina;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Parser;
import org.apache.commons.cli.PosixParser;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.hanghang.codestore.json.StringUtil;
import com.hanghang.codestore.mina.cmd.Commander;
import com.hanghang.codestore.mina.cmd.Constants;

public class TelnetServerHandler extends IoHandlerAdapter {
	
	private String PREFIX = "Console:";

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("exception");
	}

	@Override
	public void messageReceived(IoSession session, Object msg) throws Exception {
		String message = msg.toString();
		
		System.out.println("CMD: "+message);
		
		try {
			if(StringUtil.isNullOrEmpty(message)){
				return;
			}
			Commander command = Constants.cmdMap.get(message);
			if(command == null){
				session.write("Command Not Found!");
				return;
			}
			command.execute(session, message);
			if(session.isConnected()){
				session.write(PREFIX);
			}
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println(message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("close");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("Connected");
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		session.write("Welcome!\n");
	}
	
	

}
