package com.gillianbowling.data.repositories;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.approachingpi.util.PasswordHash;
import com.gillianbowling.data.model.Account;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 2/13/15
 *
 * @author T. Curran
 */
public abstract class AccountRepository extends AbstractEntityRepository<Account, Integer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

	public abstract Account findAnyByUsername(String username);

	public Account createNew(String username, String password) {
		Account account = new Account();
		account.setPassword(password);
		setPassword(account, password);
		this.save(account);
		return account;
	}

	public boolean setPassword(Account inAccount, String inPassword) {
			if (inAccount == null) {
				return false;
			}

			if (inPassword == null || inPassword.length() == 0) {
				throw new IllegalArgumentException("inPassword cannot be empty");
			}

			try {
				PasswordHash ph = new PasswordHash(inPassword);
				inAccount.setSalt(ph.getSalt());
				inAccount.setPassword(ph.getHash());
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				LOGGER.error("error setting password", e);
				return false;
			}

		return true;
		}
}
