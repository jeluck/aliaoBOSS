package com.ssctrl.interface4;


import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;






import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;








import com.ssctrl.interface4.JyLogDetect.DataType;




public class ReserveTimer_01160 implements ServletContextListener {

		// 开一个定时循环线程
		private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(2);
		//private Map<String,ShopPvInfo> infoOfDay = new HashMap<String,ShopPvInfo>();
		@Override
		public void contextDestroyed(ServletContextEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println("CtrlInit destroy");
			
			
		}

		@Override
		public void contextInitialized(ServletContextEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println("CtrlInit initialize");
			final JyLogDetect log=new JyLogDetect();
			executor.scheduleWithFixedDelay(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					//System.out.println("Thread start");
					log.send(DataType.specialType, "01160", "活动检测", "Thread start");
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date today=new Date();
					String a=df1.format(today);
					//System.out.println(a);
					String b=df.format(today);
					String c=b+":51:00";
					String d=b+":51:29";
					//log.send(DataType.specialType, "01160", "活动检测", a+"+++"+b+"+++"+c+"+++"+d);
					try {
						if(a.compareTo(c)>=0 && a.compareTo(d)<=0){
							update(a);
					    }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 5, 5, 
			TimeUnit.MINUTES);
		}
		
		public void update(String nowtime) throws SQLException, ClassNotFoundException{
			JyLogDetect log=new JyLogDetect();
			log.send(DataType.specialType, "01066", "活动检测", nowtime);
			List<String> listNames = new ArrayList<String>();
			listNames.add(JyGlobalConstant.getDbBaseName());
			String sql="";
			ArrayList<Map<String, Object>> list=null;
			for(int i=0;i<listNames.size();i++){
				SqlUtil sqlUtil = new SqlUtil(listNames.get(i));
				sql="select * from appointment where end_time<'"+nowtime +"' and isrefund=0 and isreturn = 0 ";
				log.send(DataType.specialType, "01066", "活动检测", sql);
				list=sqlUtil.get_list(sql);
				log.send(DataType.specialType, "01066", "活动检测", list.size());
				for(int m=0;m<list.size();m++){
					
//					if(Integer.parseInt(list.get(m).get("isreturn").toString())==1){
//						sql = "UPDATE appointment SET isrefund =1,set isreturn=0  "
//								+ "WHERE id = '" + list.get(m).get("id") + "' ";
//						log.send(DataType.specialType, "01066", "活动检测", sql);
//						sqlUtil.sql_exec(sql);
//					}else{
						sql = "UPDATE appointment SET isrefund =1  "
								+ "WHERE id = '" + list.get(m).get("id") + "' ";
						log.send(DataType.specialType, "01066", "活动检测", sql);
						sqlUtil.sql_exec(sql);
//					}
					
					sql = "UPDATE user_data SET money = money +  " + list.get(m).get("price")
							+ "WHERE id = '" + list.get(m).get("user_id") + "' ";
					log.send(DataType.specialType, "01066", "活动检测", sql);
					sqlUtil.sql_exec(sql);
					
					//增加一条v币收入明细
					
				}
				
			}
		}
		
	
		
}


