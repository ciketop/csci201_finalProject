package database.object;

public class CoursePrivilege {
	private int courseID;
	private int userID;
	private int privilegeID;
	
	public CoursePrivilege() {
		//
	}
	public CoursePrivilege(int courseID, int userID, int privilegeID) {
		this.courseID = courseID;
		this.userID = userID;
		this.privilegeID = privilegeID;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getPrivilegeID() {
		return privilegeID;
	}
	public void setPrivilegeID(int privilegeID) {
		this.privilegeID = privilegeID;
	}
	
}
