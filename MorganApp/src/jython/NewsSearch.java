package jython;

/**
 *
 * @author NewsCred Ltd
 */
import com.newscred.*;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class NewsSearch {

    public static String accessKey = "c4bcc3f7c9bf9ec159f51da0a86ca658";
    private ArrayList<String[]> newsArticles;
    private String frequency;
   
    void setFreq(String freq){
        frequency = freq;
    }
   
    String getFreq(){
        return frequency;
    }

    @SuppressWarnings("unchecked")
    ArrayList<String[]> newsSearch(String companyName, String start_date) {

        try {
            String formattedDates[] = getDates(start_date);
            newsArticles = new ArrayList<String[]>();
            // Search articles with custom arguments
            @SuppressWarnings("rawtypes")
            Hashtable options = new Hashtable();
            options.put("offset", 0);
            options.put("pagesize", 20);
            options.put("from_date", formattedDates[0]);
            options.put("to_date", formattedDates[1]);
            options.put("sort", "relevance");
            // options.put("sources", new String[]
            // {"1ce0362f2e764a95b0c7351c05a4eb19",
            // "2c20eeebd3486973559db5b654d87771"});
            options.put("source_countries", new String[] { "us", "uk", "in",
                    "qa", "ca" });
            // options.put("categories", new String[] {"world", "u-k", "u-s",
            // "sports", "business", "technology"});
            options.put("categories", new String[] { "world", "business",
                    "technology" });
            ArrayList<NewsCredArticle> filteredArticles = NewsCredArticle
                    .search(accessKey, companyName, options);
            int i = 0;
            for (NewsCredArticle art : filteredArticles) {
                i++;
                String[] news = new String[2];
               
                news[0] = art.title.toString();
                news[1] = art.description.toString();
                //remove html tags from news description
                news[1] = news[1].replaceAll("</?\\w++[^>]*+>", "");
                System.out.println(i + ":" + news[0]);
                System.out.println("Description:"+news[1]);
                newsArticles.add(news);

            }

        } catch (NewsCredException e) {

            System.out.println(e);
        }
        return newsArticles;
    }
   
   
    String[] getDates(String startDate){
        String [] dates = new String[2];
        @SuppressWarnings("deprecation")
        Date date = new Date(startDate);
        DateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(getFreq().equals("hourly"))
            cal.add(Calendar.HOUR, -1);
        else if(getFreq().equals("weekly")||getFreq().equals("history"))
            cal.add(Calendar.DATE, -7);
        dates[1] = formattedDate.format(date);
        System.out.println(dates[1]);
        dates[0] = formattedDate.format(cal.getTime());
        System.out.println(dates[0]);
        return dates;       
    }
}