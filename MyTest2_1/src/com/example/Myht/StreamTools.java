package com.example.Myht;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {
	public static byte[] isToData(InputStream is) throws IOException{ 

		//字节输出流 
		ByteArrayOutputStream bops = new ByteArrayOutputStream(); 
		//读取数据的缓存区 
		byte buffer[] = new byte[1024]; 
		//读取长度 的记录 
		int len = 0; 
		//循环读取 
		while((len = is.read(buffer)) != -1){ 
		bops.write(buffer, 0, len); 
		} 
		//把读取的内容转换成 byte数组 
		byte data[] = bops.toByteArray(); 

		return data; 

		} 
}
