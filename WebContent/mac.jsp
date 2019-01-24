<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mac OS</title>
</head>
<body>
	<b>Select a city</b>
	<br /><br />

	<%
		String[] cities = (String[]) request.getAttribute("cities");
	%>

	<form action="calculate" method="post">
		<select name="city">
			<%
				for (int i = 0; i < cities.length; i++) {
			%>
			<option  value="<%=i%>"><%=cities[i]%></option>
			<%
				}
			%>
		</select>
		<br></br>
		
		<b>Enter a Big Integer:</b>
		<br></br>
		<input type="text" name ="userInput"><br></br>
		<input type="submit" value="Calculate" />
		</form>
</body>
</html>