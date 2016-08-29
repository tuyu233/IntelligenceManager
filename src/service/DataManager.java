package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import properties.Configure;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.mysql.jdbc.Util;

import service.keyword.NLP;
import service.motion.Motion;
import util.Transform;
import database.DatabaseHelper;
import database.SearchType;
import entity.Record;

/**
 * 该类已静态化。
 * 该类用于暂存数据，以完成数据从计算部分到UI部分的传递。
 */
public class DataManager {
	static private int count1 = 0;
	static public void count1Plus(){
		count1++;
	}
	static public int getCount1(){
		return count1;
	}
	static public void resetCount1(){
		count1= 0;
	}
	

	static private int countPipeline = 0;
	static public void countPipelinePlus(){
		countPipeline++;
	}
	static public int getCountPipeline(){
		return countPipeline;
	}
	static public void resetCountPipeline(){
		countPipeline= 0;
	}
	
	static private HashSet<String> titlesHash = null;
	static public boolean isDuplicate(String toCheck){
		if(titlesHash == null){
			titlesHash.add(toCheck);
			return false;
		}
		if(titlesHash.contains(toCheck)){
			return true;
		}else{
			titlesHash.add(toCheck);
			return false;
		}
	}
	
	//关键词
	static private String keyword = null;
	static public String getKeyword(){
		if(keyword == null){
			keyword = "";
		}
		return keyword;
	}
	static public void setKeyword(String arg){
		if(arg != null){
			if(arg != keyword){
				keyword = arg;
				reset();
			}
		}
	}
	static public boolean haveKeyword(){
		return keyword != null;
	}
	
	//各类别记录
	static private List<Record> recordsAll = null;
	static public List<Record> getRecordsAll(){
		System.out.print("getRecordAll called\n");
		if(recordsAll == null){
			recordsAll = DatabaseHelper.search(getKeyword(), SearchType.ALL);
			System.out.print("RecordsAll get! List size:" + recordsAll.size() +"\n");
		}
		return recordsAll;
	}
	
	static private List<Record> recordsGov = null;
	static public List<Record> getRecordsGov(){
		System.out.print("getRecordGov called\n");
		if(recordsGov == null){
			recordsGov = DatabaseHelper.search(getKeyword(), SearchType.GOV);
			System.out.print("RecordsGov get!\n");
		}
		return recordsGov;
	}
	
	static private List<Record> recordsMedia = null;
	static public List<Record> getRecordsMedia(){
		System.out.print("getRecordMedia called\n");
		if(recordsMedia == null){
			recordsMedia = DatabaseHelper.search(getKeyword(), SearchType.MEDIA);
			System.out.print("RecordsMedia get!\n");
		}
		return recordsMedia;
	}
	
	static private List<Record> recordsPublic = null;
	static public List<Record> getRecordsPublic(){
		System.out.print("getRecordPublic called\n");
		if(recordsPublic == null){
			recordsPublic = DatabaseHelper.search(getKeyword(), SearchType.PUBLIC);
			System.out.print("RecordsPublic get!\n");
		}
		return recordsPublic;
	}
	
	static private List<Record> recordsGovMedia = null;
	static public List<Record> getRecordsGovMedia(){
		System.out.print("getRecordGovMedia called\n");
		if(recordsGovMedia == null){
			recordsGovMedia = DatabaseHelper.search(getKeyword(), SearchType.GOVMEDIA);
			System.out.print("RecordsGovMedia get!\n");
			
		}
		return recordsGovMedia;
	}
	
	static private List<Record> recordsHottestYear = null;
	static public List<Record> getRecordsHottestYear(){
		if(recordsHottestYear == null){
			recordsHottestYear = DatabaseHelper.search(getKeyword(), getHottestYear());
		}
		return recordsHottestYear;
	}
	
	//返回从参数中随机选取的少量records
	static public List<Record> reduceRecords(List<Record> origin, int reductTo){
		int size = origin.size();
		if(size <= reductTo) return origin;
		
		List<Record> reduced = new ArrayList<Record>();
		for(int i=0;i<reductTo;i++){
			reduced.add(origin.get((int) (Math.random()*size)));
		}
		return reduced;
	}
	
	static private String hottestYear = null;
	static public String getHottestYear(){
		if(hottestYear == null){
			Map<String, Integer> count = DatabaseHelper.count(getKeyword());
			int max = 0;
			for(int i=2016;i>2000;i--){
				int tmp = 0;
				if(count.get(Integer.toString(i))!=null) tmp = count.get(Integer.toString(i));
				if(tmp>max){
					max = tmp;
					hottestYear = Integer.toString(i);
				}
			}
		}
		return hottestYear;
	}

	//各舆论分数段记录集
	static private List<List<Record>> recordsOpinionIndexDistribution = null;
	static public List<List<Record>> getRecordsOpinionIndexDistribution(){
		if(recordsOpinionIndexDistribution == null){
			ArrayList<List<Record>> tmp = new ArrayList<List<Record>>(11);
			for(int i=0;i<11;i++) tmp.add(new ArrayList<Record>());
			for (Record record : getRecordsAll()) {
				int i = Math.round(Float.valueOf(record.getOther())*10);
				tmp.get(i).add(record);
			}
			recordsOpinionIndexDistribution = tmp;
		}
		return recordsOpinionIndexDistribution;
	}
	
	
	//各类别舆论评分，顺序为全网、政府、媒体、公众
	static private float[] opinionIndexes = null;
	static public float[] getOpinionIndex(){
		System.out.print("getOpinionIndex called\n");
		if(opinionIndexes == null){
			float sum = 0;
			
			float indexAll = 0.5f;
			if(getRecordsAll().size() != 0) {
			sum = 0;
			for (Record record : getRecordsAll()) {
				sum += Float.valueOf(record.getOther());
			}
			indexAll = sum/getRecordsAll().size();
			}

			float indexGov = 0.5f;
			if(getRecordsGov().size() != 0) {
			sum = 0;
			for (Record record : getRecordsGov()) {
				sum += Float.valueOf(record.getOther());
			}
			indexGov = sum/getRecordsGov().size();
			}

			float indexMedia = 0.5f;
			if(getRecordsMedia().size() != 0) {
			sum = 0;
			for (Record record : getRecordsMedia()) {
				sum += Float.valueOf(record.getOther());
			}
			indexMedia = sum/getRecordsMedia().size();
			}

			float indexPublic = 0.5f;
			if(getRecordsPublic().size() != 0) {
			sum = 0;
			for (Record record : getRecordsPublic()) {
				sum += Float.valueOf(record.getOther());
			}
			indexPublic = sum/getRecordsPublic().size();
			}
			
			float[] tmp = {(float)(indexAll-0.5)*10, (float)(indexGov-0.5)*10, (float)(indexMedia-0.5)*10, (float)(indexPublic-0.5)*10};
			opinionIndexes = tmp;
			System.out.print("OpinionIndex:");
			System.out.print(tmp[0] +" "+ tmp[1] +" "+ tmp[2] +" "+ tmp[3] + "\n");
		}
		return opinionIndexes;
	}
	
	//舆情分数分布
	static public int[] getOpinionIndexDistribution(List<Record> records){
		int[] opinionIndexDistribution = null;
		opinionIndexDistribution = new int[11];
		for(int i=0;i<11;i++) opinionIndexDistribution[i] = 0;
		for (Record record : records) {
			opinionIndexDistribution[Math.round(Float.valueOf(record.getOther())*10)]++;
		}
		System.out.print("OpinionIndexDistribution: ");
		System.out.print(opinionIndexDistribution[0] +" "+ 
				opinionIndexDistribution[1] +" "+ 
				opinionIndexDistribution[2] +" "+ 
				opinionIndexDistribution[3] +" "+ 
				opinionIndexDistribution[4] +" "+ 
				opinionIndexDistribution[5] +" "+ 
				opinionIndexDistribution[6] +" "+ 
				opinionIndexDistribution[7] +" "+ 
				opinionIndexDistribution[8] +" "+ 
				opinionIndexDistribution[9] +" "+ 
				opinionIndexDistribution[10]);
		System.out.print("\n");
		return opinionIndexDistribution;
	}
	
	static private int[] allDistribution = null;
	static public int[] getAllDistribution(){
		if(allDistribution == null){
			allDistribution = getOpinionIndexDistribution(getRecordsAll());
		}
		return allDistribution;
	}
	
	static private int[] govDistribution = null;
	static public int[] getGovDistribution(){
		if(govDistribution == null){
			govDistribution = getOpinionIndexDistribution(getRecordsGov());
		}
		return govDistribution;
	}
	
	static private int[] mediaDistribution = null;
	static public int[] getMediaDistribution(){
		if(mediaDistribution == null){
			mediaDistribution = getOpinionIndexDistribution(getRecordsMedia());
		}
		return mediaDistribution;
	}
	
	static private int[] publicDistribution = null;
	static public int[] getPublicDistribution(){
		if(publicDistribution == null){
			publicDistribution = getOpinionIndexDistribution(getRecordsPublic());
		}
		return publicDistribution;
	}
	
	public static int getNegMax(){
		int[] index = getAllDistribution();
		int max = 0;
		int tmp = 0;
		for(int i=0;i<5;i++){
			if(index[i]>max){
				max=index[i];
				tmp = i;
			}
		}
		return tmp-5;
	}
	public static int getPosMax(){
		int[] index = getAllDistribution();
		int max = 0;
		int tmp = 6;
		for(int i=6;i<11;i++){
			if(index[i]>max){
				max=index[i];
				tmp = i;
			}
		}
		return tmp-5;
	}
	
	//各类别关键词，顺序为全网、政府、媒体、公众
	static private List<List<String>> keywords = null;
	static public List<List<String>> getKeywords(){
		System.out.print("getKeywords called\n");
		if(keywords == null){
			keywords = new ArrayList<List<String>>();
			keywords.add(service.keyword.Keyword.getKeyword(reduceRecords(getRecordsAll(), Configure.REDUCE_RECORD_SIZE_KEYWORDS), properties.Configure.KEYWORD_SIZE_WHOLEWEB));
			keywords.add(service.keyword.Keyword.getKeyword(reduceRecords(getRecordsGov(), Configure.REDUCE_RECORD_SIZE_KEYWORDS), properties.Configure.KEYWORD_SIZE_NORMAL));
			keywords.add(service.keyword.Keyword.getKeyword(reduceRecords(getRecordsMedia(), Configure.REDUCE_RECORD_SIZE_KEYWORDS), properties.Configure.KEYWORD_SIZE_NORMAL));
			keywords.add(service.keyword.Keyword.getKeyword(reduceRecords(getRecordsPublic(), Configure.REDUCE_RECORD_SIZE_KEYWORDS), properties.Configure.KEYWORD_SIZE_NORMAL));
			List<String> tmp = service.keyword.Keyword.getKeyword(reduceRecords(getRecordsHottestYear(), Configure.REDUCE_RECORD_SIZE_KEYWORDS), properties.Configure.KEYWORD_SIZE_NORMAL);
			tmp.add(0, getHottestYear());
			keywords.add(tmp);
			
			System.out.print("keywords:\n");
			for(List<String> each:keywords){
				System.out.print(each);
				System.out.print("\n");
			}
		}
		return keywords;
	}
	
	static private List<List<String>> nounKeywords = null;
	static public List<List<String>> getNounKeywords(){
		if(nounKeywords == null){
			List<List<String>> rawKeywords = (List<List<String>>) ((ArrayList<List<String>>)getKeywords()).clone();
			for (int i=0;i<rawKeywords.size();i++) {
				rawKeywords.set(i, keepNoun(rawKeywords.get(i)));
			}
			nounKeywords = rawKeywords;
			System.out.print("noun keywords:\n");
			for(List<String> each:nounKeywords){
				System.out.print(each);
				System.out.print("\n");
			}
		}
		return nounKeywords;
	}
	
	static private List<String> keepNoun(List<String> raw){
		if(raw.size() == 0){
			List<String> tmp = new ArrayList<String>();
			for(int i=0;i<4;i++) tmp.add("无数据");
			return tmp;
		}
		int counter = 0;
		for (int i=0;i<raw.size();i++) {
			Nature nature = HanLP.segment(raw.get(i)).get(0).nature;
			if(!(nature.startsWith('n')
					||nature.startsWith("vn")
					||nature.startsWith("an"))){
				raw.remove(raw.get(i));
				i--;
			}else {
				counter++;
			}
			if(counter == 4) break;
		}
		if(raw.size() < 4){
			for(int j=raw.size();j<4;j++){
				raw.add("无数据");
			}
		}
		return raw;
	}

	//各类别记录数量，顺序为全网、政府、媒体、公众
	static private int[] recordNums = null;
	static public int[] getRecordNum(){
		System.out.print("getRecordNum called\n");
		if(recordNums == null){
			recordNums = new int[4];
			recordNums[0] = getRecordsAll().size();
			recordNums[1] = getRecordsGov().size();
			recordNums[2] = getRecordsMedia().size();
			recordNums[3] = getRecordsPublic().size();
		}
		return recordNums;
	}
	
	//各年份记录数量
	static private List<Map.Entry<String, Integer>> yearRecordList = null;
	static public List<Map.Entry<String, Integer>> getYearRecordList(){
		System.out.print("getYearRecordNums called\n");
		if(true){//TODO 这里有一个bug，该bug表现为若这里判断条件为yearRecordList==null，则会在某些未赋值的时候不为null，导致不经过下面的运算而返回错误结果。该错误结果为按数量也就是Integer排序
			Map<String, Integer> yearRecordMap = DatabaseHelper.count(getKeyword());
			yearRecordList = new ArrayList<Map.Entry<String, Integer>>(
					yearRecordMap.entrySet());
			Collections.sort(yearRecordList, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1,
						Map.Entry<String, Integer> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey().toString());
				}
			});
		}
		return yearRecordList;
	}
	
	static private HashMap<String, Integer> yearRecordNums = null;
	static public HashMap<String, Integer> getYearRecordNums(){
		System.out.print("getYearRecordNums called\n");
		if(yearRecordNums == null){
			yearRecordNums = DatabaseHelper.count(getKeyword());
		}
		return yearRecordNums;
	}
	
	static private void reset(){
		count1 = 0;
		countPipeline = 0;
		titlesHash = null;
		recordsAll = null;
		recordsGov = null;
		recordsMedia = null;
		recordsPublic = null;
		recordsGovMedia = null;
		recordsHottestYear = null;
		hottestYear = null;
		recordsOpinionIndexDistribution = null;
		opinionIndexes = null;
		allDistribution = null;
		govDistribution = null;
		mediaDistribution = null;
		publicDistribution = null;
		keywords = null;
		recordNums = null;
		yearRecordList = null;
		yearRecordNums = null;
	}
}
