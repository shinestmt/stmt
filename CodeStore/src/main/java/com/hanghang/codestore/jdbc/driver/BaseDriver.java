package com.hanghang.codestore.jdbc.driver;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;


public class BaseDriver implements Driver {
	
	private static final String PREFIX = "jdbc:sqlite:";
	
	/**
	 * ע�ᵱǰ������DriverManager
	 */
	static {
		try {
			DriverManager.registerDriver(new BaseDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��֤���ݿ������ַ���
	 */
	public boolean acceptsURL(String url) throws SQLException {
		return (url!=null) && url.startsWith(PREFIX);
	}

	/**
	 * ��ȡ����
	 */
	public Connection connect(String url, Properties info) throws SQLException {
		if(!acceptsURL(url)){
			return null;
		}
		
//		info.getProperty("");
		
		return new BaseConnection();
	}

	/**
	 * ��Ҫ�汾��
	 */
	public int getMajorVersion() {
		return 1;
	}

	/**
	 * С�汾��
	 */
	public int getMinorVersion() {
		return 1;
	}

	/**
	 * ����������Ϣ
	 */
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		DriverPropertyInfo[] driverInfo = null;
		
		return driverInfo;
	}

	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}
	
}