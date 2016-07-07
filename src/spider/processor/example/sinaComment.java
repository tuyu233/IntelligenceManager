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
public class sinaComment implements PageProcessor {

	public static final String URL_LIST = "http://search.sina.com.cn/?q=。*";
	
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
					.xpath("//a[@class='page next S_txt1 S_line1']").links().all());
			//page.setSkip(true);
			String temp = page.getHtml().xpath("//div[@class='feed_from W_textb']/tidyText()").toString();
			if (temp == null) {
				page.setSkip(true);
				return;
			}
			page.putField("title", "评论");
			page.putField("content",page.getHtml().xpath("//div[@class='WB_cardwrap S_bg2 clearfix']//p[@class='comment_txt']/tidyText()"));
			page.putField("author", page.getHtml().xpath("//a[@class='W_texta W_fb']/tidyText()"));
			page.putField("other", "");
			page.putField("baseURL", page.getUrl());
			page.putField("type", "评论");
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new sinaComment())
				.addUrl("http://s.weibo.com/weibo/公车改革")
				.addPipeline(new ConsolePipeline()).run();
	}
}
