package com.gillianbowling.security;

import com.approachingpi.util.PasswordHash;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 15-02-2015
 *
 * @Author T. Curran
 */
public class PasswordMatcher implements CredentialsMatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordMatcher.class);

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

		ByteSource saltSource = ((SaltedAuthenticationInfo) info).getCredentialsSalt();
		String salt = new String(saltSource.getBytes());

		String storedPassword = getStoredPassword(info);
		String submittedPassword = new String((char[])getSubmittedPassword(token));

		try {
			return PasswordHash.check(submittedPassword, salt, storedPassword);
		} catch (Exception e) {
			LOGGER.error("error checking password", e);
		}

		return false;
	}

	protected String getStoredPassword(AuthenticationInfo storedAccountInfo) {
		Object stored = storedAccountInfo != null ? storedAccountInfo.getCredentials() : null;
		//fix for https://issues.apache.org/jira/browse/SHIRO-363
		if (stored instanceof char[]) {
			stored = new String((char[]) stored);
		}
		return (String)stored;
	}

	protected Object getSubmittedPassword(AuthenticationToken token) {
		return token != null ? token.getCredentials() : null;
	}


}
