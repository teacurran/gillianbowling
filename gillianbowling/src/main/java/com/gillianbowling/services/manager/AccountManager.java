package com.gillianbowling.services.manager;

import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.gillianbowling.Constants;
import com.gillianbowling.data.model.Account;
import com.gillianbowling.data.repositories.AccountRepository;
import com.gillianbowling.locales.I18n;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 14-02-2015
 *
 * @Author T. Curran
 */
@Named
@ViewScoped
public class AccountManager implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountManager.class);

	@Inject
	AccountRepository accountRepository;

	@Inject
	private JsfMessage<I18n> messages;

	String initUsername;
	String initPassword;
	String initPassword2;

	public String bootstrap() {
		if (isInitialized()) {
			messages.addError().unexpectedError("Site is already initialized");
		} else {

			// TODO: do some validation here
			Account account = accountRepository.createNew(initUsername, initPassword);
			if (account == null) {
				messages.addError().unexpectedError("Error creating account");
				return Constants.ACTION_FAILURE;
			} else {
				messages.addInfo().bootstrapSuccess();
			}
		}

		return Constants.ACTION_SUCCESS;
	}

	public boolean isInitialized() {
		if (accountRepository.count() > 0) {
			return true;
		}
		return false;
	}

	public String getInitUsername() {
		return initUsername;
	}

	public void setInitUsername(String initUsername) {
		this.initUsername = initUsername;
	}

	public String getInitPassword() {
		return initPassword;
	}

	public void setInitPassword(String initPassword) {
		this.initPassword = initPassword;
	}

	public String getInitPassword2() {
		return initPassword2;
	}

	public void setInitPassword2(String initPassword2) {
		this.initPassword2 = initPassword2;
	}
}
