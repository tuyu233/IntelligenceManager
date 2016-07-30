package util;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

public class EncryptUtil {
	public static String Md5Encode(String str) {
		try {
			if(str == null)str = "";
			// 确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
			return newstr;
		} catch (Exception e) {
			e.printStackTrace();
			return String.valueOf(Math.random());
		}

	}
	
	public static void main(String args[]) {
		System.out.println(Md5Encode("sss"));
		System.out.println(Md5Encode(""));
		System.out.println(Md5Encode(null));
		
		
		String s = "";
		for(int i=0;i < 10000;i++){
			s += "啊";
		}
		long start = System.currentTimeMillis();
		for(int i=0; i< 10000;i++){
			s += "哈";
			Md5Encode(s);
		}
		
		long end = System.currentTimeMillis();
		float useTime = (float)((end-start)/1000);
		System.out.println("use time:"+useTime);
	}
}
