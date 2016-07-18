package reportfactory;

import java.util.ArrayList;
import java.util.List;

public class Information {  
    private String motion_jpg = null;//舆论态度分布
    private String source_jpg = null;//关注的主体政府官网，新闻媒体，社会公众（饼状图）
    private String year_comments_jpg = null;//关注量随时间的变化（柱状图）
    
    private List<String> media_theme = null;//不同主体(政府、媒体、公众)关注的主题
    private List<String> public_theme = null;//不同主体(政府、媒体、公众)关注的主题
    private List<String> gov_theme = null;//不同主体(政府、媒体、公众)关注的主题
    public List<String> getMedia_theme() {
		return media_theme;
	}



	public void setMedia_theme(List<String> media_theme) {
		this.media_theme = media_theme;
	}



	public List<String> getPublic_theme() {
		return public_theme;
	}



	public void setPublic_theme(List<String> public_theme) {
		this.public_theme = public_theme;
	}



	public List<String> getGov_theme() {
		return gov_theme;
	}



	public void setGov_theme(List<String> gov_theme) {
		this.gov_theme = gov_theme;
	}

	private List<String> year_theme = null;//明显峰值年度的主题
   
    private String global_attitude = null;//全网整体的舆论指数
    private String gov_attitude = null;// 政府官网态度指数
    private String media_attitude = null;// 媒体的满意度
    private String public_attitude = null;// 公众的满意度
    
    private List<String> hot_theme = null;//热点主题,总体热点的主题，热点文章，热点媒体，热点人物
    private List<String> other = null;//集中度较高的负面观点，集中度较高的正面观点
    
    private Information() {
    	
    }  
  
    private static volatile Information instance = null;  
  
    public static Information getInstance() {  
           if (instance == null) {    
             synchronized (Information.class) {    
                if (instance == null) {    
                	instance = new Information();   
                }    
             }    
           }   
           return instance;  
    }  
 
    
  
    public String getMotion_jpg() {
		return motion_jpg;
	}



	public void setMotion_jpg(String motion_jpg) {
		this.motion_jpg = motion_jpg;
	}



	public String getSource_jpg() {
		return source_jpg;
	}



	public void setSource_jpg(String source_jpg) {
		this.source_jpg = source_jpg;
	}



	public String getYear_comments_jpg() {
		return year_comments_jpg;
	}



	public void setYear_comments_jpg(String year_comments_jpg) {
		this.year_comments_jpg = year_comments_jpg;
	}



	public List<String> getYear_theme() {
		return year_theme;
	}



	public void setYear_theme(List<String> year_theme) {
		this.year_theme = year_theme;
	}



	public String getGlobal_attitude() {
		return global_attitude;
	}



	public void setGlobal_attitude(String global_attitude) {
		this.global_attitude = global_attitude;
	}



	public String getGov_attitude() {
		return gov_attitude;
	}



	public void setGov_attitude(String gov_attitude) {
		this.gov_attitude = gov_attitude;
	}



	public String getMedia_attitude() {
		return media_attitude;
	}



	public void setMedia_attitude(String media_attitude) {
		this.media_attitude = media_attitude;
	}



	public String getPublic_attitude() {
		return public_attitude;
	}



	public void setPublic_attitude(String public_attitude) {
		this.public_attitude = public_attitude;
	}



	public List<String> getHot_theme() {
		return hot_theme;
	}



	public void setHot_theme(List<String> hot_theme) {
		this.hot_theme = hot_theme;
	}



	public List<String> getOther() {
		return other;
	}



	public void setOther(List<String> other) {
		this.other = other;
	}



	public void printInfo() {  
        System.out.println("the name is " );  
    }  
  
}  

