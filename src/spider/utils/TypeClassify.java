package spider.utils;

import java.util.ArrayList;
import java.util.List;

public class TypeClassify {

	public static String typeClassifyByUrl(String url){
		if(url.contains(".gov.cn")) return "政府";
		if(isPublic(url)) return "公众";
		return "媒体";
	}
	
	private static boolean isPublic(String url){
		List<String> publicSites = new ArrayList<String>();
		publicSites.add("tianya.com");
		publicSites.add("tieba.baidu.com");
		publicSites.add("zhidao.baidu.com");
		
		for (String site : publicSites) {
			if(url.contains(site)) return true;
		}
		return false;
	}
}
