package com.hz.vliao2.mA;

import java.io.IOException;
import java.sql.SQLException;


import com.ssctrl.interface4.JyHelpManager;
import com.ssctrl.interface4.JyLogDetect.DataType;
import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

public class vliaoSqlBoss_01150 extends vliaoSqlManager implements vliaoSqlMFace {

	
	@Override
	public String searchSqlface(int current, String[] arg) throws SQLException {
		
		switch (arg[1]) {
		case "recharge_set":
			ressql = "select * from recharge_set";
			break;
		case "renzheng_v":
			if (current == 0) {
				ressql = "select count(*) from renzheng_list where status='待审核'";
			} else if (current == 1) {
				ressql = "select * from renzheng_list where status='待审核' order by id desc limit "
						+ arg[2] + "," + JyHelpManager.item + "";
			}
			break;
		case "renzheng_v_passed":
			if (current == 0) {
				ressql = "select count(*) from renzheng_list where status='已通过'";
			} else if (current == 1) {
				ressql = "select * from renzheng_list where status='已通过' order by id desc limit "
						+ arg[2] + "," + JyHelpManager.item + "";
			}
			break;				
		case "renzheng_v_no":
			if (current == 0) {
				ressql = "select count(*) from renzheng_list where status='未通过'";
			} else if (current == 1) {
				ressql = "select * from renzheng_list where status='未通过' order by id desc limit "
						+ arg[2] + "," + JyHelpManager.item + "";
			}
			break;				
		case "renzheng_photosearch":
			ressql = "select * from renzheng_list where user_id='" + arg[2] + "'";
			break;
		case "renzheng_photosearch1":
			ressql = "select * from user_data where id='" + arg[2] + "'";
			break;
		case "renzheng_photosearch_zz":
			ressql = "select * from renzheng_list where user_id='" + arg[2] + "'";
			break;
		case "photo_search":
			if (current == 0) {
				ressql = "select count(*) from photo_audit ";
			} else if (current == 1) {
				ressql = "select * from photo_audit  order by id desc limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			} else if (current == 2) {
				ressql = "select count(*) from photo_audit where audit ='"
						+ arg[3] + "'";
			} else if (current == 3) {
				ressql = "select * from photo_audit where audit='" + arg[3]
						+ "' order by id desc limit " + arg[2] + "," + JyHelpManager.item + "";
			}
			break;
		case "photo_search_one":

			ressql = "select * from photo_audit where id='" + arg[2] + "'";

			break;
		case "album_list":
			if (current == 0) {
				ressql = "select count(*) from modinfo_list ";
			} else if (current == 1) {
				ressql = "select * from modinfo_list  order by id desc limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			} else if (current == 2) {
				ressql = "select count(*) from modinfo_list where status ='"
						+ arg[3] + "'";
			} else if (current == 3) {
				ressql = "select * from modinfo_list where status='" + arg[3]
						+ "' order by id desc limit " + arg[2] + "," + JyHelpManager.item + "";
			}
			break;
		case "album_list_one":

			ressql = "select * from modinfo_list where id='" + arg[2] + "'";

			break;
		//查询系统通知列表
		case "boll_systemnews_search":
			if(current==0){
					ressql = "SELECT COUNT(*) FROM news_table ";	
			}else if(current==1){
					ressql = "SELECT * FROM news_table  limit "+arg[2]+","+JyHelpManager.item;
			}				
			break;
		
		case "video_manage":
			if(current == 1){
				ressql = "select count(*) from video_table ";
			}else if(current==2){
				ressql = "select * from video_table order by time desc limit "+arg[2]+","+JyHelpManager.item ;
			}else if(current==3){
				ressql = "select nickname from user_data where id = '"+arg[5]+"' ";
			}else if(current == 4){
				ressql = "select count(*) from video_table where status = '"+arg[3]+"' ";
			}else if(current==5){
				ressql = "select * from video_table where status = '"+arg[3]+"' order by time desc limit "+arg[2]+","+JyHelpManager.item ;
			}
			break;
		case "video_play":
			if(current==1){
				ressql = "select * from video_table where id = '"+arg[2]+"' ";
			}
			break;
		case "subject":
			if (current == 0) {
				ressql = "select count(*) from member_list";
			} else {
				ressql = "select * from member_list  limit " + current + ","
						+ JyHelpManager.item;
			}
			break;
		case "hot":
			if (current == 0) {
				ressql = "select count(*) from member_list where vip=true and gift_sum>'"
						+ arg[3] + "' and grade>1";
			} else {
				ressql = "select * from member_list where isvip='1' and gift_sum>'"
						+ arg[3]
						+ "' and grade>1 limit "
						+ current
						+ ","
						+ JyHelpManager.item;
			}
			break;
		case "admin_login":
			ressql = "select * from login where username='" + arg[3]
					+ "' and password='" + arg[4] + "'";
			break;
			
			
		case "income_table_search":
			if(arg[7].equals("0")){
				if(current==0){
					if(arg[3].equals("")&&arg[4].equals("")){
						ressql="select count(*) from income_details c,user_data u where c.user_id=u.id ";
					}else{
						ressql="select count(*) from income_details c,user_data u where c.user_id=u.id and c.time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
					}
				}else if(current==1){
					if(arg[3].equals("")&&arg[4].equals("")){
					   ressql="select * from income_details c,user_data u where c.user_id=u.id  order by c.time desc  limit "+arg[2]+","+JyHelpManager.item+" ";	
					}else{
						ressql="select * from income_details c,user_data u where c.user_id=u.id and c.time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'   order by c.time desc limit "+arg[2]+","+JyHelpManager.item+" ";
					}
				}else if(current==2){
					if(arg[3].equals("")&&arg[4].equals("")){
					   ressql="select sum(money) from income_details  ";	
					}else{
						ressql="select sum(money) from income_details where    time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
					}
				}else if(current==3){
					ressql="select count(*) from income_details c,user_data u where c.user_id=u.id  and c.time like '%"+arg[6]+"%' ";
				}else if(current==4){
					ressql="select * from income_details c,user_data u where c.user_id = u.id  and c.time like '%"+arg[6]+"%' order by c.time desc  limit "+arg[2]+","+JyHelpManager.item+"";
				}else if(current==5){
					ressql="select sum(money) from income_details where    time like '%"+arg[6]+"%'";
				}
			}else{
				if(current==0){
					if(arg[3].equals("")&&arg[4].equals("")){
						ressql="select count(*) from income_details c,user_data u where c.user_id=u.id  and c.user_id="+arg[7];
					}else{
						ressql="select count(*) from income_details c,user_data u where c.user_id=u.id  and c.user_id="+arg[7]+" and c.time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
					}
				}else if(current==1){
					if(arg[3].equals("")&&arg[4].equals("")){
					   ressql="select * from income_details c,user_data u where c.user_id=u.id and c.user_id="+arg[7]+" order by c.time desc  limit "+arg[2]+","+JyHelpManager.item+" ";	
					}else{
						ressql="select * from income_details c,user_data u where c.user_id=u.id and c.user_id="+arg[7]+"  and c.time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'  order by c.time desc limit "+arg[2]+","+JyHelpManager.item+" ";
					}
				}else if(current==2){
					if(arg[3].equals("")&&arg[4].equals("")){
					   ressql="select sum(money) from income_details where user_id="+arg[7]+" ";	
					}else{
						ressql="select sum(money) from income_details where   user_id="+arg[7]+" and  time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
					}
				}else if(current==3){
					ressql="select count(*) from income_details c,user_data u where c.user_id=u.id and c.user_id="+arg[7]+" and c.time like '%"+arg[6]+"%' ";
				}else if(current==4){
					ressql="select * from income_details c,user_data u where c.user_id = u.id and c.user_id="+arg[7]+" and c.time like '%"+arg[6]+"%' order by c.time desc  limit "+arg[2]+","+JyHelpManager.item+"";
				}else if(current==5){
					ressql="select sum(money) from income_details where   user_id="+arg[7]+" and  time like '%"+arg[6]+"%'";
				}
				
			}
			
			
			break;
		case "income_table_search1":
			
			if(arg[7].equals("0")){
				if(current==0){
					if(arg[3].equals("")&&arg[4].equals("")){
						ressql="select count(*) from tuiguang_detail c,user_data u where c.upuser_id=u.id ";
					}else{
						ressql="select count(*) from tuiguang_detail c,user_data u where c.upuser_id=u.id and c.uptime between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
					}
				}else if(current==1){
					if(arg[3].equals("")&&arg[4].equals("")){
					   ressql="select * from tuiguang_detail c,user_data u where c.upuser_id=u.id   order by c.uptime desc  limit "+arg[2]+","+JyHelpManager.item+" ";	
					}else{
						ressql="select * from tuiguang_detail c,user_data u where c.upuser_id=u.id and c.uptime between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'  order by c.uptime desc limit "+arg[2]+","+JyHelpManager.item+" ";
					}
				}else if(current==2){
					if(arg[3].equals("")&&arg[4].equals("")){
					   ressql="select sum(able_money) from tuiguang_detail  ";	
					}else{
						ressql="select sum(able_money) from tuiguang_detail where    uptime between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
					}
				}else if(current==3){
					ressql="select count(*) from tuiguang_detail c,user_data u where c.upuser_id=u.id  and c.uptime like '%"+arg[6]+"%' ";
				}else if(current==4){
					ressql="select * from tuiguang_detail c,user_data u where c.upuser_id = u.id  and c.uptime like '%"+arg[6]+"%' order by c.uptime desc  limit "+arg[2]+","+JyHelpManager.item+"";
				}else if(current==5){
					ressql="select sum(able_money) from tuiguang_detail where    uptime like '%"+arg[6]+"%'";
				}
			}else{
				if(current==0){
					if(arg[3].equals("")&&arg[4].equals("")){
						ressql="select count(*) from tuiguang_detail c,user_data u where c.upuser_id=u.id and c.upuser_id="+arg[7];
					}else{
						ressql="select count(*) from tuiguang_detail c,user_data u where c.upuser_id=u.id and c.upuser_id="+arg[7]+" and c.uptime between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
					}
				}else if(current==1){
					if(arg[3].equals("")&&arg[4].equals("")){
					   ressql="select * from tuiguang_detail c,user_data u where c.upuser_id=u.id and c.upuser_id="+arg[7]+"  order by c.uptime desc  limit "+arg[2]+","+JyHelpManager.item+" ";	
					}else{
						ressql="select * from tuiguang_detail c,user_data u where c.upuser_id=u.id and c.upuser_id="+arg[7]+"  and c.uptime between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'  order by c.uptime desc limit "+arg[2]+","+JyHelpManager.item+" ";
					}
				}else if(current==2){
					if(arg[3].equals("")&&arg[4].equals("")){
					   ressql="select sum(able_money) from tuiguang_detail where upuser_id="+arg[7];	
					}else{
						ressql="select sum(able_money) from tuiguang_detail where   upuser_id="+arg[7] +"  and uptime between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
					}
				}else if(current==3){
					ressql="select count(*) from tuiguang_detail c,user_data u where c.upuser_id=u.id and c.upuser_id="+arg[7]+" and c.uptime like '%"+arg[6]+"%' ";
				}else if(current==4){
					ressql="select * from tuiguang_detail c,user_data u where c.upuser_id = u.id and c.upuser_id="+arg[7]+" and c.uptime  like '%"+arg[6]+"%' order by c.uptime desc  limit "+arg[2]+","+JyHelpManager.item+"";
				}else if(current==5){
					ressql="select sum(able_money) from tuiguang_detail where  upuser_id="+arg[7]+" and   uptime like '%"+arg[6]+"%'";
				}
			}
			
			break;
			
		// 消费记录
		// p2开始时间 p4结束时间 p5 会员 p6积分
		case "pay_table_search":
			if (current == -1) {
				if (arg[2].equals("") && arg[4].equals("") && arg[5].equals("")) {// 查询所有总条数
					ressql = "select count(*) " + "from order_management o,user_data b where o.user_id=b.id ";
				} else if (!arg[2].equals("") && !arg[4].equals("")
						&& arg[5].equals("")) {
					ressql = "select count(*) " + "from order_management o,user_data b "
							+ "where o.pay_time between '" + arg[2]
							+ " 00:00:01' and '" + arg[4] + " 23:59:59' and o.user_id=b.id  ";
				} else if (arg[2].equals("") && arg[4].equals("")
						&& !arg[5].equals("")) {
					ressql = "select count(*) " + "from order_management o,user_data b "
							+ "where o.pay_status='" + arg[5] + "' and o.user_id=b.id ";
				} else if (!arg[2].equals("") && !arg[4].equals("")
						&& !arg[5].equals("")) {
					ressql = "select count(*) " + "from order_management o,user_data b "
							+ "where o.pay_time between '" + arg[2]
							+ " 00:00:01' and '" + arg[4]
							+ " 23:59:59' and o.pay_status='" + arg[5] + "' and o.user_id=b.id ";
				}
			} else if (current == 1) {// 查询所有集合
				if (arg[2].equals("") && arg[4].equals("") && arg[5].equals("")) {
					ressql = "select * " + "from order_management o,user_data b  where o.user_id=b.id "
							+ "order by o.pay_time desc  " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
				} else if (!arg[2].equals("") && !arg[4].equals("")
						&& arg[5].equals("")) {
					ressql = "select * " + "from order_management o,user_data b"
							+ "where o.pay_time between '" + arg[2]
							+ " 00:00:01' and '" + arg[4] + " 23:59:59' and o.user_id=b.id"
							+ " limit " + arg[arg.length - 2] + ","
							+ JyHelpManager.item;
				} else if (arg[2].equals("") && arg[4].equals("")
						&& !arg[5].equals("")) {
					ressql = "select * " + "from order_management o,user_data b "
							+ "where o.pay_status='" + arg[5] + "' and o.user_id=b.id " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
				} else if (!arg[2].equals("") && !arg[4].equals("")
						&& !arg[5].equals("")) {
					ressql = "select * " + "from order_management o,user_data b"
							+ "where o.pay_time between '" + arg[2]
							+ " 00:00:01' and '" + arg[4]
							+ " 23:59:59' and o.pay_status='" + arg[5] + "' and o.user_id=b.id "
							+ "limit " + arg[arg.length - 2] + ","
							+ JyHelpManager.item;
				}
			}
			break;

		// 礼物收入 arg[2]开始时间 arg[3] 结束时间
		/*
		 *  * arg[0] A-boss-search arg[1] gift_details_search arg[2] page arg[3]
		 * startdate arg[4] enddate arg[5] tojsp tojson arg[6] arg[7]
		 */
		case "gift_details_search":
			if (current == -1) {
				// 搜索这段时间记录
				if (arg[3] != "" && arg[4] != "") {
					ressql = "select count(*) from gift_details "
							+ "where time between '" + arg[3]
							+ " 00:00:01' and '" + arg[4] + " 23:59:59'";
					// 搜索全部记录
				} else if (arg[3] == "" && arg[4] == "") {
					ressql = "select count(*) from gift_details";

				}
			} else if (current == 1) {
				// 搜索这段时间记录
				if (arg[3] != "" && arg[4] != "") {
					ressql = "select * from gift_details "
							+ " where time between '" + arg[3]
							+ " 00:00:01' and '" + arg[4] + "23:59:59' "
							+ "order by time desc " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
					// 搜索全部记录
				} else if (arg[3] == "" && arg[4] == "") {
					ressql = "select * from gift_details "
							+ "order by time desc " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
				}
			}
			break;

		// 财富值查询 arg[2]开始时间 arg[4]结束时间
		case "Wealth_value_search":
			if (current == -1) {
				// 搜索这段时间记录
				if (arg[4] != "" && arg[5] != "") {
					ressql = "select count(*) " + "from order_management "
							+ "where user_id='" + arg[2]
							+ "' and pay_time between '" + arg[4]
							+ " 00:00:01' and '" + arg[5] + " 23:59:59' and pay_status = '已付款' ";
					// 搜索全部记录
				} else if (arg[4] == "" && arg[5] == "") {
					ressql = "select count(*) " + "from order_management "
							+ "where user_id='" + arg[2] + "' and pay_status = '已付款' ";
				}
			} else if (current == 1) {
				// 搜索这段时间记录
				if (arg[4] != "" && arg[5] != "") {
					ressql = "select * " + "from order_management "
							+ "where user_id='" + arg[2]
							+ "' and pay_time between '" + arg[4]
							+ " 00:00:01' and '" + arg[5] + "23:59:59' and pay_status = '已付款' "
							+ "order by pay_time desc " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
					// 搜索全部记录
				} else if (arg[4] == "" && arg[5] == "") {
					ressql = "select * " + "from order_management "
							+ "where user_id='" + arg[2] + "' and pay_status = '已付款' "
							+ "order by pay_time desc " + "limit " + arg[7] + ","
							+ JyHelpManager.item;
				}
			}
			break;

		case "grade_privilege":
			ressql = "select * from grade_privilege";
			// 提现查询 arg[2]开始时间 arg[4]结束时间
			break;

		case "charm_search":
			if (current == -1) {
				if (arg[4] != "" && arg[5] != "") {
					ressql = "select count(*) " + "from income_details "
							+ "where user_id='" + arg[2]
							+ "' and time between '" + arg[4]
							+ " 00:00:01' and '" + arg[5] + " 23:59:59'";
					// 搜索全部记录
				} else if (arg[4] == "" && arg[5] == "") {
					ressql = "select count(*) " + "from income_details "
							+ "where user_id='" + arg[2] + "'";
				}
			} else if (current == 1) {
				if (arg[4] != "" && arg[5] != "") {
					ressql = "select * " + "from income_details "
							+ "where user_id='" + arg[2]
							+ "' and time between '" + arg[4]
							+ " 00:00:01' and '" + arg[5] + "23:59:59' "
							+ "order by time desc " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
					// 搜索全部记录
				} else if (arg[4] == "" && arg[5] == "") {
					ressql = "select * " + "from income_details "
							+ "where user_id='" + arg[2] + "' "
							+ "order by time desc " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
				}
			}
			break;

		// 魅力值查询 arg[2]开始时间 arg[4]结束时间
		case "usercp_search":
			// 返回记录条数
			if (current == -1) {
				// 搜索这段时间记录
				if (arg[2] != "" && arg[4] != "") {
					ressql = "select count(*) from gift_details"
							+ " where user_id='" + arg[5]
							+ "' and time between '" + arg[2]
							+ " 00:00:01' and '" + arg[4] + " 23:59:59'";
					// 搜索全部记录
				} else if (arg[2] == "" && arg[4] == "") {
					ressql = "select count(*) from gift_details"
							+ " where user_id='" + arg[5] + "'";
				}
			}
			// 返回本页数据
			else {
				// 搜索这段时间记录
				if (arg[2] != "" && arg[4] != "") {
					ressql = "select * from gift_details" + " where user_id='"
							+ arg[5] + "' " + " and time between '" + arg[2]
							+ " 00:00:01' and '" + arg[4] + "23:59:59'"
							+ "order by time desc " + "limit " + current + ","
							+ JyHelpManager.item;
					// 搜索全部记录
				} else if (arg[2] == "" && arg[4] == "") {
					ressql = "select * from gift_details" + "where user_id='"
							+ arg[5] + "'" + "order by time desc " + "limit "
							+ current + "," + JyHelpManager.item;
				}
			}
			break;

		case "cash_withdrawal":
			
			if(current==0){
				if(arg[3].equals("")&&arg[4].equals("")){
					ressql="select count(*) from cash_withdrawl c,user_data u where c.user_id=u.id and c.c_type="+arg[7];
				}else{
					ressql="select count(*) from cash_withdrawl c,user_data u where c.user_id=u.id and c.time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59' and c.c_type="+arg[7];
				}
			}else if(current==1){
				if(arg[3].equals("")&&arg[4].equals("")){
				   ressql="select * from cash_withdrawl c,user_data u where c.user_id=u.id  and c.c_type="+arg[7]+" order by c.time desc  limit "+arg[2]+","+JyHelpManager.item+" ";	
				}else{
					ressql="select * from cash_withdrawl c,user_data u where c.user_id=u.id and c.time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'  and c.c_type="+arg[7]+" order by c.time desc limit "+arg[2]+","+JyHelpManager.item+" ";
				}
			}else if(current==2){
				if(arg[3].equals("")&&arg[4].equals("")){
				   ressql="select sum(cash) from cash_withdrawl  where c_type="+arg[7]+"";	
				}else{
					ressql="select sum(cash) from cash_withdrawl where    c_type="+arg[7]+" and  time between '"+arg[3]+" 00:00:01' and '"+arg[4]+" 23:59:59'";
				}
			}else if(current==3){
				ressql="select count(*) from cash_withdrawl c,user_data u where c.user_id=u.id  and c.c_type="+arg[7]+" and c.time like '%"+arg[6]+"%' ";
			}else if(current==4){
				ressql="select * from cash_withdrawl c,user_data u where c.user_id = u.id  and c.c_type="+arg[7]+" and c.time like '%"+arg[6]+"%' order by c.time desc  limit "+arg[2]+","+JyHelpManager.item+"";
			}else if(current==5){
				ressql="select sum(cash) from cash_withdrawl where  c_type="+arg[7]+" and time like '%"+arg[6]+"%'";
			}
			break;

		// 通过Id查询个人消费信息
		case "pay_list":
			if (current == 1) {
				ressql = "select count(*) " + "from order_management "
						+ "where user_id = " + arg[3];
			} else if (current == 2) {
				ressql = "select * " + "from order_management "
						+ "where user_id = " + arg[3] + " "
						+ "order by pay_time desc " + "limit "
						+ arg[arg.length - 2] + "," + JyHelpManager.item;
			}
			break;
		case "agentist":
			   if (current == 0) {
				   ressql = "select count(id) from user_data where is_agent=1";
			   }else{
				   ressql = "select * from user_data where is_agent=1 order by id desc limit "+arg[2]+","+JyHelpManager.item;
			   }
	  			break;	
		case "memberbackstage":
//			if(current == 0) {	//会员列表
//				if(arg[5].equals("") && arg[3].equals("") && arg[4].equals("")){
//					ressql = "select count(*) from user_data where is_v!='1' and is_anchor!='1'";
//				}else if(arg[3].equals("") && arg[4].equals("") && !arg[5].equals("")){
//					if("男".equals(arg[5]) || "女".equals(arg[5])){
//						ressql = "select count(*) from user_data where gender = '"+arg[5]+"' and is_v!='1' and is_anchor!='1'";
//					}else{
//						ressql = "select count(*) from user_data where vip = '"+arg[5]+"' and is_v!='1' and is_anchor!='1'";
//					}
//				}else if(!arg[3].equals("") && !arg[4].equals("") && arg[5].equals("")){
//					ressql = "select count(*) from user_data where register_time between '"+arg[3]+" 00:00:00' and '"+arg[4]+" 23:59:59' and is_v!='1' and is_anchor!='1' ";
//				}else if(!arg[3].equals("") && !arg[4].equals("") && !arg[5].equals("")){
//					if("男".equals(arg[5]) || "女".equals(arg[5])){
//						ressql = "select count(*) from user_data where gender = '"+arg[5]+"' and register_time between '"+arg[3]+" 00:00:00' and '"+arg[4]+" 23:59:59' and is_v!='1' and is_anchor!='1' ";
//					}else{
//						ressql = "select count(*) from user_data where vip = '"+arg[5]+"' and register_time between '"+arg[3]+" 00:00:00' and '"+arg[4]+" 23:59:59' and is_v!='1' and is_anchor!='1' ";
//					}
//				}
//			} else if(current == 1) {
//				if(arg[5].equals("") && arg[3].equals("") && arg[4].equals("")){
//					ressql = "select * from user_data where is_v!='1' and is_anchor!='1' order by id desc limit "+arg[arg.length-2]+","+JyHelpManager.item;
//				}else if(arg[3].equals("") && arg[4].equals("") && !arg[5].equals("")){
//					if("男".equals(arg[5]) || "女".equals(arg[5])){
//						ressql = "select * from user_data where gender = '"+arg[5]+"' and is_v!='1' and is_anchor!='1' order by id desc limit "+arg[arg.length-2]+","+JyHelpManager.item;
//					}else{
//						ressql = "select * from user_data where vip = '"+arg[5]+"' and is_v!='1' and is_anchor!='1' order by id desc limit "+arg[arg.length-2]+","+JyHelpManager.item;
//					}
//				}else if(!arg[3].equals("") && !arg[4].equals("") && arg[5].equals("")){
//					ressql = "select * from user_data where register_time between '"+arg[3]+" 00:00:00' and '"+arg[4]+" 00:00:00' and is_v!='1' and is_anchor!='1' order by id desc limit "+arg[arg.length-2]+","+JyHelpManager.item;
//				}else if(!arg[3].equals("") && !arg[4].equals("") && !arg[5].equals("")){
//					if("男".equals(arg[5]) || "女".equals(arg[5])){
//						ressql = "select * from user_data where gender = '"+arg[5]+"' and register_time between '"+arg[3]+" 00:00:00' and '"+arg[4]+" 23:59:59' and is_v!='1' and is_anchor!='1' order by id desc limit "+arg[arg.length-2]+","+JyHelpManager.item;
//					}else{
//						ressql = "select * from user_data where vip = '"+arg[5]+"' and register_time between '"+arg[3]+" 00:00:00' and '"+arg[4]+" 23:59:59' and is_v!='1' and is_anchor!='1' order by id desc limit "+arg[arg.length-2]+","+JyHelpManager.item;
//					}
//				}
//			}else if(current==3){
//				ressql = "select * from user_data where id = '"+arg[7]+"' ";
//			}else if(current==4){
//				ressql = "select count(*) from user_data where id = '"+arg[7]+"' ";
//			}
			
			if(current==0){
				if(arg[3].equals("查ID")){
				   ressql="select count(*) from user_data where is_v!='1' and is_anchor!='1' and id='"+arg[2]+"'";
				}else if(arg[3].equals("查昵称")){
				   ressql="select count(*) from user_data where is_v!='1' and is_anchor!='1' and nickname like '%"+arg[2]+"%'";
				}else if(arg[3].equals("查手机号")){
				   ressql="select count(*) from user_data where is_v!='1' and is_anchor!='1' and username='"+arg[2]+"'";
				}else if(arg[3].equals("")){
				   ressql="select count(*) from user_data where is_v!='1' and is_anchor!='1'";
				}
			}else if(current==1){
				if(arg[3].equals("查ID")){
					  ressql="select * from user_data where is_v!='1' and is_anchor!='1' and id='"+arg[2]+"' order by register_time desc limit "+arg[4]+","+JyHelpManager.item+"";
				}else if(arg[3].equals("查昵称")){
					 ressql="select * from user_data where is_v!='1' and is_anchor!='1' and nickname like '%"+arg[2]+"%' order by register_time desc limit "+arg[4]+","+JyHelpManager.item+"";
				}else if(arg[3].equals("查手机号")){
					 ressql="select * from user_data where is_v!='1' and is_anchor!='1' and username='"+arg[2]+"' order by register_time desc limit "+arg[4]+","+JyHelpManager.item+"";
				}else if(arg[3].equals("")){
					ressql="select * from user_data where is_v!='1' and is_anchor!='1' order by register_time desc limit "+arg[4]+","+JyHelpManager.item+"";
				}
			}
			break;
		case "giftlist":
			ressql = "select * from gift_table";
			break;

		case "tyrants_week_search":// 财富榜
			if (current == 0) {
				ressql = "select * " + "from user_data "
						+ "order by tyrants_thisweek desc " + "limit 0,20";
			} else {
				ressql = "select * " + "from user_data "
						+ "order by tyrants_today desc " + "limit 0,20";
			}

			break;
		case "charm_week_search":// 魅力榜
			if (current == 0) {
				ressql = "select * " + "from user_data "
						+ "order by charm_thisweek desc " + "limit 0,20";
			} else {
				ressql = "select * " + "from user_data "
						+ "order by charm_today desc " + "limit 0,20";
			}
			break;

		case "auditapply":
			if (current == 0) {
				if (arg[5].equals("") && arg[3].equals("") && arg[4].equals("")) {
					ressql = "select count(*) " + "from audit_apply ";
				} else if (!arg[5].equals("") && arg[3].equals("")
						&& arg[4].equals("")) {
					if ("自动回复".equals(arg[5]) || "打招呼".equals(arg[5])) {
						ressql = "select count(*) " + "from audit_apply "
								+ "where type = '" + arg[5] + "' ";
					} else if ("待审核".equals(arg[5]) || "未通过".equals(arg[5])
							|| "已通过".equals(arg[5])) {
						ressql = "select count(*) " + "from audit_apply "
								+ "where status = '" + arg[5] + "' ";
					}
				} else if (arg[5].equals("") && !arg[3].equals("")
						&& !arg[4].equals("")) {
					ressql = "select count(*) " + "from audit_apply "
							+ "where time between '" + arg[3]
							+ " 00:00:00' and '" + arg[4] + " 23:59:59'";
				} else if (!arg[5].equals("") && !arg[3].equals("")
						&& !arg[4].equals("")) {
					if ("自动回复".equals(arg[5]) || "打招呼".equals(arg[5])) {
						ressql = "select count(*) " + "from audit_apply "
								+ "where type = '" + arg[5]
								+ "' and time between '" + arg[3]
								+ " 00:00:00' and '" + arg[4] + " 23:59:59' ";
					} else if ("待审核".equals(arg[5]) || "未通过".equals(arg[5])
							|| "已通过".equals(arg[5])) {
						ressql = "select count(*) " + "from audit_apply "
								+ "where status = '" + arg[5]
								+ "' and time between '" + arg[3]
								+ " 00:00:00' and '" + arg[4] + " 23:59:59'";
					}
				}
			} else if (current == 1) {
				if (arg[5].equals("") && arg[3].equals("") && arg[4].equals("")) {
					ressql = "select * " + "from audit_apply "
							+ "order by time desc " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
				} else if (!arg[5].equals("") && arg[3].equals("")
						&& arg[4].equals("")) {
					if ("自动回复".equals(arg[5]) || "打招呼".equals(arg[5])) {
						ressql = "select * " + "from audit_apply "
								+ "where type = '" + arg[5] + "' "
								+ "order by time desc " + "limit "
								+ arg[arg.length - 2] + ","
								+ JyHelpManager.item;
					} else if ("待审核".equals(arg[5]) || "未通过".equals(arg[5])
							|| "已通过".equals(arg[5])) {
						ressql = "select * " + "from audit_apply "
								+ "where status = '" + arg[5] + "' "
								+ "order by time desc " + "limit "
								+ arg[arg.length - 2] + ","
								+ JyHelpManager.item;
					}
				} else if (arg[5].equals("") && !arg[3].equals("")
						&& !arg[4].equals("")) {
					ressql = "select * " + "from audit_apply "
							+ "where time between '" + arg[3]
							+ " 00:00:00' and '" + arg[4] + " 23:59:59' "
							+ "order by time desc " + "limit "
							+ arg[arg.length - 2] + "," + JyHelpManager.item;
				} else if (!arg[5].equals("") && !arg[3].equals("")
						&& !arg[4].equals("")) {
					if ("自动回复".equals(arg[5]) || "打招呼".equals(arg[5])) {
						ressql = "select * " + "from audit_apply "
								+ "where type = '" + arg[5]
								+ "' and time between '" + arg[3]
								+ " 00:00:00' and '" + arg[4] + " 23:59:59' "
								+ "order by time desc " + "limit "
								+ arg[arg.length - 2] + ","
								+ JyHelpManager.item;
					} else if ("待审核".equals(arg[5]) || "未通过".equals(arg[3])
							|| "已通过".equals(arg[4])) {
						ressql = "select count(*) " + "from audit_apply "
								+ "where status = '" + arg[5]
								+ "' and time between '" + arg[3]
								+ " 00:00:00' and '" + arg[4] + " 23:59:59' "
								+ "order by time desc " + "limit "
								+ arg[arg.length - 2] + ","
								+ JyHelpManager.item;
					}
				}
			}
			break;
		// 查询cash_withdrawal表提现记录，根据时间倒序排序，加上时分秒
		case "cashwithdrawal":
			if (current == 0) {
				ressql = "select count(*) "
						+ "from cash_withdrawl left join member_list on cash_withdrawl.user_id=member_list.id "
						+ "where cash_withdrawl.user_id = '" + arg[3] + "' ";
			} else if (current == 1) {
				ressql = "select * "
						+ "from cash_withdrawl left join member_list on cash_withdrawl.user_id=member_list.id "
						+ "where cash_withdrawl.user_id = '" + arg[3] + "' "
						+ "order by cash_withdrawl.time desc " + "limit "
						+ arg[arg.length - 2] + "," + JyHelpManager.item;
			}
			break;
		case "identity_check":
			if (current == 0) {
				ressql = "select count(*) from renzheng_list ";
			} else if (current == 1) {
				ressql = "select u.*,i.* from member_list u,renzheng_list i where u.id=i.user_id order by rz_time desc limit "
						+ arg[2] + "," + JyHelpManager.item + "";
			}
			break;
		case "dongtai_check":
			if (current == 0) {
				ressql = "select count(*) from say_art_table";
			} else if (current == 1) {
				ressql = "select * from say_art_table order by giveuptime desc limit "
						+ arg[2] + "," + JyHelpManager.item + "";
			}
			break;
		case "dongtai_photosearch":
			ressql = "select * from say_art_table where id='" + arg[2] + "'";
			break;
		case "album_search":
			if (current == 0) {
				ressql = "select count(*) from image_check ";
			} else if (current == 1) {
				ressql = "select * from image_check  order by id desc limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			} else if (current == 2) {
				ressql = "select count(*) from image_check where status='"
						+ arg[3] + "'";
			} else if (current == 3) {
				ressql = "select * from image_check where status='" + arg[3]
						+ "' order by id desc limit " + arg[2] + "," + JyHelpManager.item + "";
			}
			break;
		case "cash_set":
			ressql = "select * from cash_set";
			break;
		case "yuanfenqingqiu":
			if (current == 0) {
				ressql = "select count(*) from hnrequest ";
			} else if (current == 1) {
				ressql = "select * from hnrequest  limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			} else if (current == 2) {
				ressql = "select count(*) from hnrequest where state='"
						+ arg[3] + "'";
			} else if (current == 3) {
				ressql = "select * from hnrequest where state='" + arg[3]
						+ "'  limit " + arg[2] + "," + JyHelpManager.item + "";
			} else if (current == 4) {
				ressql = "select * from member_list where id='" + arg[5]
						+ "' or id='" + arg[6] + "'";
			}
			break;
		case "guanfangfenpei":
			if (current == 0) {
				ressql = "select count(*) from allocation";
			} else if (current == 1) {
				ressql = "select * from allocation order by time desc limit "
						+ arg[2] + "," + JyHelpManager.item + "";
			} else if (current == 2) {
				ressql = "select count(*) from allocation where state='"
						+ arg[3] + "'";
			} else if (current == 3) {
				ressql = "select * from allocation where state='" + arg[3]
						+ "' order by time desc limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			} else if (current == 4) {
				ressql = "select * from member_list where id='" + arg[5]
						+ "' or id='" + arg[6] + "'";
			}
			break;
		case "album_search_one":

			ressql = "select * from image_check where id='" + arg[2] + "'";

			break;
		case "mykin_search":
			if (current == 0) {
				ressql = "select count(*) from user_data where promoter_id!=''";
			} else if (current == 1) {
				ressql = "select * from user_data where promoter_id!='' limit "
						+ arg[2] + "," + JyHelpManager.item + "";
			} else if (current == 2) {
				ressql = "select * from user_data where promoter_id='"
						+ arg[5] + "' ";
			} else if (current == 3) {
				ressql = "select * from order_management where user_id='"
						+ arg[5]
						+ "' and pay_what='充值' and pay_status='已付款'";
			} else if (current == 4) {
				ressql = "select count(*) from user_data where " + arg[5];
			} else if (current == 5) {
				ressql = "select * from user_data where id='" + arg[7] + "'";
			} else if (current == 6) {
				ressql = "select count(*) from user_data where promoter_id='"
						+ arg[5] + "'";
			}
			break;
		case "blacklist_manage":
			if (current == 0) {
				ressql = "select count(*) from blacklist where type = '举报' ";
			} else if (current == 1) {
				ressql = "select * from blacklist where type = '举报' order by id desc limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			}else if(current==2){ 
				ressql = "select * from user_data where id='"+arg[5]+"' ";
			}else if(current==3){
				ressql = "select * from user_data where id='"+arg[6]+"' ";
			}
			break;
			
		case "agentist_manage":
			if (current == 0) {
				ressql = "select count(id) from agentup_list  ";
			} else  {
				ressql = "select * from agentup_list as a ,user_data as b  where a.user_id=b.id  order by a.id desc limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			}
			break;	

		case "aboutus":
			ressql = "select * from aboutus";
			break;
		case "vip_set":
			ressql = "select * from vip_set";
			break;
		case "fencheng_set":
			ressql = "select * from anchor_reward";
			break;
		case "member_album":
			if(current == 0){
				ressql = "SELECT photo_album "
						+ "FROM user_data "
						+ "WHERE id = "+arg[2];
			}
			break;
			//显示会员的视频
		case "member_video":
			if(current == 0){
				ressql =  "SELECT * "
						+ "FROM user_video "
						+ "WHERE userid = "+arg[2];
			}
			break;
		case "low_first":
			if(current==-1){
				ressql = "select count(*) from user_data where promoter_id = '"+arg[3]+"' ";
			}else if(current==1){
				ressql = "select * from user_data where promoter_id = '"+arg[3]+"' limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			}
			break;
		case "low_second":
			if(current==-1){
				ressql = "select count(*) from user_data where promoter_id in (select id from member_list where promoter_id = '"+arg[3]+"') ";
			}else if(current==1){
				ressql = "select * from user_data where promoter_id in (select id from member_list where promoter_id = '"+arg[3]+"') limit " + arg[2] + ","
						+ JyHelpManager.item + "";
			}
			break;
		case "complaint_search":
			if(current==0){
				ressql="select count(*) from complaint ";
				ressql = "select count(*) from("
						+ "select a.*,b.id as id1 from complaint as a, user_data as b where a.user_id=b.id) as c";
			}else if(current==1){
				ressql="select c.*,u.* from complaint c,user_data u where c.user_id=u.id order by c.time desc limit "+arg[2]+","+JyHelpManager.item+" ";
			}
			break;
		case "anchor_search":
			if(current==0){
				ressql="select count(*) from user_data where is_v='"+arg[5]+"' and is_anchor='1' ";
			}else if(current==1){
				ressql="select * from user_data where is_v='"+arg[5]+"' and is_anchor='1'  order by is_tuijian desc,sort_id desc limit "+arg[2]+","+JyHelpManager.item+"";
			}else if(current==2){
				ressql="select count(*) from user_data where is_v='"+arg[5]+"' and is_anchor='1' and nickname like '%"+arg[3]+"%'";
			}else if(current==3){
				ressql="select * from user_data where is_v='"+arg[5]+"' and is_anchor='1' and nickname like '%"+arg[3]+"%' ";
			}else if(current==4){
				ressql="";
			}else if(current==5){
				ressql="";
			}
		break;
		
		// 分销比例查询
		case "fenxiao_search_set":
			ressql="SELECT * FROM cash_set";
			break;		
		case "recycle_photo":
			  if(current==0){
				  ressql="select count(*) from recycle_photo";
			  }else if(current==1){
				  ressql="select * from recycle_photo limit "+arg[2]+","+JyHelpManager.item+"";
			  }
			break;
		case "jianhuang_list": {
			switch(current) {
			case 0:
				ressql = "select count(*) from jianhuang_list";
				break;
			case 1:
				ressql = "select * from("
						+ "select * from jianhuang_list order by time desc limit "+arg[4]+","+JyHelpManager.item+")as a left join "
						+ "(select id,nickname from user_data)as b on a.user_id=b.id";
				break;
			}
		
		}
		break;
		case "agent_set":
			if(current==0){
				ressql="select count(*) from agent_set";
			}else if(current==1){
				ressql="select * from agent_set  limit "+arg[2]+","+JyHelpManager.item+"";
			}
			
			break;	
		case "notification_search":
			if(current==0){
			  ressql="select count(*) from notification_table";	
			}else if(current==1){
			  ressql="select * from notification_table  order by time desc limit "+arg[2]+","+JyHelpManager.item+"";
			}
			break;
		case "admin_list":
			if(current==0){
				ressql="select count(*) from login";
			}else if(current==1){
				ressql="select * from login limit "+arg[2]+","+JyHelpManager.item+"";
			}
			break;
		case "gift_list":
			if(current==0){
				ressql="select count(*) from gift_list";
			}else if(current==1){
				ressql="select * from gift_list order by gift_price+0 asc limit "+arg[3]+","+JyHelpManager.item+"";
			}
			
			break;
			
			
		
		
		}
		return ressql;
		
	}

	@Override
	public String addSqlface(int current, String[] arg) throws SQLException, IOException {
		switch (arg[1]) {
		case "systemadd":
			if(current == 0){
				ressql="insert into news_table (news_type,news_time,content) values('"+arg[2]+"','"+arg[3]+"','"+arg[4]+"')";
			}else if(current == 1){
				ressql = "SELECT id FROM user_data ";
			}else if(current == 2){
				ressql = "SELECT content from news_table ORDER BY id DESC LIMIT 1 ";
			}
		
			break;
		case "photo_add":
			 ressql="insert into recycle_photo(photo,photo_item) values('http://taovip88.cn"+arg[2]+"','"+arg[3]+"') ";
			break;
		case "photo2":
			ressql="select * from recycle_photo where id='"+arg[2]+"'";
			break;
		case "notification_add":
			if(current==0){
			ressql="insert into notification_table(content,time,status) values('"+arg[2]+"',now(),'1')";
			}else if(current==1){
				ressql="select id from user_data";
			}
			break;
		case "admin_add":
			if(current==0){
				ressql="insert into login(username,password,power) values('"+arg[2]+"','"+arg[3]+"','2')";
			}
			
			break;
		case "gift_add":
			if(current==0){
				ressql="insert into gift_list (gift_name,gift_price,gift_photo) values('"+arg[2]+"','"+arg[3]+"','http://119.23.16.29:8091"+arg[4]+"')";
			}
			break;
		}
		return ressql;
	}

	@Override
	public String modSqlface(int current, String[] arg) throws SQLException, IOException {
		switch(arg[1]){
		case "recharge_mod":
			ressql = "select * from recharge_set where id='" + arg[2] + "'";
			break;
		case "recharge_mod1":
			ressql = "update recharge_set set v_num='"+arg[3]+"',price='" + arg[4] + "' where id='"
					+ arg[2] + "'";
			break;
		case "cash_mod":
			ressql = "update cash_set set cash_lower='" + arg[2]
					+ "'";
			break;
		case "cash_mod1":
			ressql = "select * from cash_set ";
			break;
		case "fencheng_mod1":
			ressql = "select * from anchor_reward ";
			break;	
			
			
		case "zhubo_pass":
			if(current==1){
				ressql = "update user_data set iszhubo = '1' where id = '"+arg[2]+"' ";
			}
			break;
		case "zhubo_nopass":
			if(current==1){
				ressql = "update user_data set iszhubo = '0' where id = '"+arg[2]+"' ";
			}
			break;
		case "video_checkpass":
			if(current==1){
				ressql = "update video_table set status = '1' where id = '"+arg[2]+"' ";
			}
			break;
		case "video_checknopass":
			if(current==1){
				ressql = "update video_table set status = '2' where id = '"+arg[2]+"' ";
			}
			break;
		case "news_all_mod0":
	    	   ressql="select * from news_table where id='"+arg[2]+"'";
	    	   break;
        case "news_all_mod1":
        	if(current == 0){
        		 ressql="update news_table set news_type='"+arg[3]+"',news_time='"+arg[4]+"',content='"+arg[5]+"' where id='"+arg[2]+"'";
        	}else if(current == 1){
				ressql = "SELECT id FROM user_data ";
			}else if(current == 2){
				ressql = "SELECT content from news_table where id='"+arg[2]+"' ";
			}
	    	break;
	   case "album_checkpass":
			if (current == 0) {
				ressql = "update modinfo_list set status='已通过' where id='"
						+ arg[2] + "'";
			}else if(current==1){
				ressql = "select picture from modinfo_list where id = '"+arg[2]+"' ";
			}else if(current==2){
				ressql = "select pictures from user_data where id = '"+arg[3]+"' ";
			}else if(current==3){
				ressql = "update user_data set pictures = '"+arg[4]+"' where id = '"+arg[3]+"' ";
			}
			break;
			
	   case "agent_checkpass":
		   if (current == 0) {
	  			ressql = "update agentup_list set status='已通过' where id='" + arg[2]
	  					+ "'";
		   }else if(current == 2){
			   ressql = "update user_data set promoter_id='0' where promoter_id='" + arg[3]
	  					+ "'";
		   }else if(current == 4){
			   ressql = "update user_data set twopromoter_id='0' where twopromoter_id='" + arg[3]
	  					+ "'";
		   }else{
			   ressql = "update user_data set is_agent='1',promoter_id='0',agent_level='1',twopromoter_id='0',up_agentid=0 where id='" + arg[3]
	  					+ "'";
		   }
  			break;
	   case "agent_checknopass":
		   if (current == 0) {
	  			ressql = "update agentup_list set status='不通过' ,nopassmsg='"+arg[4]+"' where id='" + arg[2]
	  					+ "'";
		   }
  			break;
   		case "album_checknopass":
   			ressql = "update modinfo_list set status='未通过',refusal='"+arg[4]+"' where id='" + arg[2]
   					+ "'";
   			break;
		case "photo_checkpass":
			if (current == 0) {
				ressql = "update photo_audit set audit='已通过' where id='"
						+ arg[2] + "'";
			}else if(current==1){
				ressql = "update user_data set photo = (select photo from photo_audit where id = '"+arg[2]+"') where id = '"+arg[3]+"' ";
			}
			break;
   		case "photo_checknopass":
   			ressql = "update photo_audit set audit = '未通过' where id='" + arg[2]
   					+ "'";
   			break;
   		case "renzheng_checkpass":
			if (current == 0) {
				ressql = "update renzheng_list set status='已通过' where id='"
						+ arg[2] + "'";
			}else if(current==1){
				ressql = "update user_data set is_v = '"+arg[4]+"',is_anchor='1' where id = '"+arg[3]+"' ";
			}else if(current==2){
				ressql = "select * from renzheng_list where id='"+arg[2]+"' ";
			}else if(current==3){
				ressql = "update user_data set phonenum='"+arg[6]+"',height='"+arg[7]+"',weight='"+arg[8]+"',constellation='"+arg[9]+"',city='"+arg[10]+"',introduce='"+arg[11]+"',image_label='"+arg[12]+"',signature='"+arg[13]+"',pictures = '"+arg[14]+"' where id = '"+arg[4]+"' ";
			}else if(current==4){
				ressql = "update user_data set photo = '"+arg[5]+"' where id='"+arg[4]+"' ";
			}else if(current==5){
				ressql = "update user_data set photo = '"+arg[5]+"',pictures = '"+arg[14]+"' where id='"+arg[4]+"'";
			}else if(current==6){
				ressql = "select labcolor from lablist_c where lab_name = '"+arg[0]+"' ";
			}
			break;
   		case "renzheng_checknopass":
   			ressql = "update renzheng_list set status = '未通过',refusal='"+arg[4]+"' where id='" + arg[2]
   					+ "'";
   			break;
   		case "mod_pwd":
			ressql = "select * from login where id='" + arg[2] + "'";

			break;
		case "mod_pwd1":
			ressql = "update login set password='" + arg[3] + "' where id='"
					+ arg[2] + "'";
			break;
		case "quanxian_mod":
			if(current==1){
				ressql = "update login set yonghu = '"+arg[3]+"',zhanghu = '"+arg[4]+"',hongniang = '"+arg[5]+"',"
						+ "wenzhang = '"+arg[6]+"',renzheng = '"+arg[7]+"',dongtai = '"+arg[8]+"',"
								+ "xiangce = '"+arg[9]+"',jubao = '"+arg[10]+"',guanliyuan = '"+arg[11]+"',shezhi = '"+arg[12]+"' where id = '"+arg[2]+"' ";
			}
			break;
		// 修改分销提成比例
		case "mod_fenxiao":
			if(current==0){
			ressql="UPDATE cash_set SET cash_onefee='"+arg[3]+"',cash_twofee='"+arg[4]+"' WHERE id='1'";
			}
			break;   			
		case "jujue_money":
			if(current==0){
				ressql = "update cash_withdrawl " + "set `status` = '拒绝提现"
						+ "' where id = '" + arg[2] + "'";
			}
			break;  
		case "response_money":
			if(current==0){
				ressql = "select a.*,b.tixian_account from cash_withdrawl as a ,user_data as b  where a.id="+arg[2]+" and a.status='申请中' and a.user_id=b.id";
			}else if(current==1){
				ressql = "update cash_withdrawl " + "set `status` = '已提现',payorder_id='"+arg[2]+"',pay_date='"+arg[3]+"',msg='"+arg[4]
						+ "' where out_biz_no = '" + arg[0] + "'";
			}else if(current==2){
				ressql = "update cash_withdrawl " + "set msg='"+arg[2]
						+ "' where id = '" + arg[0] + "'";
			}else if(current==3){
				ressql = "update cash_withdrawl " + "set `status` = '已提现',payorder_id='"+arg[2]+"',pay_date='"+arg[3]+"',msg='"+arg[4]
						+ "' where out_biz_no = '" + arg[0] + "'";
			}
			break;
		case "anchor_banned":
			if(current==0){
				ressql="update user_data set is_banned='1' where id='"+arg[2]+"'";
			}
			break;
		case "banned_cancel":
			if(current==0){
				ressql="update user_data set is_banned='' where id='"+arg[2]+"'";
			}
			break;
		case "photo_mod":
			ressql="update recycle_photo set photo='http://119.23.16.29:8091"+arg[3]+"',photo_item='"+arg[4]+"' where id='"+arg[2]+"'";
		break;
		case "fencheng_mod":
			 if(current==0){
				 ressql="update anchor_reward set reward_percent='"+arg[2]+"',reward_sv='"+arg[3]+"',reward_lw='"+arg[4]+"'";
			 }
			break;
		case "agent_mod":
			if(current==0){
				ressql="update agent_set set agent_percent="+arg[3]+" where id="+arg[2]+"";
			}
			break;
		case "agentlevel_mod":
			if(current==0){
			   ressql="update user_data set agent_level="+arg[3]+" where id="+arg[2]+"";	
			}
			break;
		case "agentlevel_mod1":
			if(current==0){
				ressql="select * from agent_set";
			}
			break;
		case "admin_mod":
			if(current==0){
				ressql="select * from login where id='"+arg[2]+"'";
			}
			break;
		case "admin_mod1":
			if(current==0){
				ressql="update login set username='"+arg[3]+"',password='"+arg[4]+"' where id='"+arg[2]+"'";
			}
			break;
		case "gift_mod":
			if(current==0){
				ressql="select * from gift_list where id='"+arg[2]+"'";
			}
			break;
		case "gift_mod1":
			if(current==0){
				ressql="update gift_list set gift_name='"+arg[3]+"',gift_price='"+arg[4]+"',gift_photo='"+arg[5]+"' where id='"+arg[2]+"' ";
			}
			break;
		}
		return ressql;
	}

	@Override
	public String deleteSqlface(String[] arg) throws SQLException {
		switch (arg[1]) {
		case "news_all_del":
			ressql="delete from news_table where id='"+arg[2]+"'";
			break;
		case "renzheng_record_del":
			ressql="delete from renzheng_list where id='"+arg[2]+"'";
			break;
		case "photo_del":
			ressql= "delete from recycle_photo where id='"+arg[2]+"'";
			break;
		case "video_del":
			ressql="delete from video_table where id='"+arg[2]+"'";
			break;
		case "photorecord_del":
			ressql="delete from modinfo_list where id='"+arg[2]+"' ";
		    break;
		case "admin_del":
			ressql="delete from login where id='"+arg[2]+"'";
			break;
		
		}	
			return ressql;
	}

	
}
