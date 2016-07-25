package reportfactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.omg.CORBA.CharSeqHolder;

import com.mysql.jdbc.Util;

import util.JsonHelper;


public class HtmlMaker {

	public static void make(Map<String,Object> map){
		try {
			// 模板路径
			String filePath = "output/template.html";
			System.out.print(filePath);
			String templateContent = "";
			FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
	        InputStreamReader isr = new InputStreamReader(fileinputstream, "UTF-8"); 
	        BufferedReader br = new BufferedReader(isr); 
	        
			int length = fileinputstream.available();
			byte bytes[] = new byte[length];
			fileinputstream.read(bytes);
			
			char[] chars = new char[length];
			isr.read(chars);
			fileinputstream.close();
			templateContent = new String(bytes,"UTF-8");
			System.out.print(templateContent);
			System.out.println("----------------------------:::::::------------------------");
			Set<Entry<String,Object>> set = map.entrySet();
			System.out.println("-----------Size:"+set.size());
			
			for(Entry<String,Object> entry : set){
				if(entry.getValue() instanceof String){
					System.out.println("------------Is format  String-------");
					System.out.println(entry.getKey());
					System.out.println(entry.getValue());
					System.out.println("-------fin-------");
					templateContent = templateContent.replaceAll("###"+entry.getKey()+"###",(String) entry.getValue());
				}
				else {
					System.out.println("//////////Not format  String///////////////");
					System.out.println(entry.getKey());
					System.out.println(entry.getValue());
					System.out.println("////////ddl///////////");	
					templateContent = templateContent.replaceAll("###"+entry.getKey()+"###",JsonHelper.jsonEncode(entry.getValue()));
				}
			}
			//System.out.print(templateContent);

			// 根据时间得文件名
			String fileame = "output/report.html";
			FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
			System.out.print("文件输出路径:");
			System.out.print(fileame);
			byte tag_bytes[] = templateContent.getBytes("UTF-8");
			fileoutputstream.write(tag_bytes);
			fileoutputstream.close();
			System.out.println("生成成功");
		} 
		catch (Exception e) {
			System.out.print(e.toString());
		}

	}
	
	
	
	
	/**
	 * dkfsadlkfsjalfdksajlfks
	 * @param nums 1111
	 * @param keywords 2222
	 * @param indexes 3333
	 * @param views
	 */
	public static void entrance(int[] nums, List<List<String>> keywords, float[] indexes, List<String> views) {
		/*Information t = Information.getInstance();
		t.setNumOfWebsites(nums[0]);
		t.setNumOfArticles(nums[1]);
		t.setComments(nums[2]);
		t.setGlobal_attitude(Float.toString(indexes[0]));
		t.setGov_attitude(Float.toString(indexes[1]));
		t.setMedia_attitude(Float.toString(indexes[2]));
		t.setPublic_attitude(Float.toString(indexes[3]));
		t.setHot_theme(keywords);*/
		
		//new MakeReport("test123", "./resource/report/template.html");
		Map<String,Object> map = new HashMap<>();
		map.put("numWebsite", Integer.toString(nums[0]));
		map.put("numArticle", Integer.toString(nums[1]));
		map.put("numComment", Integer.toString(nums[2]));
		map.put("keywordAll", util.RecordTrans.strings2stringWithComma(keywords.get(0)));
		map.put("keywordGov", util.RecordTrans.strings2stringWithComma(keywords.get(1)));
		map.put("keywordMedia", util.RecordTrans.strings2stringWithComma(keywords.get(2)));
		map.put("keywordPublic", util.RecordTrans.strings2stringWithComma(keywords.get(3)));
		map.put("keywordHottestYear", util.RecordTrans.strings2stringWithComma(keywords.get(4)));
		map.put("indexAll", Float.toString(indexes[0]));
		map.put("indexGov", Float.toString(indexes[1]));
		map.put("indexMedia", Float.toString(indexes[2]));
		map.put("indexPublic", Float.toString(indexes[3]));
		map.put("viewAbsPos", views.get(0));
		map.put("viewAbsNeg", views.get(1));
		map.put("indexPos", views.get(2));
		map.put("viewPos", views.get(3));
		map.put("indexNeg", views.get(4));
		map.put("viewNeg", views.get(5));
		map.put("pipData",ChartData.getTestPipData());
		map.put("yearData", ChartData.getTestYearData());
		map.put("motionData",ChartData.getTestMotionData());
		map.put("index",ChartData.getTestIndexData());
		map.put("govIndex", ChartData.getGovIndexData());
		map.put("medIndex", ChartData.getMedIndexData());
		map.put("pubIndex", ChartData.getPubIndexData());
		Date date = new Date(System.currentTimeMillis());
		String time = String.format("%tF%n", date);
		map.put("time", time);
		make(map);
		
		
		
	}

}
