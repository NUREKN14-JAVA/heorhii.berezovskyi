package kn_14_6.berezovskyi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {

	private String databaseDriver;
	private String url;
	private String user;
	private String password;

	public ConnectionFactoryImpl(String databaseDriver, String url,
			String user, String password) {

		this.databaseDriver = databaseDriver;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public ConnectionFactoryImpl(Properties properties) {
		this.databaseDriver = properties.getProperty("connection.driver");
		this.url = properties.getProperty("connection.url");
		this.user = properties.getProperty("connection.user");
		this.password = properties.getProperty("connection.password");
	}

	public Connection createConnection() throws DatabaseException {

		try {
			Class.forName(databaseDriver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException();
		}

		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseException();
		}
	}
}