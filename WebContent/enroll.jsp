<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="database.object.User" %>
<%@ page import="database.dao.CourseDAO" %>
<%
	User currUser = (User)request.getSession().getAttribute("user");
	//List<Course> userCourses = (List<Course>)request.getSession().getAttribute("courses");

	String accessCode = request.getParameter("accesscode");
	System.out.println(accessCode);
	
	CourseDAO courseDAO = new CourseDAO();
	boolean successful = courseDAO.enroll(currUser.getUserID(), accessCode);
	
	System.out.println("Successful = " + successful);
%>	
