package com.hanghang.codestore.jmx.adaptor;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistration;
import javax.management.ReflectionException;

import com.sun.jmx.snmp.daemon.CommunicationException;
import com.sun.jmx.snmp.daemon.CommunicatorServer;

public class AdaptorServer extends CommunicatorServer implements MBeanRegistration, DynamicMBean{

	public AdaptorServer() {
		super(0);
	}
	
	@Override
	protected void doBind() throws CommunicationException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doError(Exception arg0) throws CommunicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doProcess() throws CommunicationException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doReceive() throws CommunicationException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doUnbind() throws CommunicationException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeList getAttributes(String[] attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	public MBeanInfo getMBeanInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		// TODO Auto-generated method stub
		
	}

	public AttributeList setAttributes(AttributeList attributes) {
		// TODO Auto-generated method stub
		return null;
	}

}
