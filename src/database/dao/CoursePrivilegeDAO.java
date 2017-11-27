package database.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.object.CoursePrivilege;
import database.util.WhereClauseConnectType;
import util.Factory;

public class CoursePrivilegeDAO implements Factory<CoursePrivilege>, CoursePrivilegeDAOInterface {
    private String dbName;
    private GenericDAO<CoursePrivilege> genericDAO;
    private List<String> fullColumnLabels;
    private String tableName = "CoursePrivilege";

    public CoursePrivilegeDAO() {
        this.dbName = "LiveClass";
        genericDAO = new GenericDAO<>(this, dbName);

        fullColumnLabels = new ArrayList<>();
        fullColumnLabels.add("courseID");
        fullColumnLabels.add("userID");
        fullColumnLabels.add("privilegeID");
    }

    public CoursePrivilegeDAO(String dbName) {
        this.dbName = dbName;
        genericDAO = new GenericDAO<>(this, dbName);

        fullColumnLabels = new ArrayList<>();
        fullColumnLabels.add("courseID");
        fullColumnLabels.add("userID");
        fullColumnLabels.add("privilegeID");
    }

    @Override
    public List<CoursePrivilege> findAll() {
        List<CoursePrivilege> privileges = genericDAO.queryAll(tableName, fullColumnLabels);

        for (CoursePrivilege privilege : privileges) {
            System.out.println(privilege.getCourseID() + ", " + privilege.getUserID() + ", " + privilege.getPrivilegeID());
        }

        return privileges;
    }

    @Override
    public List<CoursePrivilege> findByPrivilege(int userID, int courseID, int privilegeID) {
        List<CoursePrivilege> privileges = new ArrayList<>();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            connection = ConnectionFactory.getConnection("LiveClass");
            ps = connection.prepareStatement("SELECT * FROM CoursePrivilege WHERE courseID = '" 
            		+ courseID + "' AND userID = '" + userID + "' AND privilegeID = '" + privilegeID + "'");
            

            // query database
            rs = ps.executeQuery();

            while (rs.next()) {
                CoursePrivilege privilege = new CoursePrivilege();

                privilege.setCourseID(rs.getInt("courseID"));
                privilege.setUserID(rs.getInt("userID"));
                privilege.setPrivilegeID(rs.getInt("privilegeID"));
                privileges.add(privilege);
            }

            return privileges;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(rs, ps, null, connection);
        }
    }

    @Override
    public boolean insertCoursePrivileges(List<CoursePrivilege> privileges) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("courseID");
        columnLabels.add("userID");
        columnLabels.add("privilegeID");
        
        int numInserted = genericDAO.insertObjects(tableName, columnLabels, privileges);
        System.out.println("numInserted = " + numInserted);

        return numInserted >= 0;
    }

    @Override
    public boolean updateCoursePrivileges(List<CoursePrivilege> oldPrivileges, List<CoursePrivilege> newPrivileges) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("courseID");
        columnLabels.add("userID");
        columnLabels.add("privilegeID");
        
        int numUpdated = genericDAO.updateObjects(
                tableName,
                columnLabels,
                columnLabels,
                oldPrivileges,
                newPrivileges,
                WhereClauseConnectType.AND
        );
        System.out.println("numUpdated = " + numUpdated);

        return numUpdated >= 0;
    }

    @Override
    public boolean deleteCoursePrivileges(List<CoursePrivilege> privileges) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("courseID");
        columnLabels.add("userID");
        columnLabels.add("privilegeID");
        
        int numDeleted = genericDAO.deleteObjects(tableName, columnLabels, privileges);
        System.out.println("numDeleted = " + numDeleted);

        return numDeleted >= 0;
    }

    @Override
    public CoursePrivilege factory() {
        return new CoursePrivilege();
    }
}
