package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.digester.rss.Item;

import parser.PortfolioParser;
import beans.CompanyName;
import beans.GlobalStockList;
import beans.SelectedCompanyName;
import beans.SessionService;

/**
 * Servlet implementation class PersonalTickerController
 */
@WebServlet("/PersonalTickerController")
public class PersonalTickerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
        
    /**
     * @see HttpServlet#HttpServlet()
     */ 
    public PersonalTickerController() {
        super();
        
        // TODO Auto-generated constructor stub
    }
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SessionService sessionService = SessionService.getInstance();
		HttpSession session = sessionService.getSession(request);
		List<String> portfolioList = new ArrayList<String>(); 
		// for the checkboxes to remain on output.jsp
		List<SelectedCompanyName> selectedCompanyNameList = new ArrayList<SelectedCompanyName>();
		String stockNames[] = request.getParameterValues("stocks");
		CompanyName companyName = new CompanyName(); 
		List<String> companyNameList = companyName.getCompanyName();
		
		GlobalStockList globalStockList = GlobalStockList.getInstance();
		TreeMap<String , String> companies = globalStockList.getCompanies();
		HashMap<String , String> inverseCompanies = globalStockList.getInverseCompanies();
		//System.out.println("Here>>>>>>>>   "+ stockNames.length);
		//System.out.println(stockNames[0] + "   "+stockNames[1]);
		session.setAttribute("control", "0");
		if(stockNames != null)
		{
			for(String index:stockNames)
			{
				String stock = companyNameList.get(Integer.parseInt(index));
				String tickerSymbol = companies.get(stock);
				portfolioList.add(tickerSymbol);
				// for the checkboxes to remain on output.jsp
				SelectedCompanyName selectedCompanyName = new SelectedCompanyName();
				selectedCompanyName.setName(inverseCompanies.get(tickerSymbol));
				selectedCompanyName.setValue(index);
				selectedCompanyNameList.add(selectedCompanyName);
				session.setAttribute("portfolioList", portfolioList);
				session.setAttribute("selectedCompanyNameList", selectedCompanyNameList);
			}
		}
		else
		{
			portfolioList = (List<String>)session.getAttribute("portfolioList");
		}	
		try
		{
			PortfolioParser portfolioParser = new PortfolioParser(portfolioList , session);
			String quoteURL = portfolioParser.makeQuoteURL();
			portfolioParser.retrieveQuote(quoteURL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
			RequestDispatcher requestDispatch = request.getRequestDispatcher("/portfolio.jsp");
			requestDispatch.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
