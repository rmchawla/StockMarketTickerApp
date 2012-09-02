<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript">

function change(str)
{
  	
  var hiddenController = document.getElementById("controller");
//   	if(!document.getElementById("choice").value)
//   		alert("Select Something");
//   	else
//   
{
  if(str.name == "PastRecommend")
	  hiddenController.value = 1;
   if(str.name == "WeekRecommend")
	   hiddenController.value = 2;
   if(str.name == "HourRecommend")
	   hiddenController.value = 3;
  alert(hiddenController.value);
  document.forms[1].submit();
  	}
  	}
</script>
</head>
<body bgcolor="#FAF8CC">
	<% int count = 0;%>
	
  <font color="purple">
	<a href = "http://localhost:8080/MorganApp/output.jsp">Return To Portfolio Selection</a> <br><br>
	<form method="get" action="http://localhost:8080/MorganApp/personalcontroller">
	<input type="submit"  name="refresh" value="Refresh" align="right">
	</form>
	<p> Select Stock to get Recommendation</p>
	<br>
	
	<form method="get" action="http://localhost:8080/MorganApp/portfolioController">
	
	<table border =1 width = "1000">
		<tr>
			<th>
			<th>Stock Name
			<th>Last
			<th>Change
			<th>% Change
			<th>Volume
			<c:if test ="${sessionScope.hourlyVisibility == '1'}">
			<th> Recommendation Hourly
			</c:if>
			<c:if test ="${sessionScope.weeklyVisibility == '1'}">
			<th> Recommendation Weekly
			</c:if>
			
		<c:forEach items="${sessionScope.personalList}" var="st">
            <tr>
            	<td>
            		<input type ="radio" name = "choice" id = "choice" value = <%=count++%> >
            	</td>
            	<td>
            		${st.name}&nbsp;&nbsp;&nbsp;(${st.tickerSymbol})
            	</td>
            	
            	<td>
            		${st.last}
            	</td>
            	<td>
            		${st.change}
            	</td>
            	<td>
            		${st.percentageChange}
            	</td>
            	<td>
            		${st.volume}
            	</td>
            	<c:if test ="${sessionScope.hourlyVisibility == '1'}">
            		<td>
            			${st.recommendHourly}
            		</td>
            	</c:if>
            	<c:if test ="${sessionScope.weeklyVisibility == '1'}">
            		<td>
            			${st.recommendWeekly}
            		</td>
            	</c:if>
            </tr>		
        </c:forEach>
     </table>     
    <br>
     <input type="hidden" name="control" id="controller" value="0">
     <input type="button" onclick= "change(PastRecommend)" name="PastRecommend" value="Past Recommendations">  
     <input type="button" onclick= "change(WeekRecommend)" name="WeekRecommend" value="Weekly Recommendation">
     <input type="button" onclick= "change(HourRecommend)" name="HourRecommend" value="Hourly Recommendation ">
    <br><br>
     <c:if test="${sessionScope.imageVisibility == '1'}">
     <img src="${sessionScope.stock.imageURL}">
     </c:if>
	
</form>
</font>
</body>
</html>