package database.object;

public class Course {
	private int courseID;
	private String coursePrefix;
	private String courseNumber;
	private String courseName;
	
	public Course() {
		super();
	}
	
	public Course(int courseID, String coursePrefix, String courseNumber, String courseName) {
		super();
		this.courseID = courseID;
		this.coursePrefix = coursePrefix;
		this.courseNumber = courseNumber;
		this.courseName = courseName;
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
	
	
}
