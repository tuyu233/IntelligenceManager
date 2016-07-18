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
public class Patent implements PageProcessor {

	String other=null;
	public Patent(String other){
		this.other=other;
	}
	
	public static final String URL_LIST = "http://www\\.soopat\\.com/Home/Result\\?SearchWord=。*";
	private Site site = Site.me()
			.setSleepTime(3000)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		// 列表页
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml()
					.xpath("//h2[@class='PatentTypeBlock']").links()
					.regex("http://www\\.soopat\\.com/Patent/.*").all());
			page.addTargetRequests(page.getHtml().xpath("//div[@id='SoopatPager']")
					.links().all());
			page.setSkip(true);
			// 文章页
		} else {
			String temp =page.getHtml().xpath("//span[@class='detailtitle']/strong/i/text()").toString();
			if (temp == null) {
				page.setSkip(true);
				return;
			}
			page.putField("time", temp.substring(temp.indexOf("申请日：")+4, temp.length()));
			page.putField("title", page.getHtml().xpath("//span[@class='detailtitle']/h1/text()").toString());

			temp=page.getHtml().xpath("//td[@class='sum f14']/allText()").toString();
			page.putField("content",temp.substring(temp.indexOf("摘要")+3, temp.length()));
			
			temp=page.getHtml().xpath("//table[@class='datainfo']/tbody/tr[2]/allText()").toString();
			page.putField("author", temp.substring(temp.indexOf("申请人：")+4, temp.length()));

			page.putField("baseURL", page.getUrl());
			page.putField("type", "专利");
			page.putField("other", other);
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Patent("实用新型"))
				.addUrl("http://www.soopat.com/Home/Result?SearchWord=推荐系统&PatentIndex=0&Sort=1&Valid=2&SYXX=Y")
				.addPipeline(new ConsolePipeline()).run();
	}
}
