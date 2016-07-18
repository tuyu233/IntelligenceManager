 package service.keyword;

import java.util.*;

import com.hankcs.hanlp.*;

import entity.Record;

public class NLP 
{
	//文本摘要
	public static String stringSummary(String inputstr) 
	{
		String summary = new String();
		String[] input = inputstr.split("\n");
		for(int i = 0; i < input.length; ++i) {
			input[i].replaceAll(" ", "").replaceAll("　", "").replaceAll("\t","").replaceAll("\n","");
			int length = input[i].length();
			if(length==0)
				continue;
			if(length < 30)
				summary += (input[i]);
			else if(i < 2 || i == input.length - 1)
				summary += (HanLP.getSummary(input[i], length / 2));
			else 
				summary += (HanLP.getSummary(input[i], length / 5));
			if(!summary.equals(""))
				summary += "\n";
		}
		return summary;
	}
	
	//多文本摘要
	public static String recordsSummary(List<Record> records){
		List<String> summaries = HanLP.extractSummary(util.RecordTrans.records2string(records), records.size()*3);
		StringBuffer sb = new StringBuffer();
		sb.append(summaries);
		return sb.toString();
	}
}