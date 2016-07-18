package service.motion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;

class DicStruct{
	/*
	 * 这是type的完整信息，词典仍然提供的是完整的词典，为了方便以后软件升级
	 * PA：快乐  PE：安心  PD：尊敬  PH：赞扬  PG：相信  PB：喜爱 
	 * PK：祝愿  NA：愤怒  NB：悲伤  NJ：失望  NH：愧疚  PF：思念
	 * NI：慌    NC：恐惧  NG：羞    NE：烦闷  ND：憎恶  NN：贬责
	 * NK：妒忌  NL：怀疑  PC：惊奇
	 * 
	 * 但是这里为了程序的高效性和准确性，只将PA，PE，PH，PB，PK，
	 * NA，NJ，ND，NN这九类词收入map，这九类词在本程序中只分为两类
	 * P开头的type=1，N开头的type=2
	 * 
	 * 其次还有词典中的副词，也将被收录，副词的type为3
	 * level为：0.3 0.6 0.9 1.2 1.5 1.8
	 * 
	 * 否定词的type为4
	 * 
	 * 有假设关联词的句子视为不包含情感的句子，type为5
	 * 
	 * 有反问词的视为相反感情的句子,type为4
	 * 
	 */
	protected float level=0;
	protected int type=1;
}

class fStruct{
	/*
	 * sw1 存储词语
	 * sw2 存储由 hanlp 分词后的词性
	 * 如：玩具/n 那么sw1=“玩具”sw2="n"
	 */
	protected String sw1 = null;
	protected String sw2 = null;
}

/*
 * 这是存储种子情感词的结构
 */
class wStruct{
	protected String sw1;//单词
	protected float level=0;//程度
	protected int type =0;//类型
}

public class Motion {
	/*
	 * 计算某字符串的情感评估值 float getAssessment(String)
		计算输入的所有字符串的情感评估值的平均值 float getAssessmentAve(List<String>)
		计算输入的所有字符串的情感评估值的分布 int[] getAssessmentMap(List<String>)
	 */
	Map<String , DicStruct> map =new HashMap<String , DicStruct>();
	
	private float posMot = 0;
	private float negMot = 0;
	
	public Motion(){
		DicInit();
	}
	
	public float getAssessment(String s){
		/*
		 * 计算传入的字符串s的情感评估值
		 */
		if(s==null)
			return (float)0.5;
		String [] sArray = stringIntoWord(s);//分成句号为单位
		for(int i = 0, len = sArray.length , posMot = 0 , negMot = 0 ; i < len ; i++){
			ArrayList<fStruct> alfTemp = getWord(sArray[i]);//一句话分成单词为单位
			ArrayList<wStruct> alwTemp = getSeq(alfTemp);//获取情感词语的顺序
			if(alwTemp!=null)
				getMotion(alwTemp);
		}
		if(posMot==0 && negMot==0)
			return (float)0.5;
		else 
			return posMot/(posMot+negMot*3);
		
	}
	
	public float getAssessmentAve(List<String> ls){
		if(ls==null)
			return (float)0.5;
		float sum =0 ;
		int len = ls.size();
		for(int i=0 ;i<len;i++){
			sum = sum + getAssessment(ls.get(i));
			//System.out.println("i: "+i+" "+getAssessment(ls.get(i))+ " sum "+sum);
		}
		//System.out.println("sum: "+sum+" len: "+len);
		return (float)(sum/len);
	}
	
	
	public int[] getAssessmentMap(List<String> ls){
		if(ls==null)
			return null;
		int array[]=new int[11];
		for(int i=0;i<11;i++)
			array[i]=0;
		
		for(int i =0 , len = ls.size(); i < len ;i++){
			float temp = getAssessment(ls.get(i));
			int x = (int)(temp*10);
			array[x]++;
		}

		return array;
		
	}
	
	
	private void getMotion(ArrayList<wStruct> alwTemp) {
		float pos=0, neg=0, plus=1;
		int not=0;
		for(int i=0 , len = alwTemp.size() ;i < len ;i++){
			//System.out.println(alwTemp.get(i).sw1);
			if(alwTemp.get(i).type==3){
				plus=alwTemp.get(i).level;
			}else if(alwTemp.get(i).type==2 || (alwTemp.get(i).type==1 && not==1)){
				neg += alwTemp.get(i).level*plus;
				plus = 1 ;
				not = 0 ;
			}else if(alwTemp.get(i).type==1 || (alwTemp.get(i).type==2 && not ==1)){
				pos += alwTemp.get(i).level*plus;
				plus = 1 ;
				not = 0;
			}else if(alwTemp.get(i).type==4)
				not = 1;
		}
		posMot += pos;
		negMot += neg;
	}

	private ArrayList<wStruct> getSeq( ArrayList<fStruct> alTemp ){
		if(alTemp==null)
			return null;
		ArrayList<wStruct> alw = new ArrayList<wStruct>();
		for(int i = 0, len = alTemp.size(); i < len ; i++){
			if((map.get(alTemp.get(i).sw1))!=null){
				if((map.get(alTemp.get(i).sw1).type) == 5)
					return null;
				wStruct wTemp = new wStruct();
				wTemp.level=map.get(alTemp.get(i).sw1).level;
				wTemp.sw1=alTemp.get(i).sw1;
				wTemp.type=map.get(alTemp.get(i).sw1).type;
				alw.add(wTemp);
			}
		}
		return alw;
	}
	
	private String[] stringIntoWord(String s){
		/*
		 * 把s以句号为单位分成多个字符串，返回一个String数组
		 */
		return s.split("。|？");
	}
	
	private ArrayList<fStruct> getWord(String s){
		/*
		 * 把s这一句话的词用hanlp的分词工具分为单词的形式 
		 */
		if(s==null)
			return null;
		String s0=null;
		ArrayList<fStruct> al = new ArrayList<fStruct>();
		List<Term> termList = IndexTokenizer.segment(s);
		for (Term term : termList){
			String[] sa = term.toString().split("/");
			fStruct fSTemp = new fStruct();
			fSTemp.sw1=sa[0];
			fSTemp.sw2=sa[1];
			if(fSTemp.sw1.equals("改革")){
				continue;
			}
			al.add(fSTemp);
		}
		return al;
	}
	
	protected void DicInit(){
		/*
		 * 将 词典存入map中
		 */
		FileInputStream file1 = null,file2 = null, file3 = null, file4 =null,file5 =null,file6 = null;
		try {
			file1= new FileInputStream("./resource/dict/term.txt");
			file2= new FileInputStream("./resource/dict/term-level.txt");
			file3= new FileInputStream("./resource/dict/type.txt");
			file4= new FileInputStream("./resource/dict/level.txt");
			//file5= new FileInputStream("./resource/dict/deny.txt");
			file6= new FileInputStream("./resource/dict/file-if.txt");
			InputStreamReader isr1 = new InputStreamReader(file1,"gbk");
			InputStreamReader isr2 = new InputStreamReader(file2,"gbk");
			InputStreamReader isr3 = new InputStreamReader(file3,"gbk");
			InputStreamReader isr4 = new InputStreamReader(file4,"gbk");
			//InputStreamReader isr5 = new InputStreamReader(file5,"gbk");
			InputStreamReader isr6 = new InputStreamReader(file6,"gbk");
			BufferedReader br1 = new BufferedReader( isr1 );
			BufferedReader br2 = new BufferedReader( isr2 );
			BufferedReader br3 = new BufferedReader( isr3 );
			BufferedReader br4 = new BufferedReader( isr4 );
			//BufferedReader br5 = new BufferedReader( isr5 );
			BufferedReader br6 = new BufferedReader( isr6 );
			String sTmp1, sTmp2, sTmp3;
			DicStruct ds = null;
			while((sTmp1 = br1.readLine())!= null && (sTmp2 = br2.readLine())!= null
					&& (sTmp3 = br3.readLine())!= null){
				if(sTmp3.equals("1")){
					ds = new DicStruct();
					ds.level = new Integer(sTmp2).intValue();
					ds.type = 1;
					map.put(sTmp1, ds);
				}else if(sTmp3.equals("2")){
					ds = new DicStruct();
					ds.level = new Integer(sTmp2).intValue();
					ds.type = 2;
					map.put(sTmp1, ds);
				}
			}
			file1.close();
			file2.close();
			file3.close();
			int i = 6 ;
			sTmp1=br4.readLine();
			while ( i >= 2 ){
				float mlevel = i;
				i--;
				while((sTmp1 = br4.readLine())!=null){
					if(sTmp1.equals((new Integer(i)).toString()))
						break;
					ds = new DicStruct ();
					ds.level=(float) (mlevel*0.3);//程度
					ds.type=3;//词性为副词
					map.put(sTmp1, ds);
				}
			}
			file4.close();
			ds = new DicStruct();
			ds.level=0;
			ds.type=4;
			map.put("不", ds);
			
			while((sTmp1 = br6.readLine())!=null){
				ds = new DicStruct ();
				ds.type=5;//假设关联词
				map.put(sTmp1, ds);
			}
			/*
			 * 临时添加区域
			 */
			addW("不是",4,0);
			addW("非",4,0);
			addW("并非",4,0);
			addW("支持",1,5);
			addW("反对",2,5);
			addW("同意",1,5);
			addW("难道",4,0);
			addW("怎么",4,0);
			/*
			 * 临时删除区域
			 */
			removeW("建设");
			removeW("智慧");
			removeW("专家");
			removeW("节约");
			removeW("标准");
			removeW("发达");
			removeW("大方");
			removeW("准绳");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void removeW(String s){
		map.remove(s);
	}
	
	private void addW(String s,int type,float level) {
		// TODO Auto-generated method stub
		DicStruct ds = new DicStruct();
		ds.type=type;
		ds.level=level;
		map.put(s, ds);
	}
}
