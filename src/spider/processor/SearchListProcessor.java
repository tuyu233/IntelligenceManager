package spider.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class SearchListProcessor implements PageProcessor{
	
	public static final String URL_LIST = "http://cn\\.bing\\.com/search\\?q.*";
	private Site site = Site.me()
			.setSleepTime(50);
			//.setUserAgent(
			//		"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml()
					//.xpath("//a[@class='sb_pagN']")
					.css("a.sb_pagN")
					.links()
					.all());
			List<String> sites = page.getHtml()
					//.xpath("//li[@class='b_algo']").links()
					.css("li.b_algo").links()
					.all();
			page.putField("sites", sites);
		} else{
			page.setSkip(true);
		}
	}
	
	@Override
	public Site getSite() {
		return site;
	}

}
