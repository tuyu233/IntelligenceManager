package reportfactory;

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
		HashMap<String, Integer> tmp = DataManager.getYearRecordNums();
		List<Map.Entry<String, Integer>> entrytmp = new ArrayList<Map.Entry<String, Integer>>(
				tmp.entrySet());
		Collections.sort(entrytmp, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey().toString());
			}
		});
		
		for(Entry<String, Integer> entry:entrytmp){
			data.pairs.add(new Pair(entry.getKey(),entry.getValue().intValue()));
		}
		return data;
	}
	
	public static ChartData getTestMotionData(){
		ChartData data = new ChartData();
		data.title = "舆情分析统计";
		for(int i=-5;i <= 5;i++){
			data.pairs.add(new Pair(String.valueOf(i),DataManager.getOpinionIndexDistribution()[i+5] ));
		}
		return data;
	}
}
