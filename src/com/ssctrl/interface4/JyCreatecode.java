package com.ssctrl.interface4;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;



import javax.swing.ImageIcon;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class JyCreatecode {
	private static final int BLACK = 0xFF000000;  
	private static final int WHITE = 0xFFFFFFFF; 
	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
		public static BufferedImage createCodeStream(String text,HttpServletResponse response) throws Exception{  
	        
	        // response.setContentType("image/jpeg");  
	        // ServletOutputStream sos = response.getOutputStream();  
	     
	         int width = 200;    
	         int height = 200;    
	         //二维码的图片格式    
	         String format = "jpg";    
	         MultiFormatWriter multiFormatWriter = new MultiFormatWriter();  
	         Map hints = new HashMap();  
	         hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	 		 hints.put(EncodeHintType.MARGIN, 1);
	         //内容所使用编码    
	         hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");    
	         BitMatrix bitMatrix = multiFormatWriter.encode(text,  BarcodeFormat.QR_CODE, width, height, hints);    
	           
	          
	         //生成二维码    
	          
	         BufferedImage image=writeToStream(bitMatrix, format/*,sos*/);    
	        
	        // sos.close();  
	           
	         return image;   
	     }  
	 
	 public static BufferedImage writeToStream(BitMatrix matrix, String format/*, OutputStream stream*/)  
		       throws IOException {  
		     BufferedImage image = toBufferedImage(matrix);  
//		     if (!ImageIO.write(image, format, stream)) {  
//		       throw new IOException("Could not write an image of format " + format);  
//		     }
		     return image;  
		   } 
	 public static BufferedImage toBufferedImage(BitMatrix matrix) {  
	     int width = matrix.getWidth();  
	     int height = matrix.getHeight();  
	     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	     for (int x = 0; x < width; x++) {  
	       for (int y = 0; y < height; y++) {  
	         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
	       }  
	     }  
	     return image;  
	   }  
	 
	 public static BufferedImage toBufferedImage(Image image) {
	        if (image instanceof BufferedImage) {
	            return (BufferedImage)image;
	         }
	        // This code ensures that all the pixels in the image are loaded
	         image = new ImageIcon(image).getImage();
	      
	        // Determine if the image has transparent pixels; for this method's
	        // implementation, see e661 Determining If an Image Has Transparent Pixels
	        //boolean hasAlpha = hasAlpha(image);
	          
	        // Create a buffered image with a format that's compatible with the screen
	         BufferedImage bimage = null;
	         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        try {
	            // Determine the type of transparency of the new buffered image
	            int transparency = Transparency.OPAQUE;
	           /* if (hasAlpha) {
	                 transparency = Transparency.BITMASK;
	             }*/
	      
	            // Create the buffered image
	             GraphicsDevice gs = ge.getDefaultScreenDevice();
	             GraphicsConfiguration gc = gs.getDefaultConfiguration();
	             bimage = gc.createCompatibleImage(
	                 image.getWidth(null), image.getHeight(null), transparency);
	         } catch (HeadlessException e) {
	            // The system does not have a screen
	         }
	        if (bimage == null) {
	            // Create a buffered image using the default color model
	            int type = BufferedImage.TYPE_INT_RGB;
	            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
	            /*if (hasAlpha) {
	                 type = BufferedImage.TYPE_INT_ARGB;
	             }*/
	             bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
	         }
	        // Copy image to buffered image
	         Graphics g = bimage.createGraphics();
	        // Paint the image onto the buffered image
	         g.drawImage(image, 0, 0, null);
	         g.dispose();
	        return bimage;
	     }
	 
}
