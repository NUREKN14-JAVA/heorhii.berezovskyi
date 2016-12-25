package kn_14_6.berezovskyi.db;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {

	public void setUp() throws Exception {
		super.setUp();
	}

	public void testGetUserDao() {
		DaoFactory daoFactory = DaoFactory.getInstance();
		assertNotNull("DaoFactory = null", daoFactory);
		UserDao userDao = daoFactory.getUserDao();
		assertNotNull("UserDao = null", userDao);

	}

}
