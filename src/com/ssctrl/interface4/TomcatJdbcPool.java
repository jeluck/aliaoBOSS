package com.ssctrl.interface4;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class TomcatJdbcPool {

	// 连接池相关
	private static volatile Map<String, TomcatJdbcPool> pool = new HashMap<String, TomcatJdbcPool>();
	private DataSource ds;
	private PoolProperties poolProps;
	// 数据库配置相关
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "";


	private TomcatJdbcPool(String dbName) {
//		url = "jdbc:mysql://121.42.136.119:3306/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
		 url ="jdbc:mysql://"+JyGlobalConstant.getDbBaseAddr()+"/"+dbName+"?useUnicode=true&characterEncoding=utf-8";
		init();
	}

	private static boolean isNull(Connection conn) {
		try {
			if (conn == null || conn.isClosed()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static TomcatJdbcPool getInstance(String dbName) {
		if (pool.get(dbName) == null) {
			synchronized (TomcatJdbcPool.class) {
				if (pool.get(dbName) == null) {
					pool.put(dbName, new TomcatJdbcPool(dbName));
				}
			}
		}else {
			if(isNull(pool.get(dbName).getConnection())){
				pool.put(dbName, new TomcatJdbcPool(dbName));
			}
		}
		return pool.get(dbName);
	}

	private void init() {
		poolProps = new PoolProperties();
		poolProps.setUrl(url);
		poolProps.setDriverClassName(driver);
		poolProps.setUsername(JyGlobalConstant.getDbUser());
		poolProps.setPassword(JyGlobalConstant.getDbPwd());

		poolProps.setInitialSize(10); // 初始化10个连接
		poolProps.setMaxActive(300); // 最大200个连接
		poolProps.setMaxWait(10000); // 最长10秒钟等待
		poolProps.setRemoveAbandonedTimeout(20); // 60秒移除废弃的连接
		poolProps.setRemoveAbandoned(true); // 可以移除废弃的连接
		
		poolProps.setValidationQuery("SELECT 1");
		poolProps.setTestOnBorrow(true);
		poolProps.setTestOnConnect(true);
		poolProps.setTestWhileIdle(true);
		poolProps.setTestOnReturn(true);

		// 创建连接池, 使用了 tomcat 提供的的实现，它实现了 javax.sql.DataSource 接口
		ds = new DataSource();
		// 为连接池设置属性
		ds.setPoolProperties(poolProps);
	}

	public synchronized Connection getConnection() {

		Connection connect = null;
		try {
			connect = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connect;
	}
}
