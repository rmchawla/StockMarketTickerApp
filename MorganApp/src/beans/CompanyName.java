package beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class CompanyName {

	private List<String> companyNameList;
	private GlobalStockList globalStockList= GlobalStockList.getInstance();
	
	public List<String> getCompanyName()
	{
		if(this.companyNameList == null)
		{
			this.companyNameList = globalStockList.getCompanyNameList();
		}
		return this.companyNameList;
	}



	
}
