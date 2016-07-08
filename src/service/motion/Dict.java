package service.motion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Dict {
	ArrayList<String> name = new ArrayList<String> ();
	public Dict(String fileName){
		this.readFile(fileName);
	}
	public void readFile(String fileName)
	{
	    try   
	    {       
	        File f = new File(fileName);      
	        if(f.isFile()&&f.exists())  
	        {       
	            InputStreamReader read = new InputStreamReader(new FileInputStream(f),"gbk");       
	            BufferedReader reader=new BufferedReader(read);       
	            String line;       
	            while ((line = reader.readLine()) != null)   
	            {
	            	name.add(line);   
	            }
	            read.close();      
	        }     
	    } catch (Exception e)   
	    {         
	        e.printStackTrace();     
	    }
	}
}
