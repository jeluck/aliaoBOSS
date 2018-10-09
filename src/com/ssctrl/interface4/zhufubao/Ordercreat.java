package com.ssctrl.interface4.zhufubao;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;



public class Ordercreat {

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2018050860138124";
	//public static final String APPID = "2016092800617116";
	
	//public static final String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyQOZjIjnL7vtp8zkvCYAPgul7LORy0S/m2YZk9FCuBJMJC57B+VhHN8KwCn/oRZaptH4Rv3ONkC2Tymhe+cIH45VieaCcRBUWgiIlXVKm70v2VrKJWNbRoq1BjCCWzNolT7V6K6JuiDBYsXLWVwCWA24gUFjDP2YDKpOr2v9kW2ATg7BU9V6y0RXzlW4m1vURm6A/uLqRkKIi9A2D4tkNxcCGS73Q6YMQYSmV49i1iho+WO7q3deYNCs1csDHshcwgLLdPs/C3ggkfmB3Epzy9PnbCDP2lXxmguxFzT6UnfLpQZUMQF2Al3DbSBFWzD4j/trPsNHmyZ6XdKamsDWOQIDAQAB";
	public static final String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArwIAsNajvMQ5kWW2v6UwSLUiQ87hmc9tIkJ7mKNHw5V+V1Q8+5Mck+QhHdVYHIvM7r0xlfJUaMP2beLyOYdDdvRCCuaC1kqleWHJF7DzT4smnU1Hxdz08Xp7e+5tac6B0u2eo4qnyXoVvnrgrow+AIQm0M3vlhG8XUJk1WeRAVleadZ/GGF2SVPGQACUjgdrMlttIBOXEoAgKmwcinNZ9cvkv+A+t4kIBIYrimUo9PkhnVsqoAFUXdpP0qR9EavlaG4ox3rCDBCWChpMlBtb4B/HSIVpU+A34u8UHyw7azjG5PN35VMt1tDbUoteOArPieRZ0iHfa03w+s4DTkIBdwIDAQAB";                      
	
	
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCD07OCbK92dVs7obacIRBaN6CBi0JteAs86OA0jglhGezHeoIZhwNF00u/rQXf380Pjwg1ps7OaVd+9ZA7BLq4sAfr1J+h0oI7xVbixF2cdoBGHMZTFuAqNVHXTyD1X7izyqc6eU0hcsGRYNTpSNw9GCRyvY8ZMmcZVQIj+mCHcLDxLEPrqG/h1+/PEfkZJseIoK2bTmjTXfVJXoySKuCRmrI7M70YlLd357ANVxBDV+ZWSEvR0uW+Bzxs0DiQBnExBgoaF7k1MTUs8wtjoSL7svSmvOuH64HdizsqLKzIQjlE0E0xXDC72VU4vPSnsaHlWgk1M9to+ojyzjl1Sm+NAgMBAAECggEAVhLZR5R9cIQp4KKzH/g6vB1MEKmD4wQZmliXY9lF6H/L6WdlIRPVoDbZY7qudkGjm8ARZS6YI5AGkB/4X4C0BjjxwxrebAOOA0bTd1ctpCZ/lMfe+ouaLBUKbeH9rBkm+UWdsQFYyjkqiCjIe9WV79IeokiA8bvGM3Uc467sbiHEQ0l3NySPregzOTXrkumPEVnaYjNunHYdVfT5GejYCWHccrNJH9eL+kY5sSpYmB5PM2h4+JUk1RBWCZ7REZfO01pSfiUW4DaU5mlLNBZHD57ON5/3fs5VJnoxPGlByejhKVb577L6hvFwsC1IcaFNGXGU6Axy0/L5HqtKDC1QgQKBgQDY00ottEwNFLg0QtYA1qDbEEBg7jgn+uB/r885/lXsnzQO79uvyYoeUOeXhqvvySNgU9poJlZas/zIQd7ccXDss2Om5P6CUj05vOw16oqRdHHN/VMuUv4nYZJgBHgak26toLMBsQ/aL8rFlpHpRp2q17SgnZUBmLDOc9g+2uNSoQKBgQCbpQZkWE1e6nBy2F4W8ghr6tjZjaM+3bbpQqe+OPT9aPIzP9vopx5jwMZpf2xgjclkSZZ9oYHDb4WofEz0bVt0SHRUI/kQX/xnqxLOqyW7v3etVrcFau1FHBTbLfhmd4EIkt2pppfWMpTxlRQV68eCbLkVSACc2VEfXsEWt+mhbQKBgH9uZplH7q59b+lfIpEpky5MddnCCAUPQCQrOpp17R/9TUY+GBpPU7qPiB0ZDDIIP59ciO9PIM98GuPDOuzKSzkCEV5EFYSYOt2VOCpT5w6npj8PVHTUWATjJnkCB2hvH8wxtyMWYh+0mPLZlHXKh92hmsoO80UV7UvY7825LYMhAoGAb7y8ZwOBoBd8XOZ6PczKQPdbcGufe31eV/1zQTvQnbBe2G4K6O45AfIePxSscHC52RZ/RHfTGcQRV1DNSljL5CIz6UfeAWR3ZtFI8jeqIiwltbpgMDKAXjH3wxYwkxdN+iK9oZKoxKyRgxG8VrRrCXYWNXH4hqH4/CVhR3cG8c0CgYBtSTNxL+g9pU/TtRCNRzX1LeMfXGc5pXHo4RjGlB5StSEuhlIf0PeLigJ/NZpONqg16M6xALtj6jidOQ4uYM9AcMrKkgje8cS+7HY+EnkZULVw4Ne+rl5eUCwIvO11+UrM2dvk6VX1mdZX+nGAKsFhkkCxJGj8aOLWeA1zO3SDlw==";
	//public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHJZjKhN2eGCzHeDaZMkf4OHbpaFAeh0xlZNHtBVlbJZ009ObF0zrEYlemFbY0tdFd6F+NfvwLYf7eWglK+EytGBtJ+avdLUpUbKmxte9TB+CXqKm2fnqT09rBOoxx9PZIsnje4XUlqKMXxdByNYpQKvL1PmsJPpiuMQhzQ2X68R+UvjxH0nCkUIs+chcjVX06Dw/A3UaEIYVWJFwLoY1ttA+zQGia6Nli9N9AiOw5xqhmXmCjcPGR1pFRwM5htXdkox7i8MUziJDok6muWnNMsG6zPthO1x6Sx3BVRgXlTr9Sh7Kxl9umxjJjm3lrVA5ji+SVnMy+NJ39btPdiefLAgMBAAECggEAUpd5IqSv9N051RA6NJbx2jQwvOSSNdcPDA4gMhjB0ldrN4dKHklYWhIfH7ZpdrrhNf0lg91iUqY1uanFqN4JjtAsoSV27afW5+SZDCbUNq9RqhQ9Ln6N/7khRHovvSNG8SdzkZwXpaKm4TkdFgC2eEEXujZE1fSsBbI4OvV82fFi8GcPqSDr7iV4JO8yQhXjYppsmjGJckzoh44yHObi5JFNkhZMXuS9OHZ/f7eWWSO2/fyhUoUFHS6vKE9SOVLDgDXFKicXmJBM0J0EsijN0SpXKwLIzQaDQ7U/Kv9gf0Mdo7MlicXkh/vt+C0qk7KUkAesyrKk8tSmwUss7X2bwQKBgQDwDHr7hNQ0zlyDBPTQp2oTP0/ENPRQRkDRIH+SEZ6Q6lvrlqOkImQIISsW9h6fcYMjFBF0vTIIeat4h8aXV3Rw4XT+8SPgJe2s26xNIHkFWayaqv9AOTNa37uPR13/V0QgY4fv6Fa1n0+gU1cgyPdBVbx6sG5gV99u4p7ePSZH0wKBgQDUYVNqM7+Ib/RZgQo5ZhrB3rzkMubuSzroiRFQckKVU7i4EPjb7lE9VaU8/HmJ6+wdNcjD0ksyKaa+AJUcGLY5TsM6gYb0M56u2FSH2asHANcRR1XS7y4kIDkZc32/9xUV/zq/dkzOV6F2te5Ys5Gn/rzx/ea7UIEFwdgTiLSdKQKBgHc8K8ZNZz7Hlio6RwRxc/4Vf6CdERHio4V9GADNIa8nyfrOe4Am2Ps0xnuESxe1wYamDDmpVHXjQan//0PjW+JKCtwrmT9Yd6NaBk7VJ2fh8BtvfpQ/FWQ5J6f2shkIFr3j0nz3MkLK3DltzCRrzm00kgd7JYhTfZdNl84aH66jAoGAS80c95fWsby7qsJqwsfVnwJ/yrb8Q2rocJyTaHIPn7qNAG1WVTD/H/QUkOrP+lERKe/LY1EIc7P4BnKwxaFwUI3zEkuuvjQ6W89shDxFVa+QE2OxlyK1BkHV/2aAKGboyLwa3ME9cpxGTddrZY9Z2msKRxjHJG38TvC7zWUKidkCgYEAnCc95bGVk4FLZmp+eBtRPgGdnBEWVccpcQg5u9CLjs0txwzGMD1ceXDY3zfLoowFgTgim7chSCQHfDoV2yx+tx3+afbQ4frD/dBhNVJLs9idcQErlJJ5W9/TSVR3+X6DuWWq/hC4P1BSxWhFuqgA94KmlsXi+k85HWwHR/MNNkk=";
	public static final String RSA_PRIVATE = "";
	//public static final String RSA2_PRIVATE ="";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	
	public static String order_creat(String content) {
		
		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo的获取必须来自服务端；
		 */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,content);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		String orderInfo = orderParam + "&" + sign;
		return orderInfo;
	}

}
