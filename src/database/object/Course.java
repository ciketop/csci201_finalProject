package database.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.dao.ConnectionFactory;
import util.Mail;

public class Course {
	private int courseID;
	private String coursePrefix;
	private String courseNumber;
	private String courseName;
	private String accessCode;
	
	public Course() {
		super();
	}
	
	public Course(int courseID, String coursePrefix, String courseNumber, String courseName, String accessCode) {
		super();
		this.courseID = courseID;
		this.coursePrefix = coursePrefix;
		this.courseNumber = courseNumber;
		this.courseName = courseName;
		this.accessCode = accessCode;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCoursePrefix() {
		return coursePrefix;
	}

	public void setCoursePrefix(String coursePrefix) {
		this.coursePrefix = coursePrefix;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	
	public String getAccessCode() {
		return accessCode;
	}
	
	public void sendAccessCode(String email) {
		String from = "liveclassinc@gmail.com";
        String message = "Dear Student,\n\nYou are given permission to enroll in " + coursePrefix + " " 
        			+ courseNumber + " - " + courseName + "!\n\nHere's your access code: " + accessCode 
        			+ "\n\nPlease create an account if you haven't already. Then you can "
        			+ "use the access code to complete your enrollment.\n\nThanks for choosing us!\n\n"
        			+ "LiveClass Team";
        String subject = "Welcome to LiveClass!";
        String smtp = "smtp.gmail.com";
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTo(email);
        mail.setSubject(subject);
        mail.setMessage(message);
        mail.setSmtpServ(smtp);
        int successful = mail.sendMail();
        if (successful == 0) {
            System.err.println("Access code is NOT sent to " + email);
        }
		
	}
}
