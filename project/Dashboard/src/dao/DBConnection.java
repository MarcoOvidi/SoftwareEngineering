package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	  //connessione al database con DriverManager
    public static Connection connect() throws DatabaseException {
    	//Properties prop = ReadProperties.getConnectionProperties();
    	try {
    		
    		//caricamento dinamico della classe driver
            
    		//Class.forName(prop.getProperty("driver"));
    		Class.forName("com.mysql.jdbc.Driver");
    		
            
    		//connessione al database locale
            
    		return DriverManager.getConnection("jdbc:mysql://207.180.252.160:3306/managementDB?useSSL=false","root","panzerottoGbR4LR");
        } catch (ClassNotFoundException ex) {
           
        	//Usiamo un'eccezione user-defined per trasportare e gestire più
            //agevolmente tutte le eccezioni lagate all'uso del database
            
        	throw new DatabaseException("Driver non trovato", ex);
        
        } catch (SQLException ex) {
           
        	throw new DatabaseException("Errore di connessione", ex);
        }
    }
    
    public static void logDatabaseException(DatabaseException e) {
        Throwable cause = e.getCause();
        System.err.println("ERRORE DATABASE: " + e.getMessage());
        if (cause != null) {
            if (cause instanceof SQLException) {
                System.err.println("* SQLState: " + ((SQLException) cause).getSQLState());
                System.err.println("* Codice errore DBMS: " + ((SQLException) cause).getErrorCode());
                System.err.println("* Messaggio errore DBMS: " + ((SQLException) cause).getMessage());
            } else {
                System.err.println("* Causa: " + cause.getMessage());
            }
        }
    }
	
}

