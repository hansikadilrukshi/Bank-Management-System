<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%
    if (!"customer".equals(session.getAttribute("userType"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction History - Bank Management System</title>
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
        <div class="card fade-in">
            <div class="card-header">
                <h2 class="card-title">Transaction History</h2>
                <p class="card-subtitle">
                    <c:choose>
                        <c:when test="${not empty sortBy}">
                            Transactions sorted by ${sortBy}
                        </c:when>
                        <c:otherwise>
                            Your complete transaction history
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>
            
            <div class="action-buttons">
                <a href="customerDashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
                <a href="transaction/history" class="btn btn-primary">Refresh</a>
                <a href="transaction/sort?sortBy=date" class="btn btn-secondary">Sort by Date</a>
                <a href="transaction/sort?sortBy=amount" class="btn btn-secondary">Sort by Amount</a>
            </div>
            
            <c:choose>
                <c:when test="${not empty transactions}">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Transaction ID</th>
                                    <th>Date & Time</th>
                                    <th>Type</th>
                                    <th>Amount</th>
                                    <th>Balance After</th>
                                    <th>Description</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="transaction" items="${transactions}">
                                    <tr>
                                        <td><small>${transaction.transactionId}</small></td>
                                        <td>${transaction.formattedDate}</td>
                                        <td>
                                            <span class="transaction-badge ${transaction.transactionType == 'DEPOSIT' ? 'transaction-deposit' : 'transaction-withdrawal'}">
                                                ${transaction.transactionType}
                                            </span>
                                        </td>
                                        <td>
                                            <strong style="color: ${transaction.transactionType == 'DEPOSIT' ? '#059669' : '#dc2626'};">
                                                ${transaction.transactionType == 'DEPOSIT' ? '+' : '-'}$<fmt:formatNumber value="${transaction.amount}" 
                                                   type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                            </strong>
                                        </td>
                                        <td>
                                            <strong>
                                                $<fmt:formatNumber value="${transaction.balanceAfter}" 
                                                   type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                            </strong>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty transaction.description}">
                                                    ${transaction.description}
                                                </c:when>
                                                <c:otherwise>
                                                    <em style="color: #9ca3af;">No description</em>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    
                    <div style="margin-top: 20px; text-align: center; color: #6b7280;">
                        Total Transactions: ${transactions.size()}
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; padding: 40px; color: #6b7280;">
                        <h3>No transactions found</h3>
                        <p>You haven't made any transactions yet. Start by making a deposit or withdrawal.</p>
                        <a href="customerDashboard.jsp" class="btn btn-primary">Go to Dashboard</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>