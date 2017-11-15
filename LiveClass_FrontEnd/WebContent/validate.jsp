<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.dao.UserDAO" %>
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
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("password", password);
	}
	else {
%>		
		Invalid login, please try again!		
<%		
	}
%>