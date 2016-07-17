package vision;

import java.util.List;

public class Util 
{
	public static String tagCloudTrans(List<String> list)
	{
		String str="";
		for(int i=0;i<list.size();i++)
		{
			if(i==0)
				str=str+list.get(i)+","+Integer.toString((50-i));
			else 
				str=str+"\n"+list.get(i)+","+Integer.toString(50-i);
		}
		return str;
	}
	
	public static String transFormat(List<String> list)
	{
		String str="";
		for(int i=0;i<list.size();i++)
		{
			if(i==0)
				str=str+list.get(i);
			else 
				str=str+"  "+list.get(i);
		}
		return str;
	}
}
