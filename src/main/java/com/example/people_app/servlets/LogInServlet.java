package com.example.people_app.servlets;


import com.example.people_app.repos.UserRepo;
import com.example.people_app.repos.UserRepoImpl;
import com.google.common.hash.Hashing;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {
    final static Logger LOGGER = Logger.getLogger(LogInServlet.class);
    UserRepo repo = null;

    @Override
    public void init() throws ServletException {
        repo = UserRepoImpl.getRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/views/login.html").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (repo.isExist(email, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            req.getServletContext().getRequestDispatcher("/home").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
            LOGGER.log(Level.INFO,"No such user");
        }
    }
}
