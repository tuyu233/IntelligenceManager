package util;

import java.util.ArrayList;
import java.util.List;

import entity.Record;

public class Transform {

	/**
	 * Extract the content from records
	 * @param records
	 * @return List<String> content
	 */
	public static List<String> records2strings(List<Record> records){
		List<String> strings = new ArrayList<String>();
		for (Record record : records) {
			strings.add(record.getContent());
		}
		return strings;
	}
	
	/**
	 * Extract the content from records into one String
	 */
	public static String records2string(List<Record> records){
		return strings2string(records2strings(records));
	}
	
	/**
	 * Trans List<String> to String
	 */
	public static String strings2string(List<String> strings){
		StringBuffer sb = new StringBuffer();
		for (String string : strings) {
			sb.append(string);
		}
		return sb.toString();
	}
	
	/**
	 * Trans List<String> to one String with comma
	 * @param strings
	 * @return
	 */
	public static String strings2stringWithComma(List<String> strings){
		StringBuffer sb = new StringBuffer();
		for (String string : strings) {
			sb.append(string);
			if(strings.indexOf(string)==strings.size()-1) continue;
			else sb.append(",");
		}
		return sb.toString();
	}
	
	public static boolean containsPartOfKeyword(String origin, String keyword){
		return origin.matches(".*["+keyword+"].*");
	}
}
