package rextest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import oracle.jdbc.driver.OracleBlobInputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.wltea.analyzer.lucene.IKAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;


import oracle.jdbc.driver.*;

import models.MySites;
public class Test {

	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.setReadTimeout(5000);
			conn.connect();
			// 获取所有响应头字段
			try{
			Map<String, List<String>> map = conn.getHeaderFields();
			}
			catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "gb2312"));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			// System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "gb2312"));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static Boolean IsContained(String origin, ArrayList<String> Paras) {
		for (String string : Paras) {
			if (origin.contains(string)) {
				return true;
			}
		}
		return false;
	}

	public static Boolean IsNew(String origin, Queue<String> Paras) {
		for (String string : Paras) {
			if (origin.equals(string)) {
				return false;
			}
		}
		return true;
	}

	public static ArrayList<String> urlDetector(String basicAbsoluteAddr,
			String charSet, String filterTagName, ArrayList<String> _pageParas,
			ArrayList<String> _newsParas) {
		if (urlUsed.get(basicAbsoluteAddr) ==null || urlUsed.get(basicAbsoluteAddr) != 1) {
			ArrayList<String> myUrls = new ArrayList<String>();
			// 发送POST请求
			String htmlDoc = Test.sendGet(basicAbsoluteAddr);
			 //System.out.print(htmlDoc);
			Parser parser = Parser.createParser(htmlDoc, charSet);
			NodeFilter filter = new TagNameFilter(filterTagName);
			NodeList nodelist = null;
			try {
				nodelist = parser.extractAllNodesThatMatch(filter);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int j=0;//每个page上的新闻条数，理论上应大于0
			ArrayList<String> prenext=new ArrayList<String>();
			
			for (int i = 0; i < nodelist.size(); i++) {
				// System.out.println("\n");
				LinkTag link = (LinkTag) nodelist.elementAt(i);
				// 链接地址
				// System.out.println(link.getAttribute("href"));
				// 链接名称
				//System.out.println(link.getStringText());
				if (link.getAttribute("href") != null
						&& !link.getAttribute("href").isEmpty()
						&& link.getAttribute("href") != "#") {
					if (pageUsed.get(link.getAttribute("href")) != null
							&& pageUsed.get(link.getAttribute("href")) == 1) {

					} else if (IsContained(link.getAttribute("href"),
							_pageParas)) {
						if (IsNew(link.getAttribute("href"), pagesUrlQueue)) {
							pagesUrlQueue.offer(link.getAttribute("href"));
							prenext.add(link.getAttribute("href"));
							pageUsed.put((link.getAttribute("href")), 0);
						}
					} else {
						if (IsContained(link.getAttribute("href"), _newsParas)) {
							if (IsNew(link.getAttribute("href"), newsUrlQueue)) {
								newsUrlQueue.offer(link.getAttribute("href"));
								//MySites my=new MySites(link.getStringText(), link.getAttribute("href"), "", dateString) 
//								if(link.getParent().getParent().getNextSibling().getNextSibling()!=null){
//									(link.getParent().getParent().getNextSibling().getNextSibling()).getText();
//								System.out.println((link.getParent().getParent().getNextSibling().getNextSibling()).getText());
//								Node mNode=link.getParent().getParent().getParent();
//							
//								}
								//link.getParent().getText()
								j++;
							}
						}
					}
				} else {

				}
			}
			if(j==0){
				for (String string : prenext) {
					pagesUrlQueue.remove(string);
					pageUsed.put(string, 0);
				}
			}
			urlUsed.put(basicAbsoluteAddr, 1);
			return myUrls;
		} else {
			return null;
		}

	}

	public static  ArrayList<String> getNewsUrlsPerPage(String basicAbsoluteAddr,
			String charSet, String filterTagName, ArrayList<String> _pageParas,
			ArrayList<String> _newsParas){
		if (urlUsed.get(basicAbsoluteAddr) ==null || urlUsed.get(basicAbsoluteAddr) != 1) {
			ArrayList<String> myUrls = new ArrayList<String>();
			// 发送POST请求
			String htmlDoc = Test.sendGet(basicAbsoluteAddr);
			 //System.out.print(htmlDoc);
			Document doc=Jsoup.parse(htmlDoc);
			Elements links=doc.select("a[href]");
			//Parser parser = Parser.createParser(htmlDoc, charSet);
			//NodeFilter filter = new TagNameFilter(filterTagName);
			//NodeList nodelist = null;
		//	try {
				//nodelist = parser.extractAllNodesThatMatch(filter);
			//} //catch (ParserException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			int j=0;//每个page上的新闻条数，理论上应大于0
			ArrayList<String> prenext=new ArrayList<String>();
			
			for (int i = 0; i < links.size(); i++) {
				// System.out.println("\n");
				
				// 链接地址
				// System.out.println(link.getAttribute("href"));
				// 链接名称
				//System.out.println(link.getStringText());
				if (links.eq(i).attr("href")!= null
						&& !links.eq(i).attr("href").isEmpty()
						&& links.eq(i).attr("href") != "#") {
					if (pageUsed.get(links.eq(i).attr("href")) != null
							&& pageUsed.get(links.eq(i).attr("href")) == 1) {

					} else if (IsContained(links.eq(i).attr("href"),
							_pageParas)) {
						if (IsNew(links.eq(i).attr("href"), pagesUrlQueue)) {
							pagesUrlQueue.offer(links.eq(i).attr("href"));
							prenext.add(links.eq(i).attr("href"));
							pageUsed.put((links.eq(i).attr("href")), 0);
						}
					} else {
						if (IsContained(links.eq(i).attr("href"), _newsParas)) {
							if (IsNew(links.eq(i).attr("href"), newsUrlQueue)) {
								newsUrlQueue.offer(links.eq(i).attr("href"));
								
								//links.eq(i).parents().select("td[align=left]:not(:has(div),:has(a),:has(img))").text()
								try{
								MySites my=new MySites(links.eq(i).text(), links.eq(i).attr("href"), "",links.get(i).parent().parent().parent().nextElementSibling().text()); 
								mySitesQueue.offer(my);
								}
								catch (Exception e) {
									// TODO: handle exception
									System.out.println("无效网址："+links.eq(i).attr("href"));
								}
								//								if(link.getParent().getParent().getNextSibling().getNextSibling()!=null){
//									(link.getParent().getParent().getNextSibling().getNextSibling()).getText();
//								System.out.println((link.getParent().getParent().getNextSibling().getNextSibling()).getText());
//								Node mNode=link.getParent().getParent().getParent();
//							"td[align='left']:not(:has('div'),:has('a'),:has('img'))"

//								}
								
								//link.getParent().getText()
								j++;
							}
						}
					}
				} else {

				}
			}
			if(j==0){
				for (String string : prenext) {
					pagesUrlQueue.remove(string);
					pageUsed.put(string, 0);
				}
			}
			urlUsed.put(basicAbsoluteAddr, 1);
			return myUrls;
		} else {
			return null;
		}

		
	}
	public static ArrayList<String> urlDetector(String basicAbsoluteAddr,
			String charSet, String filterTagName) {
		ArrayList<String> myUrls = new ArrayList<String>();
		// 发送POST请求
		String htmlDoc = Test.sendGet(basicAbsoluteAddr);
		// System.out.print(htmlDoc);
		Parser parser = Parser.createParser(htmlDoc, charSet);
		NodeFilter filter = new TagNameFilter(filterTagName);
		NodeList nodelist = null;
		try {
			nodelist = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < nodelist.size(); i++) {
			// System.out.println("\n");
			LinkTag link = (LinkTag) nodelist.elementAt(i);
			// 链接地址
			// System.out.println(link.getAttribute("href"));
			// 链接名称
			// System.out.println(link.getStringText());
			String mUrl;
			if (link.getAttribute("href") != null
					&& !link.getAttribute("href").isEmpty()
					&& link.getAttribute("href") != "#") {
				if (!link.getAttribute("href").contains("http:")) {
					if (link.getAttribute("href").toString().charAt(0) != '/') {
						if (basicAbsoluteAddr.substring(basicAbsoluteAddr
								.length() - 1) == "/") {
							mUrl = basicAbsoluteAddr
									+ link.getAttribute("href").toString();
						} else {
							mUrl = basicAbsoluteAddr + "/"
									+ link.getAttribute("href").toString();
						}

					} else {
						mUrl = basicAbsoluteAddr
								+ link.getAttribute("href").toString();
					}
				} else {

					mUrl = link.getAttribute("href").toString();
				}
				myUrls.add((mUrl));
			} else {

			}

		}
		return myUrls;
	}

	public static String GetSelectHtml(String tagName,String filterAttr,String filterVal,String htmlDoc,String charSet) throws ParserException{
		// System.out.print(htmlDoc);
		//
		//htmlDoc=sendGet("http://yjsb.shu.edu.cn/NewsManage/Display.asp?ID=8224&NewsType=YJSB&PageNo=1");
//		Document doc = null;
//		try {
//			doc = Jsoup.connect("http://yjsb.shu.edu.cn/NewsManage/Display.asp?ID=8224&NewsType=YJSB&PageNo=1").get();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		//Document doc2=Jsoup.parse(htmlDoc);
		//Elements links = doc.select("a"); //带有href属性的a元素
		//htmlDoc="<table><td class='NewsBody' >ss</td></table>";
		Document doc=Jsoup.parse(htmlDoc);
		Elements links=doc.select(tagName+"["+filterAttr+"="+filterVal+"]");
		return links.eq(0).text();
	}
	public static void GetAllUrls(ArrayList<String> basicUrls, String charSet,
			String filterTagName, int length) {
		ArrayList<String> currentLengthUrls = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		for (String basicUrl : basicUrls) {
			if (basicUrl.contains(NowHost)) {
				set.addAll(urlDetector(basicUrl, charSet, filterTagName));
			} else {
			}

		}
		currentLengthUrls.addAll(set);
		map.put(length + "", currentLengthUrls);
		// 插入一排数据
		System.out.println("length=" + length + "; map size="
				+ map.get(length + "").size());
		if (map.get(length + "").size() > 0) {

			GetAllUrls(map.get(length + ""), charSet, filterTagName, length + 1);
		} else {
			return;
		}

	}

	public static void GetAllNewsUrls(String pageUrl,
			ArrayList<String> _pageParas, ArrayList<String> _newsParas) {

		getNewsUrlsPerPage(InitUrlByPath(InitPageUrlByPath(nowNew, pageUrl), NowHost),
				"gb2312", "a", _pageParas, _newsParas);
		urlUsed.put(pageUrl, 1);
		if (pagesUrlQueue.size() > 0) {
			pageUsed.put(pagesUrlQueue.peek(), 1);
			GetAllNewsUrls(pagesUrlQueue.poll(), _pageParas, _newsParas);
		} else {
			return;
		}

	}

	public static String InitPageUrlByPath(String origin, String path) {
		/*
		 * if(origin.contains("?")){ if(path.charAt(0)=='?'){
		 * path=path.substring(1); } }
		 */
		return origin + "&" + path.substring(1, path.indexOf("Opt") - 1);
	}
	public static String InitUrlByPath(String origin, String host) {
		String mUrl;
		if (!origin.contains("http:")) {
			if (origin.toString().charAt(0) != '/') {
				if (host.substring(host.length() - 1) == "/") {
					mUrl = host + origin.toString();
				} else {
					mUrl = host + "/" + origin.toString();
				}

			} else {
				mUrl = host + origin.toString();
			}
		} else {

			mUrl = origin.toString();
		}
		return mUrl;
	}
	public static void InsertNews(String tablename) throws SQLException, ParserException, ParseException, CorruptIndexException, IOException{
		int index=1;
		//Analyzer analyzer =new ChineseAnalyzer();
		Analyzer analyzer=new PaodingAnalyzer();
		IndexWriter writer=new IndexWriter("G:\\lucenetest", analyzer,true);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		String url = "jdbc:oracle:thin:@192.168.18.136:1521:orcl";
		Connection conn=null;
		 Statement st =null;
		try { 
					 // 建立连接
				conn =DriverManager.getConnection(url, "rexoracle", "a123456");
				 // 创建Statement或者是PreparedStatement语句
				st = conn.createStatement();
				 for (MySites site : mySitesQueue) {
					 String currentNewsUrl=InitUrlByPath(site.url, NowHost);
						String currentNewsDoc=	sendGet(currentNewsUrl);
						if(currentNewsUrl.indexOf("NewsManage")<0){
							int m=1;
						}
						String currentNewsContentAll=  GetSelectHtml("td","class","NewsBody",currentNewsDoc,"gb2312");
						String currentNewsContent=  currentNewsContentAll.replaceAll("'", "''");
						if(currentNewsContent.length()<=500 && currentNewsContent.length()>=0){
							
						}
						else{
							try{
							currentNewsContent=currentNewsContent.substring(0,500).replaceAll("'", "''");
								//currentNewsContent=currentNewsContent.substring(0,500);
							}
							catch (Exception e) {
								// TODO: handle exception
								int m=0;
							}
						}
						//String currentNewsTitleAll=  GetSelectHtml("td","class","NewsTitle",currentNewsDoc,"gb2312");
						String currentNewsTitleAll=site.title;
						String currentNewsTitle=  currentNewsTitleAll.replaceAll("'", "''");	
						
						if(currentNewsTitle.length()<=200 && currentNewsTitle.length()>=0){
							
						}
						else{
							currentNewsTitle=currentNewsTitle.substring(0,200).replaceAll("'", "''");
							//currentNewsTitle=currentNewsTitle.substring(0,200);
						}
						
						String datestring=site.dateString;
						 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						 Date date=sdf.parse(datestring);
						 
						 //pstmt.setTimestamp(8, new java.sql.Timestamp(date.getTime())); 
						String sqlStr = "INSERT INTO "+tablename+" VALUES('"+currentNewsTitle+"','"+currentNewsUrl+"',to_date('"+datestring+"','yyyy-mm-dd hh24:mi:ss'),sysdate,'"+currentNewsContent+"',1)"; //
						try{
							
						//st.executeUpdate(sqlStr);
						System.out.println("插入第"+(index++)+"条");
						ResultSet rSet=st.executeQuery("SELECT MAX(ID) FROM "+tablename);
						int nowid=0;
						while(rSet.next()){
							nowid=rSet.getInt(1);
						}
						org.apache.lucene.document.Document newsDocument=new org.apache.lucene.document.Document();
						Field newsIdField=new Field("ID",nowid+"",Field.Store.YES,Field.Index.UN_TOKENIZED);
						Field newsContentField=new Field("NEWSCONTENT",new String((currentNewsContentAll).getBytes(),"gb2312"),Field.Store.YES,Field.Index.TOKENIZED);
						Field newsTitleField=new Field("NEWSTITLE",new String((currentNewsTitleAll).getBytes(),"gb2312"),Field.Store.YES,Field.Index.TOKENIZED);
						Field newsDateField=new Field("NEWSDATE",new String((datestring).getBytes(),"gb2312"),Field.Store.YES,Field.Index.TOKENIZED);
						Field newsUrlField=new Field("NEWSURL",new String((currentNewsUrl).getBytes(),"gb2312"),Field.Store.YES,Field.Index.UN_TOKENIZED);
						//Field newsTitleField=new Field("NEWSTITLE",new String((currentNewsTitleAll).getBytes(),"gb2312"),Field.Store.YES,Field.Index.TOKENIZED);
						newsDocument.add(newsIdField);
						newsDocument.add(newsContentField);
						newsDocument.add(newsTitleField);
						newsDocument.add(newsDateField);
						newsDocument.add(newsUrlField);
						try {
							
							writer.addDocument(newsDocument);
							
						} catch (CorruptIndexException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (LockObtainFailedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
						catch (SQLException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						}
				
				 
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			st.close();
			
		}
		finally{
			st.close();
			 conn.close();
			 writer.close();
		}
	}
	public static void InsertNews(Queue<String> urlsQueue,String tablename) throws SQLException, ParserException{
		int index=1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		String url = "jdbc:oracle:thin:@192.168.18.136:1521:orcl";
		Connection conn=null;
		 Statement st =null;
		try { 
					 // 建立连接
				conn =DriverManager.getConnection(url, "rexoracle", "a123456");
				 // 创建Statement或者是PreparedStatement语句
				st = conn.createStatement();
				 for (String urlString : urlsQueue) {
					 String currentNewsUrl=InitUrlByPath(urlString, NowHost);
						String currentNewsDoc=	sendGet(currentNewsUrl);
						if(currentNewsUrl.indexOf("NewsManage")<0){
							int m=1;
						}
						String currentNewsContentAll=  GetSelectHtml("td","class","NewsBody",currentNewsDoc,"gb2312");
						String currentNewsContent=  currentNewsContentAll.replaceAll("'", "''");
						if(currentNewsContent.length()<=500 && currentNewsContent.length()>=0){
							
						}
						else{
							try{
							currentNewsContent=currentNewsContent.substring(0,500).replaceAll("'", "''");
								//currentNewsContent=currentNewsContent.substring(0,500);
							}
							catch (Exception e) {
								// TODO: handle exception
								int m=0;
							}
						}
						String currentNewsTitleAll=  GetSelectHtml("td","class","NewsTitle",currentNewsDoc,"gb2312");
						String currentNewsTitle=  currentNewsTitleAll.replaceAll("'", "''");	
						
						if(currentNewsTitle.length()<=200 && currentNewsTitle.length()>=0){
							
						}
						else{
							currentNewsTitle=currentNewsTitle.substring(0,200).replaceAll("'", "''");
							//currentNewsTitle=currentNewsTitle.substring(0,200);
						}
						String sqlStr = "INSERT INTO "+tablename+" VALUES('"+currentNewsTitle+"','"+currentNewsUrl+"',null,sysdate,'"+currentNewsContent+"',1)"; //
						try{
							
						st.executeUpdate(sqlStr);
						System.out.println("插入第"+(index++)+"条");
						ResultSet rSet=st.executeQuery("SELECT MAX(ID) FROM "+tablename);
						int nowid=0;
						while(rSet.next()){
							nowid=rSet.getInt(1);
						}
						org.apache.lucene.document.Document newsDocument=new org.apache.lucene.document.Document();
						Field newsIdField=new Field("ID",nowid+"",Field.Store.YES,Field.Index.UN_TOKENIZED);
						Field newsContentField=new Field("NEWSCONTENT",currentNewsContentAll,Field.Store.YES,Field.Index.TOKENIZED);
						Field newsTitleField=new Field("NEWSTITLE",currentNewsTitleAll,Field.Store.YES,Field.Index.TOKENIZED);
						newsDocument.add(newsIdField);
						newsDocument.add(newsContentField);
						newsDocument.add(newsTitleField);
						try {
							Analyzer analyzer =new PaodingAnalyzer();
							IndexWriter writer=new IndexWriter("G:\\lucenetest", analyzer,true);
							writer.addDocument(newsDocument);
							writer.close();
						} catch (CorruptIndexException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (LockObtainFailedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
						catch (SQLException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						}
				
				 
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			st.close();
			
		}
		finally{
			st.close();
			 conn.close();
		}
		
	}
	
	public static void InsertUrls(Queue<String> urlsQueue,String tablename) throws SQLException{

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		String url = "jdbc:oracle:thin:@192.168.18.136:1521:orcl";
		Connection conn=null;
		 Statement st =null;
		try { 
					 // 建立连接
				conn =DriverManager.getConnection(url, "rexoracle", "a123456");
				 // 创建Statement或者是PreparedStatement语句
				 st = conn.createStatement();
				 for (String urlString : urlsQueue) {
					 String sqlStr = "INSERT INTO "+tablename+" VALUES('"+urlString+"')"; //
					 st.executeUpdate(sqlStr);
				}
				st.close();
				 conn.close();
		}
		catch (SQLException e) {
			// TODO: handle exception
			st.close();
			 conn.close();
		}
		finally{
			st.close();
			 conn.close();
		}
	}
	public ResultSet getResult(String sql){
		try{
			InitConn();
		st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		return rs;
		}
		catch(SQLException e){
		System.out.println(e);
		}
		return null;
		}

	public static void InitConn() throws SQLException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		String url = "jdbc:oracle:thin:@192.168.18.136:1521:orcl";
		try { 
					 // 建立连接
				conn =DriverManager.getConnection(url, "rexoracle", "a123456");
				 // 创建Statement或者是PreparedStatement语句
				 
		}
		catch (SQLException e) {
			// TODO: handle exception

			 conn.close();
		}
	}
	
	public static void InitLuceneIndex() throws UnsupportedEncodingException{
		org.apache.lucene.document.Document newsDocument=new org.apache.lucene.document.Document();
		Field newsIdField=new Field("ID","1",Field.Store.YES,Field.Index.UN_TOKENIZED);
		Field newsContentField=new Field("NEWSCONTENT","孙经123sss",Field.Store.YES,Field.Index.TOKENIZED);
		Field newsTitleField=new Field("NEWSTITLE",new String(("mysha孙景大大大呵呵你们怎么知道，但是我在那里，国家在那里。天气很好，哈哈123").getBytes(),"gb2312"),Field.Store.YES,Field.Index.TOKENIZED);
		newsDocument.add(newsIdField);
		newsDocument.add(newsContentField);
		newsDocument.add(newsTitleField);
		try {
			//File dicHomeFile2 = getFile(dicHome);  
			//Analyzer analyzer =new ChineseAnalyzer();
			//Analyzer analyzer=new IKAnalyzer();
			Analyzer analyzer=new PaodingAnalyzer();
			
			IndexWriter writer=new IndexWriter("G:\\lucenetest1", analyzer,true);
			writer.addDocument(newsDocument);
			writer.close();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Map<String, Integer> pageUsed = new HashMap<String, Integer>();
	public static Map<String, Integer> urlUsed = new HashMap<String, Integer>();
	public static Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	public static String NowHost = "http://yjsb.shu.edu.cn";
	public static String nowNew = "ListNews.asp?NewsType=YJSB";
	public static Queue<String> newsUrlQueue = new LinkedList<String>();
	public static Queue<String> pagesUrlQueue = new LinkedList<String>();

	public static Queue<MySites>mySitesQueue=new LinkedList<MySites>();
	public static Connection conn=null;
	public static Statement st=null;
	// public static ArrayList<String>;
	public static void main(String args[]) throws SQLException, ParserException, IOException, ParseException, JSONException {
		
		
		//InitLuceneIndex();
		//Indexer.createIndex("G:\\lucenetest");
		
		
		ArrayList<String> newParas = new ArrayList<String>();
		newParas.add("NewsManage/Display.asp?ID");
		ArrayList<String> pageParas = new ArrayList<String>();
		pageParas.add("Opt=ChangePage");
		ArrayList<String> myUrls = getNewsUrlsPerPage(
				"http://yjsb.shu.edu.cn/ListNews.asp?NewsType=YJSB", "gb2312",
				"a", pageParas, newParas);
		pageUsed.put(pagesUrlQueue.peek(), 1);
		GetAllNewsUrls(pagesUrlQueue.peek(), pageParas, newParas);
			System.out.println("现在的page队列长度："+pagesUrlQueue.size());
			System.out.println("现在的新闻队列长度："+newsUrlQueue.size());
			System.out.println("正在将研究生院的新闻url插入数据库...");
			//InsertUrls(newsUrlQueue,"B_yjsb_news_urls");
			System.out.println("成功插入数据库.");
			System.out.println("正在将研究生院的新闻内容插入数据库...");
			InsertNews("B_yjsy_news_contents");
			System.out.println("成功插入数据库.");
			
		
			
		int m = 3;
	}

}
/*
 * try { Class.forName("oracle.jdbc.driver.O racleDriver"); String url =
 * "jdbc:oracle:thin:@192.168.18.136:1521:orcl"; //String url =
 * "jdbc:oracle:thin:@so.shu.edu.cn:1521:oradb"; try { // 建立连接 Connection conn =
 * DriverManager.getConnection(url, "rexoracle", "a123456");
 * 
 * // 创建Statement或者是PreparedStatement语句 Statement st = conn.createStatement();
 * 
 * for (String urlString : myUrls) { String sqlStr =
 * "insert into testurls values('"+urlString+"',0,0) "; //ResultSet rs =
 * st.executeQuery(sqlStr); try{ int result= st.executeUpdate(sqlStr); }
 * catch(Exception e){
 * 
 * } } //String sqlStr = "select * from m "; //ResultSet rs =
 * st.executeQuery(sqlStr); // 遍历结果 //while (rs.next()) { //
 * System.out.println(rs.getString(1));
 * 
 * //} } catch (SQLException e) {
 * 
 * e.printStackTrace(); }
 * 
 * } catch (ClassNotFoundException e) {
 * 
 * e.printStackTrace();
 */

/*
 * if (!link.getAttribute("href").contains("http:")) { if
 * (link.getAttribute("href").toString().charAt(0) != '/') { if
 * (NowHost.substring(NowHost .length() - 1) == "/") { mUrl = NowHost +
 * link.getAttribute("href").toString(); } else { mUrl = NowHost + "/" +
 * link.getAttribute("href").toString(); }
 * 
 * } else { mUrl = NowHost + link.getAttribute("href").toString(); } } else {
 * 
 * mUrl = link.getAttribute("href").toString(); }
 */