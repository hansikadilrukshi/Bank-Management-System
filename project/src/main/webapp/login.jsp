<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Management System - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="login-container">
        <div class="login-card fade-in">
            <div class="login-header">
                <h1 class="login-title">🏦 SecureBank</h1>
                <p class="login-subtitle">Your trusted banking partner</p>
            </div>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>Error:</strong> ${error}
                </div>
            </c:if>
            
            <form action="login" method="post">
                <div class="form-group">
                    <label class="form-label">User Type</label>
                    <select name="userType" class="form-control form-select" required>
                        <option value="">Select User Type</option>
                        <option value="admin">Administrator</option>
                        <option value="customer">Customer</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label class="form-label">Username / Account Number</label>
                    <input type="text" name="username" class="form-control" required maxlength="50"
                           placeholder="Enter your username or account number">
                </div>
                
                <div class="form-group">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" required maxlength="50"
                           placeholder="Enter your password">
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1rem;">
                    Login to Account
                </button>
            </form>
            
            <div style="margin-top: 2rem; padding-top: 1rem; border-top: 1px solid #e5e7eb; text-align: center;">
                <p style="color: #6b7280; font-size: 0.9rem;">
                    <strong>Demo Credentials:</strong><br>
                    Admin: admin / admin123<br>
                    Customer: Create account via admin panel
                </p>
            </div>
        </div>
    </div>
</body>
</html>