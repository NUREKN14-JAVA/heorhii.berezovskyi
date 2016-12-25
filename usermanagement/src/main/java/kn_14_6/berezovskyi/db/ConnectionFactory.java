package kn_14_6.berezovskyi.db;

import java.sql.Connection;

public interface ConnectionFactory {

	Connection createConnection() throws DatabaseException;

}
