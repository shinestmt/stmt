package com.commsoft.stat.client.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.commsoft.stat.client.util.Pagination;

/**
 * 数据库操作的封装<br>
 */
public class DBService
{
    /**
     * 查询结果集中最后一列的标志
     */
    private static final int END_COL = -1;

    /**
     * 数据库连接
     */
    private Connection conn;

    /**
     * 数据库分页信息
     */
    private Pagination pagination;

    /**
     * 根据数据库名构造一个自动提交事务的数据库操作封装类。
     * @throws SQLException
     */
    public DBService() throws SQLException {
        this.conn = ConnectionFactory.getConnection();
    }

    /**
     * 获得数据库分页信息，只有先调用了<code>getPageList(...)</code>方法之后，
     * 调用此方法才会返回一个分页信息实例，否则返回null。
     * @return 数据库分页信息 or null。
     */
    public Pagination getPagination() {
        return pagination;
    }

    public Object[] fetch(String sql, Object... params) throws SQLException {
        //TODO 
        return null;
    }

    public Object fetchBean(String sql, Handler handler, Object... params) throws SQLException {
        //TODO 
        return null;
    }

    public List fetchList(String sql, Object... params) throws SQLException {
        return fetchList(sql, ResultRange.DEFAULT, params);
    }

    public List fetchList(String sql, ResultRange range, Object... params) throws SQLException {
        //TODO 
        return null;
    }

    public List<T> fetchList(String sql, Handler handler, Object... params) throws SQLException {
        //TODO 
        return null;
    }

    //TODO fetchList(String sql, Pagination pagination)


    //============================================================================================================


    /**
     * 执行一条 INSERT SQL 语句获得其中自增字段的值。
     * @param sql INSERT SQL语句
     * @return 插入的自增字段的值
     */
    public long getGeneratedKey(String sql) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql, new int[]{1});
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式执行插入语句并获得自增键的值
     * @param sql 预编译 INSERT SQL 语句
     * @param params SQL参数，可为null。
     * @return 插入的自增字段的值
     * @throws SQLException
     */
    public long getGeneratedKey(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql, new int[]{1});
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }



    /**
     * 根据SQL语句查询的一条结果集填充Bean的属性
     * @param bean 要设置的JavaBean对象
     * @param fields JavaBean的字段
     * @param sql SQL语句
     * @return true，填充bean成功
     */
    public boolean populate(Object bean, String[] fields, String sql) throws SQLException {
        
    }

    /**
     * 根据SQL语句查询的结果集填充每个Bean的属性并存入List中
     * @param cls 要设置的JavaBean的Class
     * @param fields JavaBean的字段
     * @param sql SQL语句
     * @return 存放bean的List
     */
    public List populate(Class cls, String[] fields, String sql) throws SQLException {

    }


    //====================================================================================================


    /**
     * 执行一次给定的INSERT、UPDATE或DELETE的SQL语句
     * @param sql SQL语句
     * @return 受影响的行数
     */
    public int update(String sql) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * 执行一次给定的INSERT、UPDATE或DELETE的SQL语句组
     * @param sqls SQL语句组
     * @return 返回SQL语句组受影响的行数组
     */
    public int[] update(String[] sqls) throws SQLException {
        int[] updatedRows = new int[sqls.length];
        for (int i = 0; i < sqls.length; i++) {
            updatedRows[i] = update(sqls[i]);
        }
        return updatedRows;
    }

    /**
     * 采用预编译形式执行一次给定的INSERT、UPDATE或DELETE的SQL语句
     * @param sql 预编译SQL语句
     * @param params SQL参数，可为null。
     * @return 受影响的行数
     */
    public int update(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            return stmt.executeUpdate();
        } finally {
            closeStatement(stmt);
        }
    }
    
    /**
     * 采用预编译形式执行多次给定的INSERT、UPDATE或DELETE的SQL语句，执行SQL的次数根据参数长度确定。
     * @param sql 预编译SQL语句
     * @param params SQL 参数，可为null。
     * @return int 返回更新的总行数
     */
    public int updates(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            int rows = 0;
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(1, params[i]);
                    rows += stmt.executeUpdate();
                }
            }
            return rows;
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译形式执行多次给定的INSERT、UPDATE或DELETE的SQL语句，
     * 执行SQL的次数根据<code>paramList.size()</code>确定。
     * @param sql 预编译SQL语句
     * @param paramList List(Object[]) SQL 参数，可为null。
     * @return 返回更新的总行数
     * @throws SQLException
     */
    public int updates(String sql, List<Object[]> paramList) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            int rows = 0;
            if (paramList != null) {
                for (int i = 0, ii = paramList.size(); i < ii; i++) {
                    Object[] objs = paramList.get(i);
                    for (int j = 0; j < objs.length; j++) {
                        stmt.setObject(j + 1, objs[j]);
                    }
                    rows += stmt.executeUpdate();
                }
            }
            return rows;
        } finally {
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译形式执行一次给定的 INSERT、UPDATE或DELETE的SQL语句组，
     * SQL 语句组对应同一组参数。
     * @param sqls 预编译SQL语句组
     * @param params SQL 参数，可为null。
     * @return 返回SQL语句组更新的行数组
     */
    public int[] update(String[] sqls, Object[] params) throws SQLException {
        int[] updatedRows = new int[sqls.length];
        for (int i = 0; i < sqls.length; i++) {
            updatedRows[i] = update(sqls[i], params);
        }
        return updatedRows;
    }

    /**
     * 是否查询到数据
     * @param sql 查询SQL语句
     * @return boolean 查询的结果集中有数据则返回true，否则返回false。
     * @throws SQLException
     */
    public boolean hasData(String sql) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs.next();
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得是否查询到数据
     * @param sql 预编译查询SQL语句
     * @param params SQL参数，可为null。
     * @return boolean 查询的结果集中有数据则返回true，否则返回false。
     * @throws SQLException
     */
    public boolean hasData(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个整数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 整数
     * @throws SQLException
     */
    public int getInt(String sql, Object... params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个长整数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 长整数
     * @throws SQLException
     */
    public long getLong(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            rs.next();
            return rs.getLong(1);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个浮点数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 浮点数
     * @throws SQLException
     */
    public float getFloat(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            rs.next();
            return rs.getFloat(1);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个双精度浮点数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 双精度浮点数
     * @throws SQLException
     */
    public double getDouble(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            rs.next();
            return rs.getDouble(1);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个字符串
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 返回字符串，若没有查询到数据则返回null。
     * @throws SQLException
     */
    public String getString(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            String str = null;
            if (rs.next()) {
                str = rs.getString(1);
            }
            return str;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 关闭结果集（忽略异常）
     * @param rs 结果集
     */
    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭SQL语句对象（忽略异常）
     * @param stmt SQL语句对象
     */
    public void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置数据库是否自动提交
     * @param autoCommit true，自动提交。
     * @throws SQLException
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

    /**
     * 提交数据库更新
     * @throws SQLException
     */
    public void commit() throws SQLException {
        conn.commit();
    }

    /**
     * 回滚数据库操作（忽略异常）
     */
    public void rollback() {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接（忽略异常）
     */
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * SQL结果集数据区域
 */
final class ResultRange {
    public static final int END_COL = -1; //结束列标识
    public static final ResultRange DEFAULT = range(0, END_COL, 0, END_COL); //默认数据区域(全部)
    private int horizontalStart = -1; //横向起始
    private int horizontalEnd = -1;   //横向结束
    private int verticalStart = -1;   //纵向起始
    private int verticalEnd = -1;     //纵向结束

    private ResultRange(int horizontalStart, int horizontalEnd, int verticalStart, int verticalEnd){
        this.horizontalStart = horizontalStart;
        this.horizontalEnd = horizontalEnd;
        this.verticalStart = verticalStart;
        this.verticalEnd = verticalEnd;
    }

    public static ResultRange verticalStart(int start){
        return range(0, END_COL, start, END_COL);
    }

    public static ResultRange verticalEnd(int end){
        return range(0, END_COL, 0, end);
    }

    public static ResultRange vertical(int start, int end){
        return range(0, END_COL, start, end);
    }

    public static ResultRange horizontalStart(int start){
        return range(0, END_COL, horizontalStart, END_COL);
    }

    public static ResultRange horizontalEnd(int end){
        return range(0, END_COL, 0, end);
    }

    public static ResultRange horizontal(int start, int end){
        return range(start, end, 0, END_COL);
    }

    public static ResultRange range(int hStart, int hEnd, int vStart, int vEnd){
        return new ResultRange(hStart, hEnd, vStart, vEnd);
    }

    public int getHorizontalStart() {
        retyurn horizontalStart;
    }

    public int getHorizontalEnd() {
        retyurn horizontalEnd;
    }

    public int getVerticalStart() {
        retyurn verticalStart;
    }

    public int getVerticalEnd() {
        retyurn verticalEnd;
    }
}