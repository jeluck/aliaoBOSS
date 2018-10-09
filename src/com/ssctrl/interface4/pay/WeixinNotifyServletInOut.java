package com.ssctrl.interface4.pay;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;








import com.ssctrl.interface4.JyGlobalConstant;
import com.ssctrl.interface4.JyLogDetect;
import com.ssctrl.interface4.SqlUtil;
import com.ssctrl.interface4.pay.util.RequestHandler;

@WebServlet("/wxpay/notify")
public class WeixinNotifyServletInOut extends HttpServlet {
	
	JyLogDetect log=null;

	
	public WeixinNotifyServletInOut() {
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
		String out_trade_no=null;
		String return_code =null;
		String attach =null;
		try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			String resultStr  = new String(outSteam.toByteArray(),"utf-8");
			log.send("01115", "", "支付成功的回调："+resultStr);
			//logger.info("支付成功的回调："+resultStr);
			Map<String, Object> resultMap = parseXmlToList(resultStr);
			String result_code = (String) resultMap.get("result_code");
			String is_subscribe = (String) resultMap.get("is_subscribe");
			String transaction_id = (String) resultMap.get("transaction_id");
			String sign = (String) resultMap.get("sign");
			String time_end = (String) resultMap.get("time_end");
			String bank_type = (String) resultMap.get("bank_type");
			out_trade_no = (String) resultMap.get("out_trade_no");
			return_code = (String) resultMap.get("return_code");
			//attach =(String) resultMap.get("attach");
			request.setAttribute("out_trade_no", out_trade_no);
			log.send("01115", "支付成功的回调：", ""+out_trade_no);
			//log.send("01115", "支付成功的回调：", ""+attach);
			log.send("01115", "支付成功的回调：", ""+return_code);
			//通知微信.异步确认成功.必写.不然微信会一直通知后台.八次之后就认为交易失败了.
			response.getWriter().write(RequestHandler.setXML("SUCCESS", ""));
		}  catch (Exception e) {
			//logger.error("微信回调接口出现错误：",e);
			try {
				response.getWriter().write(RequestHandler.setXML("FAIL", "error"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		log.send("01115", "支付成功的回调：", "处理订单"+return_code);
		if(return_code.equals("SUCCESS")){
			try {
				log.send("01115", "支付成功的业务逻辑：", "处理订单"+return_code);
				//String out_trade_no=request.getParameter("out_trade_no").toString();
				log.send("01115", "支付成功的回调out_trade_no：", out_trade_no);
				SqlUtil sqlUtil = new SqlUtil(JyGlobalConstant.getDbBaseName());
				String sql="select * from order_management where order_num='"+out_trade_no+"'";
				log.send("01115", "支付成功的回调out_trade_no：", sql);
				ArrayList<Map<String, Object>> list=null;
				list = sqlUtil.get_list(sql);
				log.send("01115", "支付成功的回调：", list);
				if(list.size()==1){
					if(list.get(0).get("pay_status").toString().equals("未付款")){
						String userid=list.get(0).get("user_id").toString();
						double realmoney=Double.parseDouble(list.get(0).get("pay_value").toString());
						sql="update order_management set pay_status='已付款' where order_num='"+out_trade_no+"'";
						log.send("01115", "支付成功的回调：", sql);
						sqlUtil.sql_exec(sql);
						sql="update user_data set money=money+"+list.get(0).get("pay_value").toString()+" where id='"+list.get(0).get("user_id").toString()+"'";
						log.send("01115", "支付成功的回调：", sql);
						sqlUtil.sql_exec(sql);
						
						sql="insert into income_details (user_id,time,type,money,operation) values ('"+userid+"',now(),'充值',"+realmoney+",'已到账')";
						sqlUtil.sql_exec(sql);
						ArrayList<Map<String, Object>> listcast=null;
						sql="select * from cash_set ";
						log.send("01158", "sql", sql);
						listcast=sqlUtil.get_list(sql);
						
						ArrayList<Map<String, Object>> list1=null;
						//一级分销
						sql="select promoter_id,up_agentid from user_data where id="+userid;
						log.send("01158", "sql", sql);
						list1=sqlUtil.get_list(sql);
						if(list1.size()==0){
							
						}else{
							String oneid=list1.get(0).get("promoter_id").toString();
							if(!oneid.equals("0")){
								String isv="0";
								String sacleone=listcast.get(0).get("cash_onefee").toString();
								Double oneable_money=Double.parseDouble(sacleone)*realmoney;
								sql="insert into tuiguang_detail (upuser_id,downuser_id,is_dv,levle,money_type,money_num,scale_num,able_money,uptime) values ('"+oneid+"','"+userid+"','"+isv+"',1,'充值','"+realmoney+"','"+sacleone+"','"+oneable_money+"',now())";
								log.send("01158", "sql", sql);
								sqlUtil.sql_exec(sql);
								sql="update user_data set invite_price = invite_price+"+realmoney+",ableinvite_price=ableinvite_price+"+oneable_money+" where id="+oneid+" ";
								log.send("01158", "sql", sql);
								sqlUtil.sql_exec(sql);
							}
							
							
							String up_agentid=list1.get(0).get("up_agentid").toString();
							if(!up_agentid.equals("0")){
								String agent_fee=listcast.get(0).get("agent_fee").toString();
								String isv="0";
								Double agentable_money=Double.parseDouble(agent_fee)*realmoney;
								sql="insert into tuiguang_detail (upuser_id,downuser_id,is_dv,levle,money_type,money_num,scale_num,able_money,uptime) values ('"+up_agentid+"','"+userid+"','"+isv+"',1,'充值','"+realmoney+"','"+agent_fee+"','"+agentable_money+"',now())";
								log.send("01158", "sql", sql);
								sqlUtil.sql_exec(sql);
								sql="update user_data set invite_price = invite_price+"+realmoney+",ableinvite_price=ableinvite_price+"+agentable_money+" where id="+up_agentid+" ";
								log.send("01158", "sql", sql);
								sqlUtil.sql_exec(sql);
							}
							
							
//							//二级分销
//							sql="select promoter_id from user_data where id="+oneid;
//							log.send("01158", "sql", sql);
//							list1=sqlUtil.get_list(sql);
//							if(list1.size()==0){
//								
//							}else{
//								String twoid=list1.get(0).get("promoter_id").toString();
//								String sacletwo=list.get(0).get("cash_twofee").toString();
//								Double twoable_money=Double.parseDouble(sacletwo)*realmoney;
//								sql="insert into tuiguang_detail (upuser_id,downuser_id,is_dv,levle,money_type,money_num,scale_num,able_money,uptime) values ('"+twoid+"','"+userid+"','"+isv+"',2,'充值','"+realmoney+"','"+sacletwo+"','"+twoable_money+"',now())";
//								log.send("01158", "sql", sql);
//								sqlUtil.sql_exec(sql);
//								sql="update user_data set invite_price = invite_price+"+realmoney+",ableinvite_price=ableinvite_price+"+twoable_money+" where id="+twoid+" ";
//								log.send("01158", "sql", sql);
//								sqlUtil.sql_exec(sql);
//								
//							}
						}
						
						
						
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			log.send("01115", "支付失败的业务逻辑：", "处理订单"+return_code);
			//支付失败的业务逻辑
		}
	}
	/*public  void sendAndroidPush(String sellerid,String orderid)  {
		log.send("01115", "支付成功的回调：", sellerid);
		final String appKey ="05aa32a7f711e77ad2e1b80e";
		final String masterSecret = "0623578f51c9b2fa1f4b207d";
		ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
        config.setPushHostName("https://api.jpush.cn");
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, config);
        //JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		// 解析消息类型
        String msgType = "msg_type_img";
        String message = "新订单："+orderid;
        log.send("01115", "支付成功的回调：", message);
        //com.tencent.xinge.Message message1 = new com.tencent.xinge.Message();
        PushPayload payload = null;
        switch(msgType) {
        case "msg_type_img":
        	//message1.setType(com.tencent.xinge.Message.TYPE_MESSAGE);
        	payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    //.setAudience(Audience.tag("seller"))
                    .setAudience(Audience.tag(sellerid))
                    .setNotification(Notification.newBuilder()
                    		.setAlert(message)
                    		.addPlatformNotification(AndroidNotification.newBuilder()
                    				.setTitle("订单消息").build())
                            .addPlatformNotification(IosNotification.newBuilder()
      	                          .setBadge(1)
      	                          .setSound("happy")
      	                          .addExtra("from", "JPush")
      	                          .build())
      	                      .build())
                    .setMessage(cn.jpush.api.push.model.Message.content(message))
                    .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                    .build();
        	break;
        }
     // 开始极光推送
		try {
        	if(payload != null) {
        		PushResult result = jpushClient.sendPush(payload);
        		log.send("01115", "支付成功的回调：", "message: "+message+", result: "+result);
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}*/
	/**
	 * description: 解析微信通知xml
	 * 
	 * @param xml
	 * @return
	 * @author ex_yangxiaoyi
	 * @see
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private static Map parseXmlToList(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

}
