package models;

public class MySites {
	public MySites(String title,String url,String content){
		this.title=title;
		this.url=url;
		this.content=content;
	}
	public MySites(String title,String url,String content,String dateString){
		this.title=title;
		this.url=url;
		this.content=content;
		this.dateString=dateString;
	}
	public String title;
	public String url;
	public String content;
	public String dateString;
}
