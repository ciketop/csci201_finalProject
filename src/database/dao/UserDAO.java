package database.dao;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import database.object.User;
import database.util.Crypto;
import database.util.WhereClauseConnectType;
import util.Factory;

public class UserDAO implements Factory<User>, UserDAOInterface {
    private String dbName;
    private GenericDAO<User> genericDAO;
    private List<String> fullColumnLabels;
    private String tableName = "User";

    public UserDAO() {
        this.dbName = "LiveClass";
        genericDAO = new GenericDAO<>(this, dbName);

        fullColumnLabels = new ArrayList<>();
        fullColumnLabels.add("userID");
        fullColumnLabels.add("username");
        fullColumnLabels.add("password");
        fullColumnLabels.add("email");
        fullColumnLabels.add("firstName");
        fullColumnLabels.add("lastName");
        fullColumnLabels.add("salt");
    }

    public UserDAO(String dbName) {
        this.dbName = dbName;
        genericDAO = new GenericDAO<>(this, dbName);

        fullColumnLabels = new ArrayList<>();
        fullColumnLabels.add("userID");
        fullColumnLabels.add("username");
        fullColumnLabels.add("password");
        fullColumnLabels.add("email");
        fullColumnLabels.add("firstName");
        fullColumnLabels.add("lastName");
        fullColumnLabels.add("salt");
    }

    @Override
    public List<User> findAll() {
        List<User> users = genericDAO.queryAll(tableName, fullColumnLabels);

        for (User user : users) {
            System.out.println(user.getUserID() + ", " + user.getUsername() + ", " + user.getPassword());
        }

        return users;
    }

    @Override
    public List<User> findById(int userID) {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            connection = ConnectionFactory.getConnection("LiveClass");

            int psParamIdx = 0;
            ps = connection.prepareStatement("SELECT * FROM User WHERE userID = ?");
            ps.setString(++psParamIdx, Integer.toString(userID));

            // query database
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();

                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setSalt(rs.getString("salt"));
                users.add(user);
            }

            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(rs, ps, null, connection);
        }
    }

    @Override
    public List<User> findByUsername(String username) {
    	List<User> users = new ArrayList<>();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            connection = ConnectionFactory.getConnection("LiveClass");

            int psParamIdx = 0;
            ps = connection.prepareStatement("SELECT * FROM User WHERE username = ?");
            ps.setString(++psParamIdx, username);

            // query database
            rs = ps.executeQuery();

            while (rs.next()) {
            		User user = new User();
            		user.setSalt(rs.getString("salt"));
            		user.setPassword(rs.getString("password"));
                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                

                users.add(user);
            }

            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(rs, ps, null, connection);
        }
    }

    @Override
    public boolean insertUsers(List<User> users) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("username");
        columnLabels.add("password");
        columnLabels.add("firstName");
        columnLabels.add("lastName");
        columnLabels.add("email");
        columnLabels.add("salt");

        int numInserted = genericDAO.insertObjects(tableName, columnLabels, users);
        System.out.println("numInserted = " + numInserted);

        return numInserted >= 0;
    }

    @Override
    public boolean updateUsers(List<User> oldUsers, List<User> newUsers) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("username");
        columnLabels.add("password");
        columnLabels.add("firstName");
        columnLabels.add("lastName");
        columnLabels.add("email");
        columnLabels.add("salt");
        int numUpdated = genericDAO.updateObjects(
                tableName,
                columnLabels,
                columnLabels,
                oldUsers,
                newUsers,
                WhereClauseConnectType.AND
        );
        System.out.println("numUpdated = " + numUpdated);

        return numUpdated >= 0;
    }

    @Override
    public boolean deleteUsers(List<User> users) {
        List<String> columnLabels = new ArrayList<>();
        columnLabels.add("username");
        columnLabels.add("password");
        columnLabels.add("firstName");
        columnLabels.add("lastName");
        columnLabels.add("email");
        columnLabels.add("salt");
        int numDeleted = genericDAO.deleteObjects(tableName, columnLabels, users);
        System.out.println("numDeleted = " + numDeleted);

        return numDeleted >= 0;
    }

    @Override
    public boolean login(String username, String password) {

        if (username == null
                || username.isEmpty()
                || password == null
                || password.isEmpty()) {
//            return null;
            return false;
        }
        
        List<User> users = new ArrayList<>();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            connection = ConnectionFactory.getConnection("LiveClass");

            int psParamIdx = 0;
            ps = connection.prepareStatement("SELECT * FROM User WHERE username = ?");
            ps.setString(++psParamIdx, username);

            // query database
            rs = ps.executeQuery();
            Crypto crypto = new Crypto();
            while (rs.next()) {
            		User user = new User();
            		user.setSalt(rs.getString("salt"));
            		user.setPassword(rs.getString("password"));
            		String hash = crypto.hashPassword(password, user.getSalt());
            		if(!hash.equals(user.getPassword())) {
            			continue;
            		}
                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                

                users.add(user);
            }

            return users != null && !users.isEmpty();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            // close all connection
            ConnectionFactory.closeConnection(rs, ps, null, connection);
        }
        
    }

    @Override
    public User factory() {
        return new User();
    }
}
