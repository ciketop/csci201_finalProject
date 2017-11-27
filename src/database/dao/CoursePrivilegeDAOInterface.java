package database.dao;

import java.util.List;

import com.sun.istack.internal.Nullable;

import database.object.CoursePrivilege;

public interface CoursePrivilegeDAOInterface {
	List<CoursePrivilege> findAll();

    List<CoursePrivilege> findByPrivilege(int userID, int courseID, int privilegeID);

    boolean insertCoursePrivileges(List<CoursePrivilege> coursePrivileges);

    boolean updateCoursePrivileges(@Nullable List<CoursePrivilege> oldCoursePrivileges, List<CoursePrivilege> newCoursePrivileges);

    boolean deleteCoursePrivileges(List<CoursePrivilege> coursePrivileges);
    
}
