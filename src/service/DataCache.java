package service;

import java.util.List;

/*
 * 该类已单例化，请调用静态方法getInstance来获取实例。
 * 该类用于暂存数据，以完成数据从计算部分到UI部分的传递。
 */
public class DataCache {

	static DataCache instance;
	private DataCache(){
	}
	/*
	 * 获取实例
	 */
	public static DataCache getInstance(){
		if(instance == null) return new DataCache();
		else return instance;
	}
	
	//关键词
	private String keyword = null;
	public String getKeyword(){
		return keyword;
	}
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}
	public boolean haveKeyword(){
		return keyword != null;
	}
	
	//各类别记录数量，顺序为全网、政府、媒体、公众
	private int[] recordNums;
	public int[] getRecordNum(){
		return recordNums;
	}
	public void setRecordNum(int[] recordNum){
		this.recordNums = recordNum;
	}
	
	//各类别舆论评分，顺序为全网、政府、媒体、公众
	private int[] opinionIndexes;
	public int[] getOpinionIndex(){
		return opinionIndexes;
	}
	public void setOpinionIndex(int[] opinionIndexes){
		this.opinionIndexes = opinionIndexes;
	}
	
	//各类别关键词，顺序为全网、政府、媒体、公众
	private List<String>[] keywords;
	public List<String>[] getKeywords(){
		return keywords;
	}
	public void setKeywords(List<String>[] keywords){
		this.keywords = keywords;
	}
	
	
}
