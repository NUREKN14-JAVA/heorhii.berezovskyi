package kn_14_6.berezovskyi.gui;

import java.awt.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import kn_14_6.berezovskyi.User;
import kn_14_6.berezovskyi.db.DaoFactory;
import kn_14_6.berezovskyi.db.MockDaoFactory;
import kn_14_6.berezovskyi.util.Messages;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.ComponentFinder;
import junit.extensions.jfcunit.finder.Finder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import com.mockobjects.dynamic.Mock;

public class MainFrameTest extends JFCTestCase {

	private MainFrame mainFrame;
	private List<User> users;
	private Mock mockUserDao;

	protected void setUp() throws Exception {
		super.setUp();
		try {
			Properties properties = new Properties();
			properties.setProperty("dao.factory",
					MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance())
					.getMockUserDao();

			User testUser = new User(666L, "Mike", "Tyson", LocalDate.now());
			users = new ArrayList<User>();
			users.add(testUser);
			mockUserDao.expectAndReturn("findAll", users);
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
			mockUserDao.verify();

		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);

	}

	public void testBrowseControls() {
		try {
			find(JPanel.class, "browsePanel");
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(3, table.getColumnCount());
			assertEquals(Messages.getString("UserTableModel.id"),
					table.getColumnName(0));
			assertEquals(Messages.getString("UserTableModel.first_name"),
					table.getColumnName(1));
			assertEquals(Messages.getString("UserTableModel.last_name"),
					table.getColumnName(2));
			find(JButton.class, "addButton");
			find(JButton.class, "editButton");
			find(JButton.class, "detailsButton");
			find(JButton.class, "deleteButton");
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testAddUser() {
		try {
			String firstName = "Jaja";
			String lastName = "Majaja";
			LocalDate date = LocalDate.now();

			User user = new User(firstName, lastName, date);

			User expectedUser = new User(new Long(1), firstName, lastName, date);

			mockUserDao.expectAndReturn("create", user, expectedUser);

			ArrayList<User> users = new ArrayList<User>(this.users);

			users.add(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());

			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			find(JPanel.class, "addPanel");

			JTextField firstNameField = (JTextField) find(JTextField.class,
					"firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class,
					"lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class,
					"dateOfBirthField");

			getHelper().sendString(
					new StringEventData(this, firstNameField, "Jaja"));
			getHelper().sendString(
					new StringEventData(this, lastNameField, "Majaja"));
			getHelper()
					.sendString(
							new StringEventData(this, dateOfBirthField, date
									.toString()));
			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(2, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testCancelAddUser() {
		try {
			ArrayList<User> users = new ArrayList<User>(this.users);
			mockUserDao.expectAndReturn("findAll", users);
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			find(JPanel.class, "addPanel");
			JTextField firstNameField = (JTextField) find(JTextField.class,
					"firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class,
					"lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class,
					"dateOfBirthField");
			getHelper().sendString(
					new StringEventData(this, firstNameField, "Jaja"));
			getHelper().sendString(
					new StringEventData(this, lastNameField, "Majaja"));
			getHelper().sendString(
					new StringEventData(this, dateOfBirthField, LocalDate.now()
							.toString()));
			JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
			find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(
					new MouseEventData(this, cancelButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testEditUser() {
		try {
			User expectedUser = new User(new Long(666), "Sasha Baron", "Cohen",
					LocalDate.now());
			System.out.println(expectedUser);
			mockUserDao.expect("update", expectedUser);
			List<User> users = new ArrayList<User>(this.users);
			mockUserDao.expectAndReturn("findAll", users);
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton editButton = (JButton) find(JButton.class, "editButton");
			getHelper().enterClickAndLeave(
					new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper()
					.enterClickAndLeave(new MouseEventData(this, editButton));
			find(JPanel.class, "editPanel");
			JTextField firstNameField = (JTextField) find(JTextField.class,
					"firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class,
					"lastNameField");
			find(JTextField.class, "dateOfBirthField");

			getHelper().sendString(
					new StringEventData(this, firstNameField, "Sasha Baron"));
			getHelper().sendString(
					new StringEventData(this, lastNameField, "Cohen"));

			find(JButton.class, "cancelButton");
			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

			table = (JTable) find(JTable.class, "userTable");
			find(JPanel.class, "browsePanel");
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}

	}

	public void testCancelEditUser() {
		try {
			List<User> users = new ArrayList<User>(this.users);
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton editButton = (JButton) find(JButton.class, "editButton");
			getHelper().enterClickAndLeave(
					new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper()
					.enterClickAndLeave(new MouseEventData(this, editButton));
			find(JPanel.class, "editPanel");

			JTextField firstNameField = (JTextField) find(JTextField.class,
					"firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class,
					"lastNameField");
			find(JTextField.class, "dateOfBirthField");

			getHelper().sendString(
					new StringEventData(this, firstNameField, "Sasha Baron"));
			getHelper().sendString(
					new StringEventData(this, lastNameField, "Cohen"));
			JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
			find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(
					new MouseEventData(this, cancelButton));

			table = (JTable) find(JTable.class, "userTable");
			find(JPanel.class, "browsePanel");
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}

	}

	public void testDetailsPanel() {

		try {
			mockUserDao.expectAndReturn("findAll", users);
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton detailsButton = (JButton) find(JButton.class,
					"detailsButton");
			getHelper().enterClickAndLeave(
					new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(
					new MouseEventData(this, detailsButton));
			find(JPanel.class, "detailsPanel");
			JLabel firstName = (JLabel) find(JLabel.class, "firstName");
			JLabel lastName = (JLabel) find(JLabel.class, "lastName");
			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			table = (JTable) find(JTable.class, "userTable");
			find(JPanel.class, "browsePanel");
			if (!"Mike".equals(firstName.getText())) {
				fail("Wrong user");
			}
			if (!"Tyson".equals(lastName.getText())) {
				fail("Wrong user");
			}
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testDeleteUser() {
		try {
			User expectedUser = new User(new Long(666), "Sasha Baron", "Cohen",
					LocalDate.now());
			mockUserDao.expect("delete", expectedUser);
			List<User> users = new ArrayList<User>();
			mockUserDao.expectAndReturn("findAll", users);

			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());

			JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
			getHelper().enterClickAndLeave(
					new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(
					new MouseEventData(this, deleteButton));
			List<JDialog> showingDialogs = getHelper().getShowingDialogs();
			JDialog dialog = (JDialog) showingDialogs.get(0);
			Finder buttonFinder = new ComponentFinder(JButton.class);
			JButton okButton = (JButton) buttonFinder.find(dialog, 0);
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(0, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testCancelDeleteUser() {
		try {
			List<User> users = new ArrayList<User>(this.users);
			mockUserDao.expectAndReturn("findAll", users);
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
			getHelper().enterClickAndLeave(
					new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(
					new MouseEventData(this, deleteButton));
			List<JDialog> showingDialogs = (List<JDialog>) getHelper()
					.getShowingDialogs();
			JDialog dialog = (JDialog) showingDialogs.get(0);
			Finder buttonFinder = new ComponentFinder(JButton.class);
			JButton cancelButton = (JButton) buttonFinder.find(dialog, 1);
			getHelper().enterClickAndLeave(
					new MouseEventData(this, cancelButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	protected void tearDown() throws Exception {
		try {
			mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper();
			TestHelper.cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Component find(Class componentClass, String name) {
		NamedComponentFinder finder;
		finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("no component ‘" + name + "'", component);
		return component;
	}

}
