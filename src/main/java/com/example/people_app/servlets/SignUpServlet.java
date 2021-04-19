package com.example.people_app.servlets;

import com.example.people_app.models.User;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    final static Logger LOGGER = Logger.getLogger(SignUpServlet.class);
    UserRepo repo = null;

    @Override
    public void init() throws ServletException {
        repo = UserRepoImpl.getRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hash = Hashing.sha256().hashString("lol", StandardCharsets.UTF_8).toString();
        LOGGER.log(Level.INFO, hash);
        req.getServletContext().getRequestDispatcher("/views/signup.html").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("fname");
        String lastName = req.getParameter("lname");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phone");
        String password = req.getParameter("password");
        String passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        if (!repo.isExist(email)) {
            User user = new User(firstName, lastName, email, phoneNumber, passwordHash);
            boolean success = repo.save(user);
            if (success) {
                LOGGER.log(Level.INFO,"User has been successfully added");
            } else {
                LOGGER.log(Level.FATAL,"Save method returned zero value");
            }
            doGet(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/signup");
        }
    }
}
