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
}
