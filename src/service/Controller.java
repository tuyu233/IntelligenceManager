package service;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import properties.Configure;
import reportfactory.HtmlMaker;
import service.chart.Chart;
import service.chart.tagcloud.TagCloud;
import service.keyword.Keyword;
import service.keyword.NLP;
import service.motion.Motion;
//import spider.helper.Crawler;
import util.RecordTrans;
import vision.AllData;
import vision.MainWindow;
import vision.ResultStatistic;
import vision.SearchResult;
import database.DatabaseHelper;
import database.SearchType;
import entity.Record;

public class Controller {
	
	public static boolean isrunning = false;
	//private static Crawler crawler = new Crawler();
	public static void startCrawl(String keyword)
	{
		DataManager.setKeyword(keyword);
		isrunning = true;
		boolean[] options = {true,true,true,true,false,false};
		//crawler.start(keyword, options);
	}
	
	public static void stopCrawl()
	{
		isrunning = false;
		//crawler.stop();
	}

	public static void showResult(String keyword, SearchResult panel1, ResultStatistic panel2, AllData panel3){
		
		DataManager.setKeyword(keyword);
		
		TagCloud.TagCloud(DataManager.getKeywords().get(0));
		new Chart();
		
		panel1.setResult(DataManager.getRecordsAll());
		panel2.setResult(DataManager.getOpinionIndex(), DataManager.getKeywords());
		panel3.setResult(DataManager.getRecordsAll());
		
	}
	
	public static void makeReport(){
		System.out.print("Make report\n");
		int[] nums = new int[5];
		nums[0] = 3;
		nums[1] = DataManager.getRecordNum()[0];
		nums[2] = DataManager.getRecordNum()[1];
		nums[3] = DataManager.getRecordNum()[2];
		nums[4] = DataManager.getRecordNum()[3];
		List<String> views = new ArrayList<String>(8);
		views.add(util.RecordTrans.strings2stringWithComma(Keyword.getKeyword(DataManager.getRecordsOpinionIndexDistribution().get(10), Configure.KEYWORD_SIZE_NORMAL)));
		views.add(util.RecordTrans.strings2stringWithComma(Keyword.getKeyword(DataManager.getRecordsOpinionIndexDistribution().get(0), Configure.KEYWORD_SIZE_NORMAL)));
		views.add(Integer.toString(DataManager.getPosMax()));
		views.add(util.RecordTrans.strings2stringWithComma(Keyword.getKeyword(DataManager.getRecordsOpinionIndexDistribution().get(DataManager.getPosMax()+5), Configure.KEYWORD_SIZE_NORMAL)));
		views.add(NLP.recordsSummary(DataManager.getRecordsOpinionIndexDistribution().get(DataManager.getPosMax()+5)));
		views.add(Integer.toString(DataManager.getNegMax()));
		views.add(util.RecordTrans.strings2stringWithComma(Keyword.getKeyword(DataManager.getRecordsOpinionIndexDistribution().get(DataManager.getNegMax()+5), Configure.KEYWORD_SIZE_NORMAL)));
		views.add(NLP.recordsSummary(DataManager.getRecordsOpinionIndexDistribution().get(DataManager.getNegMax()+5)));
		HtmlMaker.entrance(
				DataManager.getKeyword(),
				nums,
				DataManager.getYearRecordList(),
				DataManager.getKeywords(),
				DataManager.getOpinionIndex(), 
				views);
		try
		{
			File file = new File("./output/report.html");
			URL link = new URL("file:///"+file.getCanonicalPath());
			Desktop.getDesktop().browse(link.toURI());	
		}catch(IOException err)
		{
			err.printStackTrace();
		}catch(URISyntaxException err)
		{
			err.printStackTrace();
		}
	}
}
