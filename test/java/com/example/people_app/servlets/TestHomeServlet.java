package com.example.people_app.servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class TestHomeServlet {
    private static final String PATH = "/views/home.html";
    @Mock private ServletConfig servletConfig;
    @Mock private HttpServletRequest mockRequest;
    @Mock private HttpServletResponse mockResponse;
    @Mock private RequestDispatcher mockRequestDispatcher;
    @Mock private ServletContext mockServletContext;
    @Mock private ServletOutputStream outputStream;
    @Spy  private HomeServlet homeServlet;

    @BeforeEach
    public void setUp() {
        homeServlet = new HomeServlet();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenCallDoGetServletReturnHomePage() throws ServletException, IOException {

        when(homeServlet.getServletConfig()).thenReturn(servletConfig);
        when(homeServlet.getServletContext()).thenReturn(mockServletContext);
        when(homeServlet.getServletContext().getRequestDispatcher(PATH)).thenReturn(mockRequestDispatcher);

        homeServlet.doGet(mockRequest, mockResponse);
        verify(mockRequest, never()).getSession();
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);


    }
}
