package jython;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
 
import com.aliasi.classify.Classification;
 
public class Demo {
    String companyName;
    private String FREQUENCY;
    final String FREQHISTORY = "history";
    final String FREQWEEKLY = "weekly";
    final String FREQHOURLY = "hourly";
    String startDate;
    private ArrayList<String[]> results;
 
    void setFreq(String freq){
        FREQUENCY = freq;
    }
 
    String getFreq(){
        return FREQUENCY;
    }
 
    String getCurrDate(){
        Date date = new Date();
        DateFormat formattedDate = new SimpleDateFormat("MMM DD yyyy HH:mm");
        return formattedDate.format(date);
    }
 
    String getResults(ArrayList<String []> list,PolarityBasic pb){
        //File testFile=new File("E:\\Documents\\SUNY SB\\ACADS\\NLP\\Project\\sentimenttest.txt");
        String resultCategory;
        int positive = 0;
        int negative = 0;
        for(String[] strArr:list){
            StringBuffer sb=new StringBuffer();
            sb.append(strArr[0]);
            sb.append(strArr[1]);
            Classification classification = pb.mClassifier.classify(sb.toString());
            resultCategory = classification.bestCategory();
            if(sb.indexOf("IPO") >= 0 || sb.toString().toLowerCase().indexOf("initial public offering") >= 0)
                resultCategory = "pos";
            if(resultCategory.equals("pos"))
                positive++;
            else if(resultCategory.equals("neg"))
                negative++;
            System.out.println("best category: "+resultCategory);
        }
        if(positive >= negative)
            resultCategory = "pos";
        else
            resultCategory = "neg";
        System.out.println("Final Result:-"+resultCategory);
        return resultCategory;
    }
    /**
     * @param args
     */
    public static ArrayList<String[]> demo(String companyName, String frequency, PolarityBasic pb) throws IOException {
        //public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        //Sentiment Analysis
        Demo d = new Demo();
        d.results=new ArrayList<String[]>();
        NewsSearch n = new NewsSearch();
        d.setFreq(frequency);
        d.companyName = companyName;
        if(d.getFreq().equals("history") ){
            CsvToString c = new CsvToString();
            c.getTrends(d.companyName, d.getFreq());
            //Entry<String, Double> entry;
            //Set<Entry<String, Double>> set = c.topTen.entrySet();
            Set set = c.topTen.keySet();
            Iterator itr = set.iterator();
            for(int count = 0; count < 5;count++){
                //entry = (Entry<String, Double>) itr.next();
                d.startDate = (String) itr.next();
                System.out.println("**************"+d.startDate);
                n.setFreq(d.getFreq());
                ArrayList<String[]> list = n.newsSearch(d.companyName, d.startDate);
                String[] result = new String [2];
                if(! list.isEmpty()){
                    String output = d.getResults(list , pb);
                    result[1] = output;
                }
                else
                    result[1] = "empty";
                result[0] = d.startDate;
                d.results.add(result);
            }
            //d.startDate = c.topTen.firstKey();
 
        }
        else if(d.getFreq().equals("weekly")){
            CsvToString c = new CsvToString();
            c.getTrends(d.companyName, d.getFreq());
            d.startDate = c.topTen.firstKey();
            n.setFreq(d.getFreq());
            //n.newsSearch(d.companyName,"Aug 21 2011 18:00:00");
            // n.newsSearch(d.companyName, d.startDate);
            ArrayList<String[]> list = n.newsSearch(d.companyName, d.startDate);
 
            String[] result = new String [2];
            if(! list.isEmpty()){
                String output = d.getResults(list , pb);
                result[1] = output;
            }
            else
                result[1] = "empty";
            result[0] = d.startDate;
            d.results.add(result);
        }
        else{
            //get current date time
            d.startDate = d.getCurrDate();
            System.out.println(d.startDate);
            n.setFreq(d.getFreq());
            //n.newsSearch(d.companyName,"Aug 21 2011 18:00:00");
            //n.newsSearch(d.companyName, d.startDate);
            ArrayList<String[]> list = n.newsSearch(d.companyName, d.startDate);
 
            String[] result = new String [2];
            if(! list.isEmpty()){
                String output = d.getResults(list , pb);
                result[1] = output;
            }
            else
                result[1] = "empty";
            result[0] = d.startDate;
            d.results.add(result);
        }
        //ArrayList<String[]> list = n.newsSearch(d.companyName, startDate);
        //        
        //        //Sentiment Analysis
        //        File PolarityDir = new File("/home/ca/Desktop/review_polarity/txt_sentoken");
        //        PolarityBasic pb=new PolarityBasic(new String[]{"/home/ca/Desktop/review_polarity"});
        //        pb.train();
        //        //File testFile=new File("E:\\Documents\\SUNY SB\\ACADS\\NLP\\Project\\sentimenttest.txt");
        //        StringBuffer sb=new StringBuffer();
        //        for(String[] strArr:list){
        //            sb.append(strArr[0]);
        //            sb.append(strArr[1]);
        //        }
        //        Classification classification = pb.mClassifier.classify(sb.toString());
        //        String resultCategory = classification.bestCategory();
        //       
        //        System.out.println("best category: "+resultCategory);
        //         return(resultCategory);
 
        return d.results;
    }
 
}