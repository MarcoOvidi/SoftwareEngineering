

import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Cache2;
import controller.Checker;
import model.Sensor;

/**
 * Servlet implementation class RequestCounter
 */
@WebServlet("/SignalHandler")
public class SignalHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignalHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println(request.getParameter("id"));
		/*
		try {
			
		int id=0,value=0;
		
		if(request.getParameterMap().containsKey("value") && request.getParameterMap().containsKey("id"))
			{
			id=Integer.parseInt(request.getParameter("id"));
			value=Integer.parseInt(request.getParameter("value"));
			//Sensor s= new Sensor(id,value);
			
			
			Checker.newSensorValue(id,value);
			}
		response.getWriter().append("5000");
		
		}catch(Exception e) {
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*try {
			
			System.out.println("ok");
			
		semaphore.acquire();
		
		int id=Integer.parseInt(request.getParameter("id"));
		int value=Integer.parseInt(request.getParameter("value"));
		Sensor s= new Sensor(id,value);
		Cache.setSensor(s);
		response.getWriter().append("5000");
		semaphore.release();
		System.out.println("id="+id+"value="+value);
		}catch(Exception e) {
		}*/

	}

	
	
	
}
