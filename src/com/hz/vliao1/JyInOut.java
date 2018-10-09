package com.hz.vliao1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssctrl.interface4.JyGlobalConstant;
import com.ssctrl.interface4.JyHelpManager;
import com.ssctrl.interface4.JyInOutUtil;
import com.ssctrl.interface4.JyLogDetect;
import com.ssctrl.interface4.SqlUtil;
import com.ssctrl.interface4.JyLogDetect.DataType;

public class JyInOut {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	protected ArrayList<Map<String, Object>> list;

	protected JyHelpManager hm = new JyHelpManager();

	protected int[] pages;
	protected String[] arg;
	protected String module;
	protected String dbName = JyGlobalConstant.getDbBaseName();

	protected int num = 0;

	public SqlUtil sqlUtil;
	public JyInOutUtil inOutUtil;
	protected JyLogDetect log;

	public JyInOut(String[] arg, HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException, ServletException {
		this.arg = arg;
		this.request = request;
		this.response = response;
		log=new JyLogDetect(request);
		log.send(DataType.specialType, "01066", "dbName", dbName);
		sqlUtil = new SqlUtil(dbName);

		inOutUtil = new JyInOutUtil(arg, request, response);
		log.send(DataType.specialType, "01066", "arg", arg);
	}

	public int[] getpages() {
		return pages;
	}

}
