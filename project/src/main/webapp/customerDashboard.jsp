<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="com.bank.service.TransactionService" %>
<%
    if (!"customer".equals(session.getAttribute("userType"))) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Get current balance
    String accountNumber = (String) session.getAttribute("accountNumber");
    TransactionService transactionService = new TransactionService();
    double currentBalance = transactionService.getAccountBalance(accountNumber);
    
    // Set balance in request if not already set by transaction
    if (request.getAttribute("currentBalance") == null) {
        request.setAttribute("currentBalance", currentBalance);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Dashboard - Bank Management System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="header">
        <div class="header-content">
            <a href="customerDashboard.jsp" class="logo">🏦 SecureBank</a>
            <div class="user-info">
                <span>Welcome, ${customerName}</span>
                <span>Account: ${accountNumber}</span>
                <a href="logout" class="btn-logout">Logout</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <!-- Balance Display -->
        <div class="balance-display fade-in">
            <div class="balance-amount">
                $<fmt:formatNumber value="${currentBalance}" type="number" 
                   minFractionDigits="2" maxFractionDigits="2"/>
            </div>
            <div class="balance-label">Current Account Balance</div>
        </div>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success fade-in">
                <strong>Success:</strong> ${success}
            </div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error fade-in">
                <strong>Error:</strong> ${error}
            </div>
        </c:if>
        
        <div class="grid grid-2">
            <!-- Deposit Section -->
            <div class="card slide-in">
                <div class="card-header">
                    <h3 class="card-title">💰 Deposit Money</h3>
                    <p class="card-subtitle">Add funds to your account</p>
                </div>
                
                <form action="transaction/deposit" method="post">
                    <div class="form-group">
                        <label class="form-label">Amount</label>
                        <input type="number" name="amount" class="form-control" 
                               min="0.01" step="0.01" required placeholder="0.00">
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">Description (Optional)</label>
                        <input type="text" name="description" class="form-control" 
                               placeholder="Enter transaction description">
                    </div>
                    
                    <button type="submit" class="btn btn-success">Deposit Money</button>
                </form>
            </div>
            
            <!-- Withdraw Section -->
            <div class="card slide-in" style="animation-delay: 0.1s;">
                <div class="card-header">
                    <h3 class="card-title">🏧 Withdraw Money</h3>
                    <p class="card-subtitle">Withdraw funds from your account</p>
                </div>
                
                <form action="transaction/withdraw" method="post">
                    <div class="form-group">
                        <label class="form-label">Amount</label>
                        <input type="number" name="amount" class="form-control" 
                               min="0.01" step="0.01" required placeholder="0.00"
                               max="${currentBalance}">
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">Description (Optional)</label>
                        <input type="text" name="description" class="form-control" 
                               placeholder="Enter transaction description">
                    </div>
                    
                    <button type="submit" class="btn btn-warning">Withdraw Money</button>
                </form>
            </div>
        </div>
        
        <!-- Transaction History Section -->
        <div class="card slide-in" style="animation-delay: 0.2s;">
            <div class="card-header">
                <h3 class="card-title">📊 Transaction History</h3>
                <p class="card-subtitle">View and manage your transaction records</p>
            </div>
            
            <div class="action-buttons">
                <a href="transaction/history" class="btn btn-primary">View All Transactions</a>
                <a href="transaction/sort?sortBy=date" class="btn btn-secondary">Sort by Date</a>
                <a href="transaction/sort?sortBy=amount" class="btn btn-secondary">Sort by Amount</a>
            </div>
        </div>
        
        <!-- Quick Stats -->
        <div class="dashboard-stats">
            <div class="stat-card slide-in" style="animation-delay: 0.3s;">
                <div class="stat-value">${accountNumber}</div>
                <div class="stat-label">Account Number</div>
            </div>
            
            <div class="stat-card slide-in" style="animation-delay: 0.4s;">
                <div class="stat-value">
                    <c:choose>
                        <c:when test="${currentBalance >= 0}">
                            <span style="color: #10b981;">Active</span>
                        </c:when>
                        <c:otherwise>
                            <span style="color: #ef4444;">Overdrawn</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="stat-label">Account Status</div>
            </div>
            
            <div class="stat-card slide-in" style="animation-delay: 0.5s;">
                <div class="stat-value">🔒</div>
                <div class="stat-label">Secure Banking</div>
            </div>
        </div>
    </div>
    
    <script>
        // Auto-refresh balance every 30 seconds
        setTimeout(function() {
            location.reload();
        }, 30000);
        
        // Add animation classes on load
        document.addEventListener('DOMContentLoaded', function() {
            const cards = document.querySelectorAll('.card');
            cards.forEach((card, index) => {
                setTimeout(() => {
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0)';
                }, index * 100);
            });
        });
    </script>
</body>
</html>