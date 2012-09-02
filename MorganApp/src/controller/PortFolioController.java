package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jython.Demo;
import jython.PolarityBasic;
import beans.HistoryObject;
import beans.PersonalStock;
import beans.SessionService;

/**
 * Servlet implementation class PortFolioController
 */
@WebServlet("/PortFolioController")
public class PortFolioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PolarityBasic pb;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PortFolioController() {
        super();
        File PolarityDir = new File("/home/ca/Desktop/review_polarity/txt_sentoken");
        pb = new PolarityBasic(new String[]{"/home/ca/Desktop/review_polarity"});
        try
        {
        	pb.train();
        }
        catch(Exception e)
        {
        	
        }
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SessionService sessionService = SessionService.getInstance();
		HttpSession session = sessionService.getSession(request);
		String str = request.getParameter("control");
		String frequency = null;
		String imageURL = "http://chart.finance.yahoo.com/z?s=";
		
		switch(Integer.parseInt(request.getParameter("control")))
		{
			case 1:
				frequency = "history";
				break;
			case 2:
				frequency = "weekly";
				break;
			case 3:
				frequency = "hourly";
				break;
		}
		
		List<PersonalStock> portfolioList = (List<PersonalStock>)session.getAttribute("personalList");
		String index = request.getParameter("choice");
		if(index!=null)
		{
			PersonalStock stock = portfolioList.get(Integer.parseInt(index));
			session.setAttribute("stock", stock);
			String name[] = stock.getName().split(" ");
			String tickerSymbol = stock.getTickerSymbol();
			
			//JythonTest jython = new JythonTest();
			session.setAttribute("imageVisibility", "1");	
			session.setAttribute("visibility", "1");
		//call to python 
		//capture response and send it back
		//JythonTest test =new JythonTest();
			Demo test = new Demo();
			ArrayList<String[]> recommendation = 
					test.demo(name[0],frequency,pb);
			if(frequency.equals("weekly"))
			{
				session.setAttribute("weeklyVisibility", "1");
				if(((recommendation.get(0))[1]).equals("neg"))
					stock.setRecommendWeekly("Sell");
				else if(((recommendation.get(0))[1]).equals("pos"))
					stock.setRecommendWeekly("Buy");
				else
					stock.setRecommendWeekly("Hold");
				stock.setImageURL(imageURL+tickerSymbol+"&t=5d&z=s");
				RequestDispatcher requestDispatch = request.getRequestDispatcher("/portfolio.jsp");
				requestDispatch.forward(request, response);
				
			}
			else if(frequency.equals("hourly"))
			{
				session.setAttribute("hourlyVisibility", "1");
				if(recommendation.get(0)[1].equals("neg"))
						stock.setRecommendHourly("Sell");
				else if(((recommendation.get(0))[1]).equals("pos"))
						stock.setRecommendHourly("Buy");
				else
					stock.setRecommendHourly("Hold");
				stock.setImageURL(imageURL+tickerSymbol+"&t=1d&z=s");
				RequestDispatcher requestDispatch = request.getRequestDispatcher("/portfolio.jsp");
				requestDispatch.forward(request, response);
				
			}
			
			else if(frequency.equals("history"))
			{
			    List<HistoryObject> historyObjectList = stock.getHistoryObjectList();	
				for(String[] history: recommendation)
				{
					HistoryObject historyObject = new HistoryObject();
					historyObject.setDate(history[0]);
					if(history[1].equals("pos"))
						historyObject.setResult("Buy");
					else if(history[1].equals("neg"))
						historyObject.setResult("Sell");
					else
						historyObject.setResult("Hold");
					historyObjectList.add(historyObject);
				}
				stock.setImageURL(imageURL+tickerSymbol+"&t=7y&z=m");
				session.setAttribute("historyVisibility", "1");
				session.setAttribute("historyStockList",stock.getHistoryObjectList());
				RequestDispatcher requestDispatch = request.getRequestDispatcher("/pastHistory.jsp");
				requestDispatch.forward(request, response);	
			}
			
					
		}
		else
		{
		RequestDispatcher requestDispatch = request.getRequestDispatcher("/portfolio.jsp");
		requestDispatch.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}