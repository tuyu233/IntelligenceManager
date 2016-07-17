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
			int i=1;
			List<String> ls = new ArrayList<String>() ;
			while(i<9){
				i++;
				sFile=null;
				while((stemp=br.readLine())!=null){
					if(stemp.equals(new Integer(i).toString()))
						break;
					sFile +=stemp;
				}
				ls.add(sFile);
			}
			file.close();
			Motion nm = new Motion();
			nm.DicInit();
			int a[]=nm.getAssessmentMap(ls);
			for( i =0 ;i<11;i++)
				System.out.println(a[i]);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}
