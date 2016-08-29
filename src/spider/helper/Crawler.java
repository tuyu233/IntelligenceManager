package spider.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.management.JMException;

import properties.Configure;
import service.DataManager;
import service.SiteManager;
import spider.monitor.MonitorManager;
import spider.monitor.MySpiderMonitor;
import spider.pipeline.SearchListPipeline;
import spider.pipeline.MysqlPipeline;
import spider.processor.GeneralProcessor;
import spider.processor.SearchListProcessor;
import spider.processor.TencentCommentProcessor;
import us.codecraft.webmagic.Spider;

public class Crawler {
	
	Spider siteSpider;
	Spider[] pageSpiders;
	MySpiderMonitor spiderMonitor;
	MonitorManager monitorManager;
	
	public Crawler(){
		spiderMonitor = (MySpiderMonitor) MySpiderMonitor.instance();
		monitorManager = MonitorManager.getInstance();
	}
	
	public void start(){

		//搜索网站列表
		String keyword = DataManager.getKeyword();
		siteSpider = Spider.create(new SearchListProcessor())
				.addUrl("http://cn.bing.com/search?q="+ keyword +"")
				.addPipeline(new SearchListPipeline())
				.thread(1);
		try {
			spiderMonitor.register(siteSpider);
			monitorManager.setSiteSearchSpider(siteSpider);
		} catch (JMException e1) {
		}
		siteSpider.run();
		System.out.print("寻找网站结束，共找到"+SiteManager.getSitesSize()+"个网站\n");
		
		//根据网站列表搜索相关网页
		List<String> sites = SiteManager.getSites();
		System.out.print(sites);
		System.out.print("\n");
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(sites);
		int spiderNum = Configure.SPIDER_NUMBER;//并行的爬虫数量
		pageSpiders = new Spider[spiderNum];
		int each = sites.size()/spiderNum + 1;//每个爬虫分到的网站数
		
		Spider.create(new TencentCommentProcessor())
				.addUrl("http://cn.bing.com/search?q=site%3Acoral.qq.com+%22"+keyword+"%22")
				.addPipeline(new MysqlPipeline())
				.start();
		
		for (int i=0;i<spiderNum;i++) {
			//每个爬虫分到each个网站
			ArrayList<String> list = new ArrayList<String>();
			for(int j=0;j<each;j++){
				if(queue.peek() == null) break;
				list.add("http://cn.bing.com/search?q=site%3A"+queue.poll()+"+%22"+keyword+"%22");
			}
			String[] urls = list.toArray(new String[list.size()]);
			pageSpiders[i] = Spider.create(new GeneralProcessor())
					.addUrl(urls)
					.addPipeline(new MysqlPipeline())
					.thread(1);
			try {
				spiderMonitor.register(pageSpiders[i]);
			} catch (JMException e) {
			}
			pageSpiders[i].start();
		}
		monitorManager.setSpiders(pageSpiders);
	}
	
	public void stop(){
		if(siteSpider != null) siteSpider.stop();
		if(pageSpiders == null) return;
		for(int i=0;i<pageSpiders.length;i++){
			if(pageSpiders[i] == null) continue;
			pageSpiders[i].stop();
		}
	}

	public static void main(String[] args) {
		/*DataManager.setKeyword("军民融合");
		
		Spider.create(new GeneralProcessor())
		.addUrl("http://cn.bing.com/search?q=site%3Awww.jmrhw.org+%22%e5%86%9b%e6%b0%91%e8%9e%8d%e5%90%88%22+filetype%3ahtml&FORM=PORE")
		.addPipeline(new MysqlPipeline())
		.thread(1)
		.start();*/
		Spider.create(new SearchListProcessor())
				.addUrl("http://cn.bing.com/search?q=公车改革")
				.addPipeline(new SearchListPipeline())
				.thread(1)
				.start();
	}

}
