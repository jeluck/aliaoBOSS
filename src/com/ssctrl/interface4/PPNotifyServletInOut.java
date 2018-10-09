package com.ssctrl.interface4;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.ssctrl.interface4.JyGlobalConstant;
import com.ssctrl.interface4.JyLogDetect;
import com.ssctrl.interface4.JyLogDetect.DataType;
import com.ssctrl.interface4.SqlUtil;

@WebServlet("/paypolpay/notify")
public class PPNotifyServletInOut extends HttpServlet {
	
	JyLogDetect log=null;

	private static final String TOKEN_URL = "https://api.paypal.com/v1/oauth2/token";
	private static final String PAYMENT_DETAIL = "https://api.paypal.com/v1/payments/payment/";
	private static final String clientId = "AZtiGnm9LLypH21wRv5PCsRgkVFgaFwGr2UfeVREDqCt6zkWEN4UF6V1ZznacBiFzYbqBSIXoFl4J8XI";
	private static final String secret = "EMlv-tDEPyTqTIvvB-5Np6rj7jxtgvbwEnXKLBmUIKFYBwJK4CqURjVjHYArBxz0WTpJaD-q9HKMPAnn";
//	
//	private static final String clientId = "AZlg9dDW5go006SMUl46P6aLysLdVbqY43kNSb6luJ7RU5LLtANOpfmN-02jejXeL_q3BPosih_UK82V";
//	private static final String secret = "EBra-h1SQTDqUZxr0MwNGML_CPZnmdEBEK-j3aOSkviafCGU04chJPyyQYWvultLFMClK27br3_6X9eJ";
	
	
	//private static final String TOKEN_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
	//private static final String PAYMENT_DETAIL = "https://api.sandbox.paypal.com/v1/payments/payment/";
	//private static final String clientId = "AaiM42hsf6IDNWRt8XFZ5hRcvEPEUYQsnh0eVjoznJLM4Gz-aggaqIo4nmEbaLFAO0gJyjWtC2oPs5a8";
	//private static final String secret = "EBHMcpwv1lo1thS7QRd0lDefSPUxBpk2kTYJzrW2u8zbq9tUvoqen5Lf_PLYX0XvmW_yKqpWiUrZC39B";
	
	
	public PPNotifyServletInOut() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		log=new JyLogDetect();
		log.send("01115", "支付成功的回调：", "支付成功的回调");
		
		String paymentId= request.getParameter("paymentId").toString();
		String money= request.getParameter("value").toString();
		String userid= request.getParameter("userid").toString();
		log.send("01115", "paymentId：", paymentId+"+++"+money);
		try {
			if(verifyPayment(paymentId,userid,money)){
				String jsonadd = "{\"success\":\"OK\"}";
				PrintWriter out = response.getWriter();
				out.write(jsonadd);
			}else{
				String jsonadd = "{\"success\":\"NO\"}";
				PrintWriter out = response.getWriter();
				out.write(jsonadd);
			};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
     * 获取token
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @return
     */
    private String getAccessToken(){
        try{
            URL url = new URL(TOKEN_URL);
            String authorization = clientId+":"+secret;
            authorization = Base64.encodeBase64String(authorization.getBytes());
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Language", "en_US");
            conn.setRequestProperty("Authorization", "Basic "+authorization);
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数
            String params = "grant_type=client_credentials";
            conn.getOutputStream().write(params.getBytes());// 输入参数

            InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            String accessTokey = JSONObject.fromObject(result.toString()).optString("access_token");
            System.out.println("getAccessToken:"+accessTokey);
            log.send("01115", "getAccessToken：", accessTokey);
            return accessTokey;
        }catch(Exception err){
            err.printStackTrace();
            log.send("01115", "Exception：", err.toString());
        }
        return null;
    }
    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    public String getPaymentDetails(String paymentId){
        try{
            URL url = new URL(PAYMENT_DETAIL+paymentId);
            log.send("01115", "getPaymentDetails：", paymentId);
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");// 提交模式
            //设置请求头header
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+getAccessToken());
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            InputStreamReader inStream = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(inStream);
            StringBuilder result = new StringBuilder();
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                result.append(lineTxt);
            }
            reader.close();
            log.send("01115", "getPaymentDetails：", result.toString());
            return result.toString();
        }catch(Exception err){
            err.printStackTrace();
            log.send("01115", "Exception：", err.toString());
        }
        return null;
    }
    /**
     * 获取支付详情
     * 了解更多：https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
     * @param paymentId 支付ID，来自于用户客户端
     * @return
     */
    public boolean verifyPayment(String paymentId,String userid,String money) throws Exception {
        String str = getPaymentDetails(paymentId);
        System.out.println(str);
        log.send("01115", "str：", str);
        JSONObject detail = JSONObject.fromObject(str);
        //校验订单是否完成
        if("approved".equals(detail.optString("state"))){
            JSONObject transactions = detail.optJSONArray("transactions").optJSONObject(0);
            log.send("01115", "relatedResources：", transactions.toString());
            JSONObject amount = transactions.optJSONObject("amount");
            log.send("01115", "relatedResources：", amount.toString());
            JSONArray relatedResources = transactions.optJSONArray("related_resources");
            log.send("01115", "relatedResources：", relatedResources.toString());
            //从数据库查询支付总金额与Paypal校验支付总金额
            double total = Double.parseDouble(money);
            System.out.println("amount.optDouble('total'):"+amount.optDouble("total"));
            log.send("01115", "amount.optDouble('total')：", amount.opt("total"));
            
            double realmoney=amount.optDouble("total");
            
//            if( total != amount.optDouble("total") ){
//                return false;
//            }
            
            
            
            
            //校验交易货币类型
            String currency = "USD";
            if( !currency.equals(amount.optString("currency")) ){
                return false;
            }
            log.send("01115", "amount.optDouble('currency')：", amount.opt("currency"));
            
            SqlUtil sqlUtil = new SqlUtil(JyGlobalConstant.getDbBaseName());
            String sql="insert into order_management (user_id,pay_time,pay_type,pay_price,pay_status,pay_what) values ('"+userid+"',now(),'paypol','"+total+"','已付款','充值')";
			log.send("01158", "sql", sql);
			int c = sqlUtil.sql_exec(sql);
			sql="update user_data set money = money+"+realmoney+"*10 where id="+userid+" ";
			log.send("01158", "sql", sql);
			c = sqlUtil.sql_exec(sql);
			sql="insert into income_details (user_id,time,type,money,operation) values ('"+userid+"',now(),'Recharge',"+realmoney+"*10,'Has arrived')";
			sqlUtil.sql_exec(sql);
			ArrayList<Map<String, Object>> list=null;
			sql="select * from cash_set ";
			log.send("01158", "sql", sql);
			list=sqlUtil.get_list(sql);
			
			ArrayList<Map<String, Object>> list1=null;
			//一级分销
			sql="select promoter_id from user_data where id="+userid;
			log.send("01158", "sql", sql);
			list1=sqlUtil.get_list(sql);
			if(list1.size()==0){
				
			}else{
				String oneid=list1.get(0).get("promoter_id").toString();
				String isv="0";
				String sacleone=list.get(0).get("cash_onefee").toString();
				Double oneable_money=Double.parseDouble(sacleone)*realmoney;
				sql="insert into tuiguang_detail (upuser_id,downuser_id,is_dv,levle,money_type,money_num,scale_num,able_money,uptime) values ('"+oneid+"','"+userid+"','"+isv+"',1,'充值','"+realmoney+"','"+sacleone+"','"+oneable_money+"',now())";
				log.send("01158", "sql", sql);
				sqlUtil.sql_exec(sql);
				sql="update user_data set invite_price = invite_price+"+realmoney+",ableinvite_price=ableinvite_price+"+oneable_money+" where id="+oneid+" ";
				log.send("01158", "sql", sql);
				sqlUtil.sql_exec(sql);
				
				
				//二级分销
				sql="select promoter_id from user_data where id="+oneid;
				log.send("01158", "sql", sql);
				list1=sqlUtil.get_list(sql);
				if(list1.size()==0){
					
				}else{
					String twoid=list1.get(0).get("promoter_id").toString();
					String sacletwo=list.get(0).get("cash_twofee").toString();
					Double twoable_money=Double.parseDouble(sacletwo)*realmoney;
					sql="insert into tuiguang_detail (upuser_id,downuser_id,is_dv,levle,money_type,money_num,scale_num,able_money,uptime) values ('"+twoid+"','"+userid+"','"+isv+"',2,'充值','"+realmoney+"','"+sacletwo+"','"+twoable_money+"',now())";
					log.send("01158", "sql", sql);
					sqlUtil.sql_exec(sql);
					sql="update user_data set invite_price = invite_price+"+realmoney+",ableinvite_price=ableinvite_price+"+twoable_money+" where id="+twoid+" ";
					log.send("01158", "sql", sql);
					sqlUtil.sql_exec(sql);
					
				}
			}
			
			
            //校验每个子订单是否完成
            for (int i = 0,j = relatedResources.size(); i < j; i++) {
                JSONObject sale = relatedResources.optJSONObject(i).optJSONObject("sale");
                log.send("01115", "sale：", sale.toString());
                if(sale!=null){
                    if( !"completed".equals(sale.optString("state")) ){
                        System.out.println("子订单未完成,订单状态:"+sale.optString("state"));
                        log.send("01115", "子订单未完成,订单状态：", sale.opt("state"));
                    }else{
                    	log.send("01115", "子订单未完成,订单状态：", sale.opt("state"));
                    }
                }
            }
            return true;
        }
        return false;
    }

}
