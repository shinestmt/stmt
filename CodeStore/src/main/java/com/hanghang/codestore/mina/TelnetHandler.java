package com.hanghang.codestore.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.hanghang.codestore.json.StringUtil;
import com.hanghang.codestore.mina.cmd.Commander;
import com.hanghang.codestore.mina.cmd.Constants;

public class TelnetHandler extends IoHandlerAdapter {
	
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
			} else {
				command.execute(session, message);
			}
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
