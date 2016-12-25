package kn_14_6.berezovskyi.db;

import java.time.LocalDate;
import java.util.Collection;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import kn_14_6.berezovskyi.db.ConnectionFactory;
import kn_14_6.berezovskyi.db.ConnectionFactoryImpl;
import kn_14_6.berezovskyi.db.DatabaseException;
import kn_14_6.berezovskyi.db.HsqldbUserDao;
import kn_14_6.berezovskyi.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;

	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}

	public void testCreate() {

		User user = new User();
		user.setFirstName("Lazy");
		user.setLastName("Bone");
		user.setDateOfBirthd(LocalDate.of(1997, 2, 18));
		assertNull(user.getId());

		try {
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseException e) {

			e.printStackTrace();
			fail(e.toString());
		}

	}

	public void testFindAll() {
		try {
			Collection allUsers = dao.findAll();
			assertNotNull("Collection is null", allUsers);
			assertEquals("Collection has inproper size.", 2, allUsers.size());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}

	}

	public void testFindID() {
		try {
			User user = dao.find(1L);
			assertEquals("testFindID error - got invalid first name", "John",
					user.getFirstName());
			assertEquals("testFindID error - got invalid last name", "Galt",
					user.getLastName());
			assertEquals("testFindID error - got invalid DOB",
					LocalDate.of(1935, 9, 13), user.getDateOfBirthd());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testDelete() {

		User user = new User();
		user.setId(1L);
		user.setFirstName("John");
		user.setLastName("Galt");
		user.setDateOfBirthd(LocalDate.of(1935, 9, 13));

		try {
			dao.delete(user);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		Long id = 1L;
		try {
			user = dao.find(1L);
			fail("testDelete error - user wasn't deleted");
		} catch (DatabaseException e) {
			assertEquals(e.getMessage().toString(), "User with id" + id
					+ " is not found");
		}

	}

	public void testUpdate() {
		try {
			User user = new User();
			user.setId(1L);

			String newFirstName = "Blue";
			String newLastName = "Berry";
			LocalDate newDate = LocalDate.of(1971, 5, 10);

			user.setFirstName(newFirstName);
			user.setLastName(newLastName);
			user.setDateOfBirthd(newDate);

			dao.update(user);
			user = dao.find(user.getId());
			assertEquals("testUpdate error - first name update failed",
					newFirstName, user.getFirstName());
			assertEquals("testUpdate error - last name update failed",
					newLastName, user.getLastName());
			assertEquals("testUpdate error - DOB update failed", newDate,
					user.getDateOfBirthd());

		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl(
				"org.hsqldb.jdbcDriver",
				"jdbc:hsqldb:file:db/usermanagement;hsqldb.nio_data_file=false;hsqldb.lock_file=false",
				"sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader()
				.getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

	public User getTestUser() {
		User user = new User();
		user.setId(1L);
		user.setFirstName("John");
		user.setLastName("Galt");
		user.setDateOfBirthd(LocalDate.of(1938, 9, 13));
		return user;
	}

}
