<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@ page import="controller.*" %>  
<%@ page import="model.*" %>    
  
		
	<%
	UserBean currentUser = null;
	currentUser = (UserBean)(session.getAttribute("currentSessionUser"));
	
	if(currentUser!=null && !currentUser.checkAdministrator()){
			
		
		//BUILDINGS MANAGER
		
		if(request.getParameterMap().containsKey("home") && currentUser.getType()==1)
			out.println(AggregateHandler.buildingsManager(currentUser));
			
		if(!request.getParameterMap().containsKey("home") && currentUser.getType()==1)	{
			//visualizza i piani
			if(request.getParameterMap().containsKey("building")&& !request.getParameterMap().containsKey("floor"))
				out.println(AggregateHandler.listFloors(request.getParameter("building"), currentUser));
			    
			
			//visualizza le stanze
			if(request.getParameterMap().containsKey("floor") && !request.getParameterMap().containsKey("room"))
				out.println(AggregateHandler.listRooms(request.getParameter("building"), request.getParameter("floor"), currentUser));
			
			    
			 //visualizza i sensori
			if(request.getParameterMap().containsKey("building") && request.getParameterMap().containsKey("room"))
				out.println(AggregateHandler.listSensors(request.getParameter("building"), request.getParameter("room"), currentUser));	
			
			
		}	
		
		//AREAS MANAGER
		if(request.getParameterMap().containsKey("home") && currentUser.getType()==2)
			out.println(AggregateHandler.areasManager(currentUser));
		
		
		if(!request.getParameterMap().containsKey("home") && currentUser.getType()==2){
			//visualizza gli edifici
			 if(request.getParameterMap().containsKey("area") && !request.getParameterMap().containsKey("building"))
				out.println(AggregateHandler.listBuildings(request.getParameter("area"), currentUser));
			    
				/*
				//visualizza i piani
				if(request.getParameterMap().containsKey("building")&& !request.getParameterMap().containsKey("floor")){
				    response.setIntHeader("Refresh", 1); //time in seconds
					out.println(AggregateHandler.listFloors(request.getParameter("building"), currentUser)
							);
				    }
				
				//visualizza le stanze
				if(request.getParameterMap().containsKey("floor") && !request.getParameterMap().containsKey("room")){
				    response.setIntHeader("Refresh", 1); //time in seconds
					out.println(AggregateHandler.listRooms(request.getParameter("building"), request.getParameter("floor"), currentUser));
				}
				    
				 //visualizza i sensori
				if(request.getParameterMap().containsKey("building") && request.getParameterMap().containsKey("room")){
				    response.setIntHeader("Refresh", 1); //time in seconds
					out.println(AggregateHandler.listSensors(request.getParameter("building"), request.getParameter("room"), currentUser));	
				}*/
		}	
		
		
		
		//CITY MANAGER
		if(request.getParameterMap().containsKey("home") && currentUser.getType()==3)
			out.println(AggregateHandler.cityManager(currentUser));

		
		if(!request.getParameterMap().containsKey("home") && currentUser.getType()==3){
				//visualizza gli edifici
			/* if(request.getParameterMap().containsKey("area") && !request.getParameterMap().containsKey("building")){
			    	response.setIntHeader("Refresh", 1); //time in seconds
					out.println(AggregateHandler.listBuildings(request.getParameter("area"), currentUser));
			    }
				//visualizza i piani
				if(request.getParameterMap().containsKey("building")&& !request.getParameterMap().containsKey("floor")){
				    response.setIntHeader("Refresh", 1); //time in seconds
					out.println(AggregateHandler.listFloors(request.getParameter("building"), currentUser));
				    }
				
				//visualizza le stanze
				if(request.getParameterMap().containsKey("floor") && !request.getParameterMap().containsKey("room")){
				    response.setIntHeader("Refresh", 1); //time in seconds
					out.println(AggregateHandler.listRooms(request.getParameter("building"), request.getParameter("floor"), currentUser));
				}
				    
				 //visualizza i sensori
				if(request.getParameterMap().containsKey("building") && request.getParameterMap().containsKey("room")){
				    response.setIntHeader("Refresh", 1); //time in seconds
					out.println(AggregateHandler.listSensors(request.getParameter("building"), request.getParameter("room"), currentUser));	
				}
				 */
		}	
		
		
		
		/*
			//visualizza le aree
			if(!request.getParameterMap().containsKey("area")){
			    response.setIntHeader("Refresh", 1); //time in seconds
				out.println(AggregateHandler.listAreas(currentUser));
			    }
			    
			//visualizza gli edifici
			else if(request.getParameterMap().containsKey("area") && !request.getParameterMap().containsKey("building")){
			    response.setIntHeader("Refresh", 1); //time in seconds
				out.println(AggregateHandler.listBuildings(request.getParameter("area"), currentUser));
			    }
			    
			//visualizza i piani
			if(request.getParameterMap().containsKey("building")&& !request.getParameterMap().containsKey("floor")){
			    response.setIntHeader("Refresh", 1); //time in seconds
				out.println(AggregateHandler.listFloors(request.getParameter("area"), request.getParameter("building"), currentUser)
						);
			    }
			    
			//visualizza le stanze
			if(request.getParameterMap().containsKey("floor") && !request.getParameterMap().containsKey("room")){
			    response.setIntHeader("Refresh", 1); //time in seconds
				out.println(AggregateHandler.listRooms(request.getParameter("area"), request.getParameter("building"), request.getParameter("floor"), currentUser));
			}
			    
			//visualizza i sensori
			if(request.getParameterMap().containsKey("building") && !request.getParameterMap().containsKey("room")){
			    response.setIntHeader("Refresh", 1); //time in seconds
				out.println(AggregateHandler.listSensors(request.getParameter("building"), request.getParameter("room"), currentUser));	
			}
			
			*/
			

	}	
	%>	
	
	<div class="ContainerNotifications">
	<%
		
	if(currentUser!=null && !currentUser.checkAdministrator() && currentUser.getType()==1)
		out.println(AggregateHandler.buildingsManagerNotifications(currentUser));
	%>
	</div>

