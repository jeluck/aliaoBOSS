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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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


@WebServlet("/uiface/UploadFileServlet_renzheng")
public class UploadFileServlet_renzheng extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;
	protected ArrayList<Map<String, Object>> list1;
	private String dizhi = "http://120.27.98.128:9115/img/imgheadpic/";
	private String istongguo = "未审核";
	private String tupiandizhi = "";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFileServlet_renzheng() {
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
		JyLogDetect log = new JyLogDetect(request);
		log.send("01168更换头像", "进入interface4:", log);
		PrintWriter out = response.getWriter();
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
															// for disk-based
															// file items
		
		log.send("01168相册", "a-01:", factory);
		this.upload = new ServletFileUpload(factory);// Create a new file upload
														// handler
		
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
												// constraint 4194304

		String fullPath = "img/imgheadpic";
		String tmppath = request.getSession().getServletContext()
				.getRealPath("/");
		filedir = tmppath + fullPath;
		log.send("01168相册", "a-02:", filedir);
		File fullDir = new File(tmppath + fullPath);
		log.send("01168相册", "a-03:", fullDir);
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
						log.send("01168相册", "a-04:", fileName);
						String type2 = fileName.substring(fileName
								.lastIndexOf("."));
						log.send("01168相册", "a-05:", type2);
						Random rnd = new Random();
						int r = rnd.nextInt(100);
						Date date2 = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						String strDate2 = formatter.format(date2);
						fileName = strDate2 + r + type2;
						log.send("01168相册", "a-06:", fileName);
						String filepath = filedir + File.separator + fileName;
						log.send("01168相册", "a-07:", filepath);
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
			//tianjia 放置添加或修改的sql
			String tianjia = "";
			log.send("01150", "arg图片名称:", fileName);
			log.send("01150", "arg用户id:", user_id);
			SqlUtil sqlUtil = new SqlUtil(JyGlobalConstant.getDbBaseName());
			String img="";
			//根据fileName名称,执行sql语句
			//String sb = "bbbdsajjds";
			//sb.substring(2, 4);
			//获取第一个数
			String shu = user_id.substring(0, 1);
			user_id = user_id.substring(1);
			fileName = dizhi+fileName;
			log.send("01168", "最新的fileName", fileName);
			if(shu.equals("1")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='1'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','1')";
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id= "+user_id+" and numvalue='1'";
				}
				
			}else if(shu.equals("2")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='2'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','2')";
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id= "+user_id+" and numvalue='2'";
				}
			}else if(shu.equals("3")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='3'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','3')";
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id= "+user_id+" and numvalue='3'";
				}
			}else if(shu.equals("4")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='4'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','4')";
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id= "+user_id+" and numvalue='4'";
				}
			}else if(shu.equals("5")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='5'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','5')";
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id= "+user_id+" and numvalue='5'";
				}
			}else if(shu.equals("6")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='6'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','6')";
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id='"+user_id+"' and numvalue='6'";
				}
			}else if(shu.equals("7")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='7'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','7')";
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id= "+user_id+" and numvalue='7'";
				}
			}else if(shu.equals("8")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='8'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','8')";
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id= "+user_id+" and numvalue='8'";
				}
			}else if(shu.equals("9")){
				//查询列表里面是否有该位置图片
				String p_audit = "select count(user_id) as num from photo_album_audit where user_id="+user_id+" and numvalue='9'";                                    
				log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
				list1 = sqlUtil.get_list(p_audit);
				//获取头像审核表中是否有该用户的数据，没有为0，否则
				String geshu = list1.get(0).get("num").toString();
				if(geshu.equals("0")){
					tianjia = "insert into photo_album_audit (user_id,photo,isaudit,numvalue) values ('"+user_id+"','"+fileName+"','未审核','9')";
					
				}else{
					tianjia = "update photo_album_audit set photo ='"+fileName+"',isaudit='未审核', where user_id= "+user_id+" and numvalue='9'";
				}
				
			}
			log.send("01168", "添加或修改的图片信息", tianjia);
			sqlUtil.sql_exec(tianjia);
			/*log.send("01168更换头像", "进入服务端interface4:", "执行查询");
			String p_audit = "select count(user_id) as shu from photo_audit where user_id="+user_id+"and numvalue=1";
			log.send("01168", "查询头像审核表中是否有该用户的数据", p_audit);
			//sqlUtil.get_list(ressql);
			list1 = sqlUtil.get_list(sql2);
			//获取头像审核表中是否有该用户的数据，没有为0，否则
			String geshu = list1.get(0).get("shu").toString();
			tupiandizhi = dizhi+fileName;
			String tianjia = "";
			if(geshu.equals("0")){
				tianjia = "insert into photo_audit (user_id,photo,audit) values ('"+user_id+"','"+tupiandizhi+"','"+istongguo+"')";
			}else{
				tianjia = "update photo_audit set photo ='"+tupiandizhi+"',audit='"+istongguo+"' where id= "+user_id;
			}
			
			//String ressql = "update user_data set photo = '" + fileName+"' where id = "+user_id;
			log.send(DataType.basicType, "01150", "修改或添加头像审核表中字段:", tianjia);
			sqlUtil.sql_exec(tianjia);*/
			img = fileName;
			
			JSONObject jsonObj0 = new JSONObject();
			jsonObj0.put("imgurl", img);
			//out.write("[\"imgurl\","+fileName+"]");
			out.write(jsonObj0.toString());
			log.send("01150", "arg:", jsonObj0);
			JyClusterSync.syncImages();
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