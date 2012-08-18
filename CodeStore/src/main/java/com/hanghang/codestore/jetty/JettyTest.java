package com.hanghang.codestore.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Test;

import com.hanghang.codestore.jetty.servlet.impl.GoodbyeServlet;
import com.hanghang.codestore.jetty.servlet.impl.HelloServlet;

public class JettyTest {

	@Test
	public void testname() throws Exception {
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(Integer.getInteger("jetty.port", 8080).intValue());
		server.setConnectors(new Connector[] { connector });

		server.start();
		server.join();
	}
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		
		// http://localhost:8080/hello
		context.addServlet(new ServletHolder(new HelloServlet()), "/hello");

		// http://localhost:8080/goodbye
		context.addServlet(new ServletHolder(new GoodbyeServlet()), "/goodbye");

		server.start();
//		server.join();
	}

}
