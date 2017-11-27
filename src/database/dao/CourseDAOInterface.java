package database.dao;

import com.sun.istack.internal.Nullable;
import database.object.Course;

import java.util.List;

public interface CourseDAOInterface {
    List<Course> findAll();

    List<Course> findById(int courseID);

    List<Course> findByAccessCode(String accessCode);

    boolean insertCourses(List<Course> courses);

    boolean updateCourses(@Nullable List<Course> oldCourses, List<Course> newCourses);

    boolean deleteCourses(List<Course> courses);
    
    boolean enroll(int userID, String accessCode);
}
