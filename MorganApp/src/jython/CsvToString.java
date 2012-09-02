package jython;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class CsvToString {
	public String csvString;
	public String fileName;
	private String filePath;
	private String frequency;
	private LinkedHashMap<String, Double> data;
	public TreeMap<String, Double> topTen;
	private TreeMap<String, Double> sortedData;
	
	CsvToString(){
		//map = new HashMap<String, Double>();
		
		data = new LinkedHashMap<String, Double>();
		ValueComparator v = new ValueComparator(data);
		topTen = new TreeMap<String, Double>(v);
		sortedData = new TreeMap<String, Double>(v);
	}
	/*
	 * 
	 */
	void setFileName(String name){
		fileName = name;
	}
	/*
	 * 
	 */
	String getFileName(){
		if(! fileName.equals(null))
			return fileName;
		else 
			return null;
	}
	/*
	 * 
	 */
	void setPath(){
		String name = getFileName();
		if( ! name.equals(null))
			filePath = "/home/ca/docs/"+name+".csv";
		else 
			filePath = null;
	}
	/*
	 * 
	 */
	String getPath(){
		return filePath;
	}
	
	void setFrequency(String freq){
		frequency = freq;
	}
	
	String getFrequency(){
		return frequency;
	}
	/*
	 * 
	 */
	void extractData() throws NumberFormatException, IOException{
		String csvLine;
		boolean flag = false;
		int line = 0, count = 0;
		String path = getPath();
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		while ((csvLine = br.readLine()) != null) {
			
			line++;
			if(line > 1){
				String splitter[] = csvLine.split(",");
				System.out.println(splitter[0] + ":" +splitter[1] );
				//insert into map as date value pair
				data.put(splitter[0], Double.valueOf(splitter[1]));
				
			}
//			if (!(csvLine.length() > 1)) {
//				count++;
//
//				if (count == 5) {
//					if (flag)
//						break;
//					flag = true;
//
//				}
//
//			}
//			else if (csvLine.length() > 1 && line > 9) {
//				count = 0;	
//				String date;
//				String[] split = csvLine.split(" ");
//				//split year and search counts from split[2] 
//				String[] splitter = split[2].split("\\t");
//				//concat month = split[0] + date = split[1] + year = splitter[0]
//				date = split[0] + " " + split[1] + " " + splitter[0];
//				//remove null ascii characters from date
//				date = getStringDouble(date);
//				//remove null ascii characters from search counts = splitter[1]
//				String value = getStringDouble(splitter[1]);
//				//insert into map as date value pair
//				data.put(date, Double.valueOf(value));
//
//			}
		
	}
		// doPrint(data);
}
	
	
	/*
	 * formats the string to delete null or ascii value 0
	 * returns formatted string
	 */
		String getStringDouble(String splitter) {
			StringBuffer value = new StringBuffer();
			for (int i = 0; i < splitter.length(); i++) {
				if (splitter.charAt(i) != 0)
					value.append(splitter.charAt(i));
			}
			return value.toString();
		}
		
		
		void setSortedData(){
			sortedData.putAll(data);
		}
		
		/*
		 * get top ten values from data
		 * return TreeMap
		 */
		
		 void setTopTen(){
			//TreeMap <String, Double> map = new TreeMap<String, Double>();
			 int count = 0;
			Entry<String, Double> entry;
			if(getFrequency().equals("history")){
			Set <Entry<String, Double>> set = sortedData.entrySet();
			Iterator <Entry<String, Double>> itr = set.iterator();
			while(itr.hasNext() && (count < 15)){
				entry = (Entry<String, Double>) itr.next();
				//System.out.println(entry.getKey() + ":" + entry.getValue());
				topTen.put(entry.getKey(), entry.getValue());
				count++;
			}
		 }else{
			 
			 Set <Entry<String, Double>> set = data.entrySet();
			 Iterator <Entry<String, Double>> itr = set.iterator();
			 while(itr.hasNext() && count <= data.size()-2 ){
				 entry = (Entry<String, Double>) itr.next();
				 
				 if(count == data.size()-2 ){
					 topTen.put(entry.getKey(), entry.getValue());
				 }
				 count++;
			 }
		 }
			 
		}
		 
		/*
		 * print the TreeMap
		 */
		void doPrint() {
			Entry<String, Double> map;
			Set<Entry<String, Double>> set = topTen.entrySet();
			Iterator<Entry<String, Double>> itr = set.iterator();
			while (itr.hasNext()) {
				map = (Map.Entry<String, Double>) itr.next();
				System.out.println(map.getKey() + ":" + map.getValue());
			}

		}
		
		void getTrends(String companyName , String frequency) throws NumberFormatException, IOException{
			setFileName(companyName);
			setPath();
			setFrequency(frequency);
			extractData();
			if(getFrequency().equals("history"))
				setSortedData();
			setTopTen();
			doPrint();
		}
		
}

/*
 * Comparator defined for sorting in descending order
 */

class ValueComparator implements Comparator<Object> {
	Map<String, Double> m;

	ValueComparator(Map<String, Double> m) {
		this.m = m;
	}

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		String a = (String) o1;
		String b = (String) o2;
		
		Double val1 = Double.valueOf(this.m.get(a));
		Double val2 = Double.valueOf(this.m.get(b));
		if (val1 <= val2)
			return 1;
		else
			return -1;

	}
}
		