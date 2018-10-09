package com.ssctrl.interface4;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by liuhai.lh on 17/01/12.
 */
public class BaseSample {

    protected static String accessKeyId = "LTAI4ImqrBfkp6kZ";
    protected static String accessKeySecret = "ovs2h7q1VYXkzKmiwlVYhsqibEI2jL";

    protected static String regionId = "cn-shanghai";

//    static {
        //Properties properties = new Properties();

//        try {
            //properties.load(BaseSample.class.getResourceAsStream("config.properties"));
//            accessKeyId = "LTAI4ImqrBfkp6kZ";	//properties.getProperty("accessKeyId");
//            accessKeySecret = "ovs2h7q1VYXkzKmiwlVYhsqibEI2jL";	//properties.getProperty("accessKeySecret");
//            regionId = "cn-shanghai";	//properties.getProperty("regionId");
            
            //System.out.println("accessKeyId: "+accessKeyId+", accessKeySecret: "+accessKeySecret+",regionId: "+regionId);
//        } catch(IOException e) {
//            e.printStackTrace();
//        }

//    }


    protected static String getDomain(){
         if("cn-shanghai".equals(regionId)){
             return "green.cn-shanghai.aliyuncs.com";
         }

         if("cn-hangzhou".equals(regionId)){
             return "green.cn-hangzhou.aliyuncs.com";
         }
        
         if("local".equals(regionId)){
             return "api.green.alibaba.com";
         }

        return "green.cn-shanghai.aliyuncs.com";
    }

    protected static String getEndPointName(){
        return regionId;
    }

}
