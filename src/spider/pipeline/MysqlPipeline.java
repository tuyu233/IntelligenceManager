package spider.pipeline;

import service.DataManager;
import service.motion.Motion;
import spider.utils.TypeClassify;
import spider.utils.UrlUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import database.DatabaseHelper;
import entity.Record;

//import database.SQLop;

/**
 * Write results in MySQL database.<br>
 * 
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class MysqlPipeline implements Pipeline {

	@Override
	public synchronized void process(ResultItems resultItems, Task task) {
		String url = null;
		String content = null;
		Date time = null;
		String title = null;
		String author = "";
		String type = null;
		String other = null;
		List<String> comments = null;
		List<Date> times = null;
		
		System.out.print("pipeline processing.\n");
		if(resultItems.get("url") != null) url = resultItems.get("url");
		
		//若为评论
		if (resultItems.get("comment") != null) {
			comments = (List<String>) resultItems.get("comment");
			if (resultItems.get("times") != null) {
				List<String> temps = (List<String>) resultItems.get("times");
				times=new ArrayList<Date>();
				for (String temp : temps) {
					temp=temp.trim().replace(" ", "").replace(" ", "");
					temp = temp.replace("年", "-");
					temp = temp.replace("月", "-");
					temp = temp.replace("：", "");
					temp = temp.replaceAll("[\u4e00-\u9fa5]+", "");
					temp = temp.replaceAll("【", "");
					temp = temp.replaceAll("】", "");
					if (temp.length() > 10)
						temp = temp.substring(0, 10);
					if (temp.length() < 10) {
						if (temp.length()
								- temp.indexOf("-", temp.indexOf("-") + 1) < 4)
							temp = temp
									.substring(0, temp.indexOf("-",
											temp.indexOf("-") + 1) + 1)
									+ "0"
									+ temp.substring(temp.indexOf("-",
											temp.indexOf("-") + 1) + 1, temp
											.length() - 1);
					}
					try {
						times.add(java.sql.Date.valueOf(temp));
					} catch (Exception e) {
						System.out.println(e + "\n数据库存入的时间信息格式有误");
						times.add(null);
					}
				}
			}

			String keyword = DataManager.getKeyword() + "公众评论";
			for (String comment : comments) {
				other = Float.toString(Motion.getAssessment(comment));
				DatabaseHelper.save(
						new Record("公众", keyword, comment, url, times.get(comments.indexOf(comment)), author, other, 0));
			}
			return;
		}
		
		//不为评论
		if(resultItems.get("title") != null) title = resultItems.get("title");
		System.out.print("title set. ");
		if(resultItems.get("content") != null) content = resultItems.get("content");
		System.out.print("content set. ");
		if(resultItems.get("time") != null){
			try {
				time = java.sql.Date.valueOf((String) resultItems.get("time"));
				System.out.print("time set. ");
			} catch (Exception e) {
				System.out.println(e + "\n数据库存入的时间信息格式有误");
				return;
			}
		}
		type = TypeClassify.typeClassifyByUrl(url);
		System.out.print("type set. ");
		try{
			other = Float.toString(Motion.getAssessment(content));
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		System.out.print("index set. \n");
		System.out.print(DataManager.getCountPipeline());
		DatabaseHelper.save(new Record(type, title, content, url, time, "", other, 0));
		
		
		/*System.out.println("get page: " + resultItems.getRequest().getUrl());
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			// String temp = entry.getValue().toString();
			if (entry.getValue().toString() == null)
				continue;
			if (entry.getKey().equals("title")) {
				title = entry.getValue().toString();
			} else if (entry.getKey().equals("content")) {
				content = entry.getValue().toString().replaceAll("　", "\n")
						.replaceAll(" ", "\n").replaceAll(" ", "\n");
			} else if (entry.getKey().equals("time")) {
				String temp = entry.getValue().toString();
				temp=temp.trim().replace(" ", "").replace(" ", "").replace("　", "");
				temp = temp.replace("年", "-");
				temp = temp.replace("月", "-");
				temp = temp.replace("：", "");
				temp = temp.replaceAll("[\u4e00-\u9fa5]+", "");
				temp = temp.replaceAll("【", "");
				temp = temp.replaceAll("】", "");
				if (temp.length() > 10)
					temp = temp.substring(0, 10);
				if (temp.length() < 10) {
					if (temp.length()
							- temp.indexOf("-", temp.indexOf("-") + 1) < 4)
						temp = temp.substring(0,
								temp.indexOf("-", temp.indexOf("-") + 1) + 1)
								+ "0"
								+ temp.substring(temp.indexOf("-",
										temp.indexOf("-") + 1) + 1, temp
										.length() - 1);
				}
				try {
					time = java.sql.Date.valueOf(temp);
				} catch (Exception e) {
					System.out.println(e + "\n数据库存入的时间信息格式有误");
				}
			} else if (entry.getKey().equals("baseURL")) {
				url = entry.getValue().toString();
			} else if (entry.getKey().equals("author")) {
				author = entry.getValue().toString();
			} else if (entry.getKey().equals("type")) {
				type = entry.getValue().toString();
			} else if (entry.getKey().equals("other")) {
				other = entry.getValue().toString();
			} else
			*/
		
	}
}
