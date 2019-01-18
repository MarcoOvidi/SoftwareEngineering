<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@ page import="controller.*" %>  
  
 <%
 if(request.getParameterMap().containsKey("name") && request.getParameterMap().containsKey("aggregateType") && request.getParameterMap().containsKey("id"))
	 {
	 //inserisci aggregato nel db
	 if(request.getParameter("aggregateType").equals("area"));
	 } 
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>

<link rel="stylesheet" type="text/css" href="appstyle.css">

</head>
<body>
	<div id="title" onclick="location.href='dashboard.jsp'">
		Dashboard
	</div>
	
	<div id="ContainerTails">
		
	<%
	//visualizza le aree
	if(!request.getParameterMap().containsKey("area"))
		out.println(AggregateHandler.listAreas());
		
	//visualizza gli edifici
	if(request.getParameterMap().containsKey("area") && !request.getParameterMap().containsKey("building"))
		out.println(AggregateHandler.listBuildings(request.getParameter("area")));

	//visualizza i piani
	if(request.getParameterMap().containsKey("building")&& !request.getParameterMap().containsKey("floor"))
		out.println(AggregateHandler.listFloors(request.getParameter("area"), request.getParameter("building")));
	
	//visualizza le stanze
	if(request.getParameterMap().containsKey("floor") && !request.getParameterMap().containsKey("room"))
		out.println(AggregateHandler.listRooms(request.getParameter("area"), request.getParameter("building"), request.getParameter("floor")));
	
	//visualizza i sensori
	if(request.getParameterMap().containsKey("room")){
		  response.setIntHeader("Refresh", 1); //time in seconds
		//out.println(AggregateHandler.listSensors(request.getParameter("room")));
		}
	%>	


	</div>

</body>
</html>