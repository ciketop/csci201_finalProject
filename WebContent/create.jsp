<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="database.object.User" %>
<%@ page import="database.util.Crypto" %>
<%@ page import="java.util.ArrayList" %>
<%
	UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
	if(userDAO == null) {
		userDAO = new UserDAO();
		request.getSession().setAttribute("userDAO", userDAO);
	}

	String username = request.getParameter("username");
	String password = request.getParameter("password");
	
	String fname = request.getParameter("fname");
	String lname = request.getParameter("lname");
	String email = request.getParameter("email");
	/* System.out.println(username + " : " + password); */
	
	if(userDAO.findByUsername(username).size() > 0) {
		/* List<User> usr = userDAO.findByUsername(username);
		int userID = usr.get(0).getUserID();
		System.out.println("userID: " + userID); */
		/* request.getSession().setAttribute("userID", userID);
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("password", password); */
		/* request.getSession().setAttribute("user", usr.get(0)); */
%>
		username already taken, please enter a new one
<%		
	}
	else {
		/* request.getSession().setAttribute("user", null);	 */
		List<User> toAdd = new ArrayList<User>();
		Crypto crypto = new Crypto();
		String salt = crypto.saltGenerator();
		String hash = crypto.hashPassword(password, salt);
		User newAccount = new User(username, hash, fname, lname, email, salt);
		toAdd.add(newAccount);
		userDAO.insertUsers(toAdd);
		request.getSession().setAttribute("user", newAccount);
	}
%>