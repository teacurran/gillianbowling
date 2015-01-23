package com.gillianbowling.services;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class Authenticator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Authenticator.class);

	@Inject
	Identity identity;

	@Inject
	Credentials credentials;

	public boolean authenticate() {
		LOGGER.info("authenticating {0}", credentials.getUsername());
		//write your authentication logic here,
		//return true if the authentication was
		//successful, false otherwise
		if ("admin".equals(credentials.getUsername())
			&& "resetXX".equals(credentials.getPassword())) {
			identity.addRole("admin");
			return true;
		}
		return false;
	}

}
