package com.example.test03;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
	public static String sendGet(String path) { 
		String content = null; 
		try { 
		// 设置访问url 
		URL url = new URL(path); 
		// 打开请求 
		HttpURLConnection httpURLConnection = (HttpURLConnection) url 
		.openConnection(); 
		// 设置请求的信息 
		httpURLConnection.setRequestMethod("GET"); 
		// 设置请求是否超时时间 
		httpURLConnection.setConnectTimeout(5000); 
		// 判断服务器是否响应成功 
		if (httpURLConnection.getResponseCode() == 200) { 
		// 获取响应的输入流对象 
		InputStream is = httpURLConnection.getInputStream(); 
		byte data[] = StreamTools.isToData(is); 
		// 把转换成字符串 
		content = new String(data); 
		// 内容编码方式 
		if (content.contains("gb2312")) { 
		content = new String(data, "gb2312"); 
		} 
		} 
		// 断开连接 
		httpURLConnection.disconnect(); 
		} catch (MalformedURLException e) { 
		e.printStackTrace(); 
		} catch (IOException e) { 
		e.printStackTrace(); 
		} 
		return content; 
		} 
}
