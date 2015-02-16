package com.gillianbowling.security;

import com.gillianbowling.Constants;
import com.gillianbowling.data.model.Account;
import com.gillianbowling.data.repositories.AccountRepository;
import com.gillianbowling.locales.I18n;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Date: 2/13/15
 *
 * @author T. Curran
 */
@Named
@ViewScoped
public class Authenticator implements Serializable {

	public static final String HOME_URL = "pages/secure/index.xhtml";
	public static final String LOGIN_URL = "home/login.xhtml";
	private static final Logger LOGGER = LoggerFactory.getLogger(Authenticator.class);

	private String username;
	private String password;
	private boolean rememberMe = false;
	private boolean logoutFlag = Boolean.FALSE;

	@Inject
	JsfMessage<I18n> messages;

	@Inject
	AccountRepository accountRepository;

	public String login() throws IOException {
		try {
			SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, rememberMe));
			setLogoutFlag(Boolean.TRUE);
			Account account = accountRepository.findAnyByUsername(SecurityUtils.getSubject().getPrincipal().toString());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", account.getId());
			return Constants.ACTION_SUCCESS;

		} catch (UnknownAccountException | IncorrectCredentialsException uae) {
			LOGGER.info("Authentication failed for user: {}", username);
			messages.addError().unexpectedError("Authentication Failed");
			return Constants.ACTION_FAILURE;

		} catch (AuthenticationException e) {
			LOGGER.error("Authentication failed ", e);
			messages.addError().unexpectedError("Authentication Failed");
			return Constants.ACTION_FAILURE;
		}
	}

	public void logout() {
		SecurityUtils.getSubject().logout();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(LOGIN_URL);
		} catch (IOException e) {
			LOGGER.error("error logging out", e);
		}
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

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isLogoutFlag() {
		return logoutFlag;
	}

	public void setLogoutFlag(boolean logoutFlag) {
		this.logoutFlag = logoutFlag;
	}
}
