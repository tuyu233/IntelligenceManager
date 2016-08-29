package spider.processor;

import java.util.ArrayList;
import java.util.List;

import properties.Configure;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class TencentCommentProcessor implements PageProcessor {

	public static final String URL_LIST = "http://cn\\.bing\\.com/search\\?q.*";
	public static final String URL_comment = "http://coral\\.qq\\.com.*";
	private Site site = Site.me()
			.setSleepTime(Configure.SPIDER_SLEEP_TIME)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		// 列表页
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml()
					.css("a.sb_pagN")
					.links()
					.all());
			page.addTargetRequests(urls2Jsons(page.getHtml()
					.css("li.b_algo").links()
					.all()));
			page.setSkip(true);
			System.err.println("search list, page skipped.\n");
		} else if (page.getUrl().regex(URL_comment).match()) {
			String text = page.getRawText();
			if (text.charAt(0) != '{')
				text = text.substring(text.indexOf("{"), text.length() - 1);
			try {
				List<String> ids = new JsonPathSelector(
						"$.data.commentid[*].content").selectList(text);
				page.putField("comment", ids);
				ids = new JsonPathSelector("$.data.commentid[*].timeDifference")
						.selectList(text);
				page.putField("times", ids);
				page.putField("url", json2url(page.getUrl().toString()));
			} catch (Exception e) {
				System.out.println(e);
			}
			// 文章页
		}

	}

	@Override
	public Site getSite() {
		return site;
	}
	
	private List<String> urls2Jsons(List<String> urls){
		List<String> jsons = new ArrayList<String>();
		for (String string : urls) {
			StringBuilder sb = new StringBuilder(string);
			sb.insert(string.indexOf("coral.qq.com")+12, "/article").append("/comment");
			jsons.add(sb.toString());
		}
		return jsons;
	}
	
	private String json2url(String json){
		json = json.replace("/article/", "/");
		json = json.replace("/comment", "/");
		return json;
	}

}
