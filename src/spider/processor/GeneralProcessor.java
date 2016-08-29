package spider.processor;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.JMException;

import org.jsoup.Jsoup;

import properties.Configure;
import service.DataManager;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import util.Transform;

public class GeneralProcessor implements PageProcessor {

	public static final String URL_LIST = "http://cn\\.bing\\.com/search\\?q.*";
	 
	private Site site = Site.me().setSleepTime(Configure.SPIDER_SLEEP_TIME);

	@Override
	public void process(Page page) {
		System.out.print("\nprocessor processing\n");
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml()
					// .xpath("//a[@class='sb_pagN']")
					.css("a.sb_pagN").links().all());
			page.addTargetRequests(page.getHtml()
					// .xpath("//li[@class='b_algo']").links()
					.css("li.b_algo").links().all());
			System.err.println("search list, page skipped."+page.getUrl().get()+"\n"+"from this page:");
			System.out.print(page.getHtml().css("li.b_algo").links().all());
			System.out.print("\n");
			page.setSkip(true);
		} else {
			Html html = page.getHtml();

			String url = page.getUrl().toString();
			System.out.print("page get: " + url + "\n");
			page.putField("url", url);

			String title = Jsoup.parse(html.$("title").toString()).text();
			System.out.print("title: " + title + "\n");
			if(title == null || !Transform.containsPartOfKeyword(title, DataManager.getKeyword())){
				System.err.println("title is null, page skipped.\n");
				page.setSkip(true);
				return;
			}
			page.putField("title", title);

			String content = html.smartContent().get();
			if(content == null || !content.contains(DataManager.getKeyword())){
				System.err.println("content is null, page skipped.\n");
				page.setSkip(true);
				return;
			}
			page.putField("content", content);
			Pattern pattern = Pattern.compile("(20[0-1][0-9])[^.0-9]([0-1]?[0-9])[^.0-9]([0-3]?[0-9])");
			Matcher matcher = pattern.matcher(html.toString());
			if (matcher.find()) {
				String year = matcher.group(1);
				String month = matcher.group(2);
				String day = matcher.group(3);
				String time = year + "-" + month + "-" + day;
				// System.out.println(time);
				page.putField("time", time);

			} else {
				pattern = Pattern.compile("([0-1]?[0-9])[^.0-9]([0-1]?[0-9])");
				matcher = pattern.matcher(html.toString());
				if (matcher.find()) {
					String year = "2016";
					String month = matcher.group(1);
					String day = matcher.group(2);
					String time = year + "-" + month + "-" + day;
					// System.out.println(time);
					page.putField("time", time);
				} else {
					System.err.print("无时间信息");
					page.setSkip(true);
					return;
				}
			}

		}

//		List<String> links = page.getHtml().links().all();
//		for (String link : links) {
//			if (link.contains(domain)) {
//				page.addTargetRequest(link);
//			}
//
//		}

	}

	@Override
	public Site getSite() {
		return site;
	}
}
