package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.object.Course;
import database.object.User;

/**
 * Servlet implementation class queryClasses
 */
@WebServlet("/queryClasses")
public class queryClasses extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In servlet");
		
		List<Course> courses = new ArrayList<Course>();
		List<Course> publicCourse = new ArrayList<Course>();
		
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet courseID = null;
		ResultSet courseDetail = null;
		
		User currUser = (User)request.getSession().getAttribute("user");
		
		if(currUser != null) {
		
			int userID = currUser.getUserID();
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LiveClass?user=root&password=root&useSSL=false");
				st = conn.createStatement();
				ps = conn.prepareStatement("SELECT * from Course WHERE courseID=?");
				
				courseID = st.executeQuery("SELECT * from CoursePrivilege WHERE userID='" + userID + "'");
				while(courseID.next()) {
					int ID = courseID.getInt("courseID");
					ps.setInt(1, ID);
					courseDetail = ps.executeQuery();
					while(courseDetail.next()) {
						String prefix = courseDetail.getString("coursePrefix");
						String number = courseDetail.getString("courseNumber");
						String name = courseDetail.getString("courseName");
						String code = courseDetail.getString("accessCode");
						System.out.println("Queried: " + ID + " " + prefix + " " + number + " - " + name + " " + code);
						Course newCourse = new Course(ID, prefix, number, name, code);
						courses.add(newCourse);
					}
				}
				
			} catch (SQLException sqle) {
				System.out.println ("SQLException: " + sqle.getMessage());
			} catch (ClassNotFoundException cnfe) {
				System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
			} finally {
				try {
					if (courseID != null) courseID.close();
					if(courseDetail != null) courseDetail.close();
					if (st != null) st.close();
					if (ps != null) ps.close();
					if (conn != null) conn.close();
				} catch (SQLException sqle) {
					System.out.println("sqle: " + sqle.getMessage());
				}
			}
			request.getSession().setAttribute("courses", courses);
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LiveClass?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			ps = conn.prepareStatement("SELECT * from Course WHERE courseID=?");
			
			courseID = st.executeQuery("SELECT * from PublicCourse");
			while(courseID.next()) {
				int ID = courseID.getInt("courseID");
				ps.setInt(1, ID);
				courseDetail = ps.executeQuery();
				while(courseDetail.next()) {
					String prefix = courseDetail.getString("coursePrefix");
					String number = courseDetail.getString("courseNumber");
					String name = courseDetail.getString("courseName");
					String code = courseDetail.getString("accessCode");
					System.out.println("Queried: " + ID + " " + prefix + " " + number + " - " + name + " " + code);
					Course newCourse = new Course(ID, prefix, number, name, code);
					publicCourse.add(newCourse);
				}
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (courseID != null) courseID.close();
				if(courseDetail != null) courseDetail.close();
				if (st != null) st.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		request.getSession().setAttribute("publicCourse", publicCourse);
		
		
		String nextJSP = "/lectures.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request, response);
	
	}
	

}

