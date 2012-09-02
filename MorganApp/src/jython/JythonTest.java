package jython;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import jython.GTrendsInterface;
import jython.JythonFactory;

import org.python.util.PythonInterpreter;


public class JythonTest {
    
    
	private static HashMap<String , String> companyNames = null;
	private static Collection entry;
	private static Iterator iterator;

	
	/** Creates a new instance of Main */
    public JythonTest() {
    }
    
    
    private static void startIterator()
	{
		if(companyNames == null)
			loadStockList();
		entry = companyNames.keySet();
		iterator = entry.iterator();
	}

    private static void loadStockList()
	{	
			companyNames = new HashMap<String , String>();
			String line;
			String key[];
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
					key = str[1].replaceAll("\"", "").trim().split(" ");
					value = str[0].replaceAll("\"", "").trim();
					System.out.println(key+":"+value);
					companyNames.put(key[0], value);
					
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
    
    /**
     * @param args the command line arguments
     * @throws IOException 
     */
           public static void main(String args[])   throws IOException {
        	   
        	 loadStockList();
        JythonFactory jf = JythonFactory.getInstance();
        GTrendsInterface gTrends = (GTrendsInterface) jf.getJythonObject(
                               "jython.GTrendsInterface", "/home/ca/myworkspace/MorganApp/pyGTrends.py");
        
        startIterator();
		while(iterator.hasNext())
		{
		  String company = (String)iterator.next();
        gTrends.connect("rchawla535", "morganstanley");
        gTrends.download_report(company);
        FileWriter writer=new FileWriter(new File("/home/ca/docs/"+company+".csv"));
        writer.write(gTrends.csv());
        writer.close();
        //Demo demo=new Demo();
        //demo.demo(company,"weekly");
		}

    }
}