package com.hanghang.codestore.util.db.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * ��֤������Ƿ�Ϊ��
 * @author fanhang
 * @param <T>
 */
public class StringArrayHandler implements ResultSetHandler<Object[]> {

	@Override
	public Object[] handle(ResultSet rs) throws SQLException {
		return new BasicRowProcessor().toArray(rs);
	}
	
}
