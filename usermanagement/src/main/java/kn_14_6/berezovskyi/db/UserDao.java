package kn_14_6.berezovskyi.db;

import java.util.Collection;

import kn_14_6.berezovskyi.User;

public interface UserDao {

	User create(User user) throws DatabaseException;

	void update(User user) throws DatabaseException;

	void delete(User user) throws DatabaseException;

	User find(Long id) throws DatabaseException;

	Collection findAll() throws DatabaseException;

	void setConnectionFactory(ConnectionFactory connectionFactory);

}
