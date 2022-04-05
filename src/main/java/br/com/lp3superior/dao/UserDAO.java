package br.com.lp3superior.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.lp3superior.jdbc.ConnectionFactory;
import br.com.lp3superior.modelo.User;

public class UserDAO {
	
	private Connection connection;
	
	public UserDAO() throws SQLException {
		this.connection = ConnectionFactory.getConnection();
	}

	public boolean login(User user) throws SQLException {
		boolean logged = false;
		String sql = "SELECT * FROM user_table WHERE email = ? and password = ?";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setString(1, user.getEmail());
		statement.setString(2, user.getPassword());
		ResultSet queryDatabaseResult = statement.executeQuery();
		while (queryDatabaseResult.next()) {			
			Long idUser = queryDatabaseResult.getLong("id_user");
			String firstName = queryDatabaseResult.getString("first_name");
			String lastName = queryDatabaseResult.getString("last_name");
			user.setIdUser(idUser);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			logged = true;
		}
		statement.close();
		return logged;
	}
	
	public void addUser(User user) throws SQLException {
		String sql = "INSERT INTO user_table (first_name, last_name, email, password) values (?,?,?,?)";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setString(1, user.getFirstName());		
		statement.setString(2, user.getLastName());
		statement.setString(3, user.getEmail());
		statement.setString(4, user.getPassword());
		statement.execute();
		statement.close();
	}
	
}
