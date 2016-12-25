package kn_14_6.berezovskyi.db;

import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {

	private Mock mockUserDao;

	public MockDaoFactory() {
		mockUserDao = new Mock(UserDao.class);
	}

	@Override
	public UserDao getUserDao() {
		return (UserDao) mockUserDao.proxy();
	}

	public Mock getMockUserDao() {
		return mockUserDao;
	}

}
