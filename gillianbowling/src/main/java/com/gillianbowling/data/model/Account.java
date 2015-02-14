package com.gillianbowling.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Date: 2/13/15
 *
 * @author T. Curran
 */
@Entity
public class Account extends GeneratedIdEntity implements Serializable {

	@Column(length=200)
	String username;

	@Column(length=200)
	String password;

	@Column(length=200)
	String salt;

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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
