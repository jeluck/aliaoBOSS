package com.ssctrl.interface4;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//72
@WebServlet("/uiface/JyFileUploadServlet")
public class JyFileUploadServlet extends HttpServlet {

	// 上传路径
	private File uploadPath;

	// 当文件过大时，需要设置一个临时路径
	private File tempPath;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		doPost(req,resp);
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		System.out.println("图片上传");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 内存存储的最大值
		factory.setSizeThreshold(4096);
		factory.setRepository(tempPath);

		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置文件上传大小
		upload.setSizeMax(1024 * 1024 * 4);
		try {
			String fileName = null;
			
			List<FileItem> fileItems = upload.parseRequest(req);
			FileItem item;
			FileItem itemImage = null;
			for (Iterator<FileItem> iter = fileItems.iterator(); iter.hasNext();) {
				item = (FileItem) iter.next();

				// 是否为input="type"输入域
				if (!item.isFormField()) {

					fileName = item.getName();

					long size = item.getSize();
					if ((fileName == null || fileName.equals("")) && size == 0) {
						continue;
					}

					String[] allowedExt = { ".gif", ".png", ".jpeg", ".jpg",".GIF", ".PNG", ".JPEG", ".JPG",".BMP",".bmp" }; // 允许上传的文件格式

					List<String> list = Arrays.asList(allowedExt);

					if (!list.contains(fileName.substring(fileName.lastIndexOf(".")))) {
						res.getWriter().write("error|文件类型错误");
						return;
					}


					fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					itemImage = item;
				}
			}
			uploadPath = new File(getServletContext().getRealPath("/") + "imageUpload");
			// System.out.println("uploadPath=====" + uploadPath);
			// 如果目录不存在
			if (!uploadPath.exists()) {
				// 创建目录
				uploadPath.mkdir();
			}

			File sellerPath = new File(uploadPath.getPath());
			
			File imageFile = new File(sellerPath, "IMG" + fileName + ".png");
			itemImage.write(imageFile);
			// 重定向页面
			JyClusterSync.syncImages();
			res.getWriter().write(req.getContextPath() + "/imageUpload/IMG" + fileName + ".png");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化方法，设定目录
	 *//*
	public void init() throws ServletException {
		uploadPath = new File(getServletContext().getRealPath("/") + "imageUpload");
		// System.out.println("uploadPath=====" + uploadPath);
		// 如果目录不存在
		if (!uploadPath.exists()) {
			// 创建目录
			uploadPath.mkdir();
		}

		// 临时目录
		tempPath = new File(getServletContext().getRealPath("/") + "imageTemp");
		if (!tempPath.exists()) {
			tempPath.mkdir();
		}
	}*/
}