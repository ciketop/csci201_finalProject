package database.dao;

import com.sun.istack.internal.Nullable;
import database.object.User;

import java.sql.*;
import java.util.List;

public interface UserDAOInterface {
    List<User> findAll();

    List<User> findById(int userID);

    List<User> findByUsername(String username);

    boolean insertUsers(List<User> users);

    boolean updateUsers(@Nullable List<User> oldUsers, List<User> newUsers);

    boolean deleteUsers(List<User> users);

    boolean login(String username, String password);
}
