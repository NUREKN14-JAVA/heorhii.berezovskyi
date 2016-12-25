package kn_14_6.berezovskyi;

import java.time.LocalDate;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User user;

	private LocalDate dateOfBirthd;

	protected void setUp() throws Exception {

		super.setUp();

		user = new User();

		dateOfBirthd = LocalDate.of(1941, 5, 24);

	}

	public void testGetFullName() {

		user.setFirstName("Deja");
		user.setLastName("Vu");

		assertEquals("Vu, Deja", user.getFullName());

	}

	public void testGetAge() {

		user.setDateOfBirthd(dateOfBirthd);

		int correctAnswer = LocalDate.now().getYear() - dateOfBirthd.getYear();

		assertEquals(correctAnswer, user.getAge());

	}

}
