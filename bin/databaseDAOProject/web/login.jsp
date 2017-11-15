<%@ page import="database.dao.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: HZJ
  Date: 11/9/2017
  Time: 3:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserDAO userDAO = (UserDAO) request.getSession().getAttribute("userDAO");
    if (userDAO == null) {
        userDAO = new UserDAO();
        request.getSession().setAttribute("userDAO", userDAO);
    }
%>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login Page</h1>
<span style="color: red;font-weight:bold">${not empty sessionScope.errorMsg ? sessionScope.errorMsg : ''}</span><br>
<form name="loginForm" method="POST" action="${pageContext.request.contextPath}/authenticate.jsp">

    Username<input type="text" name='username'/><br>
    Password<input type="password" name="password"/><br>

    <input type="submit" name="submit" value="submit">
</form>
</body>
</html>
