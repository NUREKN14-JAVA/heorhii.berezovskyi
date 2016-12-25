package kn_14_6.berezovskyi.web;

import java.time.LocalDate;

import kn_14_6.berezovskyi.User;

public class AddServletTest extends MockServletTestCase {

    
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    
    public void testAdd() {
        LocalDate date = LocalDate.now();
        User user = new User("James", "May", date);
        User expectedUser = new User(666L, "James", "May", date);
        getMockUserDao().expectAndReturn("create", user,expectedUser);
        addRequestParameter("firstName", "James");
        addRequestParameter("lastName", "May");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
    }
    
    
    public void testAddEmptyFirstName() {
        LocalDate date = LocalDate.now();
        addRequestParameter("lastName", "May");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    
    public void testAddEmptyLastName() {
        LocalDate date = LocalDate.now();
        addRequestParameter("firstName", "James");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }
    
    
    public void testAddEmptyDate() {
        addRequestParameter("lastName", "May");
        addRequestParameter("firstName", "James");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }
    
    
    public void testAddInvalidDate() {
        addRequestParameter("firstName", "James");
        addRequestParameter("lastName", "May");
        addRequestParameter("date", "42");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }


}
