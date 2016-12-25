package kn_14_6.berezovskyi.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import kn_14_6.berezovskyi.User;

class HsqldbUserDao implements UserDao {

	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE users SET firstname=?, lastname=?, dateofbirth=? WHERE id=?";
	private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
	private static final String FIND_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id=?";
	private static final String SELECT_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	ConnectionFactory connectionFactory;

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public HsqldbUserDao(ConnectionFactory connectionFactory) {

		this.connectionFactory = connectionFactory;

	}

	public HsqldbUserDao() {

	}

	public User create(User user) throws DatabaseException {

		Connection connection = connectionFactory.createConnection();

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(INSERT_QUERY);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, Date.valueOf(user.getDateOfBirthd()));

			int numberOfRowsAdded = preparedStatement.executeUpdate();
			if (numberOfRowsAdded != 1) {
				throw new DatabaseException("Number of the inserted rows: "
						+ numberOfRowsAdded);
			}

			CallableStatement callableStatement = connection
					.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();

			if (keys.next()) {
				user.setId(keys.getLong(1));
			}

			keys.close();
			callableStatement.close();
			preparedStatement.close();
			connection.close();

		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public void update(User user) throws DatabaseException {

		Connection connection = connectionFactory.createConnection();
		Long id = user.getId();
		assert id != null;

		PreparedStatement preparedStatement;

		try {

			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, Date.valueOf(user.getDateOfBirthd()));
			preparedStatement.setLong(4, id);

			// execute query and check that it worked
			int numberOfRowsUpdated = preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();

			if (numberOfRowsUpdated != 1) {
				throw new DatabaseException("Number of the inserted rows: "
						+ numberOfRowsUpdated);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(User user) throws DatabaseException {
		Connection connection = connectionFactory.createConnection();

		Long id = user.getId();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setLong(1, id);
			int numberOfRowsDeleted = preparedStatement.executeUpdate();
			if (numberOfRowsDeleted != 1) {
				throw new DatabaseException("Delete failed: deleted only "
						+ numberOfRowsDeleted + " rows");
			}
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public User find(Long id) throws DatabaseException {

		Connection connection = connectionFactory.createConnection();

		PreparedStatement preparedStatement;
		User user = null;

		try {

			preparedStatement = connection.prepareStatement(FIND_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				throw new DatabaseException("User with id" + id
						+ " is not found");
			}
			user = new User();
			user.setId(resultSet.getLong(1));
			user.setFirstName(resultSet.getString(2));
			user.setLastName(resultSet.getString(3));
			user.setDateOfBirthd(resultSet.getDate(4).toLocalDate());
			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}


	public Collection<?> findAll() throws DatabaseException {

		Connection connection = connectionFactory.createConnection();

		Collection<User> result = new LinkedList<User>();
		try {
			Statement statement = connection.createStatement();
			ResultSet allUsers = statement.executeQuery(SELECT_QUERY);
			while (allUsers.next()) {
				User user = new User();
				user.setId(allUsers.getLong(1));
				user.setFirstName(allUsers.getString(2));
				user.setLastName(allUsers.getString(3));
				user.setDateOfBirthd(allUsers.getDate(4).toLocalDate());
				result.add(user);
			}
			allUsers.close();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
