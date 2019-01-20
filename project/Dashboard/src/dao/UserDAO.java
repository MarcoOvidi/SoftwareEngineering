package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.UserBean;

public class UserDAO {
	static Connection currentCon = null;
	static ResultSet rs = null;

	public static UserBean login(UserBean bean) {
		// preparing some objects for connection
		Statement stmt = null;
		String mail = bean.getMail();
		String password = bean.getPassword();

		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes(), 0, password.length());
			password = new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String searchQuery = "select * from user where mail='" + mail + "' AND password='" + password + "'"; // "System.out.println"
																												// prints
																												// in
																												// the
																												// console;

		// System.out.println("Your user name is " + username);
		// System.out.println("Your password is " + password);
		// System.out.println("Query: "+searchQuery);
		try {

			// connect to DB
			currentCon = DBConnection.connect();
			stmt = currentCon.createStatement();
			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();
			// if user does not exist set the isValid variable to false
			if (!more) {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				bean.setValid(false);
			}
			// if user exists set the isValid variable to true else
			if (more) {
				int id = rs.getInt("ID");
				String cf = rs.getString("CF");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				int type = rs.getInt("type");
				String status = rs.getString("status");
				System.out.println("Welcome " + firstName);
				bean.setId(id);
				bean.setCF(cf);
				bean.setFirstName(firstName);
				bean.setLastName(lastName);
				bean.setValid(true);
				bean.setType(type);
				bean.setStatus(status);

				if (type == 3) {
					String typeQuery = "SELECT ID_city FROM city_control_allocation INNER JOIN user ON city_control_allocation.ID_user="
							+ id;
					rs = stmt.executeQuery(typeQuery);
					while (rs.next()) {
						bean.addCity(rs.getInt("ID_city"));
					}

				}

				if (type == 2 || type == 3) {
					String typeQuery = "SELECT ID_area FROM area_control_allocation INNER JOIN user ON area_control_allocation.ID_user="
							+ id;
					rs = stmt.executeQuery(typeQuery);
					while (rs.next()) {
						bean.addArea(rs.getInt("ID_area"));
					}
				}

				if (type == 1 || type == 2 || type == 3) {
					String typeQuery = "SELECT ID_building FROM building_control_allocation INNER JOIN user ON building_control_allocation.ID_user="
							+ id;
					rs = stmt.executeQuery(typeQuery);
					int i = 0;
					while (rs.next()) {
						bean.addBuilding(rs.getInt("ID_building"));
						System.out.println("Building " + i + " =>" + rs.getInt("ID_building"));
					}

				}

			}

		} catch (Exception ex) {
			// System.out.println("Log In failed: An Exception has occurred! " + ex);
		} // some exception handling finally
		{
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {

				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
				stmt = null;
			}
			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {
				}
				currentCon = null;
			}
		}

		return bean;

	}

	public static boolean register(String nome, String email, String password) { //TODO does not check if the user already exists
		Connection con = null;
		password = getMD5(password);

		PreparedStatement ps = null;

		try {

			con = DBConnection.connect();
			// MARCO
			ps = con.prepareStatement("insert into user(nome,email,password) VALUES (?,?,?);");
			ps.setString(1, nome);
			ps.setString(2, email);

			ps.setString(3, password);

			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
		}
		
		return true;
	}

	public static String getMD5(String password) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes(), 0, password.length());
			password = new BigInteger(1, m.digest()).toString(16);
			return password;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
