package service;

import java.util.List;

import javax.swing.JPanel;

import spider.helper.Crawler;
import vision.AllData;
import vision.MainWindow;
import vision.ResultStatistic;
import vision.SearchResult;
import database.DatabaseHelper;
import database.SearchType;
import entity.Record;

public class Controller {
	
	public static void startCrawl(String keyword){
		boolean[] options = {true,true,true,true,false,false};
		new Crawler(keyword, options);
	}

	public static void showResult(String keyword, SearchResult panel1, ResultStatistic panel2, AllData panel3){
		List<Record> records;
		records = DatabaseHelper.search(keyword, SearchType.GOVMEDIA);
		panel1.setResult(records);
	}
	
}
