package service.motion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;
public class newTest {
	public static void main(String [] args) {
		System.out.println(Motion.getAssessment("第一批公务用车改革完成后"));
		/*FileInputStream file= null;
		try {
			file = new FileInputStream ("W:/libWork/comment.txt");
			InputStreamReader isr = new InputStreamReader(file,"gbk");
			BufferedReader br = new BufferedReader (isr);
			String sFile = null, stemp=null;
			while((stemp = br.readLine())!=null){
				sFile += stemp;
			}
			file.close();
			System.out.println( Motion.getAssessment(sFile) );
		
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		/*
		try {
		file = new FileInputStream ("W:/libWork/comment.txt");
		InputStreamReader isr = null;
		
			isr = new InputStreamReader(file,"gbk");
		BufferedReader br = new BufferedReader(isr);
		int i =0;
		String st =null;
		st=br.readLine();
		List <String >   l = new ArrayList<String>();
		while(i<8){
			st=br.readLine();
			l.add(st);
			i++;
		}
		System.out.println("====================");
		System.out.println(Motion.getAssessmentAve(l));System.out.println("====================");
		System.out.println(Motion.getAssessmentAve(l));System.out.println("====================");
		System.out.println(Motion.getAssessmentAve(l));System.out.println("====================");
		System.out.println(Motion.getAssessmentAve(l));
		
		System.out.println("====================");
		
		int[] ar1=Motion.getAssessmentMap(l);
		for(i=0;i<11;i++)
			System.out.println(ar1[i]);
		System.out.println("====================");
		int[] ar2=Motion.getAssessmentMap(l);
		for(i=0;i<11;i++)
			System.out.println(ar2[i]);
		System.out.println("====================");
		int[] ar3=Motion.getAssessmentMap(l);
		for(i=0;i<11;i++)
			System.out.println(ar3[i]);
		System.out.println("====================");
		int[] ar4=Motion.getAssessmentMap(l);
		for(i=0;i<11;i++)
			System.out.println(ar4[i]);
		
		}catch (Exception e) {
			e.printStackTrace();
		}*/
	}	
	
}
