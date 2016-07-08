package database;

import java.util.ArrayList;
import java.util.List;

public enum SearchType {
	GOV(0),MEDIA(1),PUBLIC(2),GOVMEDIA(3);
	
	private List<String> words = new ArrayList<>();
	private SearchType(int type) {
		if(type == 0){
			words.add("政府");
		}
		else if (type == 1) {
			words.add("媒体");
		}
		else if (type == 2) {
			words.add("公众");
		}
		else if (type == 3) {
			words.add("政府");
			words.add("媒体");
		}
	}
	public List<String> getWords() {
		return words;
	}
	
	
}
