package service.chart.tagcloud;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import processing.core.PApplet;
import vision.Util;

/**
 * Simple application wrapper to run the TagCloudGenerator from command line.
 * Please provide a config.properties for configuration.
 */
public class TagCloud 
{

   public static void TagCloud(List<String> keywords) 
    {
    	TagCloudGenerator tagCloud = new TagCloudGenerator();
		String str=Util.tagCloudTrans(keywords);
		TagCloudHelper.getInstance().makeTagcloud(str,"./output/tagcloud.png");
		try
		{
            Configuration.getInstance().load(new FileInputStream("./resource/config.properties"));
        } catch (IOException e) 
        {
            e.printStackTrace();
            return;
        }
		//tagCloud = new TagCloudGenerator();
		//tagCloud.frame=MainWindow.mainFrame;
		tagCloud.init();
		//panel[7].add(tagCloud,BorderLayout.CENTER);
		tagCloud.stop();
      } 
}

