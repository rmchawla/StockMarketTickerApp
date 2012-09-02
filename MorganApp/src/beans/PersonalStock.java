package beans;

import java.util.ArrayList;

public class PersonalStock {

	
private String name;
private String last;
private String change;
private String percentageChange;
private String volume;
private String tickerSymbol;
private String recommendHourly;
private String recommendWeekly;
private String imageURL;


public String getImageURL() {
	return imageURL;
}

public void setImageURL(String imageURL) {
	this.imageURL = imageURL;
}

private ArrayList<HistoryObject> historyObjectList;



public ArrayList<HistoryObject> getHistoryObjectList() {
	if(historyObjectList == null)
		this.historyObjectList = new ArrayList<HistoryObject>();
	return historyObjectList;
}

public void setHistoryObjectList(ArrayList<HistoryObject> historyObjectList) {
	this.historyObjectList = historyObjectList;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLast() {
	return last;
}
public void setLast(String last) {
	this.last = last;
}
public String getChange() {
	return change;
}
public void setChange(String change) {
	this.change = change;
}
public String getPercentageChange() {
	return percentageChange;
}
public void setPercentageChange(String percentageChange) {
	this.percentageChange = percentageChange;
}
public String getVolume() {
	return volume;
}
public void setVolume(String volume) {
	this.volume = volume;
}
public String getTickerSymbol() {
	return tickerSymbol;
}
public void setTickerSymbol(String tickerSymbol) {
	this.tickerSymbol = tickerSymbol;
}
public String getRecommendHourly() {
	return recommendHourly;
}
public void setRecommendHourly(String reommendHourly) {
	this.recommendHourly = reommendHourly;
}
public String getRecommendWeekly() {
	return recommendWeekly;
}
public void setRecommendWeekly(String recommendWeekly) {
	this.recommendWeekly = recommendWeekly;
}

}