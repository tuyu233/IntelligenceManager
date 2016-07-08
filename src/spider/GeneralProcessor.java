package spider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.Selectable;

public class GeneralProcessor implements PageProcessor {
	private Site site = Site.me()
			// .setDomain("blog.sina.com.cn")
			.setSleepTime(3000)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	@Override
	public void process(Page page) {
		Html html= page.getHtml();
		
		
		Selectable content = html.smartContent();
		//System.out.println("content:"+content);
		page.putField("content", content);
		
		String title = Jsoup.parse(html.$("title").toString()).text();
		//System.out.println("title:"+title);
		page.putField("title", title);
		
		Pattern pattern = Pattern.compile("(\\d{4})[^.0-9](\\d{1,2})[^.0-9](\\d{1,2})");
		Matcher matcher = pattern.matcher(html.toString());
		if(matcher.find()){
			String year = matcher.group(1);
			String month = matcher.group(2);
			String day = matcher.group(3);
			String time = year+"年"+month+"月"+day+"日";
			//System.out.println(time);
			page.putField("time",time);
			
		}
		else{
			pattern = Pattern.compile("(\\d{1,2})[^.0-9](\\d{1,2})");
			matcher = pattern.matcher(html.toString());
			if(matcher.find()){
				int  year = Calendar.getInstance().YEAR;
				String month = matcher.group(2);
				String day = matcher.group(3);
				String time = year+"年"+month+"月"+day+"日";
				//System.out.println(time);
				page.putField("time",time);
			}
		}
		
		page.putField("baseURL", page.getUrl());
		String url = page.getUrl().toString();
		String domain = site.getDomain();
		List<String> links= html.$("a").all();
		pattern = Pattern.compile("href *= *\"([^\"]+)\"");
		for(String link : links){
			if(link.contains(domain)){
				matcher = pattern.matcher(link);
				if(matcher.find()){
					page.addTargetRequest(matcher.group(1));
					//System.err.println("addlink:"+matcher.group(1));
				}
				
				
			}
			else {
				//System.out.println(link);
			}
		}
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	public static void main(String args[]) {
		Spider spider = Spider.create(new GeneralProcessor());
		//spider.addUrl("http://www.most.gov.cn/kjbgz/201607/t20160707_126445.htm");
		//spider.addUrl("http://www.chinanews.com/gn/2016/04-08/7827816.shtml");//ok
		//spider.addUrl("http://tech.ifeng.com/a/20160708/41635440_0.shtml");//ok
		spider.addUrl("http://mil.news.sina.com.cn/china/2016-07-08/doc-ifxtwiht3338415.shtml");//ok
		spider.thread(1).run();
	}
}
