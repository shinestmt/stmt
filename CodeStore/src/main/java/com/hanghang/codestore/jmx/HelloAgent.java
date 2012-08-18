package com.hanghang.codestore.jmx;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

//import com.sun.jdmk.comm.AuthInfo;
//import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloAgent {

	private MBeanServer mbs = null;

	public HelloAgent() {
//		mbs = MBeanServerFactory.createMBeanServer();
//		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//		HelloWorld hw = new HelloWorld();
//		ObjectName adapterName = null;
//		ObjectName helloWorldName = null;
//		try {
//			helloWorldName = new ObjectName("HelloAgent:name=helloWorld1");
//			mbs.registerMBean(hw, helloWorldName);
//			adapterName = new ObjectName("HelloAgent:name=htmladapter,port=9092");
//			adapter.setPort(9999);
//			
//			AuthInfo login = new AuthInfo();
//			login.setLogin("admin");
//	        login.setPassword("11111");
//	        adapter.addUserAuthenticationInfo(login);
//	        
//	        AuthInfo login2 = new AuthInfo();
//	        login2.setLogin("admin2");
//	        login2.setPassword("22222");
//	        adapter.addUserAuthenticationInfo(login2);
//			
//			mbs.registerMBean(adapter, adapterName);
//			adapter.start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("HelloAgent is running");
		new HelloAgent();
	}
}
