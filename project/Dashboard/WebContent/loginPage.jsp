<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1256"> 
		<title>Login Page</title>
		<link rel="stylesheet" type="text/css" href="appstyle.css">
 	</head> 
 	
 	<body>
 	
 		<div id="title" onclick="location.href='dashboard.jsp?home'">
		Dashboard
		</div>
		
		<div id="ContainerTails">
  		<div class="tail" >
  		<form action="LoginServlet"> 
  		 <input type="text" name="mail" placeholder="mail"/><br />
  		 <input type="password" name="password" placeholder="password"/><br />
  		<input type="submit" value="submit">
  		</form> 
   		</div>
   		</div>
   </body> 
</html>