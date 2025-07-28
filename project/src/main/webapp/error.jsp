<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Bank Management System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="login-container">
        <div class="login-card fade-in">
            <div class="login-header">
                <h1 class="login-title">⚠️ Error</h1>
                <p class="login-subtitle">Something went wrong</p>
            </div>
            
            <div class="alert alert-error">
                <strong>Error Code:</strong> ${pageContext.errorData.statusCode}<br>
                <strong>Error Message:</strong> 
                <c:choose>
                    <c:when test="${pageContext.errorData.statusCode == 404}">
                        The page you are looking for could not be found.
                    </c:when>
                    <c:when test="${pageContext.errorData.statusCode == 500}">
                        An internal server error occurred. Please try again later.
                    </c:when>
                    <c:otherwise>
                        An unexpected error occurred.
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div style="text-align: center; margin-top: 2rem;">
                <a href="login.jsp" class="btn btn-primary">Go to Login</a>
            </div>
        </div>
    </div>
</body>
</html>