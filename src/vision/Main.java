package vision;

import service.chart.tagcloud.TagCloudHelper;
import spider.helper.Crawler;

public class Main 
{

	/*
	 * 程序入口
	 */
	public Main() 
	{
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
		//new Main();
		boolean[] options = {true,true,true,true,false,false};
		new Crawler("公车改革", options);
	}

}
