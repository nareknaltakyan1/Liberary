<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="server.SqlConnection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>
	<ul>
		<%
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			
				System.out.println("Home");
	
				Connection conn = SqlConnection.getConnection();
				List<String> list = SqlConnection.SelectUserList();

				for (String user : list) {
					out.println("<li>" + user + "</li>");
				}

		%>
	</ul>

</body>
</html>