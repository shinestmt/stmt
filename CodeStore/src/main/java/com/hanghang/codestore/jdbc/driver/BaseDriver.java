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
	 * 注册当前驱动到DriverManager
	 */
	static {
		try {
			DriverManager.registerDriver(new BaseDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 验证数据库连接字符串
	 */
	public boolean acceptsURL(String url) throws SQLException {
		return (url!=null) && url.startsWith(PREFIX);
	}

	/**
	 * 获取连接
	 */
	public Connection connect(String url, Properties info) throws SQLException {
		if(!acceptsURL(url)){
			return null;
		}
		
//		info.getProperty("");
		
		return new BaseConnection();
	}

	/**
	 * 主要版本号
	 */
	public int getMajorVersion() {
		return 1;
	}

	/**
	 * 小版本号
	 */
	public int getMinorVersion() {
		return 1;
	}

	/**
	 * 驱动配置信息
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