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
 * ���ݿ�����ķ�װ
 * @author ����
 */
public class DBService {
	
	private QueryRunner executor = null;

	/**
	 * ���ݿ�����
	 */
	private Connection conn;
	
	/**
	 * ���ݿ�����Դ
	 */
	private DataSource ds = DataSourceFactory.getDataSource();
	
	public static final RowProcessor defaultBeanConvert = new BasicRowProcessor();

	/**
	 * �������ݿ�������һ���Զ��ύ��������ݿ������װ�ࡣ
	 * @throws SQLException
	 */
	public DBService() throws SQLException {
		this(true);
	}
	
	/**
	 * �������ݿ�������һ�����ݿ������װ�ࡣ
	 * @param autoCommit �Ƿ��Զ��ύ. <br>true:�Զ��ύ; false:�ֶ��ύ
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
	 * ����Ԥ����SQL����ѯ���ݿ⣬���Ԫ��ΪString[]���б�
	 * 
	 * @param sql
	 *            Ԥ����SQL���
	 * @param params
	 *            Ԥ����SQL��������Ϊnull��
	 * @return List(String[])Ԫ��ΪString������б�
	 * @throws SQLException
	 */
//	public <T> T getList(String sql, Object... params) throws SQLException {
//		executor.query(sql, new ArrayListHandler(), params);
//		return getList(sql, null, params);
////		return executor.query(sql, new ArrayListHandler(), params);
//	}
	
	/**
	 * ����Ԥ����SQL����ѯ���ݿ⣬���Ԫ��ΪString[]���б�
	 * 
	 * @param sql
	 *            Ԥ����SQL���
	 * @param params
	 *            Ԥ����SQL��������Ϊnull��
	 * @return List(String[])Ԫ��ΪString������б�
	 * @throws SQLException
	 */
//	public <T> List<T> getList(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
//		return executor.query(sql, handler, params);
//	}

	/**
	 * ����SQL����ѯ��һ����������Bean������
	 * 
	 * @param bean
	 *            Ҫ���õ�JavaBean����
	 * @param fields
	 *            JavaBean���ֶ�
	 * @param sql
	 *            SQL���
	 * @return true����ѯ����������bean�ɹ�������δ��ѯ�������
	 * @throws SQLException
	 */
	public <T> T populateBean(Class<T> cls, String sql, Object... params) throws SQLException {
		return populateBean(cls, defaultBeanConvert, sql, params);
	}
	
	/**
	 * ����SQL����ѯ��һ����������Bean������
	 * 
	 * @param bean
	 *            Ҫ���õ�JavaBean����
	 * @param fields
	 *            JavaBean���ֶ�
	 * @param sql
	 *            SQL���
	 * @return true����ѯ����������bean�ɹ�������δ��ѯ�������
	 * @throws SQLException
	 */
	public <T> T populateBean(Class<T> cls, RowProcessor convert, String sql, Object... params) throws SQLException {
		return executor.query(sql, new BeanHandler<T>(cls, convert), params);
	}

	/**
	 * ����SQL����ѯ�Ľ�������ÿ��Bean�����Բ�����List��
	 * 
	 * @param cls
	 *            Ҫ���õ�JavaBean��Class
	 * @param fields
	 *            JavaBean���ֶ�
	 * @param sql
	 *            SQL���
	 * @return ���bean��List
	 * @throws SQLException
	 */
	public <T> List<T> populateBeanList(Class<T> cls, String sql, Object... params) throws SQLException {
		return populateBeanList(cls, defaultBeanConvert, sql, params);
	}
	
	/**
	 * ����SQL����ѯ�Ľ�������ÿ��Bean�����Բ�����List��
	 * 
	 * @param cls
	 *            Ҫ���õ�JavaBean��Class
	 * @param fields
	 *            JavaBean���ֶ�
	 * @param sql
	 *            SQL���
	 * @return ���bean��List
	 * @throws SQLException
	 */
	public <T> List<T> populateBeanList(Class<T> cls, RowProcessor convert, String sql, Object... params) throws SQLException {
		return executor.query(sql, new BeanListHandler<T>(cls, convert), params);
	}

	/**
	 * ִ��һ�θ����� INSERT��UPDATE or DELETE SQL ���
	 * @throws SQLException
	 */
	public int update(String sql, Object... params) throws SQLException {
		return executeUpdate(this.conn, sql, params);
	}

	/**
	 * ����Ԥ������ʽִ�ж�θ����� INSERT��UPDATE or DELETE SQL ��䣬ִ��SQL�Ĵ������ݲ�������ȷ����
	 * 
	 * @param sql
	 *            Ԥ����SQL���
	 * @param params
	 *            SQL ��������Ϊnull��
	 * @return int ���ظ��µ�������
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
	 * ����Ԥ����SQL�����ʽ����Ƿ��ѯ������
	 * 
	 * @param sql
	 *            Ԥ�����ѯSQL���
	 * @param params
	 *            SQL��������Ϊnull��
	 * @return boolean ��ѯ�Ľ�������������򷵻�true�����򷵻�false��
	 * @throws SQLException
	 */
	public boolean hasData(String sql, Object... params) throws SQLException {
		return executor.query(sql, new EmptyHandler(), params);
	}

	/**
	 * ����Ԥ����SQL����ѯ���ݿⲢ��ȡ�õĵ�һ�����ݴ�ŵ�String�����в����ء�
	 * 
	 * @param sql
	 *            Ԥ����SQL���
	 * @param params
	 *            Ԥ����SQL��������Ϊnull��
	 * @return �������һ����ɵ����飬��û�������򷵻�null��
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
	 * �ύ���ݿ����
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		conn.commit();
	}

	/**
	 * �ع����ݿ�����������쳣��
	 */
	public void rollback() {
		try {
			DbUtils.rollback(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر����ݿ����ӣ������쳣��
	 */
	public void close() {
		DbUtils.closeQuietly(conn);
	}
}
