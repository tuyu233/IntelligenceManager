package service.motion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.List;
import java.util.ArrayList;
public class newTest {
	public static void main(String [] args){
		FileInputStream file= null;
		try {
			file = new FileInputStream ("W:/libWork/comment.txt");
			InputStreamReader isr = new InputStreamReader(file,"gbk");
			BufferedReader br = new BufferedReader (isr);
			String sFile = null, stemp=null;
			while((stemp = br.readLine())!=null){
				sFile += stemp;
			}
			file.close();
			Motion nm = new Motion();
			System.out.println(nm.getAssessment(null));
			System.out.println(nm.getAssessmentAve(null));
			nm.getAssessmentMap(null);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
}
