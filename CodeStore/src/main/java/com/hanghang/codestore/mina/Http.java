package com.hanghang.codestore.mina;

import org.apache.mina.proxy.ProxyAuthException;
import org.apache.mina.proxy.handlers.http.basic.HttpBasicAuthLogicHandler;
import org.apache.mina.proxy.session.ProxyIoSession;

public class Http extends HttpBasicAuthLogicHandler {

	public Http(ProxyIoSession proxyIoSession) throws ProxyAuthException {
		super(proxyIoSession);
		// TODO Auto-generated constructor stub
	}

}
