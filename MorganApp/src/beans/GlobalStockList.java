package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class GlobalStockList {

	private static GlobalStockList globalStockList;
	private TreeMap<String,String> companies = null;
	private HashMap<String,String> inverseCompanies = null;
	private TreeMap<String , String> sortedCompanies = null;
	private ArrayList<String> companyNameList = null;
	private Collection entry;
	private Iterator iterator;
	
	public static GlobalStockList getInstance()
	{
		if(globalStockList == null)
			globalStockList = new GlobalStockList();
		return globalStockList;
	}
	
	private GlobalStockList()
	{
	 
	}
	
	private synchronized void loadStockList()
	{	
		if(companies == null)	
		{
			companies = new TreeMap<String , String>();
			inverseCompanies = new HashMap<String, String>();
			sortedCompanies = new TreeMap<String, String>();
			String line;
			String key;
			String value;
			String[] str;
			File f = new File("/home/ca/myworkspace/MorganApp/src/companylist.csv");
			try 
			{
				FileReader fin = new FileReader(f);
				BufferedReader br =new BufferedReader(fin);
				while((line = br.readLine()) != null)
				{
					str = line.split(",");
					key = str[1].replaceAll("\"", "").trim();
					value = str[0].replaceAll("\"", "").trim();
					System.out.println(key+":"+value);
					companies.put(key, value);
					inverseCompanies.put(value, key);
					
				}
				companies.remove("Name");
				inverseCompanies.remove("Symbol");
				sortedCompanies.putAll(inverseCompanies);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}	
	
	private void populateCompanyNameList()
	{
		
		startIterator();
		companyNameList = new ArrayList<String>();
		while(iterator.hasNext())
		{
			companyNameList.add((String)iterator.next());
		}
	}
	
	public List<String> getCompanyNameList()
	{
		if(companyNameList == null)
		{
			populateCompanyNameList();
		}
		return companyNameList;
	}
	
	public  synchronized TreeMap<String , String> getCompanies()
	{
	
		if(companies == null)
			loadStockList();
		return companies;
	}
	
	public  synchronized TreeMap<String , String> getSortedCompanies()
	{
	
		if(sortedCompanies == null)
			loadStockList();
		return sortedCompanies;
	}
	
	private synchronized void startIterator()
	{
		if(companies == null)
			loadStockList();
		entry = companies.keySet();
		iterator = entry.iterator();
	}
	
	public HashMap<String , String> getInverseCompanies()
	{
		if(inverseCompanies == null)
			loadStockList();
		return inverseCompanies;
	}

}
