package com.revature.models;


import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{


	private static final long serialVersionUID = -1183527246843616399L;
	private int id;
    private String username;
    private String password;
    private String userType;

    // Constructors
    public User() {
		super();
	}    
    
    
    
    public User(int id, String username, String password, String userType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userType = userType;
	}



	// Getters and Setters



	public int getId() {
        return id;
    }



	public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", userType=" + userType + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(id, password, userType, username);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id && Objects.equals(password, other.password) && Objects.equals(userType, other.userType)
				&& Objects.equals(username, other.username);
	}

}
