package database.object;

public class User {
    private int userID;
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String email;

    public User() {
        //
    }

//    public User(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
    
    public User(int userID, String username, String password, String fname, String lname, String email) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
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

    public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
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
    
}
