package com.hanghang.codestore.util.db.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * ��֤������Ƿ�Ϊ��
 * @author fanhang
 */
public class EmptyHandler implements ResultSetHandler<Boolean> {
	
	@Override
	public Boolean handle(ResultSet rs) throws SQLException {
		return rs.next();
	}

}
