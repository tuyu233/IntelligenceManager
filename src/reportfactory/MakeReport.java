package reportfactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MakeReport {
	// final int NUMBER_gov = 10;
	// final int NUMBER_news = 10;

	// final int SELECT_gov=0;
	// final int SELECT_news=1;

	final int ICON = 0;
	final int WORDS = 1;

	String keyword = null;
	String filePath = null;
	Information t;

	public MakeReport(String keyword, String filePath) {
		t = Information.getInstance();
		this.keyword = keyword;
		this.filePath = filePath;
		excute();
	}

	public void excute() {

		HtmlFile file = new HtmlFile(filePath, keyword);
		file.write_first();
		//the first part
		file.writeinfor("&nbsp;&nbsp;"+
		"本次报告通过qiji全网络数据采集&nbsp;采集了"+
			t.getNumOfWebsites()	+
		"个网站，&nbsp;分析了"+
			t.getNumOfArticles()	+
		"篇文章,"+t.getComments()+
		"&nbsp;条评论，通过分词、&nbsp;聚类、&nbsp;查重、&nbsp;摘要等手段和方法，&nbsp;对各类数据进行分析形成图表，&nbsp;并最后形成报告如下。"

				);
		file.writerline(); //hr
		file.writeDev("first");
		file.write_mainone();
		file.writeEndDev();
		
		file.writerline(); //hr
		file.writeDev("Second");
		file.writeStatistics("二、主题分析（主题词）", WORDS);
		List<List<String>> topic =null;
		
		try {
			if(t.getHot_theme()!=null){
				topic = t.getHot_theme();
			}
			if(topic.size()<5){
				System.out.println("主题元素数量类型不够");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		for (int i = 0; i < 5; i++) {
			
			String str="";
			if(topic!=null && i<=(topic.size()-1))
			{
				List<String> in = topic.get(i);
				for (int j = 0; j < in.size(); j++) {
					str=str+in.get(j)+" ";
				}
			}
			switch (i) {
			case 0:{
				file.writeinfor("全网："+str);
				break;
			}
			case 1:{
				file.writeinfor("政府："+str);
				break;
			}
			case 2:{
				file.writeinfor("媒体："+str);
				break;
			}
			case 3:{
				file.writeinfor("公众："+str);
				break;
			}
			case 4:{
				file.writeinfor("明显峰值年度以及主题："+str);
				break;
			}
		}
		}		
		file.writeEndDev();

		file.writerline(); //hr
		file.writeDev("Third");
		file.writeStatistics("三、态度（情感）分析", WORDS);
		file.writeList();
		file.writemOfList("全网整体的舆论指数:" + t.getGlobal_attitude());
		file.writemOfList("政府官网态度指数:" + t.getGov_attitude());
		file.writemOfList("媒体的满意度:" + t.getMedia_attitude());
		file.writemOfList("公众的满意度:" + t.getPublic_attitude());
		
		file.writeEndList();
		file.writeEndDev();
		
		file.writerline(); //hr
		file.writeDev("Forth");
		file.writeStatistics("四、热点分析", WORDS);
		file.writeList();
		file.writemOfList("热点主题");
		file.writemOfList("集中度较高的观点" );
		file.writeEndList();
		file.writeEndDev();
		
		file.writeLast();
		
		
		
		/*
		
		
		file.writeStatistics("3、态度分析", WORDS);
		file.writeinfor("全网整体的舆论指数:" + t.getGlobal_attitude());
		file.writeinfor("政府官网态度指数:" + t.getGov_attitude());
		file.writeinfor("媒体的满意度:" + t.getMedia_attitude());
		file.writeinfor("公众的满意度:" + t.getPublic_attitude());

		file.writeStatistics("4、热点分析", WORDS);
		file.writeinfor("热点主题:");
		if (t.getHot_theme() != null) {
			for (int i = 0; i < t.getHot_theme().size(); i++)
				file.writeinfor(t.getHot_theme().get(i));
		}
		
		file.writeinfor("主要观点:");
		if (t.getOther() != null) {
			for (int i = 0; i < t.getOther().size(); i++)
				file.writeinfor(t.getOther().get(i));
		}*/
		
		file.finish();
		System.out.println("fin_write");
		
	}

	/**
	 * classify the abstracts
	 * 
	 * @param info
	 * @return
	 */
	// public List<Map<String, String>> classify(List<Map<String, String>> info,
	// int type) {
	// List<Map<String, String>> result=new ArrayList<Map<String, String>>();
	// for (Map map : info) {
	// if (type == SELECT_news) {
	// if (map.get("type").toString().equals("新闻"))
	// result.add(map);
	// } else if (type==SELECT_gov){
	// if(map.get("type").toString().equals("科技部")||map.get("type").toString().equals("发改委")||
	// map.get("type").toString().equals("工信部"))
	// result.add(map);
	// }
	//
	// }
	//
	// return result;
	// }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Information t = Information.getInstance();
//		t.setGlobal_attitude("globalattitude");
//		Information tt = Information.getInstance();
//		tt.setGov_attitude("govattitude");
//		// t.setHot_theme("hottheme");
//		tt.setMedia_attitude("mediaattitude");
		
		new MakeReport("test123", ".\\output\\2.html");

	}

}
