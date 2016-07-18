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
public class Ifengnews implements PageProcessor {

	public static final String URL_LIST = "http://zhannei\\.baidu\\.com/cse/search\\?q=。*";

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
					.xpath("//div[@id='results']").links()
					.regex("http://news\\.ifeng\\.com/.*").all());
			page.addTargetRequests(page.getHtml().xpath("//div[@id='pageFooter']")
					.links().regex("http://zhannei\\.baidu\\.com/cse/search\\?q=。*").all());
			page.setSkip(true);
			// 文章页
		} else {
			String temp = page.getHtml().xpath("//div[@id='artical_sth']/allText()")
					.toString();
			if (temp == null) {
				page.setSkip(true);
				return;
			}
			page.putField("time", temp.substring(0,temp.indexOf("日")).trim());
			page.putField("title", page.getHtml().xpath("title/text()"));

			page.putField("content",
					page.getHtml().xpath("//div[@id='artical_real']/tidyText()")
							);

			page.putField("author", temp.substring(temp.indexOf("来源")+3, temp.indexOf(" ",temp.indexOf("来源")+1)));

			page.putField("baseURL", page.getUrl());
			page.putField("type", "新闻");
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Ifengnews())
				.addUrl("http://zhannei.baidu.com/cse/search?q=数控机床&s=16378496155419916178")
				.addPipeline(new ConsolePipeline()).run();
	}
}
