package br.com.lp3superior.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.lp3superior.jdbc.ConnectionFactory;
import br.com.lp3superior.modelo.Client;

public class ClientDAO {

	private Connection connection;

	public ClientDAO() throws SQLException {
		this.connection = ConnectionFactory.getConnection();
	}
	
	public void deleteDatabase(Client client) throws SQLException {
		String sql = "delete from client where id_client=?";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setLong(1, client.getIdClient());
		stmt.execute();
		stmt.close();
	}
	
	public void updateDatabase(Client client) throws SQLException {
		String sql = "update client set name=?,  cpf =? where id_client=?";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setString(1, client.getName());
		stmt.setString(2, client.getCpf());
		stmt.setLong(3, client.getIdClient());
		stmt.execute();
		stmt.close();
	}
	
	public void addToDatabase(Client client) throws SQLException {				
		String sql = "insert into client (name,cpf) values (?,?)";
		PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, client.getName());		
		stmt.setString(2, client.getCpf());
		stmt.execute();
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()) {
            Long idClient = rs.getLong(1);
            client.setIdClient(idClient);
        }
		stmt.close();
	}
	
	public List<Client> makeClientList() throws SQLException {
		List<Client> clients = new ArrayList<Client>();
		String sql = "select * from client";
		Statement stmt = this.connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {			
			Long idClient = resultSet.getLong("id_client");
			String name = resultSet.getString("name");
			String cpf = resultSet.getString("cpf");
			Client client = new Client();
			client.setIdClient(idClient);
			client.setName(name);
			client.setCpf(cpf);
			clients.add(client);
		}
		stmt.close();
		return clients;
	}
	
	public int getClientsQuantity() throws SQLException {
		int clientQuantity = 0;
		String sql = "SELECT count(*) AS total FROM client";
		Statement statement = this.connection.createStatement();
		ResultSet queryDatabaseResult = statement.executeQuery(sql);
		if (queryDatabaseResult.next()) {
			clientQuantity = queryDatabaseResult.getInt(1);
		}
		statement.close();
		return clientQuantity;
	}
	
	public List<Client> getClientWithPagination(int contentLimit, int contentOffset) throws SQLException {
		List<Client> clients = new ArrayList<Client>();
		String sql = "SELECT * FROM client ORDER BY id_client LIMIT ? OFFSET ?";
		PreparedStatement statement = this.connection.prepareStatement(sql);
		statement.setInt(1, contentLimit);
		statement.setInt(2, contentOffset);
		ResultSet queryDatabaseResult = statement.executeQuery();
		while (queryDatabaseResult.next()) {
			Long idClient = queryDatabaseResult.getLong("id_client");
			String name = queryDatabaseResult.getString("name");
			String cpf = queryDatabaseResult.getString("cpf");
			Client client = new Client();
			client.setIdClient(idClient);
			client.setName(name);
			client.setCpf(cpf);
			clients.add(client);
		}
		statement.close();
		return clients;
	}
	
}
