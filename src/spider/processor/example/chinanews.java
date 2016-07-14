package spider.processor.example;

import spider.Page;
import spider.Site;
import spider.Spider;
import spider.pipeline.*;
import spider.processor.PageProcessor;
import spider.pipeline.ConsolePipeline;

/**
 * @author code4crafter@gmail.com <br>
 */
public class chinanews implements PageProcessor {

	public static final String URL_LIST = "http://sou\\.chinanews\\.com\\.cn/search.do\\?q=。*";

	private Site site = Site.me()
			// .setDomain("blog.sina.com.cn")
			.setSleepTime(3000)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		// 列表页
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml()
					.xpath("//div[@id='news_list']").links()
					.regex("http://www\\.chinanews\\.com.*").all());
			page.addTargetRequests(page.getHtml().xpath("//div[@id='pagediv']")
					.links().all());
			page.setSkip(true);
			// 文章页
		} else {
			String temp = page.getHtml().xpath("//div[@class='left-t']/text()")
					.toString();
			if (temp == null) {
				page.setSkip(true);
				return;
			}
			page.putField("time", temp.substring(1, 11));
			page.putField("title", page.getHtml().xpath("title/text()"));

			page.putField("content",
					page.getHtml().xpath("//div[@class='left_zw']/tidyText()")
							);

			page.putField("author", "中国新闻网");

			page.putField("baseURL", page.getUrl());
			page.putField("type", "新闻");
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new chinanews())
				.addUrl("http://www.chinanews.com/gn/2016/04-08/7827816.shtml")
				.addPipeline(new ConsolePipeline()).run();
	}
}
