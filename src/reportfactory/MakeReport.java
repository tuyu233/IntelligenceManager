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
	Information t = Information.getInstance();

	public MakeReport(String keyword, String filePath) {
		this.keyword = keyword;
		this.filePath = filePath;
		excute();
	}

	public void excute() {

		HtmlFile file = new HtmlFile(filePath, keyword);

		file.writeStatistics("1、舆论统计", WORDS);
		file.writeStatistics(t.getSource_jpg(), ICON);
		file.writeStatistics(t.getMotion_jpg(), ICON);
		file.writeStatistics(t.getYear_comments_jpg(), ICON);

		file.writeStatistics("2、主题分析", WORDS);
		file.writeinfor("不同主体关注的主题:");
		String str="";
		if (t.getGov_theme() != null) {
			for (int i = 0; i < t.getGov_theme().size(); i++) {
				str=str+t.getGov_theme().get(i)+" ";
			}
			file.writeinfor("政府："+str);
		}	
		str="";
		if (t.getMedia_theme() != null) {
			for (int i = 0; i < t.getMedia_theme().size(); i++) {
				str=str+t.getMedia_theme().get(i)+" ";
			}
			file.writeinfor("媒体："+str);
		}	
		str="";
		if (t.getPublic_theme() != null) {
			for (int i = 0; i < t.getPublic_theme().size(); i++) {
				str=str+t.getPublic_theme().get(i)+" ";
			}
			file.writeinfor("公众："+str);
		}
		
		file.writeinfor("明显峰值年度以及主题:");
		str="";
		if (t.getYear_theme() != null) {
			for (int i = 0; i < t.getYear_theme().size(); i++)
				str=str+t.getYear_theme().get(i)+" ";
			file.writeinfor(str);
		}
		
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
		}
		file.finish();
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
		t.setGlobal_attitude("globalattitude");
		Information tt = Information.getInstance();
		tt.setGov_attitude("govattitude");
		// t.setHot_theme("hottheme");
		tt.setMedia_attitude("mediaattitude");
		new MakeReport("test123", ".\\output\\1.html");

	}

}
