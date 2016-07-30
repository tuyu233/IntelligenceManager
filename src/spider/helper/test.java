package spider.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.management.JMException;

import service.DataManager;
import service.SiteManager;
import spider.pipeline.ConsolePipeline;
import spider.pipeline.MysqlPipeline;
import spider.processor.GeneralProcessor;
import spider.processor.SearchListProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

public class Test {
	
	public Test(){
		DataManager.setKeyword("公车改革");
		String keyword = DataManager.getKeyword();
		Spider siteSpider = Spider.create(new SearchListProcessor())
				.addUrl("http://cn.bing.com/search?q="+ keyword +"%22+filetype%3Ahtml")
				.addPipeline(new ConsolePipeline())
				.thread(1);
		
		siteSpider.run();
		System.out.print("寻找网站结束，共找到"+SiteManager.getSitesSize()+"个网站\n");
		
		List<String> sites = SiteManager.getSites();
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(sites);
		/*Spider.create(new GeneralProcessor())
				.addUrl("http://cn.bing.com/search?q=site%3A"+sites.get(0)+"+%22公车改革%22+filetype%3Ahtml")
				.addPipeline(new MysqlPipeline())
				.thread(1)
				.start();*/
		int spiderNum = 20;
		Spider[] pageSpiders = new Spider[spiderNum];
		int each = sites.size()/spiderNum + 1;
		//每一个爬虫
		for (int i=0;i<spiderNum;i++) {
			//每个爬虫取each个网站，并转换为字符串数组
			ArrayList<String> list = new ArrayList<String>();
			for(int j=0;j<each;j++){
				if(queue.peek() == null) break;
				list.add("http://cn.bing.com/search?q=site%3A"+queue.poll()+"+%22"+keyword+"%22+filetype%3Ahtml");
			}
			//System.out.print("list built! " + list +"\n");
			String[] urls = list.toArray(new String[list.size()]);
			Spider.create(new GeneralProcessor())
					.addUrl(urls)
					.addPipeline(new MysqlPipeline())
					.thread(2).run();
		}
	}

	public static void main(String[] args) {
		new Test();

	}

}
