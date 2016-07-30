package spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spider.processor.GeneralProcessor;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderMonitor.MonitorSpiderListener;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderStatus;
import us.codecraft.webmagic.monitor.SpiderStatusMXBean;
import us.codecraft.webmagic.utils.Experimental;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MySpiderMonitor extends SpiderMonitor {
	private static SpiderMonitor INSTANCE = new MySpiderMonitor();
	//private MonitorSpiderListener monitorSpiderListener = new MonitorSpiderListener();
	Map<Spider, SpiderStatusMXBean> map = new HashMap<>();

	public SpiderStatusMXBean getSpiderStatusMBean(Spider spider) {
		if (map.containsKey(spider)) {
			return map.get(spider);
		}
        MonitorSpiderListener monitorSpiderListener = new MonitorSpiderListener();
        if (spider.getSpiderListeners() == null) {
            List<SpiderListener> spiderListeners = new ArrayList<SpiderListener>();
            spiderListeners.add(monitorSpiderListener);
            spider.setSpiderListeners(spiderListeners);
        } else {
            spider.getSpiderListeners().add(monitorSpiderListener);
        }
		SpiderStatusMXBean bean = new SpiderStatus(spider, monitorSpiderListener);
		map.put(spider, bean);
		return bean;
	}
	//@Override
    public static SpiderMonitor instance() { 
         return INSTANCE; 
    } 


	public static void main(String args[]) {
		Spider spider = Spider.create(new GeneralProcessor());
		// spider.addUrl("http://www.most.gov.cn/kjbgz/201607/t20160707_126445.htm");
		// spider.addUrl("http://www.chinanews.com/gn/2016/04-08/7827816.shtml");//ok
		spider.addUrl("http://tech.ifeng.com/a/20160708/41635440_0.shtml");// ok
		spider.addUrl("http://mil.news.sina.com.cn/china/2016-07-08/doc-ifxtwiht3338415.shtml");// ok

		try {
			 //SpiderMonitor.instance().register(spider);
			// MonitorableScheduler scheculer = (MonitorableScheduler)
			// spider.getScheduler();
			// System.out.println(scheculer.getLeftRequestsCount(spider));
			MySpiderMonitor spiderMonitor = (MySpiderMonitor) MySpiderMonitor.instance();
			SpiderStatusMXBean bean = spiderMonitor.getSpiderStatusMBean(spider);
			System.err.println(bean.getLeftPageCount());
			spider.thread(1).start();

			while (true) {
				Thread.sleep(1000 * 2);
				System.err.println(bean.getLeftPageCount());
				System.err.println(bean.getSuccessPageCount() + " " + bean.getTotalPageCount());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
