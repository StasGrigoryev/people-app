package com.example.people_app.servlets;


import com.example.people_app.repos.UserRepoImpl;
import com.google.common.hash.Hashing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        UserRepoImpl repo = UserRepoImpl.getRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/views/login.html").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String passwordHash = Hashing.sha256().hashString(req.getParameter("email"),
                StandardCharsets.UTF_8).toString();

    }
}
