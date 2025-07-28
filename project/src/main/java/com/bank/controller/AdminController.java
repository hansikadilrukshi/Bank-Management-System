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
import java.util.List;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
    private AccountService accountService;
    
    @Override
    public void init() throws ServletException {
        accountService = new AccountService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (!"admin".equals(session.getAttribute("userType"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getPathInfo();
        
        switch (action) {
            case "/accounts":
                listAccounts(request, response);
                break;
            case "/search":
                searchAccounts(request, response);
                break;
            case "/sort":
                sortAccounts(request, response);
                break;
            default:
                response.sendRedirect("adminDashboard.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (!"admin".equals(session.getAttribute("userType"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getPathInfo();
        
        switch (action) {
            case "/create":
                createAccount(request, response);
                break;
            case "/delete":
                deleteAccount(request, response);
                break;
            default:
                response.sendRedirect("adminDashboard.jsp");
        }
    }
    
    private void listAccounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Account> accounts = accountService.getAllAccounts();
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/adminAccounts.jsp").forward(request, response);
    }
    
    private void searchAccounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        String searchType = request.getParameter("searchType");
        
        List<Account> accounts;
        if ("name".equals(searchType)) {
            accounts = accountService.searchAccountsByName(searchTerm);
        } else {
            Account account = accountService.getAccountByNumber(searchTerm);
            accounts = account != null ? List.of(account) : List.of();
        }
        
        request.setAttribute("accounts", accounts);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("searchType", searchType);
        request.getRequestDispatcher("/adminAccounts.jsp").forward(request, response);
    }
    
    private void sortAccounts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        
        List<Account> accounts;
        if ("balance".equals(sortBy)) {
            accounts = accountService.sortAccountsByBalance();
        } else {
            accounts = accountService.sortAccountsByName();
        }
        
        request.setAttribute("accounts", accounts);
        request.setAttribute("sortBy", sortBy);
        request.getRequestDispatcher("/adminAccounts.jsp").forward(request, response);
    }
    
    private void createAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String customerName = request.getParameter("customerName");
            String password = request.getParameter("password");
            String accountType = request.getParameter("accountType");
            String balanceStr = request.getParameter("initialBalance");
            
            if (customerName == null || customerName.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                accountType == null || accountType.trim().isEmpty() ||
                balanceStr == null || balanceStr.trim().isEmpty()) {
                request.setAttribute("error", "All fields are required");
                request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
                return;
            }
            
            double initialBalance = Double.parseDouble(balanceStr.trim());
            
            if (initialBalance < 0) {
                request.setAttribute("error", "Initial balance cannot be negative");
                request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
                return;
            }
        
            String accountNumber = accountService.generateAccountNumber();
            Account account = new Account(accountNumber, customerName.trim(), password.trim(), 
                                        accountType.trim(), initialBalance);
        
            if (accountService.createAccount(account)) {
                request.setAttribute("success", "Account created successfully! Account Number: " + accountNumber);
            } else {
                request.setAttribute("error", "Failed to create account");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid initial balance format");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while creating the account");
            System.err.println("Error creating account: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }
    
    private void deleteAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        
        if (accountService.deleteAccount(accountNumber)) {
            request.setAttribute("success", "Account deleted successfully");
        } else {
            request.setAttribute("error", "Failed to delete account or account not found");
        }
        
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }
}