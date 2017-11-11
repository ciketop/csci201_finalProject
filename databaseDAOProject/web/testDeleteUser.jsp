<%@ page import="database.dao.UserDAO" %>
<%@ page import="database.object.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>authenticate</title>
</head>
<%
    UserDAO userDAO = (UserDAO) request.getSession().getAttribute("userDAO");
    if (userDAO == null) {
        userDAO = new UserDAO();
        request.getSession().setAttribute("userDAO", userDAO);
    }

    String username = request.getParameter("username");
    System.out.println("inside authenticate");
    System.out.println(username + " " + request.getParameter("password"));

    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        users.add(new User("User#" + i, "password"));
    }

    System.out.println(userDAO.deleteUsers(users));
%>
<body>

</body>
</html>
