package spider.processor;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.JMException;

import org.jsoup.Jsoup;
import service.DataManager;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class GeneralProcessor implements PageProcessor {

	public static final String URL_LIST = "http://cn\\.bing\\.com/search\\?q.*";

	private Site site = Site.me().setSleepTime(3000);
	// .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2)
	// AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65
	// Safari/537.31");

	@Override
	public void process(Page page) {
		System.out.print("processor processing.\n");
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml()
					// .xpath("//a[@class='sb_pagN']")
					.css("a.sb_pagN").links().all());
			page.addTargetRequests(page.getHtml()
					// .xpath("//li[@class='b_algo']").links()
					.css("li.b_algo").links().all());
			page.setSkip(true);
			System.err.println("search list, page skipped.\n");
		} else {
			Html html = page.getHtml();
			System.out.print("///////////////////////////////////////////////////\n");

			String url = page.getUrl().toString();
			System.out.print("page get: " + url + "\n");
			page.putField("url", url);

			String title = Jsoup.parse(html.$("title").toString()).text();
			if (title == null) {
				System.err.println("title is null, page skipped.\n");
				page.setSkip(true);
				return;
			}
			page.putField("title", title);
			System.out.print("title: " + title + "\n");

			String content = html.smartContent().get();
			if (content == null || content.indexOf(DataManager.getKeyword()) == -1) {
				System.err.println("content is null, page skipped.\n");
				page.setSkip(true);
				return;
			}
			page.putField("content", content);

			Pattern pattern = Pattern.compile("(\\d{4})[^.0-9](\\d{1,2})[^.0-9](\\d{1,2})");
			Matcher matcher = pattern.matcher(html.toString());
			if (matcher.find()) {
				String year = matcher.group(1);
				String month = matcher.group(2);
				String day = matcher.group(3);
				String time = year + "-" + month + "-" + day;
				// System.out.println(time);
				page.putField("time", time);

			} else {
				pattern = Pattern.compile("(\\d{1,2})[^.0-9](\\d{1,2})");
				matcher = pattern.matcher(html.toString());
				if (matcher.find()) {
					int year = Calendar.YEAR;
					String month = matcher.group(2);
					String day = matcher.group(3);
					String time = year + "-" + month + "-" + day;
					// System.out.println(time);
					page.putField("time", time);
				} else {
					int year = Calendar.getInstance().YEAR;
					int month = Calendar.getInstance().MONTH;
					// int day = Calendar.getInstance().;
				}
			}

		}

		String domain = site.getDomain();

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
