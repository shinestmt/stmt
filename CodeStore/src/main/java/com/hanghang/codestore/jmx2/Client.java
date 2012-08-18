package com.hanghang.codestore.jmx2;

import java.util.HashMap;
import java.util.Map;

//import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class Client {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// provide a valid username and password (e.g., via program arguments)
	    String user = "monitorRole";
	    String pw = "password";

	    // place the username and password in a string array of credentials that
	    // can be used when making the connection to the remote JMX agent
	    String[] credentials = new String[] { user, pw };
	    // the string array of credentials is placed in a map keyed against the
	    // well-defined credentials identifier string
	    Map<String, String[]> props = new HashMap<String, String[]>();
	    props.put("jmx.remote.credentials", credentials);
	    // supply the map of credentials to the connect call
	    JMXServiceURL address =
	      new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi");
	    JMXConnector connector = JMXConnectorFactory.connect(address, props);
	    // it is a trivial matter to get a reference for the MBean server
	    // connection to the remote agent
	    MBeanServerConnection mbsc = connector.getMBeanServerConnection();

	    connector.connect();

	    ObjectName objectName=new ObjectName("hello:name=hello");
	    if(!mbsc.isRegistered(objectName)){
			mbsc.createMBean("com.rmi.HelloWorld", objectName,null,null);
		}
	    
	    System.out.println("\nMBean count = " + mbsc.getMBeanCount());
//	    for (ObjectInstance object : mbsc.queryMBeans(null, null)) {//服务器上的所有MBean
//            System.out.println("object.getObjectName="+object.getObjectName());
//        }

	    //hello对象属于远程的对象，调用sayHello方法，则是调用服务器端的sayHello方法
//	    final HelloWorldMBean hello = JMX.newMBeanProxy(mbsc, objectName, HelloWorldMBean.class);
//	    hello.setName("Peter");
//	    hello.sayHello();

//	    ObjectName unObjectName=new ObjectName("hello:name=hello");
//	    Hashtable map=new Hashtable();
//	    map.put("name", "hello");

	    //unregister MBean
//	    ObjectName unObjectName=new ObjectName("hello",map);
//	    ObjectName unObjectName=ObjectName.getInstance("hello:name=hell*");
//	    for (ObjectInstance object : mbsc.queryMBeans(unObjectName, null)) {//这里可根据查询结果进行注销MBean
//	    	 mbsc.unregisterMBean(object.getObjectName());
//        }
	    System.out.println("\nMBean count = " + mbsc.getMBeanCount());
	    System.out.println("end");
	}

}
