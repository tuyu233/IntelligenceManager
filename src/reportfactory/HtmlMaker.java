package reportfactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.omg.CORBA.CharSeqHolder;

import com.mysql.jdbc.Util;

import service.DataManager;
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
	public static void entrance(
			String keyword,
			int[] nums, //nums是5个数量：网站总数，结果总数，政府数量，媒体数量，公众数量
			List<Entry<String, Integer>> yearList, //yearList是年份-数量组，按年份从前往后排序
			List<List<String>> keywords, 
			float[] indexes, 
			List<String> views) {
		//indexList是指数-数量组，按数量从大到小
		List<Entry<String, Integer>> indexAllList = new ArrayList<Map.Entry<String,Integer>>(11);
		for (int i=0;i<11;i++) {
			indexAllList.add(new AbstractMap.SimpleEntry<String, Integer>(new Integer(i-5).toString(), new Integer(DataManager.getAllDistribution()[i])));
		}
		indexAllList = sortIndexList(indexAllList);
		List<Entry<String, Integer>> indexGovList = new ArrayList<Map.Entry<String,Integer>>(11);
		for (int i=0;i<11;i++) {
			indexGovList.add(new AbstractMap.SimpleEntry<String, Integer>(new Integer(i-5).toString(), new Integer(DataManager.getGovDistribution()[i])));
		}
		indexGovList = sortIndexList(indexGovList);
		List<Entry<String, Integer>> indexMediaList = new ArrayList<Map.Entry<String,Integer>>(11);
		for (int i=0;i<11;i++) {
			indexMediaList.add(new AbstractMap.SimpleEntry<String, Integer>(new Integer(i-5).toString(), new Integer(DataManager.getMediaDistribution()[i])));
		}
		indexMediaList = sortIndexList(indexMediaList);
		List<Entry<String, Integer>> indexPublicList = new ArrayList<Map.Entry<String,Integer>>(11);
		for (int i=0;i<11;i++) {
			indexPublicList.add(new AbstractMap.SimpleEntry<String, Integer>(new Integer(i-5).toString(), new Integer(DataManager.getPublicDistribution()[i])));
		}
		indexPublicList = sortIndexList(indexPublicList);
		
		Map<String,Object> map = new HashMap<>();
		map.put("title", keyword);
		map.put("numWebsite", Integer.toString(nums[0]));
		map.put("numArticle", Integer.toString(nums[2]+nums[3]));
		map.put("numComment", Integer.toString(nums[4]));
		map.put("startYear", yearList.get(0).getKey());
		//countList是年份-数量组，按数量从大到小排序
		List<Entry<String, Integer>> countList = yearList;
		Collections.sort(countList, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		map.put("peakYear", countList.get(0).getKey());
		map.put("popularity", countList.get(0).getValue().toString());
		map.put("secondYear", countList.get(1).getKey());
		map.put("secondPopularity", countList.get(1).getValue().toString());
		map.put("thirdYear", countList.get(2).getKey());
		map.put("thirdPopularity", countList.get(2).getValue().toString());
		map.put("peakPercent", Float.toString(indexAllList.get(0).getValue()*100.0f/nums[1]));
		map.put("peakIndex", indexAllList.get(0).getKey());
		map.put("secondPercent", Float.toString(indexAllList.get(1).getValue()*100.0f/nums[1]));
		map.put("secondIndex", indexAllList.get(1).getKey());
		map.put("thirdPercent", Float.toString(indexAllList.get(2).getValue()*100.0f/nums[1]));
		map.put("thirdIndex", indexAllList.get(2).getKey());
		map.put("govPaper", Integer.toString(nums[2]));
		map.put("govPercent", Float.toString(nums[2]*100.0f/nums[1]));
		map.put("medPaper", Integer.toString(nums[3]));
		map.put("medPercent", Float.toString(nums[3]*100.0f/nums[1]));
		map.put("pubPaper", Integer.toString(nums[4]));
		map.put("pubPercent", Float.toString(nums[4]*100.0f/nums[1]));
		map.put("peakGovPercent", Float.toString(indexGovList.get(0).getValue()*100.0f/nums[2]));
		map.put("peakGovIndex", indexGovList.get(0).getKey());
		map.put("secondGovPercent", Float.toString(indexGovList.get(1).getValue()*100.0f/nums[2]));
		map.put("secondGovIndex", indexGovList.get(1).getKey());
		map.put("thirdGovPercent", Float.toString(indexGovList.get(2).getValue()*100.0f/nums[2]));
		map.put("thirdGovIndex", indexGovList.get(2).getKey());
		
		map.put("peakMedPercent", Float.toString(indexMediaList.get(0).getValue()*100.0f/nums[3]));
		map.put("peakMedIndex", indexMediaList.get(0).getKey());
		map.put("secondMedPercent", Float.toString(indexMediaList.get(1).getValue()*100.0f/nums[3]));
		map.put("secondMedIndex", indexMediaList.get(1).getKey());
		map.put("thirdMedPercent", Float.toString(indexMediaList.get(2).getValue()*100.0f/nums[3]));
		map.put("thirdMedIndex", indexMediaList.get(2).getKey());
		
		map.put("peakPubPercent", Float.toString(indexPublicList.get(0).getValue()*100.0f/nums[4]));
		map.put("peakPubIndex", indexPublicList.get(0).getKey());
		map.put("secondPubPercent", Float.toString(indexPublicList.get(1).getValue()*100.0f/nums[4]));
		map.put("secondPubIndex", indexPublicList.get(1).getKey());
		map.put("thirdPubPercent", Float.toString(indexPublicList.get(2).getValue()*100.0f/nums[4]));
		map.put("thirdPubIndex", indexPublicList.get(2).getKey());

		map.put("keywordAll", util.Transform.strings2stringWithComma(keywords.get(0)));
		map.put("keywordGov", util.Transform.strings2stringWithComma(keywords.get(1)));
		map.put("keywordMedia", util.Transform.strings2stringWithComma(keywords.get(2)));
		map.put("keywordPublic", util.Transform.strings2stringWithComma(keywords.get(3)));
		map.put("keywordHottestYear", util.Transform.strings2stringWithComma(keywords.get(4)));
		String string = null;
		for(int i=1;i<=4;i++){
			for(int j=1;j<=4;j++){
				string = "t" + Integer.toString(i) + Integer.toString(j);
				map.put(string, DataManager.getNounKeywords().get(i).get(j-1));
			}
		}
		
		map.put("indexAll", Float.toString(indexes[0]));
		map.put("commentAll", posORneg(indexes[0]));
		List<Entry<String, Float>> indexList= new ArrayList<Entry<String, Float>>(3);
		indexList.add(new AbstractMap.SimpleEntry<String,Float>("政府",new Float(indexes[1])));
		indexList.add(new AbstractMap.SimpleEntry<String,Float>("媒体",new Float(indexes[2])));
		indexList.add(new AbstractMap.SimpleEntry<String,Float>("公众",new Float(indexes[3])));
		Collections.sort(indexList, new Comparator<Map.Entry<String, Float>>() {
				public int compare(Map.Entry<String, Float> o1,
						Map.Entry<String, Float> o2) {
					return (o2.getValue().compareTo(o1.getValue()));
				}
			});
		map.put("top", indexList.get(0).getKey());
		map.put("topComment", posORneg(indexList.get(0).getValue().floatValue()));
		map.put("topIndex", indexList.get(0).getValue().toString());
		map.put("bottom", indexList.get(2).getKey());
		map.put("bottomComment", posORneg(indexList.get(2).getValue().floatValue()));
		map.put("bottomIndex", indexList.get(2).getValue().toString());
		map.put("middle", indexList.get(1).getKey());
		map.put("midComment", posORneg(indexList.get(1).getValue().floatValue()));
		map.put("midIndex", indexList.get(1).getValue().toString());

		map.put("posWord", views.get(0));
		map.put("negWord", views.get(1));
		map.put("indexPos", views.get(2));
		map.put("viewPos", views.get(3));
		map.put("indexNeg", views.get(4));
		map.put("viewNeg", views.get(5));
		map.put("pipData",ChartData.getTestPipData());
		map.put("yearData", ChartData.getTestYearData());
		map.put("motionData",ChartData.getTestMotionData(DataManager.getAllDistribution()));
		map.put("govMotionData",ChartData.getTestMotionData(DataManager.getGovDistribution()));
		map.put("medMotionData",ChartData.getTestMotionData(DataManager.getMediaDistribution()));
		map.put("pubMotionData",ChartData.getTestMotionData(DataManager.getPublicDistribution()));
		map.put("index",ChartData.getTestIndexData());
		map.put("govIndex", ChartData.getGovIndexData());
		map.put("medIndex", ChartData.getMedIndexData());
		map.put("pubIndex", ChartData.getPubIndexData());
		Date date = new Date(System.currentTimeMillis());
		String time = String.format("%tF%n", date);
		map.put("time", time);
		make(map);
		
		
		
	}
	
	static private List<Entry<String, Integer>> sortIndexList(List<Entry<String, Integer>> list){
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue().compareTo(o1.getValue()));
			}
		});
		return list;
	}
	
	static String posORneg(float index){
		if(index>0) return "正面";
		else if(index<0) return "负面";
		else return "中立";
	}

}
