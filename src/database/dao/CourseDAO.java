package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.object.Course;
import database.object.CoursePrivilege;
import database.util.WhereClauseConnectType;
import util.Factory;

public class CourseDAO implements Factory<Course>, CourseDAOInterface {
    private String dbName;
    private GenericDAO<Course> genericDAO;
    private List<String> fullColumnLabels;
    private String tableName = "Course";

    public CourseDAO() {
        this.dbName = "LiveClass";
        genericDAO = new GenericDAO<>(this, dbName);

        fullColumnLabels = new ArrayList<>();
        fullColumnLabels.add("courseID");
        fullColumnLabels.add("coursePrefix");
        fullColumnLabels.add("courseNumber");
        fullColumnLabels.add("courseName");
        fullColumnLabels.add("accessCode");
    }

    public CourseDAO(String dbName) {
        this.dbName = dbName;
        genericDAO = new GenericDAO<>(this, dbName);

        fullColumnLabels = new ArrayList<>();
        fullColumnLabels.add("courseID");
        fullColumnLabels.add("coursePrefix");
        fullColumnLabels.add("courseNumber");
        fullColumnLabels.add("courseName");
        fullColumnLabels.add("accessCode");
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = genericDAO.queryAll(tableName, fullColumnLabels);

        for (Course course : courses) {
            System.out.println(course.getCourseID() + ", " + course.getCoursePrefix() + ", " + course.getCourseNumber());
        }

        return courses;
    }

    @Override
    public List<Course> findById(int courseID) {
        List<Course> courses = new ArrayList<>();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            connection = ConnectionFactory.getConnection("LiveClass");

            int psParamIdx = 0;
            ps = connection.prepareStatement("SELECT * FROM Course WHERE courseID = ?");
            ps.setString(++psParamIdx, Integer.toString(courseID));

            // query database
            rs = ps.executeQuery();

            while (rs.next()) {
                Course course = new Course();

                course.setCourseID(rs.getInt("courseID"));
                course.setCoursePrefix(rs.getString("coursePrefix"));
                course.setCourseNumber(rs.getString("courseNumber"));
                course.setCourseName(rs.getString("courseName"));
                course.setAccessCode(rs.getString("accessCode"));
                courses.add(course);
            }

            return courses;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(rs, ps, null, connection);
        }
    }

    @Override
    public List<Course> findByAccessCode(String accessCode) {
    	List<Course> courses = new ArrayList<>();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            connection = ConnectionFactory.getConnection("LiveClass");

            int psParamIdx = 0;
            ps = connection.prepareStatement("SELECT * FROM Course WHERE accessCode = ?");
            ps.setString(++psParamIdx, accessCode);

            // query database
            rs = ps.executeQuery();

            while (rs.next()) {
            		Course course = new Course();
            		course.setCourseID(rs.getInt("courseID"));
                course.setCoursePrefix(rs.getString("coursePrefix"));
                course.setCourseNumber(rs.getString("courseNumber"));
                course.setCourseName(rs.getString("courseName"));
                course.setAccessCode(rs.getString("accessCode"));
                courses.add(course);
                
            }

            return courses;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(rs, ps, null, connection);
        }
    }

    @Override
    public boolean insertCourses(List<Course> courses) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("courseID");
        columnLabels.add("coursePrefix");
        columnLabels.add("courseNumber");
        columnLabels.add("courseName");
        columnLabels.add("accessCode");
        
        int numInserted = genericDAO.insertObjects(tableName, columnLabels, courses);
        System.out.println("numInserted = " + numInserted);

        return numInserted >= 0;
    }

    @Override
    public boolean updateCourses(List<Course> oldCourses, List<Course> newCourses) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("courseID");
        columnLabels.add("coursePrefix");
        columnLabels.add("courseNumber");
        columnLabels.add("courseName");
        columnLabels.add("accessCode");
        
        int numUpdated = genericDAO.updateObjects(
                tableName,
                columnLabels,
                columnLabels,
                oldCourses,
                newCourses,
                WhereClauseConnectType.AND
        );
        System.out.println("numUpdated = " + numUpdated);

        return numUpdated >= 0;
    }

    @Override
    public boolean deleteCourses(List<Course> courses) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("courseID");
        columnLabels.add("coursePrefix");
        columnLabels.add("courseNumber");
        columnLabels.add("courseName");
        columnLabels.add("accessCode");
        
        int numDeleted = genericDAO.deleteObjects(tableName, columnLabels, courses);
        System.out.println("numDeleted = " + numDeleted);

        return numDeleted >= 0;
    }
    
    @Override
    public boolean enroll(int userID, String accessCode) {
    	if (accessCode == null || accessCode.isEmpty()) {
//            return null;
            return false;
        }
        
        List<Course> courses = findByAccessCode(accessCode);
        System.out.println("In enroll " + courses.size());
        
        if(courses == null || courses.isEmpty())
        		return false;
        
        CoursePrivilegeDAO coursePrivilegeDAO = new CoursePrivilegeDAO();
        List<CoursePrivilege> privileges = coursePrivilegeDAO.findByPrivilege(userID, courses.get(0).getCourseID(), 2);
        if(privileges != null && !privileges.isEmpty())
        		return true;
        privileges = new ArrayList<CoursePrivilege>();
        
        System.out.println("userID: " + userID);
        System.out.println("courseID: " + courses.get(0).getCourseID());
        
        CoursePrivilege coursePrivilege = new CoursePrivilege(courses.get(0).getCourseID(), userID, 2);
        privileges.add(coursePrivilege);
        return coursePrivilegeDAO.insertCoursePrivileges(privileges);
    }

    @Override
    public Course factory() {
        return new Course();
    }
}
