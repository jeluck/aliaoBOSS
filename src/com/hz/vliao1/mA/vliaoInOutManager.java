package com.hz.vliao1.mA;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hz.vliao1.JyInOut;

public class vliaoInOutManager extends JyInOut {
	
	public vliaoInOutManager(String[] arg, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, SQLException {
		super(arg,request,response);
	}
}
