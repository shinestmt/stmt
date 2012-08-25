package com.hanghang.codestore.util.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 创建数据库连接的工厂<br>
 * Company：深圳市康索特软件有限公司<br>
 * copyright Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * 
 * @author 韦斯多
 */
public class ConnectionFactory {

	private static DataSource ds = null;

	/**
	 * 私有构造方法，不允许外部实例化。
	 * 
	 * @throws SQLException
	 */
	private ConnectionFactory() throws SQLException {

	}

	/**
	 * 获得数据库连接
	 * 
	 * @return 数据库连接
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (ds == null) {
			ds = DataSourceFactory.getDataSource();
		}
		return ds.getConnection();
	}
}
