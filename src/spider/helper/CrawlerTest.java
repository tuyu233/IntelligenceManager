package spider.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import spider.processor.GeneralProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

public class CrawlerTest {

	public CrawlerTest() {
		Spider spider = Spider
				.create(new GeneralProcessor())
				.addUrl("http://www.baidu.com/s?wd=公车改革&rn=15")
				.addPipeline(new ConsolePipeline())
				.thread(1);
		spider.start();
		
/*
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet("http://www.baidu.com/s?wd=公车改革&tn=json&rn=15");  
            System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                System.out.println("--------------------------------------");  
                // 打印响应状态    
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    //System.out.println("toString: " + EntityUtils.toString(entity));
                    //System.out.println("inputStream: " + entity.getContent());
                    ObjectMapper mapper = new ObjectMapper();
                    Map<?, ?> feed = mapper.readValue(EntityUtils.toString(entity), Map.class);
                    //System.out.println(feed.get("feed").getClass().toString());
                    Map<?, ?> data = (LinkedHashMap)feed.get("feed");
                    ArrayList<HashMap> list = (ArrayList) data.get("entry");
                    //System.out.println(list.get(0).getClass().toString());
                    for (HashMap map : list) {
                    	Iterator<?> iterator = map.keySet().iterator();   
                        while ( iterator.hasNext() ) {
                        	Object key = iterator.next();
                        	if(map.get(key) == null) continue;
                        	System.out.print(key+"值为");    
                        	System.out.println(map.get(key).toString() + "\n");
                        }
					}
                }  
                System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {
            	httpclient.close();
            	} catch (IOException e) {  
                e.printStackTrace();
                }
            }*/
        }
	
	public static void main(String[] args) {
		new CrawlerTest();
		

	}

}
