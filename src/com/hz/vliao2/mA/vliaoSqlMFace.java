package com.hz.vliao2.mA;

import java.io.IOException;
import java.sql.SQLException;

public interface vliaoSqlMFace {

	public String addSqlface(int current,String[] arg) throws SQLException, IOException;

	public String modSqlface(int current,String[] arg) throws SQLException, IOException;

	public String deleteSqlface(String[] arg) throws SQLException;

	public String searchSqlface(int current,String[] arg) throws SQLException, IOException;
	
	
}
