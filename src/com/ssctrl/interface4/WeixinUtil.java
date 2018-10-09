package com.ssctrl.interface4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssctrl.interface4.JyLogDetect.DataType;

import net.sf.json.JSONObject;


public class WeixinUtil {
	
	private static long timestamp;
	private static String accessToken;
	private static String jsApiTicket;
	private static String randomString;
	private static String signature;
	/**
	 * 向指定URL发送GET方法的请求
	 * @param url
	 * 发送请求的URL
	 * @param param
	 * 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static JSONObject sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return JSONObject.fromObject(result);
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * @param url
	 * 发送请求的URL
	 * @param param
	 * 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static JSONObject sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return JSONObject.fromObject(result);
	}
	
	
	public static JSONObject getOpenId(String code,String appid,String appstrect){
		JSONObject access_token=sendGet("https://api.weixin.qq.com/sns/oauth2/access_token", "appid="+appid+"&secret="+appstrect+"&code="+code+"&grant_type=authorization_code");
		return access_token;
	}
	
	public static JSONObject getUserInfo(String Openid,String accessToken){
		//https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN 公众号获取信息
		//https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN 网页获取信息
		JSONObject jsonWebUserInfo=sendGet("https://api.weixin.qq.com/sns/userinfo","access_token="+accessToken+"&openid="+Openid+"&lang=zh_CN");
		return jsonWebUserInfo;
	}
	
	public static String getAppid(String sellerid,SqlUtil sqlUtil) throws SQLException {
		String ssql="select appID from merchant where shop_accountname='"+sellerid+"'";
		return sqlUtil.get_string(ssql);
	}
	
	public static String getAppSecret(String sellerid,SqlUtil sqlUtil) throws SQLException {
		
		String ssql="select appsecret from merchant where shop_accountname='"+sellerid+"'";
		return sqlUtil.get_string(ssql);
	}
	
	public static String getLogo(String sellerid,SqlUtil sqlUtil) throws SQLException {
		
		String ssql="select logo_img from merchant where shop_accountname='"+sellerid+"'";
		return sqlUtil.get_string(ssql);
	}
	
	public static String getShopName(String sellerid,SqlUtil sqlUtil) throws SQLException {
		String ssql="select shop_name from merchant where shop_accountname='"+sellerid+"'";
		return sqlUtil.get_string(ssql);
	}
	
	public static String getShopNote(String sellerid,SqlUtil sqlUtil) throws SQLException {
		
		String ssql="select note from merchant where shop_accountname='"+sellerid+"'";
		return sqlUtil.get_string(ssql);
	}
	
	@SuppressWarnings("deprecation")
	public static String useridCheck(HttpSession session,HttpServletRequest request, HttpServletResponse response,JyLogDetect log,String module,String dbName,String sellerid,SqlUtil sqlUtil,JyInOutUtil inOutUtil) throws ServletException, IOException, SQLException {
		String username = "";
        if (session.getAttribute("UserName") != null && !"".equals(session.getAttribute("UserName").toString())) {
			
			//if(session.getAttribute("Sellerid").toString().equals(sellerid)){
				username = session.getAttribute("UserName").toString();
				log.send("01107","useridCheck()-username: ", username);
				/*}else{
				//session.setAttribute("URL", GlobalConstant.getWebBaseAddr()+request.getRequestURI() + "?" + request.getQueryString());
				//log.send(DataType.specialType, "01072", "拼接的url",GlobalConstant.getWebBaseAddr()+request.getRequestURI() + "?" + request.getQueryString());
				String nowurl="";
				String duankou="8222";
				String  yuming="";
				log.send("01107","useridCheck()-username: ", request.getRequestURL().toString());
				if(request.getRequestURL().toString().contains("mingweishipin")){
					nowurl=request.getRequestURL()+"?" + request.getQueryString();
					if(request.getRequestURL().toString().contains("yunzhiyi")){
						yuming="chaochaoa";
					}else{
						yuming="jiaxun";
					}
				}else{
					yuming="chaochaoa";
					nowurl=GlobalConstant.getWebBaseAddr()+request.getRequestURI() + "?" + request.getQueryString();
					duankou=GlobalConstant.getWebPort();
				}
				log.send(DataType.specialType, "01072", "拼接的url",duankou);
				session.setAttribute("URL", nowurl);
				log.send(DataType.specialType, "01072", "拼接的url",nowurl);
				log.send(DataType.specialType, "01072", "拼接的url",session.getAttribute("URL").toString());
				String appid="";
				String dbname="";
				if (module.indexOf("微信支付") != -1) {
					String ssql="select appID from merchant where shop_accountname='"+sellerid+"'";
					appid=sqlUtil.get_string(ssql);
					dbname=dbName;
					inOutUtil.toWX("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
							//+ "&redirect_uri=http%3A%2F%2Fchaochaoa.mingweishipin.com%2Fuiface%2fmembers%3fmode1%3dA-users-search%26mode2%3dUserLoginCheck%26sellerid%3d"+arg[2]+"&response_type=code&scope=snsapi_userinfo&state="+help.order_create()+"&connect_redirect=1#wechat_redirect");
							+ "&redirect_uri=http%3A%2F%2F"+yuming+".mingweishipin.com%2Fuiface%2fmembers%3fmode1%3dA-users-search%26mode2%3dUserLoginCheck%26sellerid%3d"+sellerid+"%26daebname%3d"+dbname+"&response_type=code&scope=snsapi_userinfo&state="+duankou+"&connect_redirect=1#wechat_redirect");
				}else{
					appid="wx15034b1300905282";
					inOutUtil.toWX("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
							//+ "&redirect_uri=http%3A%2F%2Fchaochaoa.mingweishipin.com%2Fuiface%2fmembers%3fmode1%3dA-users-search%26mode2%3dUserLoginCheck%26sellerid%3d"+arg[2]+"&response_type=code&scope=snsapi_userinfo&state="+help.order_create()+"&connect_redirect=1#wechat_redirect");
							+ "&redirect_uri=http%3A%2F%2F"+yuming+".mingweishipin.com%2Fuiface%2fmembers%3fmode1%3dA-users-search%26mode2%3dUserLoginCheck%26sellerid%3d"+sellerid+"%26daebname%3d"+dbname+"&response_type=code&scope=snsapi_userinfo&state="+duankou+"&connect_redirect=1#wechat_redirect");
				}
				}*/
			
			
		} else {
			
			String nowurl=request.getRequestURL()+"?" + request.getQueryString();
			log.send("01107","useridCheck()-yuming: ", nowurl);
			String[] a=nowurl.split("\\.");
			String yuming=a[0].substring(7);
			log.send("01107","useridCheck()-yuming: ", yuming);
			String targurl="http://code1.mingweishipin.com/Wxcode?" + request.getQueryString();
			log.send("01107","useridCheck()-yuming: ", targurl);
			targurl=java.net.URLEncoder.encode(targurl);
			inOutUtil.toWX("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx15034b1300905282" /*+ GlobalConstant.getAppid()*/
					+ "&redirect_uri="+targurl+"&response_type=code&scope=snsapi_base&state="+yuming+"&connect_redirect=1#wechat_redirect");
			
			
			//log.send(DataType.specialType, "01115", "argOld参数", arg);
			//session.setAttribute("URL", request.getRequestURL() +":"+request.getServerPort()  + "?" + request.getQueryString());
			//session.setAttribute("URL", GlobalConstant.getWebBaseAddr()+request.getRequestURI() + "?" + request.getQueryString());
			//log.send(DataType.specialType, "01072", "拼接的url",GlobalConstant.getWebBaseAddr()+request.getRequestURI() + "?" + request.getQueryString());
			/*String nowurl="";
			String duankou="8222";
			log.send("01107","useridCheck()-username: ", request.getRequestURL().toString());
			String  yuming="";
			if(request.getRequestURL().toString().contains("mingweishipin")){
				nowurl=request.getRequestURL()+"?" + request.getQueryString();
				if(request.getRequestURL().toString().contains("yunzhiyi")){
					yuming="chaochaoa";
				}else{
					yuming="jiaxun";
				}
			}else{
				yuming="chaochaoa";
				nowurl=GlobalConstant.getWebBaseAddr()+request.getRequestURI() + "?" + request.getQueryString();
				duankou=GlobalConstant.getWebPort();
			}
			log.send(DataType.specialType, "01072", "拼接的url",duankou);
			session.setAttribute("URL", nowurl);
			log.send(DataType.specialType, "01072", "拼接的url",nowurl);
			log.send(DataType.specialType, "01072", "拼接的url",session.getAttribute("URL").toString());
			String appid="";
			String dbname="";
			if (module.indexOf("微信支付") != -1) {
				String ssql="select appID from merchant where shop_accountname='"+sellerid+"'";
				appid=sqlUtil.get_string(ssql);
				dbname=dbName;
				inOutUtil.toWX("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
						//+ "&redirect_uri=http%3A%2F%2Fchaochaoa.mingweishipin.com%2Fuiface%2fmembers%3fmode1%3dA-users-search%26mode2%3dUserLoginCheck%26sellerid%3d"+arg[2]+"&response_type=code&scope=snsapi_userinfo&state="+help.order_create()+"&connect_redirect=1#wechat_redirect");
						+ "&redirect_uri=http%3A%2F%2F"+yuming+".mingweishipin.com%2Fuiface%2fmembers%3fmode1%3dA-users-search%26mode2%3dUserLoginCheck%26sellerid%3d"+sellerid+"%26daebname%3d"+dbname+"&response_type=code&scope=snsapi_userinfo&state="+duankou+"&connect_redirect=1#wechat_redirect");
			}else{
				appid="wx15034b1300905282";
				inOutUtil.toWX("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
						//+ "&redirect_uri=http%3A%2F%2Fchaochaoa.mingweishipin.com%2Fuiface%2fmembers%3fmode1%3dA-users-search%26mode2%3dUserLoginCheck%26sellerid%3d"+arg[2]+"&response_type=code&scope=snsapi_userinfo&state="+help.order_create()+"&connect_redirect=1#wechat_redirect");
						+ "&redirect_uri=http%3A%2F%2F"+yuming+".mingweishipin.com%2Fuiface%2fmembers%3fmode1%3dA-users-search%26mode2%3dUserLoginCheck%26sellerid%3d"+sellerid+"%26daebname%3d"+dbname+"&response_type=code&scope=snsapi_userinfo&state="+duankou+"&connect_redirect=1#wechat_redirect");
			}*/
		}
		// log.send(DataType.basicType, "01115", "用户ID", userid);
		return username;
	}

	
	public static int getSubscribe(String sellerid, SqlUtil sqlUtil,String openid) throws SQLException {
		
		JSONObject jsonRet = sendGet("https://api.weixin.qq.com/cgi-bin/user/info","access_token="+accessToken+"&openid="+openid);
		int Subscribe = jsonRet.getInt("subscribe");
		return Subscribe;
	}
	public static String getAccessToken(String sellerid, SqlUtil sqlUtil) throws SQLException {
		
		JyLogDetect logDbg = new JyLogDetect();
		// 判断当前时间戳是否超时
		if(timestamp != 0) {
			if( new Date().getTime()-timestamp <= 7000) {
				return accessToken;
			}
		}
		
		String appid = JyGlobalConstant.getAppid();	//"wx321142214f00fed6";	//getAppid(sellerid,sqlUtil);
		logDbg.send(DataType.noType, "01107", "Appid: ", appid);
		String appsecret = JyGlobalConstant.getAppsecret();	//"fb63cd9b600e652d42c5c59d116f6196";	//getAppSecret(sellerid,sqlUtil);
		logDbg.send(DataType.noType, "01107", "AppSecret: ", appsecret);
		
		JSONObject jsonRet = sendGet("https://api.weixin.qq.com/cgi-bin/token","grant_type=client_credential&appid="+appid+"&secret="+appsecret);
		
		if(jsonRet.containsKey("access_token")) {
			timestamp = new Date().getTime()/1000;	// 生成时间戳-秒数
			accessToken = jsonRet.getString("access_token");
			
			JSONObject jsonRet2 = sendGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket","access_token="+accessToken+"&type=jsapi");
			if("ok".equals(jsonRet2.getString("errmsg"))) {
				jsApiTicket = jsonRet2.getString("ticket");
				
				//-------------------------------------
				//生成签名
				logDbg.send(DataType.noType, "01107", "时间戳: ", timestamp+","+new Date(timestamp));
				randomString = getRandomString();
				logDbg.send(DataType.noType, "01107", "获取随机字符: ", randomString);
				return accessToken;
			} else {
				logDbg.send(DataType.exceptionType, "01107", "获取jsapi_ticket失败: ", jsonRet2);
				return null;
			}
		} else {
			logDbg.send(DataType.exceptionType, "01107", "获取access_token失败: ", jsonRet);
			return null;
		}
	}
	
	public static String getJsApiTicket() {
		return jsApiTicket;
	}
	
	public static String getSign(String url) {
		
		JyLogDetect logDbg = new JyLogDetect();
		
		String args = "jsapi_ticket="+jsApiTicket+"&noncestr="+randomString+"&timestamp="+timestamp
				+ "&url="+url;
		logDbg.send(DataType.noType, "01107", "签名前字符串: ", args);
		signature = new SHA1().getDigestOfString(args.getBytes());
		logDbg.send(DataType.noType, "01107", "签名结果字符串: ", signature);
		return signature;
	}
	
	public static String getRandomStr() {
		
		return randomString;
	}
	
	public static long getTimeStamp() {
		
		return timestamp;
	}
	
	/**
	 * 获取随机字符串
	 * @return
	 */
	public final static String getRandomString() {
		byte[] buffer = String.valueOf( new Random().nextInt(10000)).getBytes();
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

class SHA1 {   
    private final int[] abcde = {   
            0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476, 0xc3d2e1f0   
        };   
    // 摘要数据存储数组   
    private int[] digestInt = new int[5];   
    // 计算过程中的临时数据存储数组   
    private int[] tmpData = new int[80];   
    // 计算sha-1摘要   
    private int process_input_bytes(byte[] bytedata) {   
        // 初试化常量   
        System.arraycopy(abcde, 0, digestInt, 0, abcde.length);   
        // 格式化输入字节数组，补10及长度数据   
        byte[] newbyte = byteArrayFormatData(bytedata);   
        // 获取数据摘要计算的数据单元个数   
        int MCount = newbyte.length / 64;   
        // 循环对每个数据单元进行摘要计算   
        for (int pos = 0; pos < MCount; pos++) {   
            // 将每个单元的数据转换成16个整型数据，并保存到tmpData的前16个数组元素中   
            for (int j = 0; j < 16; j++) {   
                tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4));   
            }   
            // 摘要计算函数   
            encrypt();   
        }   
        return 20;   
    }   
    // 格式化输入字节数组格式   
    private byte[] byteArrayFormatData(byte[] bytedata) {   
        // 补0数量   
        int zeros = 0;   
        // 补位后总位数   
        int size = 0;   
        // 原始数据长度   
        int n = bytedata.length;   
        // 模64后的剩余位数   
        int m = n % 64;   
        // 计算添加0的个数以及添加10后的总长度   
        if (m < 56) {   
            zeros = 55 - m;   
            size = n - m + 64;   
        } else if (m == 56) {   
            zeros = 63;   
            size = n + 8 + 64;   
        } else {   
            zeros = 63 - m + 56;   
            size = (n + 64) - m + 64;   
        }   
        // 补位后生成的新数组内容   
        byte[] newbyte = new byte[size];   
        // 复制数组的前面部分   
        System.arraycopy(bytedata, 0, newbyte, 0, n);   
        // 获得数组Append数据元素的位置   
        int l = n;   
        // 补1操作   
        newbyte[l++] = (byte) 0x80;   
        // 补0操作   
        for (int i = 0; i < zeros; i++) {   
            newbyte[l++] = (byte) 0x00;   
        }   
        // 计算数据长度，补数据长度位共8字节，长整型   
        long N = (long) n * 8;   
        byte h8 = (byte) (N & 0xFF);   
        byte h7 = (byte) ((N >> 8) & 0xFF);   
        byte h6 = (byte) ((N >> 16) & 0xFF);   
        byte h5 = (byte) ((N >> 24) & 0xFF);   
        byte h4 = (byte) ((N >> 32) & 0xFF);   
        byte h3 = (byte) ((N >> 40) & 0xFF);   
        byte h2 = (byte) ((N >> 48) & 0xFF);   
        byte h1 = (byte) (N >> 56);   
        newbyte[l++] = h1;   
        newbyte[l++] = h2;   
        newbyte[l++] = h3;   
        newbyte[l++] = h4;   
        newbyte[l++] = h5;   
        newbyte[l++] = h6;   
        newbyte[l++] = h7;   
        newbyte[l++] = h8;   
        return newbyte;   
    }   
    private int f1(int x, int y, int z) {   
        return (x & y) | (~x & z);   
    }   
    private int f2(int x, int y, int z) {   
        return x ^ y ^ z;   
    }   
    private int f3(int x, int y, int z) {   
        return (x & y) | (x & z) | (y & z);   
    }   
    private int f4(int x, int y) {   
        return (x << y) | x >>> (32 - y);   
    }   
    // 单元摘要计算函数   
    private void encrypt() {   
        for (int i = 16; i <= 79; i++) {   
            tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14] ^   
                    tmpData[i - 16], 1);   
        }   
        int[] tmpabcde = new int[5];   
        for (int i1 = 0; i1 < tmpabcde.length; i1++) {   
            tmpabcde[i1] = digestInt[i1];   
        }   
        for (int j = 0; j <= 19; j++) {   
            int tmp = f4(tmpabcde[0], 5) +   
                f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +   
                tmpData[j] + 0x5a827999;   
            tmpabcde[4] = tmpabcde[3];   
            tmpabcde[3] = tmpabcde[2];   
            tmpabcde[2] = f4(tmpabcde[1], 30);   
            tmpabcde[1] = tmpabcde[0];   
            tmpabcde[0] = tmp;   
        }   
        for (int k = 20; k <= 39; k++) {   
            int tmp = f4(tmpabcde[0], 5) +   
                f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +   
                tmpData[k] + 0x6ed9eba1;   
            tmpabcde[4] = tmpabcde[3];   
            tmpabcde[3] = tmpabcde[2];   
            tmpabcde[2] = f4(tmpabcde[1], 30);   
            tmpabcde[1] = tmpabcde[0];   
            tmpabcde[0] = tmp;   
        }   
        for (int l = 40; l <= 59; l++) {   
            int tmp = f4(tmpabcde[0], 5) +   
                f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +   
                tmpData[l] + 0x8f1bbcdc;   
            tmpabcde[4] = tmpabcde[3];   
            tmpabcde[3] = tmpabcde[2];   
            tmpabcde[2] = f4(tmpabcde[1], 30);   
            tmpabcde[1] = tmpabcde[0];   
            tmpabcde[0] = tmp;   
        }   
        for (int m = 60; m <= 79; m++) {   
            int tmp = f4(tmpabcde[0], 5) +   
                f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +   
                tmpData[m] + 0xca62c1d6;   
            tmpabcde[4] = tmpabcde[3];   
            tmpabcde[3] = tmpabcde[2];   
            tmpabcde[2] = f4(tmpabcde[1], 30);   
            tmpabcde[1] = tmpabcde[0];   
            tmpabcde[0] = tmp;   
        }   
        for (int i2 = 0; i2 < tmpabcde.length; i2++) {   
            digestInt[i2] = digestInt[i2] + tmpabcde[i2];   
        }   
        for (int n = 0; n < tmpData.length; n++) {   
            tmpData[n] = 0;   
        }   
    }   
    // 4字节数组转换为整数   
    private int byteArrayToInt(byte[] bytedata, int i) {   
        return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16) |   
        ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff);   
    }   
    // 整数转换为4字节数组   
    private void intToByteArray(int intValue, byte[] byteData, int i) {   
        byteData[i] = (byte) (intValue >>> 24);   
        byteData[i + 1] = (byte) (intValue >>> 16);   
        byteData[i + 2] = (byte) (intValue >>> 8);   
        byteData[i + 3] = (byte) intValue;   
    }   
    // 将字节转换为十六进制字符串   
    private static String byteToHexString(byte ib) {   
        char[] Digit = {   
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',   
                'D', 'E', 'F'   
            };   
        char[] ob = new char[2];   
        ob[0] = Digit[(ib >>> 4) & 0X0F];   
        ob[1] = Digit[ib & 0X0F];   
        String s = new String(ob);   
        return s;   
    }   
    // 将字节数组转换为十六进制字符串   
    private static String byteArrayToHexString(byte[] bytearray) {   
        String strDigest = "";   
        for (int i = 0; i < bytearray.length; i++) {   
            strDigest += byteToHexString(bytearray[i]);   
        }   
        return strDigest;   
    }   
    // 计算sha-1摘要，返回相应的字节数组   
    public byte[] getDigestOfBytes(byte[] byteData) {   
        process_input_bytes(byteData);   
        byte[] digest = new byte[20];   
        for (int i = 0; i < digestInt.length; i++) {   
            intToByteArray(digestInt[i], digest, i * 4);   
        }   
        return digest;   
    }   
    // 计算sha-1摘要，返回相应的十六进制字符串   
    public String getDigestOfString(byte[] byteData) {   
        return byteArrayToHexString(getDigestOfBytes(byteData));   
    }
}