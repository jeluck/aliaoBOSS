package com.ssctrl.interface4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class SqlUtil {
	TomcatJdbcPool sqlPool;
	Connection conn;

	public SqlUtil(String dbName) {
		sqlPool = TomcatJdbcPool.getInstance(dbName);
		conn = sqlPool.getConnection();
	}

	@Override
	public void finalize() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Map<String, Object>> get_list(String sql)
			throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = JyResultSetToListConverter.ResultSetToList(rs);
		return list;
	}

	public int get_int(String sql) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}else{
			return 0;
		}
	}
	public int get_countint(String sql) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
		    return rs.getInt(1);
		}else{
			return -1;
		}
	}
	public String get_string(String sql) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
		    return rs.getString(1);
		}else{
			return "";
		}
	}
	
	public double get_double(String sql) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
		return rs.getDouble(1);
		}else{
			return 0.0;
		}
	}

	public int sql_exec(String sql) throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(sql);
	}
	
	
	public ResultSet get_rs(String sql) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);

		return  pstmt.executeQuery();
	}


}
