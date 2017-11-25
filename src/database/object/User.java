package database.object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    private int userID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String salt;

    public User() {
        //
    }
    
    public User(String username, String password, String fname, String lname, String email, String salt) {
		super();
//		this.userID = userID;
		this.username = username;
		this.password = password;
		this.firstName = fname;
		this.lastName = lname;
		this.email = email;
		this.salt = salt;
	}

	public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fname) {
		this.firstName = fname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lname) {
		this.lastName = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
    
    // getPriv class
    // Takes in a courseID and return the user's privilege in that class
    public int getPriv(int courseID) {
    		int priv = 0;
    		
    		// Set up all the variables
    		Connection conn = null;
    		Statement st = null;
    		ResultSet rs = null;
    		
    		try {
    			// Connect to database
    			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LiveClass?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			
			// Select from the coursePrivilege table with userID and courseID
			rs = st.executeQuery("SELECT privilegeID FROM CoursePrivilege WHERE userID='" + userID + "' AND courseID='" + courseID + "'");
    			
			// If entry is found, get privilegeID from there
			if(rs.next()) {
    				priv = rs.getInt("privilegeID");
    				System.out.println("privilege: " + priv);
    			}
			// If not found, then it's a public class, set privilege to 2 (student)
    			else {
    				priv = 2;
    			}	
    		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			// Close everything
			try {
				if(conn != null) conn.close();
				if(st != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
			}
		}
    		
    		// Return the privilegeID
    		return priv;
    }
    
}
