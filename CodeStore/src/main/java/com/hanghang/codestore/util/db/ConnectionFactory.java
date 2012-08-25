package com.hanghang.codestore.util.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * �������ݿ����ӵĹ���<br>
 * Company�������п�����������޹�˾<br>
 * copyright Copyright (c) 2010<br>
 * version 3.0.0.0<br>
 * 
 * @author Τ˹��
 */
public class ConnectionFactory {

	private static DataSource ds = null;

	/**
	 * ˽�й��췽�����������ⲿʵ������
	 * 
	 * @throws SQLException
	 */
	private ConnectionFactory() throws SQLException {

	}

	/**
	 * ������ݿ�����
	 * 
	 * @return ���ݿ�����
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (ds == null) {
			ds = DataSourceFactory.getDataSource();
		}
		return ds.getConnection();
	}
}
