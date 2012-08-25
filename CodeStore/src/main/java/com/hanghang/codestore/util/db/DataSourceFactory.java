package com.hanghang.codestore.util.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DataSourceFactory {

	private static DruidDataSource ds;

	/**
	 * 私有构造方法，不允许外部实例化。
	 */
	private DataSourceFactory() {
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("./druid.properties"));
			ds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得数据库连接
	 * 
	 * @return 数据库连接
	 * @throws SQLException
	 */
	public static DataSource getDataSource() throws SQLException {
		return ds;
	}

}
