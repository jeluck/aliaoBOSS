package com.ssctrl.interface4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ssctrl.interface4.JyLogDetect.DataType;

/**
 * 文件上传的Serlvet�?
 * 
 * Servlet implementation class FileImageUploadServlet
 * 
 * 此处的文件上传比较简单没有处理各种验证，文件处理的错误等�? 如果�?要处理，请修改源代码即可�?
 * 
 * @Title:
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2011
 * @Company:易程科技股份有限公司
 * @Date:2012-7-22
 * @author longgangbai
 * @version 1.0
 */


@WebServlet("/uiface/UploadFileServlet")
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;
	private String dizhi = "http://120.27.98.128:9118/img/imgheadpic/";
    JyLogDetect log;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log=new JyLogDetect(request);
		PrintWriter out = response.getWriter();
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
															// for disk-based
															// file items
		this.upload = new ServletFileUpload(factory);// Create a new file upload
														// handler
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
												// constraint 4194304

		String fullPath = "img/imgheadpic";
		String tmppath = request.getSession().getServletContext()
				.getRealPath("/");
		filedir = tmppath + fullPath;
		File fullDir = new File(tmppath + fullPath);
		if (!fullDir.exists()) {
			fullDir.mkdirs();
		}
		String fileName = "";
		try {
			String user_id="";
			List<FileItem> items = this.upload.parseRequest(request);
			if (items != null && !items.isEmpty()) {
				for (FileItem fileItem : items) {

					if (fileItem.isFormField()) {
						user_id=fileItem.getString("UTF-8");
					} else {

						fileName = fileItem.getName();

						String type2 = fileName.substring(fileName
								.lastIndexOf("."));
						Random rnd = new Random();
						int r = rnd.nextInt(100);
						Date date2 = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						String strDate2 = formatter.format(date2);
						fileName = strDate2 + r + type2;
						
						log.send("01152++++", "filepath图片", fileName);
						String filepath = filedir + File.separator + fileName;
						File file = new File(filepath);
						InputStream inputSteam = fileItem.getInputStream();
						BufferedInputStream fis = new BufferedInputStream(
								inputSteam);
						FileOutputStream fos = new FileOutputStream(file);
						int f;
						while ((f = fis.read()) != -1) {
							fos.write(f);
						}
						fos.flush();
						fos.close();
						fis.close();
						inputSteam.close();
					}
				}
			}
			JSONObject jsonObj0 = new JSONObject();
			jsonObj0.put("imgurl", fileName);
			// out.write("[\"imgurl\","+fileName+"]");
			out.write(jsonObj0.toString());
			log.send(DataType.basicType, "01150", "fileName:", fileName);
			String tupiandizhi = dizhi + fileName;
			log.send(DataType.basicType, "01152", "图片地址:", tupiandizhi);
			SqlUtil sqlUtil = new SqlUtil(JyGlobalConstant.getDbBaseName());
			String sqlUpdate = "update user_data set photo='"+tupiandizhi+"'"
					+ " where id=" + user_id;
			log.send(DataType.basicType, "01152", "sql:", sqlUpdate);
			sqlUtil.sql_exec(sqlUpdate);
		} catch (FileUploadException e) {
			e.printStackTrace();
			JSONObject jsonObj0 = new JSONObject();
			jsonObj0.put("imgurl", "0");
			out.write(jsonObj0.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}