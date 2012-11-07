package com.hanghang.codestore.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.hanghang.codestore.util.db.handler.EmptyHandler;

/**
 * 数据库操作的封装
 * @author 樊航
 */
public class DBService {
	
	private QueryRunner executor = null;

	/**
	 * 数据库连接
	 */
	private Connection conn;
	
	/**
	 * 数据库连接源
	 */
	private DataSource ds = DataSourceFactory.getDataSource();
	
	public static final RowProcessor defaultBeanConvert = new BasicRowProcessor();

	/**
	 * 根据数据库名构造一个自动提交事务的数据库操作封装类。
	 * @throws SQLException
	 */
	public DBService() throws SQLException {
		this(true);
	}
	
	/**
	 * 根据数据库名构造一个数据库操作封装类。
	 * @param autoCommit 是否自动提交. <br>true:自动提交; false:手动提交
	 * @throws SQLException
	 */
	public DBService(boolean autoCommit) throws SQLException {
		if(autoCommit){
			executor = new QueryRunner(ds);
		}else{
			this.conn = ds.getConnection();
			this.conn.setAutoCommit(false);
		}
	}

	/**
	 * 利用预编译SQL语句查询数据库，获得元素为String[]的列表。
	 * 
	 * @param sql
	 *            预编译SQL语句
	 * @param params
	 *            预编译SQL参数，可为null。
	 * @return List(String[])元素为String数组的列表
	 * @throws SQLException
	 */
//	public <T> T getList(String sql, Object... params) throws SQLException {
//		executor.query(sql, new ArrayListHandler(), params);
//		return getList(sql, null, params);
////		return executor.query(sql, new ArrayListHandler(), params);
//	}
	
	/**
	 * 利用预编译SQL语句查询数据库，获得元素为String[]的列表。
	 * 
	 * @param sql
	 *            预编译SQL语句
	 * @param params
	 *            预编译SQL参数，可为null。
	 * @return List(String[])元素为String数组的列表
	 * @throws SQLException
	 */
//	public <T> List<T> getList(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
//		return executor.query(sql, handler, params);
//	}

	/**
	 * 根据SQL语句查询的一条结果集填充Bean的属性
	 * 
	 * @param bean
	 *            要设置的JavaBean对象
	 * @param fields
	 *            JavaBean的字段
	 * @param sql
	 *            SQL语句
	 * @return true，查询到结果并填充bean成功；否则未查询到结果。
	 * @throws SQLException
	 */
	public <T> T populateBean(Class<T> cls, String sql, Object... params) throws SQLException {
		return populateBean(cls, defaultBeanConvert, sql, params);
	}
	
	/**
	 * 根据SQL语句查询的一条结果集填充Bean的属性
	 * 
	 * @param bean
	 *            要设置的JavaBean对象
	 * @param fields
	 *            JavaBean的字段
	 * @param sql
	 *            SQL语句
	 * @return true，查询到结果并填充bean成功；否则未查询到结果。
	 * @throws SQLException
	 */
	public <T> T populateBean(Class<T> cls, RowProcessor convert, String sql, Object... params) throws SQLException {
		return executor.query(sql, new BeanHandler<T>(cls, convert), params);
	}

	/**
	 * 根据SQL语句查询的结果集填充每个Bean的属性并存入List中
	 * 
	 * @param cls
	 *            要设置的JavaBean的Class
	 * @param fields
	 *            JavaBean的字段
	 * @param sql
	 *            SQL语句
	 * @return 存放bean的List
	 * @throws SQLException
	 */
	public <T> List<T> populateBeanList(Class<T> cls, String sql, Object... params) throws SQLException {
		return populateBeanList(cls, defaultBeanConvert, sql, params);
	}
	
	/**
	 * 根据SQL语句查询的结果集填充每个Bean的属性并存入List中
	 * 
	 * @param cls
	 *            要设置的JavaBean的Class
	 * @param fields
	 *            JavaBean的字段
	 * @param sql
	 *            SQL语句
	 * @return 存放bean的List
	 * @throws SQLException
	 */
	public <T> List<T> populateBeanList(Class<T> cls, RowProcessor convert, String sql, Object... params) throws SQLException {
		return executor.query(sql, new BeanListHandler<T>(cls, convert), params);
	}

	/**
	 * 执行一次给定的 INSERT、UPDATE or DELETE SQL 语句
	 * @throws SQLException
	 */
	public int update(String sql, Object... params) throws SQLException {
		return executeUpdate(this.conn, sql, params);
	}

	/**
	 * 采用预编译形式执行多次给定的 INSERT、UPDATE or DELETE SQL 语句，执行SQL的次数根据参数长度确定。
	 * 
	 * @param sql
	 *            预编译SQL语句
	 * @param params
	 *            SQL 参数，可为null。
	 * @return int 返回更新的总行数
	 * @throws SQLException
	 */
	public int[] updates(String sql, Object... params) throws SQLException {
		try {
			int[] result = new int[params.length];
			for (int i = 0; i < params.length; i++) {
				result[i] = executor.update(conn, sql, params[i]);
			}
			return result;
		} finally {
			close();
		}
	}

	/**
	 * 采用预编译SQL语句形式获得是否查询到数据
	 * 
	 * @param sql
	 *            预编译查询SQL语句
	 * @param params
	 *            SQL参数，可为null。
	 * @return boolean 查询的结果集中有数据则返回true，否则返回false。
	 * @throws SQLException
	 */
	public boolean hasData(String sql, Object... params) throws SQLException {
		return executor.query(sql, new EmptyHandler(), params);
	}

	/**
	 * 根据预编译SQL语句查询数据库并将取得的第一行数据存放到String数组中并返回。
	 * 
	 * @param sql
	 *            预编译SQL语句
	 * @param params
	 *            预编译SQL参数，可为null。
	 * @return 结果集第一行组成的数组，若没有数据则返回null。
	 * @throws SQLException
	 */
	public String[] getStringArray(String sql, Object... params) throws SQLException {
		return (String[])executor.query(sql, new ArrayHandler(), params);
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public List<Object[]> getMapList(String sql, Object... params) throws SQLException {
		return executor.query(sql, new ArrayListHandler(), params);
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public Map<String, Object> getMap(String sql, Object... params) throws SQLException {
		return executor.query(sql, new MapHandler(), params);
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getMapList(String sql, RowProcessor convert, Object... params) throws SQLException {
		return executor.query(sql, new MapListHandler(), params);
	}
	
	public boolean execute(String sql) throws Exception{
		this.conn = ds.getConnection();
		Statement stmt = conn.createStatement();
		return stmt.execute(sql);
	}

	private int executeUpdate(Connection conn, String sql, Object... params) throws SQLException{
		int result;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
			result = pstmt.executeUpdate();
		} finally {
			close();
		}
		return result;
	}

	/**
	 * 提交数据库更新
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		conn.commit();
	}

	/**
	 * 回滚数据库操作（忽略异常）
	 */
	public void rollback() {
		try {
			DbUtils.rollback(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭数据库连接（忽略异常）
	 */
	public void close() {
		DbUtils.closeQuietly(conn);
	}
}
