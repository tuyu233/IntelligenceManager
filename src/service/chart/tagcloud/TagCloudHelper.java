package service.chart.tagcloud;

public class TagCloudHelper 
{
	private static TagCloudHelper instance;
	private String content;
	private String outputfile;
	private TagCloudHelper()
	{
		content = null; 	
	}
	public static TagCloudHelper getInstance()
	{
		if (instance == null)
			instance = new TagCloudHelper();
		return instance;
		
	}
	public void makeTagcloud(String content, String outputfile)
	{
		instance.outputfile = outputfile;
		instance.content = content;	
		//new TagCloud();
	}
	public String getInstanceOutputfile()
	{
		return instance.outputfile;
	}
	public String getInstanceContent()
	{
		return instance.content;
	}
}
