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
		String str="神话中,25\n孩子,25\n柠檬水,25\n压缩,25\n美国,25\n柠檬,25\n就出,25\n黑暗中,25\n大脑,25\n小狗,24";
		TagCloudHelper.getInstance().makeTagcloud(str,"./output/tagcloud.png");
	}

	public static void main(String[] args) 
	{
		//new Main();
		boolean[] options = {true,true,true,true,false,false};
		new Crawler("公车改革", options);
	}

}
