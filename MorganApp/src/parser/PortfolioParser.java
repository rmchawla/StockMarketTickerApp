package parser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;
import org.apache.commons.digester.rss.RSSDigester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import beans.GlobalStockList;
import beans.PersonalStock;

public class PortfolioParser {

	private RSSDigester digester=new RSSDigester();
	private static final String PREFIX = "http://www.nasdaq.com/aspxcontent/NasdaqRSS.aspx?data=quotes";
	private static final String SYMBOL = "&symbol=";
	private static List<String> portfolioSymbolList;
	private static HttpSession session = null;
	
	public PortfolioParser(List<String> portfolioSymbolList , HttpSession session)
	{
		this.portfolioSymbolList = portfolioSymbolList;
		this.session = session;
	}
	
	public String makeQuoteURL()
	{
		String quoteURL = PREFIX;
		for(String quoteSymbol:portfolioSymbolList)
		{
			quoteURL += SYMBOL + quoteSymbol;
		}
		return quoteURL;
//		try
//		{
//			getQuote(quoteURL);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
	}
	
	
	public void retrieveQuote(String feed) throws Exception {

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
        //return item;
       // Thread.sleep(300);
        
    }

	private void parseItem(Item item)
   	{
	   String tickerSymbol = null;
	   int count = 0;
	   GlobalStockList globalStockList = GlobalStockList.getInstance();
	   HashMap<String,String> companyNameMap = globalStockList.getInverseCompanies();
	   List<PersonalStock> personalStockList = new ArrayList<PersonalStock>();
	   String html = item.getDescription();
	   System.out.println(html);
	   Document doc = Jsoup.parse(html);
	   Elements elements = doc.select("[width=200]");
	   for (Element element : elements)
	   {
		   tickerSymbol = portfolioSymbolList.get(count);
		   Elements childElements = element.select("td[align=right]");
		   //Elements stockName = element.select("td[align=LEFT]");
		   //System.out.println(">>>"+stockName.first().text());
		   //System.out.println(name);
		   //System.out.println(companyNameMap.get(name));
		   PersonalStock personalStock = new PersonalStock();
		   personalStock.setName(companyNameMap.get(tickerSymbol));
		   personalStock.setTickerSymbol(tickerSymbol);
		   personalStock.setLast(childElements.get(0).html());
		   personalStock.setChange(childElements.get(1).html());
		   personalStock.setPercentageChange(childElements.get(2).html());
		   personalStock.setVolume(childElements.get(3).html());
		   personalStock.setRecommendWeekly("");
		   personalStock.setRecommendHourly("");
		   personalStockList.add(personalStock);
		   count++;
	   }
	   session.setAttribute("personalList", personalStockList);
	   session.setAttribute("weeklyVisibility", "0");
	   session.setAttribute("hourlyVisibility", "0");
	   session.setAttribute("imageVisibility", "0");
   	}

	
}
