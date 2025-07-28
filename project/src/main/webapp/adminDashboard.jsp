<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (!"admin".equals(session.getAttribute("userType"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Bank Management System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="header">
        <div class="header-content">
            <a href="adminDashboard.jsp" class="logo">🏦 SecureBank Admin</a>
            <div class="user-info">
                <span>Welcome, ${username}</span>
                <a href="logout" class="btn-logout">Logout</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <div class="card fade-in">
            <div class="card-header">
                <h2 class="card-title">Administrator Dashboard</h2>
                <p class="card-subtitle">Manage customer accounts and monitor bank operations</p>
            </div>
            
            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    <strong>Success:</strong> ${success}
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>Error:</strong> ${error}
                </div>
            </c:if>
            
            <div class="grid grid-2">
                <!-- Create Account Section -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">Create New Account</h3>
                        <p class="card-subtitle">Add a new customer account to the system</p>
                    </div>
                    
                    <form action="admin/create" method="post">
                        <div class="form-group">
                            <label class="form-label">Customer Name</label>
                            <input type="text" name="customerName" class="form-control" required maxlength="100"
                                   placeholder="Enter customer full name">
                        </div>
                        
                        <div class="form-group">
                            <label class="form-label">Password</label>
                            <input type="password" name="password" class="form-control" required maxlength="50"
                                   placeholder="Set account password">
                        </div>
                        
                        <div class="form-group">
                            <label class="form-label">Account Type</label>
                            <select name="accountType" class="form-control form-select" required>
                                <option value="">Select Account Type</option>
                                <option value="Savings">Savings Account</option>
                                <option value="Current">Current Account</option>
                                <option value="Fixed Deposit">Fixed Deposit</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label class="form-label">Initial Balance</label>
                            <input type="number" name="initialBalance" class="form-control" 
                                   min="0" step="0.01" required placeholder="0.00">
                        </div>
                        
                        <button type="submit" class="btn btn-success">Create Account</button>
                    </form>
                </div>
                
                <!-- Delete Account Section -->
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">Delete Account</h3>
                        <p class="card-subtitle">Remove an existing customer account</p>
                    </div>
                    
                    <form action="admin/delete" method="post" onsubmit="return confirm('Are you sure you want to delete this account? This action cannot be undone.')">
                        <div class="form-group">
                            <label class="form-label">Account Number</label>
                            <input type="text" name="accountNumber" class="form-control" required 
                                   placeholder="Enter account number to delete">
                        </div>
                        
                        <button type="submit" class="btn btn-danger">Delete Account</button>
                    </form>
                </div>
            </div>
            
            <!-- Account Management Actions -->
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Account Management</h3>
                    <p class="card-subtitle">Search, sort, and view customer accounts</p>
                </div>
                
                <div class="search-filter">
                    <form action="admin/search" method="get" style="display: flex; gap: 15px; align-items: end; flex: 1;">
                        <div class="form-group" style="margin-bottom: 0; flex: 1;">
                            <label class="form-label">Search Term</label>
                            <input type="text" name="searchTerm" class="form-control" 
                                   placeholder="Enter account number or customer name" value="${searchTerm}">
                        </div>
                        
                        <div class="form-group" style="margin-bottom: 0;">
                            <label class="form-label">Search By</label>
                            <select name="searchType" class="form-control form-select">
                                <option value="number" ${searchType == 'number' ? 'selected' : ''}>Account Number</option>
                                <option value="name" ${searchType == 'name' ? 'selected' : ''}>Customer Name</option>
                            </select>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>
                </div>
                
                <div class="action-buttons">
                    <a href="admin/accounts" class="btn btn-secondary">View All Accounts</a>
                    <a href="admin/sort?sortBy=name" class="btn btn-secondary">Sort by Name</a>
                    <a href="admin/sort?sortBy=balance" class="btn btn-secondary">Sort by Balance</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>