package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.Util;

import service.motion.Motion;
import util.RecordTrans;
import database.DatabaseHelper;
import database.SearchType;
import entity.Record;

/**
 * 该类已单例化，请调用静态方法getInstance来获取实例。
 * 该类用于暂存数据，以完成数据从计算部分到UI部分的传递。
 */
public class DataManager {
	
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
	
	//各类别舆论评分，顺序为全网、政府、媒体、公众
	static private float[] opinionIndexes = null;
	static public float[] getOpinionIndex(){
		System.out.print("getOpinionIndex called\n");
		if(opinionIndexes == null){
			Motion motion = new Motion();
			float indexAll = motion.getAssessmentAve(RecordTrans.records2strings(getRecordsAll()));
			motion = new Motion();
			float indexGov = motion.getAssessmentAve(RecordTrans.records2strings(getRecordsGov()));
			motion = new Motion();
			float indexMedia = motion.getAssessmentAve(RecordTrans.records2strings(getRecordsMedia()));
			motion = new Motion();
			float indexPublic = motion.getAssessmentAve(RecordTrans.records2strings(getRecordsPublic()));
			float[] tmp = {indexAll, indexGov, indexMedia, indexPublic};
			opinionIndexes = tmp;
			System.out.print("OpinionIndex:");
			System.out.print(tmp[0] +" "+ tmp[1] +" "+ tmp[2] +" "+ tmp[3] + "\n");
		}
		return opinionIndexes;
	}
	
	//各类别关键词，顺序为全网、政府、媒体、公众
	static private List<List<String>> keywords = null;
	static public List<List<String>> getKeywords(){
		System.out.print("getKeywords called\n");
		if(keywords == null){
			keywords = new ArrayList<List<String>>();
			keywords.add(service.keyword.Keyword.getKeyword(getRecordsAll(), properties.Configure.KEYWORD_SIZE_WHOLEWEB));
			keywords.add(service.keyword.Keyword.getKeyword(getRecordsGov(), properties.Configure.KEYWORD_SIZE_NORMAL));
			keywords.add(service.keyword.Keyword.getKeyword(getRecordsMedia(), properties.Configure.KEYWORD_SIZE_NORMAL));
			keywords.add(service.keyword.Keyword.getKeyword(getRecordsPublic(), properties.Configure.KEYWORD_SIZE_NORMAL));
		}
		return keywords;
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
	static private HashMap<String, Integer> yearRecordNums = null;
	static public HashMap<String, Integer> getYearRecordNums(){
		System.out.print("getYearRecordNums called\n");
		if(yearRecordNums == null){
			yearRecordNums = DatabaseHelper.count(getKeyword());
		}
		return yearRecordNums;
	}
	
	//舆情分数分布
	static private int[] opinionIndexDistribution = null;
	static public int[] getOpinionIndexDistribution(){
		System.out.print("getOpinionIndexDistribution called\n");
		if(opinionIndexDistribution == null){
			Motion motion = new Motion();
			opinionIndexDistribution = motion.getAssessmentMap(RecordTrans.records2strings(getRecordsAll()));
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
		}
		return opinionIndexDistribution;
	}
	
	static private void reset(){
		recordsAll = null;
		recordsGov = null;
		recordsMedia = null;
		recordsPublic = null;
		recordsGovMedia = null;
		opinionIndexes = null;
		keywords = null;
		recordNums = null;
		yearRecordNums = null;
		opinionIndexDistribution = null;
	}
}
