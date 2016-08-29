package spider.monitor;

import java.util.ArrayList;
import java.util.List;

import properties.Attributes;
import service.DataManager;
import service.SiteManager;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderStatusMXBean;

public class MonitorManager {

	private static MonitorManager instance = new MonitorManager();
	
	MySpiderMonitor spiderMonitor;
	
	private SpiderStatusMXBean siteSearchBean = null;
	public void setSiteSearchSpider(Spider siteSpider){
		siteSearchBean = spiderMonitor.getSpiderStatusMBean(siteSpider);
	}
	
	private List<SpiderStatusMXBean> spidersBeans = null;
	public void setSpiders(Spider[] spiders){
		spidersBeans = new ArrayList<SpiderStatusMXBean>();
		for(int i=0;i<spiders.length;i++)
			spidersBeans.add(spiderMonitor.getSpiderStatusMBean(spiders[i]));
	}
	
	public String getStatus(){
		if(siteSearchBean == null && spidersBeans == null) return Attributes.SPIDER_NOT_RUNNING;
		if(spidersBeans != null){
			StringBuilder sb = new StringBuilder();
			int left=0, success=0;
			for(SpiderStatusMXBean bean :spidersBeans){
				left += bean.getLeftPageCount();
				success += bean.getSuccessPageCount();
			}
			
			if(left == 0) return Attributes.SPIDER_DONE;
			sb.append("正在爬取页面...已扫描")
			.append(success)
			.append("页，已成功下载符合要求的页面")
			.append(DataManager.getCountPipeline())
			.append("页，队列中剩余")
			.append(left)
			.append("页");
			return sb.toString();
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("正在寻找待爬取网站...已经找到");
			sb.append(SiteManager.getRawSitesSize());
			sb.append("个(未去重)");
			return sb.toString();
		}
	}
	
	public int getTotal(){
		int total=0;
		for(SpiderStatusMXBean bean :spidersBeans){
			total += bean.getSuccessPageCount();
		}
		return total;
	}
	
	private MonitorManager(){
		spiderMonitor = (MySpiderMonitor) MySpiderMonitor.instance();
	}
	
	public static MonitorManager getInstance(){
		return instance;
	}
}
