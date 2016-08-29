package spider.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {

	static public String getDomain(String url){
		
		Pattern p = Pattern.compile("(?<=http://)[^/]*(?=/)");
		Matcher m = p.matcher(url);
		while(m.find()){
			return m.group();
		}
		return null;
	}
}
