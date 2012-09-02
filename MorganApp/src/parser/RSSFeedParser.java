package parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;
import org.apache.commons.digester.rss.RSSDigester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import beans.GlobalStockList;

import controller.ServletController;


public class RSSFeedParser implements Runnable {
	// "http://www.nasdaq.com/aspxcontent/NasdaqRSS.aspx?data=quotes&symbol=AAXJ&symbol=ABAX&symbol=ABCB&symbol=ABCD&symbol=ABCO&symbol=ABFS&symbol=ABIO&symbol=ABMD";
	//String feed2 = "http://www.nasdaq.com/aspxcontent/NasdaqRSS.aspx?data=quotes&symbol=ACHC&symbol=ACHN&symbol=ACIW&symbol=ACLS&symbol=ACNB&symbol=ACOM&symbol=ACOR&symbol=ACPW";
	public static String quotes = "";
	private List<Element> validElements = new ArrayList<Element>();
	private static TreeMap<String , String> companies = null;
	private Collection entry;
	private Iterator iterator;
	private static final String SYMBOL = "&symbol=";
	private static final String PREFIX = "http://www.nasdaq.com/aspxcontent/NasdaqRSS.aspx?data=quotes";
	String feed = PREFIX;
	private RSSDigester digester=new RSSDigester();
	private GlobalStockList globalStockList= GlobalStockList.getInstance();
	public RSSFeedParser()
	{
		loadCompanies();
	}
	
	private void loadCompanies() {
		
		try
		{
		companies = globalStockList.getSortedCompanies();
		startIterator();
		}
		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		}
	}
	
	private void startIterator()
	{
		entry = companies.keySet();
		iterator = entry.iterator();
	}
	
	public void run()
	{
		int i = 0;
		try
		{
			while(true)
			{
				nextFeed();
				//marks the end....sttart agaiin
				if(this.feed.equals(PREFIX))
				{
					
					synchronized(this)
			        {
			        	this.notify();
			        }
			        
					break;
//					Thread.sleep(200000);
//					quotes = null;
//					startIterator();
//					nextFeed();
				}
				
				getQuote(this.feed);
				
				//Thread.yield();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();this.validElements.clear();
			
		}
	}
	
   private void nextFeed() {
		// TODO Auto-generated method stub
	    this.feed = PREFIX;
	    int i = 0;
		while(iterator.hasNext() && i < 7)
		{
			this.feed = this.feed + SYMBOL + (iterator.next());
			i++;
		}
	}

public void getQuote(String feed) throws Exception {

			this.validElements.clear();
			//System.out.println(feed);
	        //String feed = "http://www.nasdaq.com/aspxcontent/NasdaqRSS.aspx?data=quotes&symbol=AAXJ&symbol=ABAX&symbol=ABCB&symbol=ABCD&symbol=ABCO&symbol=ABFS&symbol=ABIO&symbol=ABMD";
	        URL url=new URL(feed.toString());
	        HttpURLConnection httpSource=
	            (HttpURLConnection)url.openConnection();
	        Channel channel=
	            (Channel)digester.parse(httpSource.getInputStream());
	        if (channel == null) {
	            throw new Exception("can't communicate with " + url);
	        }
	        Item rssItems[]=channel.findItems(); 
	        Item item = rssItems[0];
	        parseItem(item);
	        concatenateString(this.validElements);
	        //return quotes;
	       // Thread.sleep(300);
	        
	    }

   private void concatenateString(List<Element> parsedList) {
	   int count = 1;
	   String temp = "";
	   for(Element element: parsedList)
	   {
		   
		   if(count%4 == 0)
		   {
			   //System.out.println(temp);
			   ServletController.queue.add(temp+"&nbsp;&nbsp;&nbsp;&nbsp;");
			   temp = "";
			   count++;
		   }
		   temp+=element.html();
		   count++;
	   }
	   //System.out.println(quotes);
	   
   }

private void parseItem(Item item)
   	{
	   String str = null;
	   //List<Element> validElements = new ArrayList<Element>();
	   String html = item.getDescription();
	   Document doc = Jsoup.parse(html);
	   Elements elements = doc.select("[width=200]");
	   for (Element element : elements)
	   {
		   Elements childElements = element.select("td[align=right]");
		   Elements stockName = element.select("td[align=LEFT]");
		   //childElements.add(stockName.first());
		   // adding the stock name as it has different alignment, so cant be parsed in the first call.
		   Element name = stockName.first();
		   if(childElements.size()>2)
		   {
			   validElements.add(stockName.first());
			   validElements.addAll(childElements.subList(0,2));
		   }/*for (Element child : stockName)
		   {
			   str = (child).html();
			   System.out.println(str);
			   break;
		   }
		   */
	   }
   	}
   
}








