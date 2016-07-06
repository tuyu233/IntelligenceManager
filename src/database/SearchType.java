package database;

import java.util.ArrayList;
import java.util.List;

public enum SearchType {
	gov(0),pa(1),news(2);
	
	private List<String> words = new ArrayList<>();
	private SearchType(int type) {
		if(type == 0){
			words.add("科技部");
			words.add("发改委");
			words.add("工信部");
		}
		else if (type == 1) {
			words.add("论文");
			words.add("专利");
		}
		else if (type == 2) {
			words.add("新闻");
		}
	}
	public List<String> getWords() {
		return words;
	}
	
	
}
