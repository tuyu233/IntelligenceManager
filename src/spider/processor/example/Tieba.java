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
public class Tieba implements PageProcessor {

	public static final String URL_LIST = "http://tieba\\.baidu\\.com/f\\?kw=。*";

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
					.xpath("//div[@id='frs_list_pager']").links()
					.regex("http://tieba\\.baidu\\.com/p/.*").all());
			page.addTargetRequests(page.getHtml().xpath("//div[@id='frs_list_pager']")
					.links().all());
			page.setSkip(true);
			// 文章页
		} else {
			String temp = page.getHtml().xpath("//div[@class='p_content ']").toString();
			
			if(temp==null)
				temp=page.getHtml().xpath("//div[@class='bd']/allText()").toString();
//			if (temp.indexOf("发布时间") != -1) {
//				temp = temp.substring(temp.indexOf("发布时间") + 5,
//						temp.indexOf("发布时间") + 15);
//				page.putField("time", temp);
//			} else {
//				page.setSkip(true);
//				return;
//			}
			page.putField("title", page.getHtml().xpath("title/text()"));

			page.putField("content", temp);

//			temp = page.getHtml().xpath("body/tidyText()").toString();
//			if (temp.indexOf("来源") != -1
//					&& (temp.indexOf("】", temp.indexOf("来源") + 3) - (temp
//							.indexOf("来源") + 3)) < 10) {
//				temp = temp.substring(temp.indexOf("来源") + 3,
//						temp.indexOf("】", temp.indexOf("来源") + 3));
//				page.putField("author", temp);
//			}

			page.putField("baseURL", page.getUrl());
			page.putField("type", "评论");
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Tieba())
				.addUrl("http://tieba.baidu.com/f?kw=%E5%85%AC%E8%BD%A6%E6%94%B9%E9%9D%A9&fr=wwwt")
				.addPipeline(new MysqlPipeline())
				.addPipeline(new ConsolePipeline())
				.run();
	}
}
