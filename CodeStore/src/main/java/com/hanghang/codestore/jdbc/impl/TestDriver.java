package com.hanghang.codestore.jdbc.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

import com.hanghang.codestore.jdbc.driver.BaseDriver;

public class TestDriver extends BaseDriver {
	
	static {
		try {
			DriverManager.registerDriver(new TestDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean acceptsURL(String url) throws SQLException {
		if(url.startsWith("jdbc:fanhang//")){
			return true;
		}
		return false;
	}

	public Connection connect(String url, Properties info) throws SQLException {
		if(acceptsURL(url)){
			return new TestConnection();
		}
		return null;
	}

	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
