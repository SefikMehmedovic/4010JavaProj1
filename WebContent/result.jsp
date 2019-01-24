<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result Page</title>
</head>
<body>
<%
			
		String city = (String) request.getAttribute("city");
		String tax = (String) request.getAttribute("tax");
		BigInteger userInput= (BigInteger) request.getAttribute("userInput");
		BigInteger factor1= (BigInteger) request.getAttribute("factor1");
		BigInteger factor2 = (BigInteger) request.getAttribute("factor2");
		long timer= (Long) request.getAttribute("timer");
		//double totalCost = (Double) request.getAttribute("totalCost");
		
		double rate=Double.parseDouble(tax);
		
		//calculate totalCost
	if(timer==0)
	{
		timer=1;
	}
	double totalCost=(Math.ceil(timer))+((rate/100)*(Math.ceil(timer)));
		
%>
<h1 id = "result_header"> RESULTS PAGE </h1>
<br>
<a href="start">HOME</a>
<br>
City:   	<%=city%>
<br>
Tax:	    <%=tax %>
<br>
User Input:	<%=userInput %>		
<br><br>

<b>Recipt:</b><br>

<%if(timer<=10)
{ %>
factor1: <%=factor1 %>
<br>
factor2: <%=factor2 %>
<br>
Time:<%=timer%>
<br>
Total Cost: <%=totalCost%>
<br>

<% } else{ %>
<br>
The current technology cannot handle this job
<% }%>

</body>
</html>