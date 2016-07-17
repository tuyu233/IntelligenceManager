package service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import service.chart.Chart;
import service.chart.tagcloud.TagCloud;
import service.motion.Motion;
import spider.helper.Crawler;
import util.RecordTrans;
import vision.AllData;
import vision.MainWindow;
import vision.ResultStatistic;
import vision.SearchResult;
import database.DatabaseHelper;
import database.SearchType;
import entity.Record;

public class Controller {
	
	public static void startCrawl(String keyword){
		DataManager.setKeyword(keyword);
		boolean[] options = {true,true,true,true,false,false};
		new Crawler(keyword, options);
	}
	
	public static void stopCrawl(){
		//TODO
	}

	public static void showResult(String keyword, SearchResult panel1, ResultStatistic panel2, AllData panel3){
		
		DataManager.setKeyword(keyword);
		
		TagCloud.TagCloud(DataManager.getKeywords().get(0));
		new Chart();
		
		panel1.setResult(DataManager.getRecordsGovMedia());
		panel2.setResult(DataManager.getOpinionIndex(), DataManager.getKeywords());
		panel3.setResult(DataManager.getRecordsAll());
	}
	
	public static void makeReport(){
		
	}
}
