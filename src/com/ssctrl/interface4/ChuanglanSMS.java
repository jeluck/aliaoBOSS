package com.ssctrl.interface4;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ChuanglanSMS {
	private CloseableHttpClient client;
	private String account;
	private String password;

	private static final String INTERNATIONAL_URL="https://intapi.253.com/mt";

	public ChuanglanSMS(String account,String password){
		this.account = account;
		this.password = password;
		client = HttpClients.createDefault();
	}



	public CloseableHttpResponse sendInternationalMessage(String phone, String content) {
		String encodedContent = null;
		try {
			encodedContent = URLEncoder.encode(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		StringBuffer strBuf = new StringBuffer(INTERNATIONAL_URL);
		strBuf.append("?un=").append(account);
		strBuf.append("&pw=").append(password);
		strBuf.append("&da=").append(phone);
		strBuf.append("&sm=").append(encodedContent);
		strBuf.append("&dc=15&rd=1&rf=2&tf=3");
		HttpGet get = new HttpGet( strBuf.toString() );
		
		try {
			return client.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		if(client != null){
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
