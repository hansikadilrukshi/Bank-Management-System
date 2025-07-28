package com.bank.controller;

import com.bank.model.Account;
import com.bank.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private AccountService accountService;
    
    @Override
    public void init() throws ServletException {
        accountService = new AccountService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userType = request.getParameter("userType");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        HttpSession session = request.getSession();
        
        if ("admin".equals(userType)) {
            if (accountService.validateAdmin(username, password)) {
                session.setAttribute("userType", "admin");
                session.setAttribute("username", username);
                response.sendRedirect("adminDashboard.jsp");
            } else {
                request.setAttribute("error", "Invalid admin credentials");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else if ("customer".equals(userType)) {
            Account account = accountService.validateCustomer(username, password);
            if (account != null) {
                session.setAttribute("userType", "customer");
                session.setAttribute("accountNumber", account.getAccountNumber());
                session.setAttribute("customerName", account.getCustomerName());
                session.setAttribute("account", account);
                response.sendRedirect("customerDashboard.jsp");
            } else {
                request.setAttribute("error", "Invalid account number or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Please select user type");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}