package rextest;

import java.util.LinkedList;
import java.util.Queue;

public class ParserJSJXY {
	
	public ParserJSJXY(Queue<String> newsUrlQueue, Queue<String> pagesUrlQueue){
		this.newsUrlQueue=newsUrlQueue;
		this.pagesUrlQueue=pagesUrlQueue;
	}
	private static Queue<String> newsUrlQueue = new LinkedList<String>();
	private static Queue<String> pagesUrlQueue = new LinkedList<String>();
	private static String NowHost = "http://yjsb.shu.edu.cn";
	private static String nowNewsUrl = "ListNews.asp?NewsType=YJSB";
	public void  SetNowHost(String nowHost) {
		this.NowHost=nowHost;
	}
	public void  SetNowNewsUrl(String nowNewsUrl) {
		this.nowNewsUrl=nowNewsUrl;
	}
	

}
