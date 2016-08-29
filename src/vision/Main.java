package vision;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import service.chart.tagcloud.TagCloudHelper;
import spider.helper.Crawler;

public class Main 
{

	/*
	 * 程序入口
	 */
	public Main() 
	{
		//控制台信息输出到out.txt
	    /*File f=new File("./output/out.txt");
	    try {
			f.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(f);
			PrintStream printStream = new PrintStream(fileOutputStream);
			System.setOut(printStream);
			System.setErr(printStream);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		try 
		{
			MainWindow window = new MainWindow();
			window.mainFrame.setVisible(true);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		new Main();
		//boolean[] options = {true,true,true,true,false,false};
		//new Crawler("公车改革", options);
	}

}
