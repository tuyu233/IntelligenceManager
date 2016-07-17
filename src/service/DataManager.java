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

	static DataManager instance;
	
	public static DataManager getInstance(){
		if(instance == null) return new DataManager();
		else return instance;
	}
	
	//关键词
	private String keyword = null;
	public String getKeyword(){
		if(keyword == null){
			keyword = "";
		}
		return keyword;
	}
	public void setKeyword(String keyword){
		if(keyword != null) this.keyword = keyword;
	}
	public boolean haveKeyword(){
		return keyword != null;
	}
	
	//各类别记录
	private List<Record> recordsAll = null;
	public List<Record> getRecordsAll(){
		if(recordsAll == null){
			recordsAll = DatabaseHelper.search(getKeyword(), SearchType.ALL);
		}
		return recordsAll;
	}
	public void setRecordsAll(List<Record> recordsAll){
		this.recordsAll = recordsAll;
	}
	
	private List<Record> recordsGov = null;
	public List<Record> getRecordsGov(){
		if(recordsGov == null){
			recordsGov = DatabaseHelper.search(getKeyword(), SearchType.GOV);
		}
		return recordsGov;
	}
	public void setRecordsGov(List<Record> recordsGov){
		this.recordsGov = recordsGov;
	}
	
	private List<Record> recordsMedia = null;
	public List<Record> getRecordsMedia(){
		if(recordsMedia == null){
			recordsMedia = DatabaseHelper.search(getKeyword(), SearchType.MEDIA);
		}
		return recordsMedia;
	}
	public void setRecordsMedia(List<Record> recordsMedia){
		this.recordsMedia = recordsMedia;
	}
	
	private List<Record> recordsPublic = null;
	public List<Record> getRecordsPublic(){
		if(recordsPublic == null){
			recordsPublic = DatabaseHelper.search(getKeyword(), SearchType.PUBLIC);
		}
		return recordsPublic;
	}
	public void setRecordsPublic(List<Record> recordsPublic){
		this.recordsPublic = recordsPublic;
	}
	
	private List<Record> recordsGovMedia = null;
	public List<Record> getRecordsGovMedia(){
		if(recordsGovMedia == null){
			recordsGovMedia = DatabaseHelper.search(getKeyword(), SearchType.GOVMEDIA);
		}
		return recordsGovMedia;
	}
	public void setRecordsGovMedia(List<Record> recordsGovMedia){
		this.recordsGovMedia = recordsGovMedia;
	}
	
	//各类别舆论评分，顺序为全网、政府、媒体、公众
	private float[] opinionIndexes = null;
	public float[] getOpinionIndex(){
		if(opinionIndexes == null){
			Motion motion = new Motion();
			float[] opinionIndexes = {
					motion.getAssessmentAve(RecordTrans.records2strings(getRecordsAll())),
					motion.getAssessmentAve(RecordTrans.records2strings(getRecordsGov())),
					motion.getAssessmentAve(RecordTrans.records2strings(getRecordsMedia())),
					motion.getAssessmentAve(RecordTrans.records2strings(getRecordsPublic()))
			};
			this.opinionIndexes = opinionIndexes;
		}
		return opinionIndexes;
	}
	public void setOpinionIndex(float[] opinionIndexes){
		this.opinionIndexes = opinionIndexes;
	}
	
	//各类别关键词，顺序为全网、政府、媒体、公众
	private List<List<String>> keywords = null;
	public List<List<String>> getKeywords(){
		if(keywords == null){
			keywords = new ArrayList<List<String>>();
			keywords.add(service.keyword.Keyword.getKeyword(getRecordsAll(), properties.Configure.KEYWORD_SIZE_WHOLEWEB));
			keywords.add(service.keyword.Keyword.getKeyword(getRecordsGov(), properties.Configure.KEYWORD_SIZE_NORMAL));
			keywords.add(service.keyword.Keyword.getKeyword(getRecordsMedia(), properties.Configure.KEYWORD_SIZE_NORMAL));
			keywords.add(service.keyword.Keyword.getKeyword(getRecordsPublic(), properties.Configure.KEYWORD_SIZE_NORMAL));
		}
		return keywords;
	}
	public void setKeywords(List<List<String>> keywords){
		this.keywords = keywords;
	}

	//各类别记录数量，顺序为全网、政府、媒体、公众
	private int[] recordNums = null;
	public int[] getRecordNum(){
		if(recordNums == null){
			recordNums[0] = getRecordsAll().size();
			recordNums[1] = getRecordsGov().size();
			recordNums[2] = getRecordsMedia().size();
			recordNums[3] = getRecordsPublic().size();
		}
		return recordNums;
	}
	public void setRecordNum(int[] recordNum){
		this.recordNums = recordNum;
	}
	
	//各年份记录数量
	private HashMap<String, Integer> yearRecordNums = null;
	public HashMap<String, Integer> getYearRecordNums(){
		if(yearRecordNums == null){
			yearRecordNums = DatabaseHelper.count(getKeyword());
		}
		return yearRecordNums;
	}
	public void setYearRecordNums(HashMap<String, Integer> yearRecordNums){
		this.yearRecordNums = yearRecordNums;
	}
	
	//舆情分数分布
	private int[] opinionIndexDistribution = null;
	public int[] getOpinionIndexDistribution(){
		if(opinionIndexDistribution == null){
			Motion motion = new Motion();
			opinionIndexDistribution = motion.getAssessmentMap(util.RecordTrans.records2strings(getRecordsAll()));
		}
		return opinionIndexDistribution;
	}
	
	
}
