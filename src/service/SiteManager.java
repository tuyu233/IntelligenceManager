package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SiteManager {
	
	//爬取到的网页地址
	static private List<String> rawSites = null;
	static public List<String> getRawSites(){
		if(rawSites == null){
			rawSites = new ArrayList<String>();
		}
		return rawSites;
	}
	static public void addSites(List<String> newSites){
		getRawSites().addAll(newSites);
	}
	static public void addSite(String newSite){
		getRawSites().add(newSite);
	}
	static public int getRawSitesSize(){
		return getRawSites().size();
	}
	
	//处理过的域名
	static private List<String> sites = null;
	static public List<String> getSites(){
		if(sites == null){
			ArrayList<String> list = new ArrayList<String>();
			//取域名部分
			for(String rawSite:getRawSites()){
				Pattern p = Pattern.compile("(?<=http://)[^/]*(?=/)");
				Matcher m = p.matcher(rawSite);
				while(m.find()){
					list.add("http://"+m.group()+"/");
				}
			}
			//去重
			HashSet<String> set = new HashSet<String>(list);
			list.clear();
			list.addAll(set);
			sites = list;
		}
		return sites;
	}
	static public int getSitesSize(){
		if(sites == null){
			getSites();
		}
		return sites.size();
	}
	
}
