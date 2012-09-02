<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.concurrent.ConcurrentLinkedQueue" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%! int counter = 0; 
	int refreshTime = 0;%>
<%  if(counter == 0)
	{ 
%>
<title>Insert title here</title>
</head>
<body bgcolor="#FAF8CC" onload="document.forms[0].submit();">
<form id="submitIt" action = "http://localhost:8080/MorganApp/controller" method="get">
</form>
</body> 
		<% System.out.println(counter);
		   counter++;
	} 
	%>
	
<body bgcolor="#FAF8CC">	 
<font color="purple">
<marquee SCROLLAMOUNT="6" onmouseover="this.stop()" onmouseout="this.start();">
<% 
ConcurrentLinkedQueue<String> qoute = (ConcurrentLinkedQueue<String>)request.getAttribute("qoute");
	//System.out.print(qoute);
	if(qoute != null) 
	{
		int count = 0;
		String str = null;
		refreshTime = qoute.size()*3 -10;
			while(!qoute.isEmpty() && (str = qoute.remove()) != null)
			{
				out.print(str);
				count++;
			}
	}	
%>
</font>
</body>
<meta http-equiv="Refresh" content="<%=refreshTime%>">
</marquee>
</html>