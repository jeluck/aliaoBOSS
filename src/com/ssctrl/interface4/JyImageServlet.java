package com.ssctrl.interface4;


import java.io.File;
import java.io.IOException;



import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;



public class JyImageServlet extends HttpServlet{

	 /**
     * @see HttpServlet#HttpServlet()
     */
    public JyImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
		String fullPath = "img/goodsdes_images";
		String tmppath=request.getSession( ).getServletContext( ).getRealPath( "/" );
		File fullDir = new File(tmppath+fullPath);
		if (!fullDir.exists()) {
			fullDir.mkdirs();
		}
		SmartUpload smartUpload = new SmartUpload();
		long maxSize = 10 * 1024 * 1024;// 
		String allowFileExtList = "jar,exe,doc,docx,txt,html,xml,xls,pdf,jpg,png,PNG,gif,GIF,jpeg,JPEG,JPG,BMP,bmp";
		smartUpload.initialize(getServletConfig(), request, response);
		smartUpload.setMaxFileSize(maxSize);
		smartUpload.setAllowedFilesList(allowFileExtList);
		smartUpload.upload();
		PrintWriter out = response.getWriter(); 
		String fileName="";
		 String callback = request.getParameter("CKEditorFuncNum"); 
	
		
			com.jspsmart.upload.File smartFile = smartUpload.getFiles().getFile(0);
			if (!smartFile.isMissing()) {
				fileName = smartFile.getFileName();
				String type2 = fileName.substring(fileName.lastIndexOf("."));
				Random rnd = new Random();
				int r = rnd.nextInt(100);
				Date date2 = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyyMMddHHmmss");
				String strDate2 = formatter.format(date2);
				fileName = strDate2 + r + type2;
				response.setContentType("text/html;charset=gbk");// 
				smartFile.saveAs(tmppath +fullPath+"/" + fileName,
						com.jspsmart.upload.File.SAVEAS_PHYSICAL);
				
		
			}
			JyClusterSync.syncImages();
		 out.println("<script type=\"text/javascript\">");  
	        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback  
	                + ",'" +request.getContextPath()+"/"+fullPath + "/" +fileName + "','')");  
	        out.println("</script>");  
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}