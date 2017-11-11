<%@ page import="java.util.List" %>
<%@ page import="database.object.User" %>
<%@ page import="database.dao.UserDAO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    UserDAO userDAO = (UserDAO) request.getSession().getAttribute("userDAO");
    if (userDAO == null) {
        userDAO = new UserDAO();
        request.getSession().setAttribute("userDAO", userDAO);
    }
    List<User> users = userDAO.findAll();
    pageContext.setAttribute("users", users);
%>
<html>
<head>
    <title>List all users</title>
</head>
<body>
<lu>
    <c:forEach var="user" items="${users}">
        <li>${user.userID}, ${user.username}, ${user.password}</li>
    </c:forEach>
</lu>
</body>
</html>
