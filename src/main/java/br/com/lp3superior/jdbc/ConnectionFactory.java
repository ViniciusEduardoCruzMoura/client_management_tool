package br.com.lp3superior.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory{
	 
    public static Connection getConnection() throws SQLException {
    	
    	try {
    		Class.forName("org.postgresql.Driver");
    		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/lp3_Client", 
    				"postgres", "admin");
    	} catch (SQLException | ClassNotFoundException e) {
    		throw new SQLException(e.getMessage());
    	} 
	}

}
