package vision;

import service.chart.tagcloud.TagCloudHelper;

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
		new Main();
	}

}
