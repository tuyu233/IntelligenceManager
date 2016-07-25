package reportfactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import service.DataManager;
import database.Pair;

public class ChartData {
	public String title = "title";
	public List<Pair> pairs = new ArrayList<>();

	public static ChartData getTestPipData(){
		ChartData data = new ChartData();
		data.title = "关注主题统计";
		data.pairs.add(new Pair("政府", DataManager.getRecordNum()[1]));
		data.pairs.add(new Pair("公众", DataManager.getRecordNum()[3]));
		data.pairs.add(new Pair("媒体", DataManager.getRecordNum()[2]));
		return data;
	}
	
	public static ChartData getTestYearData(){
		ChartData data = new ChartData();
		data.title = "关注热度统计";
		List<Map.Entry<String, Integer>> yearList = DataManager.getYearRecordList();
		
		for(Entry<String, Integer> entry:yearList){
			data.pairs.add(new Pair(entry.getKey(),entry.getValue().intValue()));
		}
		return data;
	}
	
	public static ChartData getTestMotionData(int[] distribution){
		ChartData data = new ChartData();
		data.title = "舆情分析统计";
		for(int i=-5;i <= 5;i++){
			data.pairs.add(new Pair(String.valueOf(i),distribution[i+5]));
		}
		return data;
	}
	
	public static ChartData getTestIndexData()
	{
		ChartData data = new ChartData();
		data.title = "全网舆论指数";
		data.pairs.add(new Pair("index",DataManager.getOpinionIndex()[0]));
		System.out.print("indexofall"+(float)(Math.round(DataManager.getOpinionIndex()[0]*100)/100));
		return data;
	}
	
	public static ChartData getGovIndexData()
	{
		ChartData data = new ChartData();
		data.title = "政府舆论指数";
		data.pairs.add(new Pair("govIndex",DataManager.getOpinionIndex()[1]));
		System.out.print("indexofgov"+(float)(Math.round(DataManager.getOpinionIndex()[1]*100)/100));
		return data;
	}
	
	public static ChartData getMedIndexData()
	{
		ChartData data = new ChartData();
		data.title = "媒体舆论指数";
		data.pairs.add(new Pair("medIndex",DataManager.getOpinionIndex()[2]));
		System.out.print("indexofall"+(float)(Math.round(DataManager.getOpinionIndex()[2]*100)/100));
		return data;
	}
	
	public static ChartData getPubIndexData()
	{
		ChartData data = new ChartData();
		data.title = "公众舆论指数";
		data.pairs.add(new Pair("pubIndex",DataManager.getOpinionIndex()[3]));
		System.out.print("indexofall"+(float)(Math.round(DataManager.getOpinionIndex()[3]*100)/100));
		return data;
	}
}
