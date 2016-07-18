package util;

import java.util.ArrayList;
import java.util.List;

import entity.Record;

public class RecordTrans {

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
}
