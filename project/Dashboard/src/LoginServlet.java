

import java.io.IOException; 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.UserBean; 


@WebServlet("/LoginServlet")

/** * Servlet implementation class LoginServlet */ 
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException { 
		try { 
			UserBean user = new UserBean(); 
			user.setMail(request.getParameter("mail"));
			user.setPassword(request.getParameter("password"));
			user = UserDAO.login(user);
			
			if (user.isValid()) { 
				HttpSession session = request.getSession(true); 
				session.setAttribute("currentSessionUser",user);
				response.sendRedirect("dashboard.jsp?home"); //logged-in page 
			} 
			
			else response.sendRedirect("invalidLogin.jsp"); //error page 
			} 
		catch (Throwable theException) {
			System.out.println(theException); 
			} 
		}

	}
			

