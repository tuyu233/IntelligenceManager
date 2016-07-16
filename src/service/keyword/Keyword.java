package service.keyword;

import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;

import entity.Record;

public class Keyword {

	
	//提取关键词
	public static List<String> getKeyword(String document, int size){
		return HanLP.extractKeyword(document, size);
	}
	
	public static List<String> getKeyword(List<Record> records, int size){
		List<String> document = new ArrayList<String>();
		for (Record record : records) {
			document.add(record.getContent());
		}
		
		StringBuffer sb = new StringBuffer();
		for (String string : document) {
			sb.append(string);
		}
		
		List<String> keywords = HanLP.extractKeyword(sb.toString(), size);
		
		for(int i = 0;i<keywords.size();i++){
			if(keywords.get(i).contains("公车")
					||keywords.get(i).contains("改革")
					||keywords.get(i).contains("车改")){
				keywords.remove(i);
				i--;
			}
		}
		return keywords;
	}
}
