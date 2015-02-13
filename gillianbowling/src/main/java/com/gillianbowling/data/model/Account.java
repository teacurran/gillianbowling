package com.gillianbowling.data.model;

import java.io.Serializable;

/**
 * Date: 2/13/15
 *
 * @author T. Curran
 */
public class Account extends GeneratedIdEntity implements Serializable {

	String username;
	String password;

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
}
