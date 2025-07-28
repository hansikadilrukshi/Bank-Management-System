<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
    <title>Account Management - Bank Management System</title>
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
                <h2 class="card-title">Customer Accounts</h2>
                <p class="card-subtitle">
                    <c:choose>
                        <c:when test="${not empty searchTerm}">
                            Search results for "${searchTerm}" (${searchType})
                        </c:when>
                        <c:when test="${not empty sortBy}">
                            Accounts sorted by ${sortBy}
                        </c:when>
                        <c:otherwise>
                            All customer accounts in the system
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>
            
            <div class="action-buttons">
                <a href="adminDashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
                <a href="admin/accounts" class="btn btn-primary">Refresh</a>
            </div>
            
            <c:choose>
                <c:when test="${not empty accounts}">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Account Number</th>
                                    <th>Customer Name</th>
                                    <th>Account Type</th>
                                    <th>Balance</th>
                                    <th>Created Date</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="account" items="${accounts}">
                                    <tr>
                                        <td><strong>${account.accountNumber}</strong></td>
                                        <td>${account.customerName}</td>
                                        <td>
                                            <span class="transaction-badge" 
                                                  style="background: #e0f2fe; color: #0277bd;">
                                                ${account.accountType}
                                            </span>
                                        </td>
                                        <td>
                                            <strong style="color: #059669;">
                                                $<fmt:formatNumber value="${account.balance}" 
                                                   type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                            </strong>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${account.createdDate}" 
                                                          pattern="yyyy-MM-dd HH:mm"/>
                                        </td>
                                        <td>
                                            <button onclick="deleteAccount('${account.accountNumber}')" 
                                                    class="btn btn-danger btn-small">
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    
                    <div style="margin-top: 20px; text-align: center; color: #6b7280;">
                        Total Accounts: ${accounts.size()}
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; padding: 40px; color: #6b7280;">
                        <h3>No accounts found</h3>
                        <p>
                            <c:choose>
                                <c:when test="${not empty searchTerm}">
                                    No accounts match your search criteria.
                                </c:when>
                                <c:otherwise>
                                    No customer accounts have been created yet.
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <a href="adminDashboard.jsp" class="btn btn-primary">Go to Dashboard</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <script>
        function deleteAccount(accountNumber) {
            if (confirm('Are you sure you want to delete account ' + accountNumber + '? This action cannot be undone and will delete all associated transaction history.')) {
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = 'admin/delete';
                
                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'accountNumber';
                input.value = accountNumber;
                
                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>