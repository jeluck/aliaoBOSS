package com.hz.vliao1.mA;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

public interface vliaoInOutFace {
	public void addface() throws SQLException, ServletException, IOException;

	public void modface() throws SQLException, ServletException, IOException;

	public void deleteface() throws SQLException, ServletException, IOException;

	public void searchface() throws SQLException, ServletException, IOException;
	
	
	
	
}
