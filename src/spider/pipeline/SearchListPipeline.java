package spider.pipeline;

import java.util.List;
import java.util.Map;

import service.SiteManager;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Write results in console.<br>
 * Usually used in test.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class SearchListPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        //System.out.println("get page: " + resultItems.getRequest().getUrl());
        //for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
        //    System.out.println(entry.getKey() + ":\t" + entry.getValue());
        //}
    	List<String> sites = resultItems.get("sites");
    	SiteManager.addSites(sites);
    	System.out.println(SiteManager.getRawSitesSize() + " sites get.");
    	System.out.print(sites);
    	System.out.print("\n");
    	//System.out.println("get sites: " + sites);
    	
    }
}
