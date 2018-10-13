package com.hz.vliao1.mA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssctrl.interface4.ExcelUtils;
import org.apache.commons.httpclient.HttpConnection;

import javax.servlet.ServletException;
import javax.swing.text.Position.Bias;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.hz.vliao2.mA.vliaoSqlBoss_01150;
import com.hz.vliao2.mA.vliaoSqlMFace;
import com.ssctrl.interface4.JsonUtil;
import com.ssctrl.interface4.JyHelpManager;
import com.ssctrl.interface4.JyLogDetect.DataType;
import com.ssctrl.interface4.OkHttp;
import com.ssctrl.interface4.zhufubao.Ordercreat;
import org.apache.commons.lang.StringUtils;

public class vliaoInoutBoss_01150 extends vliaoInOutManager implements
		vliaoInOutFace {
	protected ArrayList<Map<String, Object>> list;
	protected ArrayList<Map<String, Object>> list1;
	protected ArrayList<Map<String, Object>> list2;
	protected String json = "";
	vliaoSqlMFace sqlmface = new vliaoSqlBoss_01150();
	protected Main main;
	// OkHttp okhttp=new OkHttp();
	OkHttp okhttp = new OkHttp();

	private ExcelUtils excelUtils = new ExcelUtils();

	public vliaoInoutBoss_01150(String[] arg, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SQLException {
		super(arg, request, response);

	}

	@Override
	public void addface() throws SQLException, ServletException, IOException {
		// TODO Auto-generated method stub
		switch (arg[1]) {
		case "bossand":
			bossand(arg);
			break;
		case "photo_add":
			photo_add(arg);
			break;
		case "photo2":
			photo2(arg);
			break;
		case "notification_add":
			notification_add(arg);
			break;

		case "automsg_add":
			automsg_add(arg);
			break;

		case "admin_add":
			admin_add(arg);
			break;
		case "gift_add":
			gift_add(arg);
			break;

		}
	}

	private void gift_add(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.addSqlface(0, arg);
		int a = sqlUtil.sql_exec(sql);
		if (a != 0) {
			inOutUtil.return_ajax("添加成功");
		}

	}

	private void admin_add(String[] arg) throws SQLException, IOException,
			ServletException {

		String sql = sqlmface.addSqlface(0, arg);
		int a = sqlUtil.sql_exec(sql);
		if (a == 1) {
			inOutUtil.return_ajax("添加成功");
		}
	}

	private void automsg_add(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql = "insert into automsg (msg) value ('"+arg[2]+"')";
		log.send(DataType.basicType, "01162", "轮播图修改1", sql);
		sqlUtil.sql_exec(sql);
	}

	private void czAmount(String[] arg) throws SQLException, IOException,
			ServletException {
		int i=(int)(Math.random()*900)+100;
		String s = getDate() + i;
		String money = sqlUtil.get_string("select (" + arg[3] + "-money) mo from user_data where id=" + arg[2]);
		String sql="update user_data set money='"+arg[3]+"' where id="+arg[2];
		log.send(DataType.basicType, "01162", "充值",  sql);
		sqlUtil.sql_exec(sql);
		
		sql = "INSERT INTO order_management(`user_id`, `pay_type`, `pay_price`, `pay_value`, `pay_what`, `pay_status`, `order_num`, `pay_time`,`create_name`) " +
				"VALUES ("+arg[2]+", '现金', '"+money+"', '"+money+"', '充值', '已付款', '"+s+"', now(),'"+arg[4]+"');";
		log.send(DataType.basicType, "01162", "充值记录", sql);
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	private void czpass(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql="update user_data set password='"+arg[3]+"' where id="+arg[2];
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}
	private void e_warning(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql="update warning set warning='"+arg[3]+"' where id="+arg[2];
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}
	private void e_wxcode(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql="update warning set wxcode='"+arg[3]+"' where id="+arg[2];
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
	}

	private void notification_add(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.addSqlface(0, arg);
		log.send(DataType.basicType, "01162", "发布通知", sql);
		int a = sqlUtil.sql_exec(sql);
		sql = sqlmface.addSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		String user_id = "";
		String content = arg[2];
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				user_id = list.get(i).get("id").toString();
			} else {
				user_id = user_id + "," + list.get(i).get("id").toString();
			}
		}
		log.send(DataType.basicType, "01160", "最新一条内容 ", "用户ID" + user_id
				+ "内容:----" + content);
		BizRenderTask send = new BizRenderTask(user_id + "卍" + content);
		send.run();
		log.send(DataType.basicType, "01160", "最新一条内容 ", "通过并返回");
		inOutUtil.return_ajax("修改成功");
	}

	private void photo2(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.addSqlface(0, arg);
		log.send(DataType.basicType, "01162", "轮播图修改1", sql);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/photo_mod.jsp");
	}

	public class BizRenderTask implements Runnable {
		private String title;
		private String content;
		private String group;

		BizRenderTask(String content) {
			// this.group=group;
			// this.title=title;
			this.content = content;
		}

		public void run() {
			Socket client;
			try {
				// log.send(DataType.specialType, "01066", "BizRenderTask",
				// content);

				client = new Socket("119.23.16.29", 9200);
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(
						client.getOutputStream(), "UTF-8"), true);
				writer.println(content);
				writer.close();
				client.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.send(DataType.basicType, "01160", "流错误", e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.send(DataType.basicType, "01160", "IO错 ", e);
			}
		}

	}

	/**
	 * 增加用户 arg[0]:A-boss-add arg[1]:bossand
	 *
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 *
	 */
	private void bossand(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.addSqlface(1, arg);
		log.send("01152", "sql", sql);
		int a = sqlUtil.get_int(sql);
		log.send("01152", "a", a);
		String jsonadd = "{\"1\":\"添加成功\"}";
		inOutUtil.return_ajax(jsonadd);

	}

	@Override
	public void modface() throws SQLException, ServletException, IOException {
		// TODO Auto-generated method stub
		switch (arg[1]) {
		case "cash_mod1":
			cash_mod1(arg);
			break;
		case "fencheng_mod1":
			fencheng_mod1(arg);
			break;

		case "cash_mod":
			cash_mod(arg);
			break;
		case "zhubo_pass":
			zhubo_pass(arg);
			break;
		case "zhubo_nopass":
			zhubo_nopass(arg);
			break;
		case "video_checkpass":
			video_checkpass(arg);
			break;
		case "video_checknopass":
			video_checknopass(arg);
			break;
		case "news_all_mod0":
			news_all_mod0(arg);
			break;
		case "news_all_mod1":
			news_all_mod1(arg);
			break;
		case "album_checkpass":
			album_checkpass(arg);
			break;
		case "agent_checkpass":
			agent_checkpass(arg);
			break;
		case "album_checknopass":
			album_checknopass(arg);
			break;
		case "agent_checknopass":
			agent_checknopass(arg);
			break;
		case "photo_checkpass":
			photo_checkpass(arg);
			break;
		case "photo_checknopass":
			photo_checknopass(arg);
			break;
		case "renzheng_checkpass":
			renzheng_checkpass(arg);
			break;
		case "renzheng_checknopass":
			renzheng_checknopass(arg);
			break;
		case "recharge_mod":
			recharge_mod(arg);
			break;
		case "recharge_mod1":
			recharge_mod1(arg);
			break;
		// 修改分销提成比例
		case "mod_fenxiao":
			mod_fenxiao(arg);
			break;
		case "response_money":
			response_money(arg);
			break;
		case "jujue_money":
			jujue_money(arg);
			break;

		case "anchor_banned":
			anchor_banned(arg);
			break;
		case "banned_cancel":
			banned_cancel(arg);
			break;
		case "photo_mod":
			photo_mod(arg);
			break;
		case "fencheng_mod":
			fencheng_mod(arg);
			break;
		case "agent_mod":
			agent_mod(arg);
			break;
		case "agentlevel_mod":
			agentlevel_mod(arg);
			break;
		case "agentlevel_mod1":
			agentlevel_mod1(arg);
			break;
		case "admin_mod":
			admin_mod(arg);
			break;
		case "admin_mod1":
			admin_mod1(arg);
			break;
		case "gift_mod":
			gift_mod(arg);
			break;
		case "gift_mod1":
			gift_mod1(arg);
			break;

		case "anchor_tuijian":
			anchor_tuijian(arg);
			break;
		case "is_zhouxing":
			is_zhouxing(arg);
			break;


		case "sortmod":
			sortmod(arg);
			break;

		case "xingmod":
			xingmod(arg);
			break;
		case "anchor_nickname":
			anchor_nickname(arg);
			break;
		case "czAmount":
			czAmount(arg);
			break;
		case "e_warning":
			e_warning(arg);
			break;
		case "e_wxcode":
			e_wxcode(arg);
			break;
		case "czpass":
			czpass(arg);
			break;
		case "anchor_phonenum":
			anchor_phonenum(arg);
			break;
		case "anchor_photo":
			anchor_photo(arg);
			break;
		case "anchor_pictures":
			anchor_pictures(arg);
			break;
		case "automsg_num":
			automsg_num(arg);
			break;
		case "album_picture":
			album_picture(arg);
			break;


		}
	}


	private void automsg_num(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql="update auto_num set auto_num="+arg[2]+" where id=1";
		log.send(DataType.basicType, "01162", "代理对应等级修改",  sql);
    	sqlUtil.sql_exec(sql);

	}

	private void is_zhouxing(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql="update gift_list set is_tuijian=1 where id="+arg[2];
		log.send(DataType.basicType, "01162", "代理对应等级修改",  sql);
    	sqlUtil.sql_exec(sql);

    	sql="update gift_list set is_tuijian=0 where id !="+arg[2];
		log.send(DataType.basicType, "01162", "代理对应等级修改",  sql);
    	sqlUtil.sql_exec(sql);

    	inOutUtil.return_ajax("1");
	}

	private void xingmod(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql="update user_data set star="+arg[3]+" where id="+arg[2];
		log.send(DataType.basicType, "01162", "代理对应等级修改",  sql);
    	sqlUtil.sql_exec(sql);
    	inOutUtil.return_ajax("1");
	}

	private void anchor_nickname(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql="update user_data set nickname='"+arg[3]+"' where id="+arg[2];
		log.send(DataType.basicType, "01162", "昵称修改",  sql);
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	private void anchor_phonenum(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql="update user_data set phonenum='"+arg[3]+"' where id="+arg[2];
		log.send(DataType.basicType, "01162", "手机号修改",  sql);
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	private void album_picture(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql="update modinfo_list set picture='"+arg[3]+"' where id="+arg[2];
		log.send(DataType.basicType, "01162", "相册审核修改",  sql);
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}
	private void anchor_photo(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql="update user_data set photo='"+arg[3]+"' where id="+arg[2];
		log.send(DataType.basicType, "01162", "头像修改",  sql);
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}
	private void anchor_pictures(String[] arg) throws SQLException, IOException,
		ServletException {
		String sql="update user_data set pictures='"+arg[3]+"' where id="+arg[2];
		log.send(DataType.basicType, "01162", "认证图片修改",  sql);
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	private void sortmod(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql="update user_data set sort_id="+arg[3]+" where id="+arg[2];
    	sqlUtil.sql_exec(sql);
    	inOutUtil.return_ajax("1");
	}
	private void anchor_tuijian(String[] arg) throws SQLException, IOException,
	ServletException {
        String sql="select is_tuijian from user_data where id="+arg[2];
        int ist=sqlUtil.get_int(sql);
        if(ist==0){
        	sql="update user_data set is_tuijian=1 where id="+arg[2];
        	sqlUtil.sql_exec(sql);
        	inOutUtil.return_ajax("1");
        }else{
        	sql="update user_data set is_tuijian=0,sort_id=0 where id="+arg[2];
        	sqlUtil.sql_exec(sql);
        	inOutUtil.return_ajax("0");
        }


	}
	private void gift_mod1(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		int a = sqlUtil.sql_exec(sql);
		if (a == 1) {
			inOutUtil.return_ajax("1");
		}
	}

	private void gift_mod(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/gift_mod.jsp");
	}

	private void admin_mod1(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		int a = sqlUtil.sql_exec(sql);
		if (a == 1) {
			inOutUtil.return_ajax("1");
		}
	}

	private void admin_mod(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		list = sqlUtil.get_list(sql);

		inOutUtil.return_list(list, "/uiface1/boss/admin_mod.jsp");
	}

	private void agentlevel_mod1(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		list.get(0).put("user_id", arg[2]);
		inOutUtil.return_list(list, "/uiface1/boss/agent_mod.jsp");
	}

	private void agentlevel_mod(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "代理对应等级修改", sql);
		int a = sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	private void agent_mod(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "代理对应等级修改", sql);
		int a = sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	private void fencheng_mod(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "轮播图更改", sql);
		int a = sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	private void photo_mod(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "轮播图更改", sql);
		int a = sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("1");
	}

	private void banned_cancel(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		sqlUtil.sql_exec(sql);
		BizRenderTask send = new BizRenderTask(arg[2] + "卍" + "解封");
		send.run();

	}

	private void anchor_banned(String[] arg) throws SQLException, IOException,
			ServletException {
		if (!arg[2].equals("")) {
			String sql = sqlmface.modSqlface(0, arg);
			sqlUtil.sql_exec(sql);
			BizRenderTask send = new BizRenderTask(arg[2] + "卍" + "封禁");
			send.run();
		}
	}

	// arg[2]:0、修改分销比例，1、修改代理商比例,arg[3]:提成比例
	private void mod_fenxiao(String[] arg) throws SQLException, IOException,
			ServletException {
		// TODO Auto-generated method stub

		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "修改分销提成比例-sql", sql);
		sqlUtil.sql_exec(sql);
		String jsonadd = "{\"success\":\"1\"}";
		inOutUtil.return_ajax(jsonadd);

	}

	private void jujue_money(String[] arg) throws SQLException, IOException,
			ServletException {
		// TODO Auto-generated method stub

		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "修改分销提成比例-sql", sql);
		sqlUtil.sql_exec(sql);
		String jsonadd = "{\"success\":\"1\"}";
		inOutUtil.return_ajax(jsonadd);

	}

	/**
	 * arg[0] A-boss-mod arg[1] response_money arg[2] id
	 *
	 * @param arg
	 * @throws IOException
	 * @throws SQLException
	 */
	private void response_money(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		if (list.size() == 1) {
			log.send(DataType.basicType, "01158", "response_money()-list: ",
					list);

			double realmoney = Double.parseDouble(list.get(0).get("cash")
					.toString());

			AlipayClient alipayClient = new DefaultAlipayClient(
					"https://openapi.alipay.com/gateway.do", Ordercreat.APPID,
					Ordercreat.RSA2_PRIVATE, "json", "utf-8",
					Ordercreat.ALIPAY_PUBLIC_KEY, "RSA2");
			AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
			request.setBizContent("{" + "\"out_biz_no\":\""
					+ list.get(0).get("out_biz_no").toString() + "\","
					+ "\"payee_type\":\"ALIPAY_LOGONID\","
					+ "\"payee_account\":\""
					+ list.get(0).get("tixian_account").toString() + "\","
					+ "\"amount\":\"" + list.get(0).get("cash").toString()
					+ "\"," + "\"payer_show_name\":\"钻石\","
					+ "\"payee_real_name\":\"\"," + "\"remark\":\"钻石提现\"" // +
																			// "\"payee_real_name\":\"张三\","
					+ "}");
			AlipayFundTransToaccountTransferResponse response = null;
			log.send(DataType.basicType, "01158",
					"response_money()-response: ", 1);
			try {
				response = alipayClient.execute(request);
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.send(DataType.basicType, "01158",
						"response_money()-response: ", e.toString());
			}
			log.send(DataType.basicType, "01158",
					"response_money()-response: ", response.getParams());
			log.send(DataType.basicType, "01158",
					"response_money()-response: ", response.getBody()
							+ response.getErrorCode() + response.getParams());
			if (response.isSuccess()) {
				System.out.println("调用成功");

				String code = response.getCode();
				if (code.equals("10000")) {
					String[] newarg = new String[5];
					newarg[0] = response.getOutBizNo();
					newarg[1] = arg[1];
					newarg[2] = response.getOrderId();
					newarg[3] = response.getPayDate();
					newarg[4] = response.getMsg();
					sql = sqlmface.modSqlface(1, newarg);
					log.send(DataType.basicType, "01158",
							"response_money()-sql: ", sql);
					int i = sqlUtil.sql_exec(sql);
					log.send(DataType.basicType, "01158",
							"response_money()-i: ", i);
					String jsonadd = "提现成功！";
					log.send(DataType.basicType, "01158",
							"response_money()-jsonadd: ", jsonadd);

					if (list.get(0).get("c_type").toString().equals("0")) {

						ArrayList<Map<String, Object>> list2 = null;
						sql = "select * from cash_set ";
						log.send("01158", "sql", sql);
						list2 = sqlUtil.get_list(sql);

						ArrayList<Map<String, Object>> list1 = null;
						// 一级分销
						sql = "select promoter_id from user_data where id="
								+ list.get(0).get("user_id").toString();
						log.send("01158", "sql", sql);
						list1 = sqlUtil.get_list(sql);
						if (list1.size() == 0) {

						} else {
							/*String oneid = list1.get(0).get("promoter_id")
									.toString();
							if (!oneid.equals("0")) {
								String isv = "0";
								String sacleone = list2.get(0)
										.get("cash_onefee").toString();
								Double oneable_money = Double
										.parseDouble(sacleone) * realmoney;
								sql = "insert into tuiguang_detail (upuser_id,downuser_id,is_dv,levle,money_type,money_num,scale_num,able_money,uptime) values ('"
										+ oneid
										+ "','"
										+ list.get(0).get("user_id").toString()
										+ "','"
										+ isv
										+ "',1,'提现','"
										+ realmoney
										+ "','"
										+ sacleone
										+ "','"
										+ oneable_money + "',now())";
								log.send("01158", "sql", sql);
								sqlUtil.sql_exec(sql);
								sql = "update user_data set invite_price = invite_price+"
										+ realmoney
										+ ",ableinvite_price=ableinvite_price+"
										+ oneable_money
										+ " where id="
										+ oneid
										+ " ";
								log.send("01158", "sql", sql);
								sqlUtil.sql_exec(sql);
							}*/
						}
					}

					BizRenderTask send = new BizRenderTask(list.get(0)
							.get("user_id").toString()
							+ "卍您于"
							+ list.get(0).get("time").toString()
							+ "提交的 "
							+ list.get(0).get("cash").toString()
							+ " 元的提现申请已经通过，请注意查看！");
					send.run();
					inOutUtil.return_ajax(jsonadd);
				} else {
					String[] newarg = new String[3];
					newarg[0] = arg[2];
					newarg[1] = arg[1];
					newarg[2] = response.getSubMsg();
					sql = sqlmface.modSqlface(2, newarg);
					log.send(DataType.basicType, "01158",
							"response_money()-sql: ", sql);
					int i = sqlUtil.sql_exec(sql);
					log.send(DataType.basicType, "01158",
							"response_money()-i: ", i);
					String jsonadd = newarg[2];
					log.send(DataType.basicType, "01158",
							"response_money()-jsonadd: ", jsonadd);
					inOutUtil.return_ajax(jsonadd);
				}

			} else {
				String[] newarg = new String[3];
				newarg[0] = arg[2];
				newarg[1] = arg[1];
				newarg[2] = response.getSubMsg();
				sql = sqlmface.modSqlface(2, newarg);
				log.send(DataType.basicType, "01158", "response_money()-sql: ",
						sql);
				int i = sqlUtil.sql_exec(sql);
				log.send(DataType.basicType, "01158", "response_money()-i: ", i);
				String jsonadd = newarg[2];
				log.send(DataType.basicType, "01158",
						"response_money()-jsonadd: ", jsonadd);
				inOutUtil.return_ajax(jsonadd);
			}

		} else {
			String jsonadd = "没有此条提现明细或已提现！";
			log.send(DataType.basicType, "01158", "response_money()-jsonadd: ",
					jsonadd);
			inOutUtil.return_ajax(jsonadd);
		}

	}

	/**
	 * v币充值修改
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void recharge_mod1(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("修改成功");
	}

	/**
	 * v币充值查询 arg[2]id
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void recharge_mod(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/recharge_mod.jsp");
	}

	/**
	 * 认证审核未通过
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	// arg[2]:id,arg[4]:拒绝理由
	private void renzheng_checknopass(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "认证审核拒绝-sql", sql);
		sqlUtil.sql_exec(sql);
		BizRenderTask send = new BizRenderTask(arg[2] + "卍" + "认证失败");
		String jsonadd = "{\"success\":\"1\"}";
		inOutUtil.return_ajax(jsonadd);
	}

	/**
	 * 认证审核通过
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void renzheng_checkpass(String[] arg) throws SQLException,
			IOException, ServletException {
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);

		String sql0 = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "认证审核通过-sql0", sql0);
		sqlUtil.sql_exec(sql0);

		String sql1 = sqlmface.modSqlface(1, arg);
		log.send(DataType.basicType, "01150", "认证审核通过-sql1:", sql1);
		sqlUtil.sql_exec(sql1);

		String sql2 = sqlmface.modSqlface(2, arg);
		log.send(DataType.basicType, "01150", "认证审核通过-sql2:", sql2);
		list = sqlUtil.get_list(sql2);
		log.send(DataType.basicType, "01150", "认证审核通过的个人资料-list:", list);

		arg = Arrays.copyOfRange(arg, 0, arg.length + 11);

		arg[4] = list.get(0).get("user_id").toString();
		String picture = list.get(0).get("picture").toString();
		arg[6] = list.get(0).get("phonenum").toString();
		arg[7] = list.get(0).get("height").toString();
		arg[8] = list.get(0).get("weight").toString();
		arg[9] = list.get(0).get("constellation").toString();
		arg[10] = list.get(0).get("city").toString();
		arg[11] = list.get(0).get("intro").toString();
		arg[12] = list.get(0).get("label").toString();
		arg[13] = list.get(0).get("signature").toString();

		String lab_color = "";

		if (!arg[12].equals("")) {
			String[] img = arg[12].split(",");
			for (int i = 0; i < img.length; i++) {
				arg[0] = img[i];
				String sqlm = sqlmface.modSqlface(6, arg);
				log.send(DataType.basicType, "01150",
						"查找标签对应颜色============-sqlm:", sqlm);
				String color = sqlUtil.get_string(sqlm);
				log.send(DataType.basicType, "01150",
						"查找标签对应颜色============-color:", color);

				if (lab_color.equals("")) {
					lab_color = img[i] + "@" + color;
				} else {
					lab_color = lab_color + "卍" + img[i] + "@" + color;
				}
			}
		}

		arg[12] = lab_color;
		arg[14] = picture;
		String sql4 = sqlmface.modSqlface(3, arg);
		log.send(DataType.basicType, "01150", "修改资料============-sql4:", sql4);
		sqlUtil.sql_exec(sql4);

		BizRenderTask send = new BizRenderTask(arg[4] + "卍" + "认证成功");
		send.run();
		String jsonadd = "{\"success\":\"1\"}";
		inOutUtil.return_ajax(jsonadd);
	}

	/**
	 * 头像审核未通过
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void photo_checknopass(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		sqlUtil.sql_exec(sql);
	}

	/**
	 * 头像审核通过
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void photo_checkpass(String[] arg) throws SQLException,
			IOException, ServletException {
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);

		String sql0 = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "头像审核通过-sql0", sql0);
		sqlUtil.sql_exec(sql0);

		String sql1 = sqlmface.modSqlface(1, arg);
		log.send(DataType.basicType, "01150", "头像审核通过-sql1:", sql1);
		sqlUtil.sql_exec(sql1);

	}

	/**
	 * 相册审核未通过
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	// arg[4]:拒绝理由
	private void album_checknopass(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		sqlUtil.sql_exec(sql);
		String jsonadd = "{\"success\":\"1\"}";
		inOutUtil.return_ajax(jsonadd);
	}

	/**
	 * 相册审核通过
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void album_checkpass(String[] arg) throws SQLException,
			IOException, ServletException {
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);

		String sql0 = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "相册审核通过-sql0", sql0);
		sqlUtil.sql_exec(sql0);

		String sql1 = sqlmface.modSqlface(1, arg);
		log.send(DataType.basicType, "01150", "相册审核通过-sql1:", sql1);
		String pic = sqlUtil.get_string(sql1);
		log.send(DataType.basicType, "01150", "审核通过的上传图片：", pic);

		String sql2 = sqlmface.modSqlface(2, arg);
		log.send(DataType.basicType, "01150", "相册审核通过-sql2:", sql2);
		String album = sqlUtil.get_string(sql2);
		log.send(DataType.basicType, "01150", "我的相册图片：", album);

		/* if(album.equals("")){ */
		arg[4] = pic + "";
		/*
		 * }else{ arg[4] = album + "," + pic; }
		 */

		String sql3 = sqlmface.modSqlface(3, arg);
		log.send(DataType.basicType, "01162", "相册审核通过-sql3", sql3);
		sqlUtil.sql_exec(sql3);

		String jsonadd = "{\"success\":\"1\"}";
		inOutUtil.return_ajax(jsonadd);

	}

	/**
	 * 代理商通过
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void agent_checkpass(String[] arg) throws SQLException,
			IOException, ServletException {
		// arg = Arrays.copyOfRange(arg, 0, arg.length + 2);

		String sql0 = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "代理商通过-sql0", sql0);
		sqlUtil.sql_exec(sql0);

		String sql2 = sqlmface.modSqlface(2, arg);
		log.send(DataType.basicType, "01162", "代理商通过-sql3", sql2);
		sqlUtil.sql_exec(sql2);

		String sql4 = sqlmface.modSqlface(4, arg);
		log.send(DataType.basicType, "01162", "代理商通过-sql4", sql4);
		sqlUtil.sql_exec(sql4);

		String sql3 = sqlmface.modSqlface(3, arg);
		log.send(DataType.basicType, "01162", "代理商通过-sql3", sql3);
		sqlUtil.sql_exec(sql3);

		inOutUtil.return_ajax("已成功");

	}

	/**
	 * 代理商不通过
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void agent_checknopass(String[] arg) throws SQLException,
			IOException, ServletException {
		// arg = Arrays.copyOfRange(arg, 0, arg.length + 2);

		String sql0 = sqlmface.modSqlface(0, arg);
		log.send(DataType.basicType, "01162", "代理商不通过sql0", sql0);
		sqlUtil.sql_exec(sql0);

		inOutUtil.return_ajax("不通过");

	}

	/**
	 * 修改通知
	 *
	 * @param arg
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	private void news_all_mod1(String[] arg) throws ServletException,
			IOException, SQLException {
		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.noType, "01165", "修改全体通知", sql);
		sqlUtil.sql_exec(sql);

		String sql1 = sqlmface.modSqlface(1, arg);
		log.send(DataType.basicType, "01160", "用户表 ", sql1);
		list1 = sqlUtil.get_list(sql1);
		log.send(DataType.basicType, "01160", "所有人 ", list1);

		String sql2 = sqlmface.modSqlface(2, arg);
		log.send(DataType.basicType, "01160", "内容 ", sql2);
		String content = sqlUtil.get_string(sql2);
		log.send(DataType.basicType, "01160", "修改的一条内容 ", content);

		String user_id = "";
		for (int i = 0; i < list1.size(); i++) {
			if (i == 0) {
				user_id = list1.get(i).get("id").toString();
			} else {
				user_id = user_id + "," + list1.get(i).get("id").toString();
			}
		}

		BizRenderTask send = new BizRenderTask(user_id + "卍" + content);
		send.run();
	}

	/**
	 * 修改通知
	 *
	 * @param arg
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	private void news_all_mod0(String[] arg) throws ServletException,
			IOException, SQLException {
		String sql = sqlmface.modSqlface(0, arg);
		log.send(DataType.noType, "01160", "b111", sql);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/news_all_mod.jsp");
	}

	/**
	 * 不予通过 arg[2]视频id
	 *
	 * @param arg
	 */
	private void video_checknopass(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql1 = sqlmface.modSqlface(1, arg);
		log.send(DataType.basicType, "01150", "不予通过", sql1);
		sqlUtil.sql_exec(sql1);

	}

	/**
	 * 设置视频标签 arg[2]视频id arg[3]标签编号
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void video_checkpass(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql1 = sqlmface.modSqlface(1, arg);
		log.send(DataType.basicType, "01150", "设置视频通过", sql1);
		sqlUtil.sql_exec(sql1);

	}

	/**
	 * 取消主播资格
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void zhubo_nopass(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.modSqlface(1, arg);
		log.send(DataType.basicType, "01150", "取消主播资格", sql1);
		sqlUtil.sql_exec(sql1);

	}

	/**
	 * 成为主播
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void zhubo_pass(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.modSqlface(1, arg);
		log.send(DataType.basicType, "01150", "成为主播", sql1);
		sqlUtil.sql_exec(sql1);

	}

	private void cash_mod(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		sqlUtil.sql_exec(sql);
		inOutUtil.return_ajax("修改");
	}

	private void cash_mod1(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/cash_modify_persent.jsp");
	}

	private void fencheng_mod1(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql = sqlmface.modSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/cash_modify_persent1.jsp");
	}



	@Override
	public void deleteface() throws SQLException, ServletException, IOException {
		switch (arg[1]) {
		case "news_all_del":
			news_all_del(arg);
			break;
		case "renzheng_record_del":
			renzheng_record_del(arg);
			break;
		case "photo_del":
			photo_del(arg);
			break;
		case "video_del":
			video_del(arg);
			break;
		case "photorecord_del":
			photorecord_del(arg);
			break;
		case "admin_del":
			admin_del(arg);
			break;

		case "del_xitong_one":
			del_xitong_one(arg);
			break;

		case "del_automsg":
			del_automsg(arg);
			break;

		}
	}

	private void admin_del(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.deleteSqlface(arg);
		int a = sqlUtil.sql_exec(sql);
	}

	private void del_xitong_one(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql = "delete from notification_table where id='"+arg[2]+"'";
		int a = sqlUtil.sql_exec(sql);
		}

	private void del_automsg(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql = "delete from automsg where id='"+arg[2]+"'";
		int a = sqlUtil.sql_exec(sql);
		}


	private void photorecord_del(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.deleteSqlface(arg);
		int a = sqlUtil.sql_exec(sql);
	}

	private void video_del(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.deleteSqlface(arg);
		int a = sqlUtil.sql_exec(sql);
	}

	private void photo_del(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.deleteSqlface(arg);
		int a = sqlUtil.sql_exec(sql);
	}

	private void renzheng_record_del(String[] arg) throws ServletException,
			IOException, SQLException {
		String sql = sqlmface.deleteSqlface(arg);
		log.send(DataType.noType, "01162", "删除未通过的认证记录", sql);
		sqlUtil.sql_exec(sql);
	}

	/**
	 * 删除
	 *
	 * @param arg
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	private void news_all_del(String[] arg) throws ServletException,
			IOException, SQLException {
		String sql = sqlmface.deleteSqlface(arg);
		log.send(DataType.noType, "01165", "删除全体通知", sql);
		sqlUtil.sql_exec(sql);
	}

	@Override
	public void searchface() throws SQLException, ServletException, IOException {
		// TODO Auto-generated method stub
		switch (arg[1]) {
		case "subject":
			subject(arg);
			break;
		case "hot":
			hot(arg);
			break;
		// 消费记录表查询
		case "pay_table_search":
			pay_table_search(arg);
			break;
		// 主播收入明细
		case "income_table_search":
			income_table_search(arg);
			break;

		case "income_table_search1":
			income_table_search1(arg);
			break;

		// 礼物收入查询
		case "gift_details_search":
			gift_details_search(arg);
			break;

		// 财富值查询
		case "Wealth_value_search":
			Wealth_value_search(arg);
			break;

		case "charm_search":
			charm_search(arg);
			break;

		// 魅力值查询
		case "usercp_search":
			usercp_search(arg);
			break;

		// 爵位查询
		case "grade_privilege":
			grade_privilege(arg);
			break;

		case "cash_withdrawal":
			cash_withdrawal(arg);
			break;
		case "pay_list":
			pay_list(arg);
			break;
		case "memberbackstage":// 会员后台
			memberbackstage(arg);
			break;
		case "userdataexecl":// 会员导出
			userDateExcel(arg);
			break;
		case "anchorexecl":// 主播导出
			anchorexecl(arg);
			break;
		case "anchorsrexecl":// 主播收入明细导出
			anchorsrexecl(arg);
			break;
		case "payexecl":// 充值记录导出
			payexecl(arg);
			break;
		case "anchortxexecl":// 主播提现导出
			anchortxexecl(arg);
			break;
		case "tgsrexecl":// 推广收入明细导出
			tgsrexecl(arg);
			break;
		case "tgtxexecl":// 推广提现明细导出
			tgtxexecl(arg);
			break;
		case "agentist"://
			agentist(arg);
			break;
		case "giftlist":
			tcqo_gift_list(arg);
			break;
		case "tyrants_week_search":
			tyrants_week_search(arg);
			break;
		case "charm_week_search":
			charm_week_search(arg);
			break;
		case "auditapply":
			auditapply(arg);
			break;
		case "cashwithdrawal":
			cashwithdrawal(arg);
			break;
		case "admin_login":
			admin_login(arg);
			break;
		case "identity_check":
			identity_check(arg);
			break;
		case "member_album":
			member_album(arg);
			break;
		case "blacklist_manage":
			blacklist_manage(arg);
			break;
		case "agentist_manage":
			agentist_manage(arg);
			break;
		case "member_video":
			member_video(arg);
			break;
		case "cash_set":
			cash_set(arg);
			break;
		case "mykin_search":
			mykin_search(arg);
			break;
		case "low_first":
			low_first(arg);
			break;
		case "low_second":
			low_second(arg);
			break;
		case "complaint_search":
			complaint_search(arg);
			break;
		case "video_manage":
			video_manage(arg);
			break;
		// 全体通知
		case "boll_systemnews_search":
			boll_systemnews_search(arg);
			break;
		case "video_play":
			video_play(arg);
			break;
		case "photo_search_one":
			photo_search_one(arg);
			break;
		case "photo_search":
			photo_search(arg);
			break;
		case "album_list_one":
			album_list_one(arg);
			break;
		case "album_list":
			album_list(arg);
			break;
		case "renzheng_v":
			renzheng_v(arg);
			break;
		case "renzheng_v_passed":
			renzheng_v_passed(arg);
			break;
		case "renzheng_v_no":
			renzheng_v_no(arg);
			break;
		case "renzheng_photosearch":
			renzheng_photosearch(arg);
			break;
		case "renzheng_photosearch1":
			renzheng_photosearch1(arg);
			break;
		case "renzheng_photosearch_zz":
			renzheng_photosearch_zz(arg);
			break;
		case "recharge_set":
			recharge_set(arg);
			break;
		case "anchor_search":	//主播列表
			anchor_search(arg);
			break;
		// 分销比例查询
		case "fenxiao_search_set":
			fenxiao_search_set(arg);
			break;
		case "fenxiao_search_mod":
			fenxiao_search_mod(arg);
			break;
		case "daili_search_mod":
			daili_search_mod(arg);
			break;
		case "recycle_photo":
			recycle_photo(arg);
			break;
		case "labellist_zhubo":
			labellist_zhubo(arg);
			break;
		case "jianhuang_list":
			jianhuang_list(arg);
			break;
		case "fencheng_set":
			fencheng_set(arg);
			break;
		case "agent_set":
			agent_set(arg);
			break;
		case "notification_search":
			notification_search(arg);
			break;
		case "app_search":
			app_search(arg);
			break;



		case "automsg_search":
			automsg_search(arg);
			break;
		case "jay_search":
			jay_search(arg);
			break;
		case "admin_list":
			admin_list(arg);
			break;
		case "gift_list":
			gift_list(arg);
			break;

		case "fenxiang_search":
			fenxiang_search(arg);
			break;
		case "fenxiang_info":
			fenxiang_info(arg);
			break;
		case "fenxiang_mod":
			fenxiang_mod(arg);
			break;

		}
	}

	private void fenxiang_mod(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql="";
		if(Integer.parseInt(arg[2])<4){
			sql="update reward_percentmanager set male_percent="+arg[3]+",female_percent="+arg[4]+"  where id="+arg[2];
		}else{
			int a=Integer.parseInt(arg[2])-3;
			sql="update fixed_percentmanager set male_percent="+arg[3]+",female_percent="+arg[4]+"  where id="+a;
		}
		sqlUtil.sql_exec(sql);
	}
	private void fenxiang_info(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql="";
		if(Integer.parseInt(arg[2])<4){
			sql="select * from reward_percentmanager where id="+arg[2];
		}else{
			int a=Integer.parseInt(arg[2])-3;
			sql="select * from fixed_percentmanager where id="+a;
		}
		list = sqlUtil.get_list(sql);
		int[] pages=new int[]{Integer.parseInt(arg[2]),0,0,0,0};
		inOutUtil.return_listpage(list, pages,
				"/uiface1/boss/fenxiang_setmod.jsp");
	}


	private void fenxiang_search(String[] arg) throws SQLException, IOException,
	ServletException {
		String sql = "select * from reward_percentmanager";
		list = sqlUtil.get_list(sql);
		sql = "select * from fixed_percentmanager";
		list2 = sqlUtil.get_list(sql);

		list.addAll(list2);

		inOutUtil.return_listpage(list, pages,
				"/uiface1/boss/fenxiang_set.jsp");
	}


	private void gift_list(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "gift_list-sql: ", sql);
		int a = sqlUtil.get_countint(sql);
		pages = hm.pages(arg[3], a);
		arg[3] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01162", "gift_list-sql: ", sql);
		list = sqlUtil.get_list(sql);
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/gift_list.jsp");
		} else if (arg[4].equals("tojson")) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	private void admin_list(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "admin_list-sql: ", sql);
		int a = sqlUtil.get_countint(sql);
		pages = hm.pages(arg[2], a);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01162", "admin_list-sql: ", sql);
		list = sqlUtil.get_list(sql);
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/admin_list.jsp");
		} else if (arg[4].equals("tojson")) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	private void notification_search(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int a = sqlUtil.get_countint(sql);
		pages = hm.pages(arg[2], a);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/notification_list.jsp");
		} else if (arg[4].equals("tojson")) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}



	private void app_search(String[] arg) throws SQLException,
	IOException, ServletException {
		String sql = "select count(*) from app_analyse";
		int a = sqlUtil.get_countint(sql);
		pages = hm.pages(arg[2], a);


		arg[2] = pages[2] + "";
		sql = "select * from app_analyse order by id desc limit "+arg[2]+","+JyHelpManager.item+"";
		list = sqlUtil.get_list(sql);
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/app_list.jsp");
		} else if (arg[4].equals("tojson")) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

   }

	private void automsg_search(String[] arg) throws SQLException,
	IOException, ServletException {
		String sql = "select count(*) from automsg";
		int a = sqlUtil.get_countint(sql);
		pages = hm.pages(arg[2], a);

		sql="select auto_num from auto_num where id=1";
		pages[0]=sqlUtil.get_int(sql);

		arg[2] = pages[2] + "";
		sql = "select * from automsg order by id desc limit "+arg[2]+","+JyHelpManager.item+"";
		list = sqlUtil.get_list(sql);
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/automsg_list.jsp");
		} else if (arg[4].equals("tojson")) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

   }

	private void jay_search(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = "select * from warning";
		list = sqlUtil.get_list(sql);
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/warning.jsp");
		} else if (arg[4].equals("tojson")) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}



	private void agent_set(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int a = sqlUtil.get_countint(sql);
		pages = hm.pages(arg[2], a);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_list(list, "/uiface1/boss/agentpersent_set.jsp");
		} else if (arg[4].equals("tojson")) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	private void fencheng_set(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "fencheng_set:", sql);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql);
		log.send(DataType.basicType, "01162", "fencheng_set:", list);
		inOutUtil.return_list(list, "/uiface1/boss/cash_set1.jsp");
	}

	/**
	 * 查看鉴黄上传图片列表 arg[0]: A-boss-search arg[1]: jianhuang_list arg[2]: out
	 * tojsp/tojson arg[3]: pagenum ---- 页码 ---------------------------------
	 * arg[4]: pagefirst
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void jianhuang_list(String[] arg) throws SQLException, IOException,
			ServletException {

		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "jianhuang_list()-sql: ", sql);
		int total = sqlUtil.get_int(sql);
		log.send(DataType.basicType, "01162", "jianhuang_list()-total: ", total);
		int[] pages = hm.pages(arg[3], total);
		arg = Arrays.copyOf(arg, arg.length + 1);
		arg[arg.length - 1] = pages[2] + "";

		sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01162", "jianhuang_list()-sql2: ", sql);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql);
		log.send(DataType.basicType, "01162", "jianhuang_list()-list: ", list);

		if ("tojsp".equals(arg[2])) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/jianhuang_list.jsp");
		} else if ("tojson".equals(arg[2])) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	private void labellist_zhubo(String[] arg) throws SQLException,
			IOException, ServletException {

		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "labellist_zhubo-sql: ", sql);

		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql);
		log.send(DataType.basicType, "01162", "labellist_zhubo-list: ", list);

		inOutUtil.return_list(list, "/uiface1/boss/labellist_zhubo.jsp");
	}

	private void photo_add(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.addSqlface(0, arg);
		log.send(DataType.basicType, "01162", "后台——轮播图添加", sql);
		int a = sqlUtil.sql_exec(sql);
		String jsonadd = "{\"success\":\"1\"}";
		inOutUtil.return_ajax(jsonadd);

		/*
		 * if(a==1){ inOutUtil.return_only("boss/recycle_photo.jsp"); }
		 */
	}

	private void daili_search_mod(String[] arg) throws SQLException,
			IOException, ServletException {
		// TODO Auto-generated method stub
		arg[1] = "fenxiao_search_set";
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "daili_search_mod:", sql);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/daili_search_set.jsp");
	}

	private void fenxiao_search_mod(String[] arg) throws SQLException,
			IOException, ServletException {
		// TODO Auto-generated method stub
		arg[1] = "fenxiao_search_set";
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "fenxiao_search_mod:", sql);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/fenxiao_search_set.jsp");
	}

	// 分销比例查询
	private void fenxiao_search_set(String[] arg) throws SQLException,
			IOException, ServletException {
		// TODO Auto-generated method stub
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "主播列表", sql);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/fenxiao_set.jsp");
	}

	// arg[5]:1.普通主播，2.明星，3.心理，4.律师
	public void anchor_search(String[] arg) throws SQLException, IOException,
			ServletException {
		if (arg[3].equals("")) {
			String sql = sqlmface.searchSqlface(0, arg);
			log.send(DataType.basicType, "01162", "主播列表", sql);
			int total = sqlUtil.get_countint(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(1, arg);
			log.send(DataType.basicType, "01162", "主播列表", sql);
			list = sqlUtil.get_list(sql);
			if (arg[4].equals("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/anchor_list.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		} else {
			String sql = sqlmface.searchSqlface(2, arg);
			log.send(DataType.basicType, "01162", "主播列表", sql);
			int total = sqlUtil.get_countint(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(3, arg);
			log.send(DataType.basicType, "01162", "主播列表", sql);
			list = sqlUtil.get_list(sql);
			if (arg[4].equals("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/anchor_list.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		}
	}

	/**
	 * 后台充值设置查询
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void recharge_set(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		log.send(DataType.basicType, "01162", "v币充值设置查询", sql);
		inOutUtil.return_list(list, "/uiface1/boss/recharge_set.jsp");
	}

	/**
	 * 用户以及主播列表图片查看
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void renzheng_photosearch1(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "认证图片查看 ", sql);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/renzheng_photosearch1.jsp");

	}

	/**
	 * 认证图片查看
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void renzheng_photosearch(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "认证图片查看 ", sql);
		list = sqlUtil.get_list(sql);
		inOutUtil.return_list(list, "/uiface1/boss/renzheng_photosearch.jsp");

	}

	// 资质图片
	private void renzheng_photosearch_zz(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "认证图片查看 ", sql);
		list = sqlUtil.get_list(sql);
		inOutUtil
				.return_list(list, "/uiface1/boss/renzheng_photosearch_zz.jsp");

	}

	/**
	 * 大V认证申请 arg[2]分页信息 arg[3]tojsp/tojson
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	// 待审核
	private void renzheng_v(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int totle = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], totle);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		for (int i = 0; i < list.size(); i++) {
			String a = (String) list.get(i).get("picture");
			String a1[] = a.split(",");
			int b = a1.length;
			String a2 = String.valueOf(b);
			list.get(i).put("photo1", a2);
		}
		if (arg[3].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/renzheng_check.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	// 已通过
	private void renzheng_v_passed(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int totle = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], totle);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		for (int i = 0; i < list.size(); i++) {
			String a = (String) list.get(i).get("picture");
			String a1[] = a.split(",");
			int b = a1.length;
			String a2 = String.valueOf(b);
			list.get(i).put("photo1", a2);
		}
		if (arg[3].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/renzheng_check_passed.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	// 未通过
	private void renzheng_v_no(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int totle = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], totle);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		for (int i = 0; i < list.size(); i++) {
			String a = (String) list.get(i).get("picture");
			String a1[] = a.split(",");
			int b = a1.length;
			String a2 = String.valueOf(b);
			list.get(i).put("photo1", a2);
		}
		if (arg[3].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/renzheng_check_no.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	/**
	 * 相册图片--点击查看
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void album_list_one(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		log.send(DataType.basicType, "01162", "相册图片--点击查看", sql);
		inOutUtil.return_list(list, "/uiface1/boss/album_search_one1.jsp");
	}

	/**
	 * 相册图片审核--列表显示 arg[2]page arg[3]查询条件 arg[4]tojsp/tojson
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void album_list(String[] arg) throws SQLException, IOException,
			ServletException {
		if (arg[3].equals("")) {
			String sql = sqlmface.searchSqlface(0, arg);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(1, arg);
			log.send(DataType.basicType, "01162", "相册图片审核-无条件搜索", sql);
			list = sqlUtil.get_list(sql);
			if (arg[4].endsWith("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/album_search1.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		} else {
			String sql = sqlmface.searchSqlface(2, arg);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(3, arg);
			log.send(DataType.basicType, "01162", "相册图片审核-根据条件搜索", sql);
			list = sqlUtil.get_list(sql);
			if (arg[4].endsWith("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/album_search1.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		}
	}

	public void agentist(String[] arg) throws SQLException, IOException,
			ServletException {
		if (arg[3].equals("")) {
			String sql = sqlmface.searchSqlface(0, arg);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(1, arg);
			log.send(DataType.basicType, "01162", "相册图片审核-无条件搜索", sql);
			list = sqlUtil.get_list(sql);
			if (arg[4].endsWith("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/agent_list.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		} else {
			String sql = sqlmface.searchSqlface(2, arg);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(3, arg);
			log.send(DataType.basicType, "01162", "相册图片审核-根据条件搜索", sql);
			list = sqlUtil.get_list(sql);
			if (arg[4].endsWith("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/agent_list.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		}
	}

	/**
	 * 头像图片--点击查看
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void photo_search_one(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		log.send(DataType.basicType, "01162", "头像图片--点击查看", sql);
		inOutUtil.return_list(list, "/uiface1/boss/album_search_one.jsp");
	}

	/**
	 * 头像图片审核--列表显示 arg[2]page arg[3]查询条件 arg[4]tojsp/tojson
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void photo_search(String[] arg) throws SQLException, IOException,
			ServletException {
		if (arg[3].equals("")) {
			String sql = sqlmface.searchSqlface(0, arg);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(1, arg);
			log.send(DataType.basicType, "01162", "头像图片审核-无条件搜索", sql);
			list = sqlUtil.get_list(sql);
			if (arg[4].endsWith("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/album_search.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		} else {
			String sql = sqlmface.searchSqlface(2, arg);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(3, arg);
			log.send(DataType.basicType, "01162", "头像图片审核-根据条件搜索", sql);
			list = sqlUtil.get_list(sql);
			if (arg[4].endsWith("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/album_search.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		}
	}

	/**
	 * 点击播放视频 arg[2]视频id
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void video_play(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01150", "点击播放视频()-sql1:", sql1);
		list = sqlUtil.get_list(sql1);

		arg = Arrays.copyOfRange(arg, 0, arg.length + 1);
		String video_id = list.get(0).get("video_id").toString();
		log.send(DataType.basicType, "01150", "video_id", video_id);
		arg[3] = video_id + "";
		log.send(DataType.basicType, "01150", "arg:=========", arg);
		String url = main.main(arg);
		log.send(DataType.basicType, "01150", "url:=========", url);

		String json = okhttp.requestPostBySyn(url);

		log.send("01150", "json==============", json);

		String[] josn1 = json.split("mp4");
		log.send(DataType.basicType, "01150", "josn1:=========", josn1);

		String json2 = josn1[1];
		log.send(DataType.basicType, "01150", "josn2:=========", json2);

		String[] json3 = json2.split("http");
		log.send(DataType.basicType, "01150", "josn3:=========", json3);
		String json4 = json3[json3.length - 1];
		log.send(DataType.basicType, "01150", "josn4:=========", json4);

		url = "http" + json4 + "mp4";

		log.send(DataType.basicType, "01150",
				"url===========111===============", url);

		/*
		 * JSONObject jsonObject = JSONObject.fromObject(json);
		 * log.send(DataType.basicType, "01150", "jsonObject:=========",
		 * jsonObject);
		 *
		 * Map<String, Object> mapJson = JSONObject.fromObject(jsonObject);
		 * log.send(DataType.basicType, "01150", "mapJson:=========", mapJson);
		 *
		 * for(Entry<String,Object> entry : mapJson.entrySet()){ Object strval1
		 * = entry.getValue(); JSONObject jsonObjectStrval1 =
		 * JSONObject.fromObject(strval1);
		 *
		 * if(entry.getKey().equals("PlayURL")){ url =
		 * entry.getValue().toString(); }
		 *
		 * // Map<String, Object> mapJsonObjectStrval1 =
		 * JSONObject.fromObject(jsonObjectStrval1); //
		 * System.out.println("KEY:"
		 * +entry.getKey()+"  -->  Value:"+entry.getValue()+"\n"); //
		 * for(Entry<String, Object> entry1:mapJsonObjectStrval1.entrySet()){ //
		 * System
		 * .out.println("KEY:"+entry1.getKey()+"  -->  Value:"+entry1.getValue
		 * ()+"\n"); // }
		 *
		 * } log.send(DataType.basicType, "01150",
		 * "url===========111===============", url); // Map maps =
		 * (Map)JSON.parse(json); //
		 * System.out.println("这个是用JSON类来解析JSON字符串!!!"); // for (Object map :
		 * maps.entrySet()){ //
		 * //System.out.println(((Map.Entry)map).getKey()+"     " +
		 * ((Map.Entry)map).getValue()); // }
		 */
		// inOutUtil.return_listpage(list, pages,
		// "boss/demo1.htm?url="+url+"");//videoid="+video_id+"&
		response.sendRedirect("http://120.27.98.128:9118/uiface1/boss/demo2.jsp?url="
				+ url + "");

	}

	/*
	 * 系统通知列表 arg[0]: A-boss-search arg[1]: boll_systemnews_search arg[2]:
	 * pageIndex arg[3]: starttime arg[4]: endtime
	 */
	private void boll_systemnews_search(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int total = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], total);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		if (arg[3].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/systemnewsfei_1165.jsp");
			log.send(DataType.noType, "01165_systemnews_search", "list:", list);
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 * 短视频审核 arg[2]page arg[3]条件查询 arg[4]tojsp/tojson
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void video_manage(String[] arg) throws SQLException, IOException,
			ServletException {
		if (arg[3].equals("")) {

			String sql1 = sqlmface.searchSqlface(1, arg);
			log.send(DataType.basicType, "01150", "短视频审核()-sql1:", sql1);
			int count = sqlUtil.get_int(sql1);
			log.send(DataType.basicType, "01150", "短视频审核()-num:", count);

			pages = hm.pages(arg[2], count);
			arg[2] = pages[2] + "";

			String sql2 = sqlmface.searchSqlface(2, arg);
			log.send(DataType.basicType, "01150", "短视频审核()-sql2:", sql2);
			list = sqlUtil.get_list(sql2);
			log.send(DataType.basicType, "01150", "短视频审核()-list:", list);

			arg = Arrays.copyOfRange(arg, 0, arg.length + 1);
			for (int i = 0; i < list.size(); i++) {
				arg[5] = list.get(i).get("user_id").toString();
				String sqln = sqlmface.searchSqlface(3, arg);
				String nickname = sqlUtil.get_string(sqln);

				list.get(i).put("nickname1", nickname);
			}
		} else {
			if (arg[3].equals("未通过")) {
				arg[3] = "2";
			} else if (arg[3].equals("待审核")) {
				arg[3] = "0";
			} else if (arg[3].equals("已通过")) {
				arg[3] = "1";
			}

			String sql1 = sqlmface.searchSqlface(4, arg);
			log.send(DataType.basicType, "01150", "短视频审核()-sql1:", sql1);
			int count = sqlUtil.get_int(sql1);
			log.send(DataType.basicType, "01150", "短视频审核()-num:", count);

			pages = hm.pages(arg[2], count);
			arg[2] = pages[2] + "";

			String sql2 = sqlmface.searchSqlface(5, arg);
			log.send(DataType.basicType, "01150", "短视频审核()-sql2:", sql2);
			list = sqlUtil.get_list(sql2);
			log.send(DataType.basicType, "01150", "短视频审核()-list:", list);

			arg = Arrays.copyOfRange(arg, 0, arg.length + 1);
			for (int i = 0; i < list.size(); i++) {
				arg[5] = list.get(i).get("user_id").toString();
				String sqln = sqlmface.searchSqlface(3, arg);
				String nickname = sqlUtil.get_string(sqln);

				list.get(i).put("nickname1", nickname);
			}
		}

		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/video_manage_01150.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	private void complaint_search(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int a = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], a);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		if (arg[3].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/complaint.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	/**
	 * 查询二级下属 arg[0]A-boss-search arg[1]low_second arg[3]用户id arg[2]page
	 * arg[4]tojsp/tojson
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void low_second(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(-1, arg);
		log.send(DataType.basicType, "01150", "查询二级下属()-sql1:", sql1);
		int totle = sqlUtil.get_int(sql1);
		pages = hm.pages(arg[2], totle);
		arg[2] = pages[2] + "";
		String sql2 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01150", "查询二级下属()-sql2:", sql2);
		list = sqlUtil.get_list(sql2);
		log.send(DataType.basicType, "01150", "查询二级下属()-list:", list);

		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/mykin_list2.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	/**
	 * 查询一级下属 arg[0]A-boss-search arg[1]low_first arg[3]用户id arg[2]page
	 * arg[4]tojsp/tojson
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void low_first(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(-1, arg);
		log.send(DataType.basicType, "01150", "查询一级下属()-sql1:", sql1);
		int totle = sqlUtil.get_int(sql1);
		pages = hm.pages(arg[2], totle);
		arg[2] = pages[2] + "";
		String sql2 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01150", "查询一级下属()-sql2:", sql2);
		list = sqlUtil.get_list(sql2);
		log.send(DataType.basicType, "01150", "查询一级下属()-list:", list);
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/mykin_list1.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	public void mykin_search(String[] arg) throws SQLException, IOException,
			ServletException {
		arg = Arrays.copyOfRange(arg, 0, arg.length + 3);
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "我的家族查询所有", sql);
		int total = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], total);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01162", "我的加速查询所有-1", sql);
		list = sqlUtil.get_list(sql);
		int t = 0;

		for (int i = 0; i < list.size(); i++) {
			StringBuffer b = new StringBuffer();
			arg[5] = list.get(i).get("id") + "";
			arg[7] = list.get(i).get("promoter_id") + "";
			sql = sqlmface.searchSqlface(5, arg);
			log.send(DataType.basicType, "01162", "我的家族查询个人推荐人联系方式", sql);
			list1 = sqlUtil.get_list(sql);
			list.get(i).put("nick1", list1.get(0).get("nickname"));
			list.get(i).put("phone1", list1.get(0).get("phonenum"));
			sql = sqlmface.searchSqlface(6, arg);
			log.send(DataType.basicType, "01162", "一级下属人数", sql);
			t = sqlUtil.get_int(sql);
			list.get(i).put("my_first", t + "");
			sql = sqlmface.searchSqlface(2, arg);
			log.send(DataType.basicType, "01162", "一级下属人数id", sql);
			list1 = sqlUtil.get_list(sql);
			for (int i1 = 0; i1 < list1.size(); i1++) {
				if (i1 == 0) {
					b.append("promoter_id=" + list1.get(i1).get("id") + "");
				} else {
					b.append(" or promoter_id=" + list1.get(i1).get("id") + "");
				}
			}
			arg[5] = b.toString();
			if (arg[5].equals("")) {
				list.get(i).put("my_second", 0);
			} else {
				log.send(DataType.basicType, "01162", "二级下属人数-拼接", arg[5]);
				sql = sqlmface.searchSqlface(4, arg);
				log.send(DataType.basicType, "01162", "二级下属人数", sql);
				t = sqlUtil.get_int(sql);
				list.get(i).put("my_second", t + "");
			}
		}
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages, "/uiface1/boss/mykin.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	public void cash_set(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		log.send(DataType.basicType, "01162", "提现金额-提现手续费设置", sql);
		inOutUtil.return_list(list, "/uiface1/boss/cash_set.jsp");
	}

	public void blacklist_manage(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01162", "举报拉黑", sql);
		int totle = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], totle);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		list = sqlUtil.get_list(sql);
		for (int i = 0; i < list.size(); i++) {
			arg[5] = list.get(i).get("user_id") + "";
			arg[6] = list.get(i).get("target_id") + "";
			sql = sqlmface.searchSqlface(2, arg);
			log.send(DataType.basicType, "01162", "举报拉黑", sql);
			list1 = sqlUtil.get_list(sql);
			list.get(i).put("name1", list1.get(0).get("nickname"));
			list.get(i).put("phone1", list1.get(0).get("phonenum"));
			sql = sqlmface.searchSqlface(3, arg);
			log.send(DataType.basicType, "01162", "举报拉黑", sql);
			list2 = sqlUtil.get_list(sql);
			list.get(i).put("name2", list2.get(0).get("nickname"));
			list.get(i).put("phone2", list2.get(0).get("phonenum"));
		}
		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/blacklist.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	public void agentist_manage(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int totle = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], totle);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);

		if (arg[4].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/agentask_list.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	/**
	 * 搜索 arg[0]:A-boss-search arg[1]:hot arg[2]:page arg[3]:gift_sum（礼物的个数）
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void hot(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send("01152", "sql", sql);
		int count = sqlUtil.get_int(sql);
		log.send("01152", "count", count);
		pages = JyHelpManager.pages(arg[2], count);
		num = pages[2];
		String sql1 = sqlmface.searchSqlface(num, arg);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql1);
		log.send("01152", "list", list);
		if (arg[2].equals("0")) {
			inOutUtil.return_listpage(list, pages, "/uiface1/boss/hot.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}

	}

	/**
	 * 进后台 arg[0]:A-boss-search arg[1]:subject arg[2]:page
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */

	private void subject(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send("01152", "sql", sql);
		int count = sqlUtil.get_int(sql);
		log.send("01152", "count", count);
		pages = JyHelpManager.pages(arg[2], count);
		num = pages[2];
		String sql1 = sqlmface.searchSqlface(num, arg);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql1);
		log.send("01152", "list", list);
		if (arg[2].equals("0")) {
			inOutUtil.return_listpage(list, pages, "/uiface1/boss/subject.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 * arg[0] A-boss-search arg[1] pay_table_search arg[2] startTime arg[3] page
	 * arg[4] endTime arg[5] vip 元宝充值 arg[6] tojsp tojson arg[7] arg[8]
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void pay_table_search(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql1 = sqlmface.searchSqlface(-1, arg);
		log.send(DataType.basicType, "01170", "pay_table_search-sql1: ", sql1);
		int total = sqlUtil.get_int(sql1);
		log.send(DataType.basicType, "01170", "pay_table_search-total: ", total);
		pages = hm.pages(arg[3], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";
		String sql2 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01170", "pay_table_search-sql2: ", sql2);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql2);
		log.send(DataType.basicType, "01170", "pay_table_search-sql2: ", list);
		if ("tojsp".equals(arg[7])) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/pay_table.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	private void income_table_search(String[] arg) throws SQLException,
			IOException, ServletException {

		if (arg[6].equals("")) {
			ArrayList<Map<String, Object>> result = new ArrayList<>();
			if(arg[2].equals("")){
				arg[2] = "1";
				Map<String, Object> map = new HashMap<>();
				map.put("pagesign", "1");
				result.add(map);
			}
			String sql = sqlmface.searchSqlface(0, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(1, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			list = sqlUtil.get_list(sql);
			sql = sqlmface.searchSqlface(2, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			String sum = sqlUtil.get_string(sql);
			log.send(DataType.basicType, "01162", "提现明细---总和", sum);
			if (list.size() > 0) {
				list.get(0).put("sum", sum);
			}
			if (arg[5].equals("tojsp")) {
				inOutUtil.return_listpage(list, pages,result,
						"/uiface1/boss/income_table.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		} else {
			String sql = sqlmface.searchSqlface(3, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(4, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			list = sqlUtil.get_list(sql);
			sql = sqlmface.searchSqlface(5, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			String sum = sqlUtil.get_string(sql);
			log.send(DataType.basicType, "01162", "提现明细---总和", sum);
			if (list.size() > 0) {
				list.get(0).put("sum", sum);
			}
			if (arg[5].equals("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/income_table.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		}
	}

	private void income_table_search1(String[] arg) throws SQLException,
			IOException, ServletException {

		if (arg[6].equals("")) {
			ArrayList<Map<String, Object>> result = new ArrayList<>();
			if(arg[2].equals("")){
				arg[2] = "1";
				Map<String, Object> map = new HashMap<>();
				map.put("pagesign", "1");
				result.add(map);
			}
			String sql = sqlmface.searchSqlface(0, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(1, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			list = sqlUtil.get_list(sql);
			sql = sqlmface.searchSqlface(2, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			String sum = sqlUtil.get_string(sql);
			log.send(DataType.basicType, "01162", "提现明细---总和", sum);
			if (list.size() > 0) {
				list.get(0).put("sum", sum);
			}

			if (arg[5].equals("tojsp")) {
				inOutUtil.return_listpage(list, pages,result,
						"/uiface1/boss/income_table1.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		} else {
			String sql = sqlmface.searchSqlface(3, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(4, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			list = sqlUtil.get_list(sql);
			sql = sqlmface.searchSqlface(5, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			String sum = sqlUtil.get_string(sql);
			log.send(DataType.basicType, "01162", "提现明细---总和", sum);
			if (list.size() > 0) {
				list.get(0).put("sum", sum);
			}
			if (arg[5].equals("tojsp")) {
				inOutUtil.return_listpage(list, pages,
						"/uiface1/boss/income_table1.jsp");
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		}
	}

	/**
	 * arg[0] A-boss-search arg[1] gift_details_search arg[2] page arg[3]
	 * startdate arg[4] enddate arg[5] tojsp tojson arg[6] arg[7]
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void gift_details_search(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(-1, arg);
		int total = sqlUtil.get_int(sql);

		pages = hm.pages(arg[2], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";

		String sql2 = sqlmface.searchSqlface(1, arg);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql2);

		if ("tojsp".equals(arg[5])) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/gift_details.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 * arg[0] A-boss-search arg[1] Wealth_value_search arg[2] user_id arg[3]
	 * page arg[4] startdate arg[5] enddate arg[6] tojsp tojson arg[7] arg[8]
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	// 财富值查询
	private void Wealth_value_search(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(-1, arg);
		log.send(DataType.basicType, "01170", "Wealth_value_search-sql: ", sql);
		int total = sqlUtil.get_int(sql);
		log.send(DataType.basicType, "01170", "Wealth_value_search-total: ",
				total);

		pages = hm.pages(arg[3], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";

		String sql1 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01170", "Wealth_value_search-sql1: ",
				sql1);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql1);
		log.send(DataType.basicType, "01170", "Wealth_value_search-list: ",
				list);
		if ("tojsp".equals(arg[6])) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/Wealth_value.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 * arg[0]A-boss-search arg[1]charm_search arg[2]user_id arg[3]page
	 * arg[4]startdate arg[5]enddate arg[6]tojsp tojson arg[7] arg[8]
	 *
	 * @param arg
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	public void charm_search(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(-1, arg);
		log.send(DataType.basicType, "01170", "charm_search-sql: ", sql);
		int total = sqlUtil.get_int(sql);
		log.send(DataType.basicType, "01170", "charm_search-total: ", total);

		pages = hm.pages(arg[3], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";

		String sql1 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01170", "charm_search-sql1: ", sql1);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql1);
		log.send(DataType.basicType, "01170", "charm_search-list: ", list);
		if ("tojsp".equals(arg[6])) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/charm_search.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	// 魅力值查询
	private void usercp_search(String[] arg) throws SQLException, IOException,
			ServletException {
		// 记录条数查询
		String sql = sqlmface.searchSqlface(-1, arg);
		// 返回记录条数
		int total = sqlUtil.get_int(sql);
		// 返回本页第一条记录数是第几条记录

		pages = hm.pages(arg[3], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";

		// 返回本页第一条到最后一条数据
		String sqlList = sqlmface.searchSqlface(num, arg);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql);
		// 如果页数为0跳转到该页面
		if (arg[3].equals("0")) {
			inOutUtil.return_listpage(list, pages, "/uiface1/boss/usercp.jsp");
		} else {
			// 返回ajax请求
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 * arg[0]A-boss-search arg[1]grade_privilege
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	// 爵位查询
	private void grade_privilege(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.noType, "01170", "grade_privilege-sql", sql);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql);
		log.send(DataType.noType, "01170", "grade_privilege-list", list);
		inOutUtil.return_list(list, "boss/grade_privilege.jsp");
	}

	/**
	 * arg[0]A-boss-search arg[1]cash_withdrawal arg[2]page arg[3]startdate
	 * arg[4]enddate arg[5]tojsp tojson arg[6] arg[7]
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void cash_withdrawal(String[] arg) throws SQLException,
			IOException, ServletException {
		/*
		 * String sql = sqlmface.searchSqlface(-1, arg);
		 * log.send(DataType.noType, "01170", "search_reserve ", sql); int total
		 * = sqlUtil.get_int(sql); log.send(DataType.noType, "01170",
		 * "search_reserve ", total); pages = hm.pages(arg[2], total); arg =
		 * Arrays.copyOfRange(arg, 0, arg.length + 2); arg[arg.length - 2] =
		 * pages[2] + ""; arg[arg.length - 1] = JyHelpManager.item + ""; String
		 * sql_List = sqlmface.searchSqlface(1, arg); ArrayList<Map<String,
		 * Object>> list = sqlUtil.get_list(sql_List); if
		 * (arg[5].equals("tojsp")) { inOutUtil.return_listpage(list, pages,
		 * "/uiface1/boss/cash_withdrawal.jsp"); } else { json =
		 * JsonUtil.listPageToJson(list, pages); inOutUtil.return_ajax(json); }
		 */
		if (arg[6].equals("")) {
			String sql = sqlmface.searchSqlface(0, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(1, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			list = sqlUtil.get_list(sql);
			 sql = sqlmface.searchSqlface(2, arg);
			 log.send(DataType.basicType, "01162", "提现明细", sql);
			 String sum = sqlUtil.get_string(sql);
			 log.send(DataType.basicType, "01162", "提现明细---总和", sum);
			if(list!=null && list.size()>0){
				list.get(0).put("sum", sum);
			}
			if (arg[5].equals("tojsp")) {
				if (arg[7].equals("0")) {
					inOutUtil.return_listpage(list, pages,
							"/uiface1/boss/cash_withdrawal.jsp");
				} else {
					inOutUtil.return_listpage(list, pages,
							"/uiface1/boss/cash_withdrawal1.jsp");
				}
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		} else {
			String sql = sqlmface.searchSqlface(3, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			int total = sqlUtil.get_int(sql);
			pages = hm.pages(arg[2], total);
			arg[2] = pages[2] + "";
			sql = sqlmface.searchSqlface(4, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			list = sqlUtil.get_list(sql);
			 sql = sqlmface.searchSqlface(5, arg);
			log.send(DataType.basicType, "01162", "提现明细", sql);
			 String sum = sqlUtil.get_string(sql);
			 log.send(DataType.basicType, "01162", "提现明细---总和", sum);
			 list.get(0).put("sum", sum);
			if (arg[5].equals("tojsp")) {
				if (arg[7].equals("0")) {
					inOutUtil.return_listpage(list, pages,
							"/uiface1/boss/cash_withdrawal.jsp");
				} else {
					inOutUtil.return_listpage(list, pages,
							"/uiface1/boss/cash_withdrawal1.jsp");
				}
			} else {
				inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
			}
		}
	}

	/**
	 * arg[0] A-boss-search arg[1] pay_list arg[2] pageIndex arg[3] user_id
	 * arg[4] tojson arg[5] arg[6]
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void pay_list(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01168", "pay_list-sql1: ", sql1);
		int total = sqlUtil.get_int(sql1);
		log.send(DataType.basicType, "01168", "pay_list-total: ", total);
		pages = hm.pages(arg[2], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";

		String sql2 = sqlmface.searchSqlface(2, arg);
		log.send(DataType.basicType, "01168", "pay_list-sql2: ", sql2);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql2);
		log.send(DataType.basicType, "01168", "pay_list-sql2: ", list);

		if ("tojsp".equals(arg[4])) {
			inOutUtil
					.return_listpage(list, pages, "/uiface1/boss/pay_list.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	public void memberbackstage(String[] arg) throws SQLException, IOException,
			ServletException {
		/*
		 * if (arg[7].equals("")) { String sql1 = sqlmface.searchSqlface(0,
		 * arg); log.send(DataType.basicType, "01158", "求总条数SQL", sql1); int
		 * total = sqlUtil.get_int(sql1); log.send(DataType.basicType, "01158",
		 * "总条数Int", total); pages = JyHelpManager.pages(arg[2], total); arg =
		 * Arrays.copyOfRange(arg, 0, arg.length + 2); arg[arg.length - 2] =
		 * pages[2] + ""; arg[arg.length - 1] = JyHelpManager.item + ""; String
		 * sql2 = sqlmface.searchSqlface(1, arg); log.send(DataType.basicType,
		 * "01178", "获取到会员列表SQL", sql2); ArrayList<Map<String, Object>> list =
		 * sqlUtil.get_list(sql2); // log.send(DataType.basicType, "01178",
		 * "获取到会员列表", list); if ("tojsp".equals(arg[6])) {
		 * inOutUtil.return_listpage(list, pages,
		 * "/uiface1/boss/tcqo_meberbackstage01178.jsp"); } else { json =
		 * JsonUtil.listPageToJson(list, pages); inOutUtil.return_ajax(json); }
		 * } else if (!arg[7].equals("")) { int count = 1; String sqlm =
		 * sqlmface.searchSqlface(3, arg); log.send(DataType.basicType, "01150",
		 * "查询魅聊号（）sqlm:", sqlm); list = sqlUtil.get_list(sqlm);
		 * log.send(DataType.basicType, "01150", "查询魅聊号（）list:", list); pages =
		 * JyHelpManager.pages(arg[2], count); if (list.size() != 0) { json =
		 * JsonUtil.listPageToJson(list, pages); inOutUtil.return_ajax(json); }
		 * }
		 */

		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01178", "查询会员列表", sql);
		int total = sqlUtil.get_countint(sql);
		pages = hm.pages(arg[4], total);
		arg[4] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01178", "查询会员列表", sql);
		list = sqlUtil.get_list(sql);
		if (arg[5].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/tcqo_meberbackstage01178.jsp");
		} else if (arg[5].equals("tojson")) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}

	}

	public void anchorexecl(String[] arg) throws SQLException, IOException,
			ServletException {
		List<List<String>> listForExport = new ArrayList<List<String>>();
		String sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		List<String> listOne = new ArrayList<String>();
		listOne.add("ID");
		listOne.add("用户名");
		listOne.add("昵称");
		listOne.add("邀请人ID");
		listOne.add("推荐分类");
		listOne.add("手机号码");
		listOne.add("账户余额");
		listOne.add("是否在线");
		listOne.add("排序参数");
		listOne.add("注册时间");
		listOne.add("账号状态");
		listForExport.add(listOne);
		for (Map map : list) {
			List<String> arrayList = new ArrayList<String>();
			arrayList.add(map.get("id").toString());
			arrayList.add(map.get("username").toString());
			arrayList.add(map.get("nickname").toString());
			arrayList.add(map.get("promoter_id").toString());
			if(StringUtils.isNotEmpty(map.get("star").toString())){
				if(map.get("star").toString().equals("1")){
					arrayList.add("热门");
				}else if(map.get("star").toString().equals("5")){
					arrayList.add("活跃");
				}else{
					arrayList.add((map.get("star").toString()+"星"));
				}
			}
			arrayList.add(map.get("phonenum").toString());
			arrayList.add(map.get("money").toString());
			if(StringUtils.isNotEmpty(map.get("online").toString())){
				if(map.get("online").toString().equals("0")){
					arrayList.add("离线");
				}else if(map.get("online").toString().equals("1")){
					arrayList.add("在线");
				}else if(map.get("online").toString().equals("2")){
					arrayList.add("忙碌");
				}else{
					arrayList.add("");
				}
			}
			arrayList.add(map.get("sort_id").toString());
			arrayList.add(map.get("register_time").toString());
			if(StringUtils.isNotEmpty(map.get("is_banned").toString())){
				if(map.get("is_banned").toString().equals("1")){
					arrayList.add("封禁");
				}else{
					arrayList.add("正常");
				}
			}else{
				arrayList.add("正常");
			}
			listForExport.add(arrayList);
		}
		response.reset();
		excelUtils.export("主播列表"+getDateString(),listForExport,response);
	}

	public void anchorsrexecl(String[] arg) throws SQLException, IOException,
			ServletException {
		List<List<String>> listForExport = new ArrayList<List<String>>();
		String sql = sqlmface.searchSqlface(1, arg);
		String sum = sqlUtil.get_string(sql);
		List<String> listOne1 = new ArrayList<String>();
		listOne1.add("总收入:");
		listOne1.add(sum);
		listForExport.add(listOne1);
		sql = sqlmface.searchSqlface(0, arg);
		list = sqlUtil.get_list(sql);
		List<String> listOne = new ArrayList<String>();
		listOne.add("ID");
		listOne.add("主播昵称");
		listOne.add("收入来源");
		listOne.add("邀请人ID");
		listOne.add("收入价格");
		listOne.add("结算状态");
		listOne.add("结算时间");
		listForExport.add(listOne);
		for (Map map : list) {
			List<String> arrayList = new ArrayList<String>();
			arrayList.add(map.get("user_id").toString());
			arrayList.add(map.get("nickname").toString());
			arrayList.add(map.get("type").toString());
			arrayList.add(map.get("promoter_id").toString());
			arrayList.add(map.get("money").toString());
			arrayList.add("已结算");
			arrayList.add(map.get("time").toString());
			listForExport.add(arrayList);
		}
		response.reset();
		excelUtils.export("主播收入明细"+getDateString(),listForExport,response);
	}

	public void userDateExcel(String[] arg) throws SQLException, IOException,
			ServletException {
		List<List<String>> listForExport = new ArrayList<List<String>>();
		String sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		List<String> listOne = new ArrayList<String>();
		listOne.add("ID");
		listOne.add("用户名");
		listOne.add("昵称");
		listOne.add("邀请人ID");
		listOne.add("性别");
		listOne.add("手机号码");
		listOne.add("账户余额");
		listOne.add("注册时间");
		listOne.add("是否在线");
		listForExport.add(listOne);
		for (Map map : list) {
			List<String> arrayList = new ArrayList<String>();
			arrayList.add(map.get("id").toString());
			arrayList.add(map.get("username").toString());
			arrayList.add(map.get("nickname").toString());
			arrayList.add(map.get("promoter_id").toString());
			arrayList.add(map.get("gender").toString());
			arrayList.add(map.get("phonenum").toString());
			arrayList.add(map.get("money").toString());
			arrayList.add(map.get("register_time").toString());
			if(map.get("online").toString().equals("0")){
				arrayList.add("离线");
			}else if(map.get("online").toString().equals("1")){
				arrayList.add("在线");
			}else{
				arrayList.add("在聊");
			}
			listForExport.add(arrayList);
		}
		response.reset();
		excelUtils.export("会员列表"+getDateString(),listForExport,response);
	}

	//充值记录导出
	public void payexecl(String[] arg) throws SQLException, IOException,
			ServletException {
		List<List<String>> listForExport = new ArrayList<List<String>>();
		String sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01162", "昵称修改",  sql);
		list = sqlUtil.get_list(sql);
		List<String> listOne = new ArrayList<String>();
		listOne.add("ID");
		listOne.add("昵称");
		listOne.add("付费方式");
		listOne.add("付费价格");
		listOne.add("A币数量");
		listOne.add("付费类型");
		listOne.add("支付状态");
		listOne.add("付费时间");
		listForExport.add(listOne);
		for (Map map : list) {
			List<String> arrayList = new ArrayList<String>();
			arrayList.add(map.get("user_id").toString());
			arrayList.add(map.get("nickname").toString());
			arrayList.add(map.get("pay_type").toString());
			arrayList.add(map.get("pay_price").toString()+"元");
			arrayList.add(map.get("pay_value").toString()+"A币");
			arrayList.add(map.get("pay_what").toString());
			arrayList.add(map.get("pay_status").toString());
			arrayList.add(map.get("pay_time").toString());
			listForExport.add(arrayList);
		}
		response.reset();
		excelUtils.export("充值记录表"+getDateString(),listForExport,response);
	}

	public void tgsrexecl(String[] arg) throws SQLException, IOException,
			ServletException {
		List<List<String>> listForExport = new ArrayList<List<String>>();
		String sql = sqlmface.searchSqlface(0, arg);
		String sum = sqlUtil.get_string(sql);
		List<String> listOne1 = new ArrayList<String>();
		listOne1.add("总收入:");
		listOne1.add(sum);
		listForExport.add(listOne1);
		sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01162", "昵称修改",  sql);
		list = sqlUtil.get_list(sql);
		List<String> listOne = new ArrayList<String>();
		listOne.add("ID");
		listOne.add("主播昵称");
		listOne.add("收入来源");
		listOne.add("邀请人ID");
		listOne.add("收入价格");
		listOne.add("结算状态");
		listOne.add("结算时间");
		listForExport.add(listOne);
		for (Map map : list) {
			List<String> arrayList = new ArrayList<String>();
			arrayList.add(map.get("upuser_id").toString());
			arrayList.add(map.get("nickname").toString());
			if(map.get("money_type").toString().equals("充值")){
				arrayList.add("用户充值");
			}else if(map.get("money_type").toString().equals("提现")){
				arrayList.add("主播提现");
			}else{
				arrayList.add("");
			}
			arrayList.add(map.get("promoter_id").toString());
			arrayList.add(map.get("able_money").toString()+"元");
			arrayList.add("已结算");
			arrayList.add(map.get("uptime").toString());
			listForExport.add(arrayList);
		}
		response.reset();
		excelUtils.export("推广收入明细"+getDateString(),listForExport,response);
	}
	public void anchortxexecl(String[] arg) throws SQLException, IOException,
			ServletException {
		List<List<String>> listForExport = new ArrayList<List<String>>();
		String sql = sqlmface.searchSqlface(0, arg);
		String sum = sqlUtil.get_string(sql);
		List<String> listOne1 = new ArrayList<String>();
		listOne1.add("总收入:");
		listOne1.add(sum);
		listForExport.add(listOne1);
		sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01162", "昵称修改",  sql);
		list = sqlUtil.get_list(sql);
		List<String> listOne = new ArrayList<String>();
		listOne.add("ID");
		listOne.add("用户昵称");
		listOne.add("提现时间");
		listOne.add("提现金额");
		listOne.add("提现账号");
		listOne.add("提现名称");
		listOne.add("提现状态");
		listOne.add("提现操作状态");
		listForExport.add(listOne);
		for (Map map : list) {
			List<String> arrayList = new ArrayList<String>();
			arrayList.add(map.get("user_id").toString());
			arrayList.add(map.get("nickname").toString());
			arrayList.add(map.get("time").toString());
			arrayList.add(map.get("cash").toString()+"元");
			arrayList.add(map.get("tixian_account").toString());
			arrayList.add(map.get("account_name").toString());
			arrayList.add(map.get("status").toString());
			arrayList.add(map.get("msg").toString());
			listForExport.add(arrayList);
		}
		response.reset();
		excelUtils.export("主播提现明细"+getDateString(),listForExport,response);
	}

	public void tgtxexecl(String[] arg) throws SQLException, IOException,
			ServletException {
		List<List<String>> listForExport = new ArrayList<List<String>>();
		String sql = sqlmface.searchSqlface(0, arg);
		String sum = sqlUtil.get_string(sql);
		List<String> listOne1 = new ArrayList<String>();
		listOne1.add("总收入:");
		listOne1.add(sum);
		listForExport.add(listOne1);
		sql = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01162", "昵称修改",  sql);
		list = sqlUtil.get_list(sql);
		List<String> listOne = new ArrayList<String>();
		listOne.add("ID");
		listOne.add("用户昵称");
		listOne.add("提现时间");
		listOne.add("提现金额");
		listOne.add("提现账号");
		listOne.add("提现名称");
		listOne.add("提现状态");
		listOne.add("提现操作状态");
		listForExport.add(listOne);
		for (Map map : list) {
			List<String> arrayList = new ArrayList<String>();
			arrayList.add(map.get("user_id").toString());
			arrayList.add(map.get("nickname").toString());
			arrayList.add(map.get("time").toString());
			arrayList.add(map.get("cash").toString()+"元");
			arrayList.add(map.get("tixian_account").toString());
			arrayList.add(map.get("account_name").toString());
			arrayList.add(map.get("status").toString());
			arrayList.add(map.get("msg").toString());
			listForExport.add(arrayList);
		}
		response.reset();
		excelUtils.export("推广提现明细"+getDateString(),listForExport,response);
	}


	/**
	 * arg[0]A-boss-search arg[1]giftlist
	 *
	 * @param arg
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void tcqo_gift_list(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01178", "读gift_table的礼物数据SQL", sql);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql);
		log.send(DataType.basicType, "01178", "读gift_table的礼物数据", list);
		inOutUtil.return_list(list, "/uiface1/boss/tcqo_gift_list01178.jsp");
	}

	/**
	 * arg[0]A-boss-search arg[1]tyrants_week_search arg[2]0 本周 1 上周 arg[3]tojsp
	 * tojson
	 *
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	// 财富榜
	public void tyrants_week_search(String[] arg) throws SQLException,
			IOException, ServletException {
		String sql = sqlmface.searchSqlface(Integer.parseInt(arg[2]), arg);
		log.send(DataType.basicType, "01158", "tyrants_week_search()-sql", sql);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql);
		// log.send(DataType.basicType, "01158",
		// "tyrants_week_search()-list",list);
		if ("tojsp".equals(arg[3])) {
			inOutUtil.return_list(list,
					"/uiface1/boss/tyrants_week_search_01158.jsp");
		} else {
			json = JsonUtil.listToJson(list);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 * arg[0] A-boss-search arg[1] charm_week_search arg[2] 0本周 1上周 arg[3] tojsp
	 * tojson
	 *
	 * @param arg
	 * @throws IOException
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws ServletException
	 */
	// 魅力榜
	public void charm_week_search(String[] arg) throws NumberFormatException,
			SQLException, IOException, ServletException {
		String sql = sqlmface.searchSqlface(Integer.parseInt(arg[2]), arg);
		log.send(DataType.basicType, "01158", "tyrants_week_search()-sql", sql);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql);
		if ("tojsp".equals(arg[3])) {
			inOutUtil.return_list(list,
					"/uiface1/boss/charm_week_search_01158.jsp");
		} else {
			json = JsonUtil.listToJson(list);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 * 查询某userid收入明细列表 arg[0]: A-boss-search arg[1]: auditapply arg[2]:
	 * pageIndex 页码(jsp页面翻页的页码) arg[3]: startdate: 第一个时间 arg[4]: enddate 第二个时间
	 * arg[5]: sele_condition 搜索条件 atg[6]: tojson 返回jsp tojson: 返回json数据
	 *
	 * @param arg
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	public void auditapply(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01178", "求总条数SQL", sql1);
		int total = sqlUtil.get_int(sql1);
		log.send(DataType.basicType, "01178", "求总条数Int", total);

		pages = JyHelpManager.pages(arg[2], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";
		String sql2 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01178", "获取到audit_apply表的SQL", sql2);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql2);
		// log.send(DataType.basicType, "01178", "获取到audit_apply表", list);

		if ("tojsp".equals(arg[6])) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/tcqo_audit_apply01178.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 * 查询cash_withdrawal表提现记录，带翻页，不带搜索 arg[0]: A-boss-search arg[1]:
	 * cashwithdrawal arg[2]: pageIndex 页码(jsp页面翻页的页码) arg[3]: user_id atg[4]:
	 * tojsp tojson
	 *
	 * @param arg
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	public void cashwithdrawal(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01178", "求总条数SQL", sql1);
		int total = sqlUtil.get_int(sql1);
		log.send(DataType.basicType, "01178", "求总条数Int", total);

		pages = JyHelpManager.pages(arg[2], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";

		String sql2 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01178", "获取到cash_withdrawal表的SQL", sql2);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql2);
		// log.send(DataType.basicType, "01178", "获取到cash_withdrawal表的集合",
		// list);

		if ("tojsp".equals(arg[4])) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/tcqo_cashwithdrawal01178.jsp");
		} else {
			json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}
	}

	/**
	 *
	 * 钻石支出明细 arg[0]A-boss-search arg[1]diamond_pay_detail arg[2]page arg[3]用户id
	 */
	private void diamond_pay_detail(String[] arg) throws SQLException,
			ServletException, IOException {
		String sql1 = sqlmface.searchSqlface(1, arg);
		log.send("01150", "diamond_pay_detail_sql1", sql1);
		int total = sqlUtil.get_int(sql1);
		pages = hm.pages(arg[2], total);
		num = pages[2];
		String sql2 = sqlmface.searchSqlface(num, arg);
		list = sqlUtil.get_list(sql2);
		log.send("01150", "diamond_pay_detail_sql2", sql2);
		if (arg[2].equals("0")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/diamond_pay_detail.jsp");
		} else {
			String json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}

	}

	/**
	 *
	 * 提现明细 arg[0]A-boss-search arg[1]withdrawal_detail arg[2]page arg[3]用户id
	 */
	private void withdrawal_detail(String[] arg) throws SQLException,
			ServletException, IOException {
		String sql1 = sqlmface.searchSqlface(1, arg);
		log.send("01150", "withdrawal_detail_sql1", sql1);
		int total = sqlUtil.get_int(sql1);
		pages = hm.pages(arg[2], total);
		num = pages[2];
		String sql2 = sqlmface.searchSqlface(num, arg);
		list = sqlUtil.get_list(sql2);
		// log.send("01150", "withdrawal_detail_sql2", list);
		if (arg[2].equals("0")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/withdrawal_detail.jsp");
		} else {
			String json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}

	}

	/**
	 * 付费明细 arg[0]A-boss-search arg[1]pay_detail arg[2]page arg[3]用户id
	 *
	 */
	private void pay_detail(String[] arg) throws SQLException,
			ServletException, IOException {
		String sql1 = sqlmface.searchSqlface(1, arg);
		log.send("01150", "pay_detail_sql1", sql1);
		int total = sqlUtil.get_int(sql1);
		pages = hm.pages(arg[2], total);
		num = pages[2];
		String sql2 = sqlmface.searchSqlface(num, arg);
		log.send("01150", "pay_detail_sql2", sql2);
		list = sqlUtil.get_list(sql2);
		log.send("01150", "pay_detail_list", list);
		if (arg[2].equals("0")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/pay_detail.jsp");
		} else {
			String json = JsonUtil.listPageToJson(list, pages);
			inOutUtil.return_ajax(json);
		}

	}

	private void mod_anchor(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01160", "member_video()-sql1: ", sql1);
		list = sqlUtil.get_list(sql1);
		log.send(DataType.basicType, "01160", "member_video()-list: ", list);
		inOutUtil.return_list(list, "/uiface1/boss/integral_set.jsp");
	}

	private void anchor_persent(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01160", "member_video()-sql1: ", sql1);
		list = sqlUtil.get_list(sql1);
		log.send(DataType.basicType, "01160", "member_video()-list: ", list);
		inOutUtil.return_list(list, "/uiface1/boss/modify_persent.jsp");
	}

	private void recycle_photo(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int b = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], b);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		if (arg[3].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/recycle_photo.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	private void identity_check(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql = sqlmface.searchSqlface(0, arg);
		int totle = sqlUtil.get_int(sql);
		pages = hm.pages(arg[2], totle);
		arg[2] = pages[2] + "";
		sql = sqlmface.searchSqlface(1, arg);
		list = sqlUtil.get_list(sql);
		if (arg[3].equals("tojsp")) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/identity_check.jsp");
		} else {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	private void admin_login(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01156", "admin_login()-sql1: ", sql1);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql1);
		log.send(DataType.basicType, "01156", "admin_login()-list: ", list);
		String power = list.get(0).get("power") + "";
		String admin = list.get(0).get("username") + "";
		HttpSession session = request.getSession();
		session.setAttribute("admin", "success");
		session.setAttribute("username", admin);
		session.setAttribute("power", power);
		session.setMaxInactiveInterval(60 * 60 * 5);
		if (list.size() == 1) {
			inOutUtil.return_listpage(list, pages,
					"/uiface1/boss/admincenter.jsp");
		} else {
			// request.getSession().setAttribute("adminLoginStatus", "err");
			inOutUtil.return_only("boss/adminLogin.jsp");
		}
	}

	/**
	 * 显示所有会员的视频状态 arg[0]: A-boss-search arg[1]: all_video arg[2]: pageNum
	 * 页码(jsp页面翻页的页码) arg[3]: tojsp: 返回jsp tojson: 返回json数据 arg[4]: time1
	 * arg[5]: time2 -------------------------------------------- arg[6]:
	 * curIndex 页面首元素索引 arg[7]: items 一页显示的元素个数
	 *
	 * @param arg
	 *            未付款，未评价，已付款
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private void all_video(String[] arg) throws SQLException, IOException,
			ServletException {
		// 查询列表元素数目
		String sql1 = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01160", "all_video()-sql1: ", sql1);
		int total = sqlUtil.get_int(sql1);
		log.send(DataType.basicType, "01160", "all_video()-total: ", total);
		// 追加参数
		pages = hm.pages(arg[2], total);
		arg = Arrays.copyOfRange(arg, 0, arg.length + 2);
		arg[arg.length - 2] = pages[2] + "";
		arg[arg.length - 1] = JyHelpManager.item + "";
		String sql2 = sqlmface.searchSqlface(1, arg);
		log.send(DataType.basicType, "01160", "all_video()-sql2: ", sql2);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql2);
		log.send(DataType.basicType, "01160", "all_video()-list: ", list);
		if ("tojsp".equals(arg[3])) {
			inOutUtil.return_listpage(list, pages,
					"boss/all_video_list_01160.jsp");
		} else if ("tojson".equals(arg[3])) {
			inOutUtil.return_ajax(JsonUtil.listPageToJson(list, pages));
		}
	}

	/**
	 * 显示会员的视频 arg[0]:A-boss-search arg[1]:member_video arg[2]:id
	 *
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private void member_video(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01160", "member_video()-sql1: ", sql1);
		list = sqlUtil.get_list(sql1);
		log.send(DataType.basicType, "01160", "member_video()-list: ", list);
		inOutUtil
				.return_list(list, "/uiface1/boss/member_video_list_01160.jsp");
	}

	/**
	 * 显示会员的相册 arg[0]:A-boss-search arg[1]:member_album arg[2]:id
	 *
	 * @param arg
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private void member_album(String[] arg) throws SQLException, IOException,
			ServletException {
		String sql1 = sqlmface.searchSqlface(0, arg);
		log.send(DataType.basicType, "01160", "member_album()-sql1: ", sql1);
		ArrayList<Map<String, Object>> list = sqlUtil.get_list(sql1);
		log.send(DataType.basicType, "01160", "member_album()-list: ", list);
		inOutUtil
				.return_list(list, "/uiface1/boss/member_album_list_01160.jsp");
	}
	public String getDateString() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(date);
		return dateString;
	}
}