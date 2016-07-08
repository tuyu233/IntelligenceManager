package service.chart.tagcloud;
import java.io.FileInputStream;
import java.io.IOException;

import processing.core.PApplet;

/**
 * Simple application wrapper to run the TagCloudGenerator from command line.
 * Please provide a config.properties for configuration.
 */
public class TagCloud {

    TagCloud() {
    		try
    		{
                Configuration.getInstance().load(new FileInputStream("config.properties"));
            } catch (IOException e) 
            {
                e.printStackTrace();
                return;
            }
        
        PApplet.main(new String[]{"--present", "service.chart.tagcloud.TagCloudGenerator"});
      }
    }

