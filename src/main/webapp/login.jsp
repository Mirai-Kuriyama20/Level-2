<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Book Management System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; min-height: 100vh; display: grid; place-items: center; background: #f4f6f8; }
        .login-box { width: 360px; border: 1px solid #ccc; border-radius: 5px; padding: 24px; background: #fff; }
        h1 { margin-top: 0; font-size: 24px; }
        label { display: block; margin-top: 14px; font-weight: bold; }
        input { width: 100%; box-sizing: border-box; padding: 8px; margin-top: 6px; }
        button { width: 100%; padding: 9px; margin-top: 18px; cursor: pointer; }
        .error { padding: 10px; margin-bottom: 15px; border: 1px solid #d32f2f; background: #ffebee; color: #b71c1c; }
    </style>
</head>
<body>
<div class="login-box">
    <h1>Login</h1>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="login" method="post">
        <label for="username">Username</label>
        <input id="username" name="username" value="${username}" required autofocus>

        <label for="password">Password</label>
        <input id="password" name="password" type="password" required>

        <button type="submit">Login</button>
    </form>
</div>
</body>
</html>
