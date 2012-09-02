package beans;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class SessionService {

	private static SessionService sessionService = null;
	
	private SessionService()
	{
		
	}
	
	public static SessionService getInstance()
	{
		if(sessionService == null)
			sessionService = new SessionService();
		return sessionService;
	}
	
	public synchronized HttpSession getSession(HttpServletRequest request)
	{
		HttpSession session = request.getSession(true);
		return session;
	}
}
