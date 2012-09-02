package controller;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import parser.RSSFeedParser;

/**
 * Servlet implementation class ServletController
 */
@WebServlet("/ServletController")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Queue<String> queue = new ConcurrentLinkedQueue<String>();
	
    /**
     * Default constructor. v 
     */
    public ServletController() {
        // TODO Auto-generated constructor stub
    	
    } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try
		{
			RSSFeedParser parser = new RSSFeedParser();
			Thread dataCollector = new Thread(parser , "Fetch");
			dataCollector.setPriority(Thread.MIN_PRIORITY);
	    	dataCollector.start();
			// add String to pass to backend
			//Item item = parser.getQuote();
			//String quotes = parser.getQuote();
				synchronized(parser)
				{
					parser.wait();
				}
				
			//Thread.currentThread().sleep(300);
			request.setAttribute("qoute", this.queue);
				
		}
		catch (Exception e)
		{
			
			System.out.println(e.getMessage() + "hi");
//			try {
//				
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			//doGet(request,response);
		}
		RequestDispatcher requestDispatch = request.getRequestDispatcher("/UI.jsp");
		requestDispatch.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
	}

}
