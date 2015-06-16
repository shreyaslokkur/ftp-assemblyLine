package com.lks.orm.entities;

import com.lks.core.IFALDictionaryBased;

import java.util.HashSet;
import java.util.Set;

public class User implements IEntity{

    private String employeeId;
	private String username;
	private String password;
	private boolean enabled;
	private Set<UserRole> userRole = new HashSet<UserRole>(0);

	public User() {
	}

	public User(String employeeId, String username, String password, boolean enabled) {
        this.employeeId = employeeId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String employeeId, String username, String password, boolean enabled, Set<UserRole> userRole) {
        this.employeeId = employeeId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRole() {
		return this.userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

}
