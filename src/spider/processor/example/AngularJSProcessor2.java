package spider.processor.example;

import org.apache.commons.collections.CollectionUtils;

import spider.Page;
import spider.Site;
import spider.Spider;
import spider.processor.PageProcessor;
import spider.selector.JsonPathSelector;

import java.util.List;

/**
 * @author code4crafter@gmail.com
 * @since 0.5.0
 */
public class AngularJSProcessor2 implements PageProcessor {

    private Site site = Site.me();

    private static final String ARITICALE_URL = "http://angularjs\\.cn/api/article/\\w+";

    private static final String LIST_URL = "http://angularjs\\.cn/api/article/latest.*";

    @Override
    public void process(Page page) {
        if (page.getUrl().regex(LIST_URL).match()) {
            List<String> ids = new JsonPathSelector("$.data[*]._id").selectList(page.getRawText());
            if (CollectionUtils.isNotEmpty(ids)) {
                for (String id : ids) {
                    page.addTargetRequest("http://angularjs.cn/api/article/" + id);
                }
            }
        } else {
        	String text=page.getRawText();
        	if(text.charAt(0)!='{')
        		text=text.substring(text.indexOf("{"),text.length()-1);
        	List<String> ids=new JsonPathSelector("$.data.commentid[*].content").selectList(text);
            page.putField("comment", ids);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new AngularJSProcessor2()).addUrl("http://coral.qq.com/article/1422621504/comment?commentid=0&reqnum=50&tag=&callback=mainComment&_=1465057695855").run();
        //addUrl("http://angularjs.cn/api/article/latest?p=1&s=20").run();
    }
}
