package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Record;
import spider.utils.UrlUtil;
import us.codecraft.webmagic.Spider;

public class SiteManager {
	static private List<String> sitesToAdd = new ArrayList<String>();
	static{
		sitesToAdd.add(".gov.cn");
		sitesToAdd.add("tianya.cn");
	}
	
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
		if(rawSites == null){
			getRawSites();
		}
		return rawSites.size();
	}
	
	//处理过的域名
	static private List<String> sites = null;
	static public List<String> getSites(){
		if(sites == null){
			ArrayList<String> list = new ArrayList<String>();
			//取域名部分
			for(String rawSite:getRawSites()){
				if(rawSite.contains(".gov.cn")
						||rawSite.contains("tianya.cn")) continue;//政府网站先删掉，后面统一添加
				String domain = UrlUtil.getDomain(rawSite);
				if(domain != null) list.add(domain);
			}
			//去重
			HashSet<String> set = new HashSet<String>(list);
			list.clear();
			list.addAll(sitesToAdd);
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
	
	static public int getSitesSizeWithoutSpider(){
		List<Record> records = DataManager.getRecordsAll();
		HashSet<String> set = new HashSet<String>();
		//取域名部分
		for(Record record: records){
			set.add(UrlUtil.getDomain(record.getBaseUrl()));
		}
		return set.size();
	}
	
	static public void reset(){
		rawSites = null;
		sites = null;
	}
}
