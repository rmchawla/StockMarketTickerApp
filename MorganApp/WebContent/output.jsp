<%@ page import="java.util.*" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<jsp:useBean id="company" scope="session" class="beans.CompanyName"/>  
  
<html>  
  <head>  
  <title></title> 
  <script type="text/javascript">
  
//   var decider = "${sessionScope.selectedCompanyNameList}";
//   if(decider)
// 	{
// 	  count = "${sessionScope.selectedCompanyNameList.size()}";
// 	}
  function start() {
  
	  var count = 0 ;
	  var tablerow = document.getElementById("tablerow");
	  var table = document.getElementById("tabl");
	  var tablecolumn = document.getElementById('insertion');
	  var frm = document.getElementById("test");
	  
  	  var addedstocks = document.getElementById("personalStocks");
      var selectedIndex = document.getElementById("companyList").value;
      var flag = 0;
   	
     for (var i = 0; i < frm.elements.length; i++ ) 
  	{
	  if (frm.elements[i].type == 'checkbox') 
	  { 
		   if(frm.elements[i].checked = "true")
			   count++;
          if (frm.elements[i].value == selectedIndex)
          {
              flag = 1;
              alert("Already Added");
          }
      }
  	} 
      
    if(count < 9 && flag == 0)
    {
      //alert(count);
      var tblBody = document.createElement("tbody");
      var row = document.createElement("tr");
      var cell = document.createElement("td");
      var cb = document.createElement("input");
      cb.type = "checkbox";
      cb.id = "personalStocks";
      
      cb.value = selectedIndex;
      cb.checked = true;
      cb.name = "stocks";
      //alert(cb.value);
      
      var selectedText = document.getElementById("companyList").options[selectedIndex].text;
      //alert(selectedText);
      //alert("hii2");
      //document.forms[0].appendChild(cb);
      cell.appendChild(cb);  // tablecolumn. before
      var text = document.createTextNode(selectedText);
      //alert("hii");
      cell.appendChild(text);  // tablecolumn. before
      
      row.appendChild(cell);
      tblBody.appendChild(row);
      tablecolumn.appendChild(tblBody);
      //tablerow.appendChild(tablecolumn);
      //table.appendChild(tablerow);
      //document.forms[0].appendChild(cb);
      //alert("secondlast");
      //document.forms[0].appendChild(text);
      //document.getElementsByTagName("body").appendChild(forms);
      //alert("bottom");
      count++;	
    }
  }
  </script> 
  </head>  
  <% int count = 0;%>
  <body bgcolor="#FAF8CC"> 
    
    <form id="test" action = "http://localhost:8080/MorganApp/personalcontroller" method="get"> 
      <table  id ="tabl" border="0" width="60%,*">
      <th align="justify"> <b>Choose to Add:</b>
      <th > My List:
      	<tr id="tablerow">
      		<td> 
      			<Select name="companies" id="companyList" multiple="multiple" size="10">  
      				<c:forEach items="${company.companyName}" var="st">
            			<option value=<%=count++%>><c:out value="${st}"/></option>
      				</c:forEach>  
      			</Select>	  
      <br><br><br> 
      <input type="button" name="addStock" value="Add To Portfolio List" onclick="start()">&nbsp;&nbsp;&nbsp; 
      <input type="Submit" name="Submit" value="Personal Detailed Ticker">
           </td>
           <td>
           		<table id = "insertion" width="400%">
           			<c:forEach items="${sessionScope.selectedCompanyNameList}" var="st">
            			<tr>
            				<td><input type="checkbox" name="stocks" checked="true" value=${st.value}>${st.name}
            				</td>
            			</tr>	
      				</c:forEach>
           		</table>
           	
           </td>
       </tr>
      </table>     
    </form>  
  </body>  
</html>  