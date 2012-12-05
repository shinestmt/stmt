package com.hanghang.codestore.mina;

public class TelnetServer {
	
	private static int PORT = 3005;

	public static void main(String[]  t){
//		IoAcceptor acceptor = null;
//		try{
//			acceptor = new NioSocketAcceptor();
//			acceptor.getFilterChain().addLast(
//					"telnet", 
//					new ProtocolCodecFilter(new TextLineCodecFactory(
//							Charset.forName("UTF-8"),
//							LineDelimiter.WINDOWS.getValue(),
//							LineDelimiter.WINDOWS.getValue())));
//			acceptor.getSessionConfig().setReadBufferSize(2048);
//			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
//			acceptor.setHandler(new telnetServerHandler());
//			acceptor.bind(new InetSocketAddress(PORT));
//			logger.info("Finish");
//		}catch (Exception e){
//			logger.error("Error"+e);
//		}
		
	}

}




import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.log4j.Logger;


public class telnetServerHandler extends IoHandlerAdapter{
	private static Logger logger = Logger.getLogger(telnetServerHandler.class);
	public void sessionCreated(IoSession session) throws Exception{
		logger.info("Connected");
	}
	public void sessionOpened(IoSession session) throws Exception{
		logger.info("Seesion Open");
	}
	public void messageReceived(IoSession session,Object message) throws Exception{
		String msg=message.toString();
		logger.info("Massage:"+msg);
		Process pro=Runtime.getRuntime().exec(msg);
		
		BufferedReader msgBr=new BufferedReader(new InputStreamReader(pro.getInputStream()));
		String strTemp="";
		while((strTemp = msgBr.readLine()) != null){
			session.write(strTemp);
		}
	}
	public void messageSent(IoSession session,Object message) throws Exception{
		logger.info("Send Message:"+message.toString());
	}
}
