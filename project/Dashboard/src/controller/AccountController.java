package controller;

import model.UserBean;
import java.util.Random;

import dao.UserDAO;;

public class AccountController {

	
	public static UserBean register (String firstName, String lastName, String email) {
		
		String pass;
	
		Random rand = new Random();

		int n = rand.nextInt(4) + 7;
		pass= PasswordGenerator.generatePassword(n);
		
		UserBean newuser=new UserBean();
		newuser.setFirstName(firstName);
		newuser.setLastName(lastName);
		newuser.setMail(email);
		newuser.setPassword(pass);
		UserDAO.register(firstName, email, pass);
		return newuser;
		
	}
	
	
}
