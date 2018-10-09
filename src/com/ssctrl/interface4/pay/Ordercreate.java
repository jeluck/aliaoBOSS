package com.ssctrl.interface4.pay;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

















import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.ssctrl.interface4.JyGlobalConstant;
import com.ssctrl.interface4.JyLogDetect;
import com.ssctrl.interface4.SqlUtil;
import com.ssctrl.interface4.pay.util.RequestHandler;


public class Ordercreate {
	
	
	
	public static String order_creat(String money,String out_trade_no,HttpServletRequest request,HttpServletResponse response) {
		JyLogDetect log=new JyLogDetect();
		log.send("01115", "PayServletInOut=", "PayServletInOut");
		/*------1.获取参数信息------- */
		//商户订单号
		
		//HttpSession session = request.getSession();
		
		String GZHID="wx4057609dfe8a090f";
		String GZHSecret="735ec3df449701bbcbf146bead9c6cea";
		String SHHID= "1503465011";
    	String SHHKEY= "AXpULvRane7eeY34pjoC56taA82LXTpV";
    	String notify_url="http://xinliao.mingweishipin.cn/wxpay/notify";
    	
    	
    	//String money= "";
    	//String out_trade_no="";
		
		//金额转化为分为单位
		String finalmoney = getMoney(money);

		/*------3.生成预支付订单需要的的package数据------- */
		//随机数 
		String nonce_str= MD5.getMessageDigest(String.valueOf(new Random().nextInt(10000)).getBytes());
		//订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		
		
		
		//交易类型 ：jsapi代表微信公众号支付
		String trade_type = "APP";
		//这里notify_url是 微信处理完支付后的回调的应用系统接口url。
		
//		if(isgroup.equals("1")){
//			notify_url ="http://rest.mingweishipin.com/uiface/weixintuanNotify";
//		}else if(isgroup.equals("2")||isgroup.equals("3")){
//			notify_url ="http://rest.mingweishipin.com/uiface/weixintuanBNotify";
//		}else{
//			notify_url ="http://rest.mingweishipin.com/uiface/weixinNotify";
//		}
		log.send("01115", "notify_url=", notify_url);
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid",  GZHID);  
		packageParams.put("mch_id",  SHHID);  
		packageParams.put("nonce_str", nonce_str);  
		packageParams.put("body", "充值心币"); 
		//packageParams.put("attach", sellerid+","+dbname);  
		packageParams.put("out_trade_no", out_trade_no);  
		packageParams.put("total_fee", finalmoney);  
		packageParams.put("spbill_create_ip", spbill_create_ip);  
		packageParams.put("notify_url", notify_url);  
		packageParams.put("trade_type", trade_type); 
		//packageParams.put("openid", openid); 
		log.send("01115", "packageParams", packageParams);
		/*------4.根据package数据生成预支付订单号的签名sign------- */
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init( GZHID,  GZHSecret,  SHHKEY);
		String sign = reqHandler.createSign(packageParams);
		log.send("01115", "sign", sign);
		/*------5.生成需要提交给统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder 的xml数据-------*/
		String xml="<xml>"+
				"<appid><![CDATA["+GZHID+"]]></appid>"+
				//"<attach><![CDATA["+sellerid+","+dbname+"]]></attach>"+ 
				"<body><![CDATA["+"充值心币"+"]]></body>"+ 
				"<mch_id><![CDATA["+ SHHID+"]]></mch_id>"+
				"<nonce_str><![CDATA["+nonce_str+"]]></nonce_str>"+
				"<notify_url><![CDATA["+notify_url+"]]></notify_url>"+
				//"<openid><![CDATA["+openid+"]]></openid>"+
				"<out_trade_no><![CDATA["+out_trade_no+"]]></out_trade_no>"+
				"<spbill_create_ip><![CDATA["+spbill_create_ip+"]]></spbill_create_ip>"+
				"<sign><![CDATA["+sign+"]]></sign>"+
				"<total_fee><![CDATA["+finalmoney+"]]></total_fee>"+
				"<trade_type><![CDATA["+trade_type+"]]></trade_type>"+
				"</xml>";
		
		
//		<xml>
//		   <appid>wx2421b1c4370ec43b</appid>
//		   <attach>支付测试</attach>
//		   <body>APP支付测试</body>
//		   <mch_id>10000100</mch_id>
//		   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
//		   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>
//		   <out_trade_no>1415659990</out_trade_no>
//		   <spbill_create_ip>14.23.150.211</spbill_create_ip>
//		   <total_fee>1</total_fee>
//		   <trade_type>APP</trade_type>
//		   <sign>0CB01533B8C1EF103065174F50BCA001</sign>
//		</xml>
//		
		
		log.send("01115", "xml", xml);
		/*------6.调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder 生产预支付订单----------*/
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id="";
		try {
			prepay_id = GetWxOrderno.getPayNo(createOrderURL, xml);
//			if(prepay_id.equals("")){
//				mv.addObject("ErrorMsg", "支付错误");
//				mv.setViewName("error");
//				return mv;
//			}
		} catch (Exception e) {
//			logger.error("统一支付接口获取预支付订单出错", e);
//			mv.setViewName("error");
//			return mv;
			log.send("01115", "prepay_id", e.toString());
		}
		log.send("01115", "prepay_id", prepay_id);
		/*将prepay_id存到库中*/
//		PageData p = new PageData();
//		p.put("shopId", out_trade_no);
//		p.put("prePayId", prepay_id);
//		activityService.updatePrePayId(p);

		
		/*------7.将预支付订单的id和其他信息生成签名并一起返回到jsp页面 ------- */
		nonce_str= MD5.getMessageDigest(String.valueOf(new Random().nextInt(10000)).getBytes());
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		//String packages = "prepay_id="+prepay_id;
		finalpackage.put("appid",  GZHID);  
		finalpackage.put("noncestr", nonce_str); 
		finalpackage.put("package", "Sign=WXPay");  
		finalpackage.put("partnerid",  SHHID); 
		finalpackage.put("prepayid", prepay_id);  
		finalpackage.put("timestamp", timestamp);  
		String finalsign = reqHandler.createSign(finalpackage);
		//finalpackage.put("sign", finalsign);  
		JSONObject jsonObj0 = new JSONObject();
		jsonObj0.put("appId",  GZHID);  
		jsonObj0.put("partnerid",  SHHID); 
		jsonObj0.put("prepayid", prepay_id);  
		jsonObj0.put("timeStamp", timestamp);  
		jsonObj0.put("nonceStr", nonce_str);  
		jsonObj0.put("package", "Sign=WXPay"); 
		jsonObj0.put("sign", finalsign);  
		return jsonObj0.toString();
	}
	
	/**
	 * MD5加密
	 */
	public static class MD5 {

	    private MD5() {}

	    /**
	     * 对传入的数据提取摘要
	     * @param buffer
	     * @return
	     */
	    public final static String getMessageDigest(byte[] buffer) {
	        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	        try {
	            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	            mdTemp.update(buffer);
	            byte[] md = mdTemp.digest();
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) {
	            return null;
	        }
	    }
	}
	
	  /**
     * 元转换成分
     * @param money
     * @return
     */
    public static String getMoney(String amount) {
        if(amount==null){
            return "";
        }
        // 金额转化为分为单位
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
    }
}
