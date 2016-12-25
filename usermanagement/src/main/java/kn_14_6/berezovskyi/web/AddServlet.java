package kn_14_6.berezovskyi.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kn_14_6.berezovskyi.User;
import kn_14_6.berezovskyi.db.DaoFactory;
import kn_14_6.berezovskyi.db.DatabaseException;

public class AddServlet extends EditServlet {

    private static final long serialVersionUID = 1L;
    
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }
    
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }

}
