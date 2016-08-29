 package service.keyword;

import java.util.*;

import properties.Configure;
import service.motion.Motion;

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
				summary += (HanLP.getSummary(input[i], length ));
			else 
				summary += (HanLP.getSummary(input[i], length /2));
			if(!summary.equals(""))
				summary += "\n";
		}
		return summary;
	}
	
	//多文本摘要
	public static String recordsSummary(List<Record> records,int level){
		List<String> summaries = HanLP.extractSummary(util.Transform.records2string(records), Configure.SUMMARY_SIZE);
		StringBuffer sb = new StringBuffer();
		System.out.println("当前的level:"+level);
		for (String string : summaries) {
			/*System.out.print("String = "+string+"level=");
			System.out.println((int)(Motion.getAssessment(string)*10)-6);*/
			if((level==5 && (int)(Motion.getAssessment(string)*10)>5)||(level==-5 && (int)(Motion.getAssessment(string)*10)<5)){
				System.out.println(string);
				sb.append(string);
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}