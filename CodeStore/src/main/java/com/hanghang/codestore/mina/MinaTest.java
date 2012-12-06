package com.hanghang.codestore.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.hanghang.codestore.mina.cmd.Constants;
import com.hanghang.codestore.mina.cmd.impl.ExitCommand;
import com.hanghang.codestore.mina.cmd.impl.HelpCommand;

public class MinaTest {
	
	private static void init() {
		Constants.cmdMap.put("help", new HelpCommand());
		Constants.cmdMap.put("exit", new ExitCommand());
//		Constants.cmdMap.put("help", new HelpCommand());
		
	}
	
	public static void main(String[] args) {
		
		IoAcceptor acceptor = null;
		
		try{
			acceptor = new NioSocketAcceptor();
			LineDelimiter.WINDOWS.getValue();
						
			acceptor.getFilterChain().addLast( "logger", new LoggingFilter());
//			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS, LineDelimiter.WINDOWS)));
			acceptor.getFilterChain().addLast("http", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
			
//			acceptor.getSessionConfig().setReadBufferSize(2048);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 5);
			acceptor.setHandler(new HttpHandler());
			
			acceptor.bind(new InetSocketAddress(9000));
			
			init();
			
			System.out.println("Finish");
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Error"+e);
		}
	}

}
