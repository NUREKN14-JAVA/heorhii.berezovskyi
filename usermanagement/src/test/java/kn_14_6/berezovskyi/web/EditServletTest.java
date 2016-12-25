package kn_14_6.berezovskyi.web;

import java.time.LocalDate;

import kn_14_6.berezovskyi.User;

public class EditServletTest extends MockServletTestCase {
    
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    
    public void testEdit() {
        LocalDate date = LocalDate.now();
        User user = new User(666L, "James", "May", date);
        
        getMockUserDao().expect("update", user);
        
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "James");
        addRequestParameter("lastName", "May");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        

        doPost();
    }
    
    
    public void testEditEmptyFirstName() {
        
        LocalDate date = LocalDate.now();
        addRequestParameter("id", "666");
        addRequestParameter("lastName", "May");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    
    public void testEditEmptyLastName() {
        
        LocalDate date = LocalDate.now();
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "James");
        addRequestParameter("date", date.toString());
        addRequestParameter("okButton", "Ok");
        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    
    public void testEditEmptyDate() {
        
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "James");
        addRequestParameter("lastName", "May");
        addRequestParameter("okButton", "Ok");
        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
    
    public void testEditInvalidDate() {
        
        addRequestParameter("id", "666");
        addRequestParameter("firstName", "James");
        addRequestParameter("lastName", "May");
        addRequestParameter("date", "42");
        addRequestParameter("okButton", "Ok");
        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
        
    }
}
