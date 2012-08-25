package com.hanghang.codestore.util.db.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 验证结果集是否为空
 * @author fanhang
 */
public class EmptyHandler implements ResultSetHandler<Boolean> {
	
	@Override
	public Boolean handle(ResultSet rs) throws SQLException {
		return rs.next();
	}

}
