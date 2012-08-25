package com.hanghang.codestore.util.db.processor;

import java.beans.PropertyDescriptor;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.StringUtils;

public class OracleProcessor extends BeanProcessor {

	private final Map<String, String> columnToPropertyOverrides;

	public OracleProcessor() {
		this(new HashMap<String, String>());
	}

	public OracleProcessor(Map<String, String> columnToPropertyOverrides) {
		super();
		if (columnToPropertyOverrides == null) {
			throw new IllegalArgumentException("columnToPropertyOverrides map cannot be null");
		}
		this.columnToPropertyOverrides = columnToPropertyOverrides;
	}

	@Override
	protected int[] mapColumnsToProperties(ResultSetMetaData rsmd,
			PropertyDescriptor[] props) throws SQLException {
		int cols = rsmd.getColumnCount();
		int[] columnToProperty = new int[cols + 1];
		Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);

		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnLabel(col);
			if (null == columnName || 0 == columnName.length()) {
				columnName = rsmd.getColumnName(col);
			}
			columnName = columnName.toLowerCase();
			if(StringUtils.indexOf(columnName, '_')!=-1){
				String[] array = columnName.split("_");
				StringBuilder sb = new StringBuilder(array[0]);
				for (int i = 1; i < array.length; i++) {
					sb.append(Character.toUpperCase(array[i].charAt(0)))
					.append(array[i].substring(1));
				}
				columnName = sb.toString();
			}

			String propertyName = columnToPropertyOverrides.get(columnName);
			if (propertyName == null) {
				propertyName = columnName;
			}
			for (int i = 0; i < props.length; i++) {
				if (propertyName.equalsIgnoreCase(props[i].getName())) {
					columnToProperty[col] = i;
					break;
				}
			}
		}
		return columnToProperty;
	}

}
