package entity;
 
import java.util.Date;

import org.hibernate.annotations.*;;

public class Record 
{
	private int id;
	private String baseUrl;
	private String content;
	private Date saveTime;
	private String title;
	private String author;
	private String type;
	private String other;
	private int access;
	
	public Record(){}
	public Record(String type, String title, String content, String baseUrl, Date saveTime, String author, String other, int access){
		this.type = type;
		this.title = title;
		this.content = content;
		this.baseUrl = baseUrl;
		this.saveTime = saveTime;
		this.author = author;
		this.other = other;
		this.access = access;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getContent() {
		if(content!=null) return content;
		else return "无内容";
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSaveTime() {
		if(saveTime!=null) return saveTime;
		else return new Date();
	}
	public void setSaveTime(Date savetime) {
		this.saveTime = savetime;
	}
	public String getTitle() {
		if(title!=null) return title;
		else return "无标题";
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		if(author!=null) return author;
		else return "未知作者";
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	public String toString(){
		return "[record:"+id+"]";
	}
}
