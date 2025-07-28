package com.bank.controller;

import com.bank.model.Transaction;
import com.bank.service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/transaction/*")
public class TransactionController extends HttpServlet {
    private TransactionService transactionService;
    
    @Override
    public void init() throws ServletException {
        transactionService = new TransactionService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (!"customer".equals(session.getAttribute("userType"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getPathInfo();
        String accountNumber = (String) session.getAttribute("accountNumber");
        
        switch (action) {
            case "/history":
                getTransactionHistory(request, response, accountNumber);
                break;
            case "/sort":
                sortTransactions(request, response, accountNumber);
                break;
            default:
                response.sendRedirect("customerDashboard.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (!"customer".equals(session.getAttribute("userType"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getPathInfo();
        String accountNumber = (String) session.getAttribute("accountNumber");
        
        switch (action) {
            case "/deposit":
                deposit(request, response, accountNumber);
                break;
            case "/withdraw":
                withdraw(request, response, accountNumber);
                break;
            default:
                response.sendRedirect("customerDashboard.jsp");
        }
    }
    
    private void deposit(HttpServletRequest request, HttpServletResponse response, String accountNumber)
            throws ServletException, IOException {
        try {
            String amountStr = request.getParameter("amount");
            String description = request.getParameter("description");
            
            if (amountStr == null || amountStr.trim().isEmpty()) {
                request.setAttribute("error", "Amount is required");
                updateBalanceAndForward(request, response, accountNumber);
                return;
            }
            
            double amount = Double.parseDouble(amountStr.trim());
            
            if (amount <= 0) {
                request.setAttribute("error", "Amount must be greater than zero");
                updateBalanceAndForward(request, response, accountNumber);
                return;
            }
            
            if (transactionService.deposit(accountNumber, amount, description)) {
                request.setAttribute("success", String.format("Deposit successful! Amount: $%.2f", amount));
            } else {
                request.setAttribute("error", "Deposit failed. Please check the amount.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid amount format");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during deposit");
            System.err.println("Error during deposit: " + e.getMessage());
        }
        
        updateBalanceAndForward(request, response, accountNumber);
    }
    
    private void withdraw(HttpServletRequest request, HttpServletResponse response, String accountNumber)
            throws ServletException, IOException {
        try {
            String amountStr = request.getParameter("amount");
            String description = request.getParameter("description");
            
            if (amountStr == null || amountStr.trim().isEmpty()) {
                request.setAttribute("error", "Amount is required");
                updateBalanceAndForward(request, response, accountNumber);
                return;
            }
            
            double amount = Double.parseDouble(amountStr.trim());
            
            if (amount <= 0) {
                request.setAttribute("error", "Amount must be greater than zero");
                updateBalanceAndForward(request, response, accountNumber);
                return;
            }
            
            if (transactionService.withdraw(accountNumber, amount, description)) {
                request.setAttribute("success", String.format("Withdrawal successful! Amount: $%.2f", amount));
            } else {
                request.setAttribute("error", "Withdrawal failed. Insufficient balance or invalid amount.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid amount format");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during withdrawal");
            System.err.println("Error during withdrawal: " + e.getMessage());
        }
        
        updateBalanceAndForward(request, response, accountNumber);
    }
    
    private void updateBalanceAndForward(HttpServletRequest request, HttpServletResponse response, String accountNumber)
            throws ServletException, IOException {
        double newBalance = transactionService.getAccountBalance(accountNumber);
        request.setAttribute("currentBalance", newBalance);
        request.getRequestDispatcher("/customerDashboard.jsp").forward(request, response);
    }
    
    private void getTransactionHistory(HttpServletRequest request, HttpServletResponse response, String accountNumber)
            throws ServletException, IOException {
        List<Transaction> transactions = transactionService.getTransactionHistory(accountNumber);
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/transactionHistory.jsp").forward(request, response);
    }
    
    private void sortTransactions(HttpServletRequest request, HttpServletResponse response, String accountNumber)
            throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        
        List<Transaction> transactions;
        if ("amount".equals(sortBy)) {
            transactions = transactionService.sortTransactionsByAmount(accountNumber);
        } else {
            transactions = transactionService.sortTransactionsByDate(accountNumber);
        }
        
        request.setAttribute("transactions", transactions);
        request.setAttribute("sortBy", sortBy);
        request.getRequestDispatcher("/transactionHistory.jsp").forward(request, response);
    }
}