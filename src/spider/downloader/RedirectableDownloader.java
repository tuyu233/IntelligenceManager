package spider.downloader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;

public class RedirectableDownloader extends AbstractDownloader {
	//private HtmlUnitPool pool = new HtmlUnitPool();
	private int threadNum;
	
	public RedirectableDownloader() {
	}
	
	@Override
	public Page download(Request request, Task task){
		//String url = request.getUrl();
		Page page = new Page();
		try {
			URL url = new URL(request.getUrl());
			// 打开连接
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			// con.addRequestProperty("Accept-Encoding", "gzip, deflate,
			// sdch,utf-8");
			con.addRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			con.addRequestProperty("Cache-Control", "max-age=0");
			con.addRequestProperty("Connection", "keep-alive");
			con.addRequestProperty("Referer", "http://" + url.getHost());
			// con.addRequestProperty("DNT", "1");
			con.addRequestProperty("Host", url.getHost());
			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:13.0) Gecko/20100101 Firefox/13.0");
			//con.setFollowRedirects(false);
			con.connect();
			String line = "";
			String output = "";
			StringBuffer sb = new StringBuffer();
			BufferedReader URLinput = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			while ((line = URLinput.readLine()) != null) {
				sb.append(line);
				output += line+"\n";
			}
			con.disconnect();
			if(con.getResponseCode() < 300){
				page.setRawText(output);
				page.setStatusCode(con.getResponseCode());
				page.setRequest(request);
				page.setUrl(new PlainText(request.getUrl()));
				return page;
			}
			else if(con.getResponseCode() >=300 && con.getResponseCode() <400){
				request.setUrl(con.getHeaderField("Location"));
				return download(request, task);
			}
			else {
				page.setRawText("<html></html>");
				page.setStatusCode(con.getResponseCode());
				page.setUrl(new PlainText(request.getUrl()));
				return page;
			}
		} catch (Exception e) {
			e.printStackTrace();
			page.setRawText("<html></html>");
			page.setStatusCode(500);
			page.setUrl(new PlainText(request.getUrl()));
			return page;
		}
		
	}

	@Override
	public void setThread(int threadNum) {
		this.threadNum = threadNum;

	}
	public static void main(String args[]) {
		Spider spider = Spider.create(new PageProcessor() {
			@Override
			public void process(Page page) {
				System.out.println(page.getRawText());
			}
			@Override
			public Site getSite() {
				return Site.me();
			}
		});
		
		spider.addUrl("https://www.baidu.com/link?url=dMtwtNe6rk2AcQvijR992EdmPWyVPFDHOg8fVejd6-gwMkMIV5Fx8-gbp2INkWF7KqhsztKqZCaDsd7MvUCVRa");
		spider.setDownloader(new RedirectableDownloader());
		spider.run();
	}
}
