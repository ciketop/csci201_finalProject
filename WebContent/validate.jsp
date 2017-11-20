<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="database.object.User" %>
<%
	UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
	if(userDAO == null) {
		userDAO = new UserDAO();
		request.getSession().setAttribute("userDAO", userDAO);
	}

	String username = request.getParameter("username");
	String password = request.getParameter("password");
	System.out.println(username + " : " + password);
	
	if(userDAO.login(username, password)) {
		List<User> usr = userDAO.findByUsername(username);
		int userID = usr.get(0).getUserID();
		System.out.println("userID: " + userID);
		/* request.getSession().setAttribute("userID", userID);
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("password", password); */
		request.getSession().setAttribute("user", usr.get(0));
	}
	else {
		request.getSession().setAttribute("user", null);
%>		
		Invalid login, please try again!		
<%		
	}
%>