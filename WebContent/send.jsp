<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="database.object.User" %>
<%@ page import="database.object.Course" %>
<%
	//User currUser = (User)request.getSession().getAttribute("user");
	List<Course> userCourses = (List<Course>)request.getSession().getAttribute("courses");

	String email = request.getParameter("email");
	String courseID = request.getParameter("courseID");
	System.out.println(email + " : " + courseID);
	
	for(Course course : userCourses) {
		if(course.getCourseID() == Integer.parseInt(courseID)) {
			course.sendAccessCode(email);
			break;
		}
	}
%>	
