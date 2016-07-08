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
public class Wanfang implements PageProcessor {

	public static final String URL_LIST = "http://s\\.wanfangdata\\.com\\.cn/Paper\\.aspx\\?q=。*";
	
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
					.xpath("//div[@class='record-item-list']").links()
					.regex("http://d\\.wanfangdata\\.com\\.cn/.*").all());
			page.addTargetRequests(page.getHtml().xpath("//p[@class='pager']")
					.links().all());
			page.setSkip(true);
			// 文章页
		} else {
			String temp = page.getHtml().xpath("//div[@class='fixed-width baseinfo-feild']/tidyText()").toString();
			if (temp == null) {
				page.setSkip(true);
				return;
			}
			if(temp.indexOf("在线出版日期：")!=-1)
				page.putField("time", temp.substring(temp.indexOf("在线出版日期：")+8, temp.indexOf("在线出版日期：")+18));
			page.putField("title", page.getHtml().xpath("//div[@class='section-baseinfo']/h1/text()").toString());

			page.putField("content",page.getHtml().xpath("//div[@class='row clear zh']//div[@class='text']/tidyText()"));

			page.putField("author", temp.substring(temp.indexOf("作者")+4, temp.indexOf("作者")+7).trim()+"等");
			page.putField("other", temp.substring(temp.indexOf("刊 名")+4, temp.indexOf(" ", temp.indexOf("刊 名")+5)).trim());
			page.putField("baseURL", page.getUrl());
			page.putField("type", "论文");
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Wanfang())
				.addUrl("http://s.wanfangdata.com.cn/Paper.aspx?q=推荐系统")
				.addPipeline(new ConsolePipeline()).run();
	}
}
