package reportfactory;

import java.util.ArrayList;
import java.util.List;

import database.Pair;

public class ChartData {
	public String title = "title";
	public List<Pair> pairs = new ArrayList<>();
	public String legendName = "数量";
	public static ChartData getTestPipData(){
		ChartData data = new ChartData();
		data.title = "关注主题统计";
		data.pairs.add(new Pair("政府",233));
		data.pairs.add(new Pair("公众", 666));
		data.pairs.add(new Pair("媒体", 222));
		return data;
	}
	
	public static ChartData getTestYearData(){
		ChartData data = new ChartData();
		data.title = "关注热度统计";
		data.legendName = "热度";
		for(int i=2000;i < 2016;i++){
			data.pairs.add(new Pair(String.valueOf(i),(int)(Math.random()*50) ));
		}
		return data;
	}
	
	public static ChartData getTestMotionData(){
		ChartData data = new ChartData();
		data.title = "舆情分析统计";
		data.legendName = "评价数";
		for(int i=-5;i <= 5;i++){
			data.pairs.add(new Pair(String.valueOf(i),(int)(Math.random()*50) ));
		}
		return data;
	}
}
