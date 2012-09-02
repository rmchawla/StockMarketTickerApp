/* Copyright (c) 2006 Google Inc.                                                                                                                                                                                
 *                                                                                                                                                                                                               
 * Licensed under the Apache License, Version 2.0 (the "License");                                                                                                                                               
 * you may not use this file except in compliance with the License.                                                                                                                                              
 * You may obtain a copy of the License at                                                                                                                                                                       
 *                                                                                                                                                                                                               
 *     http://www.apache.org/licenses/LICENSE-2.0                                                                                                                                                                
 *                                                                                                                                                                                                               
 * Unless required by applicable law or agreed to in writing, software                                                                                                                                           
 * distributed under the License is distributed on an "AS IS" BASIS,                                                                                                                                             
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.                                                                                                                                      
 * See the License for the specific language governing permissions and                                                                                                                                           
 * limitations under the License.                                                                                                                                                                                
 */



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Add a new item to Google Base using the Google Base data API server.
 */
public class InsertExample {

	private static HashMap<String , String> companies = new HashMap<String , String>();
	public InsertExample()
	{
		loadCompanies();
	}
	
	private void loadCompanies() {
		String line;
		String key;
		String value;
		String[] str;
		File f = new File("/home/ca/myworkspace/MorganApp/src/companylist.csv");
		try {
			FileReader fin = new FileReader(f);
			BufferedReader br =new BufferedReader(fin);
				while((line = br.readLine()) != null)
				{
					str = line.split(",");
					key = str[1].replaceAll("\"", "");
					value = str[0].replaceAll("\"", "");
					companies.put(key, value);
				}
				Collection entry = companies.values();
				Iterator i = entry.iterator();
				while(i.hasNext())
				{
					System.out.println(i.next());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		}
	}
		
		public static void main(String arg[])
		{
			InsertExample i =new InsertExample();
		}
		
	}


