package com.gillianbowling.security;

import com.gillianbowling.data.model.Account;
import com.gillianbowling.data.repositories.AccountRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import java.util.Set;

/**
 * Date: 2/12/15
 *
 * @author T. Curran
 */
public class SecurityRealm extends AuthorizingRealm {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityRealm.class);

	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken inToken) {

		// Try to get the account manager from CDI
		BeanManager beanManager = CDI.current().getBeanManager();
		Set<Bean<?>> accountRepositories = beanManager.getBeans(AccountRepository.class);
		AccountRepository accountRepository = null;
		if (accountRepositories != null) {
			Bean<AccountRepository> accountRepositoryBean = (Bean<AccountRepository>) beanManager
					.getBeans(AccountRepository.class).iterator().next();

			CreationalContext<AccountRepository> ctx = beanManager.createCreationalContext(accountRepositoryBean);
			accountRepository = (AccountRepository) beanManager
					.getReference(accountRepositoryBean, AccountRepository.class, ctx);
		}

		if (accountRepository == null) {
			throw new AuthenticationException("Unable to get accountRepository from CDI");
		}


		UsernamePasswordToken upToken = (UsernamePasswordToken)inToken;
  		String username = upToken.getUsername();

		Account account = accountRepository.findAnyByUsername(username);
		if (account == null) {
			throw new UnknownAccountException("No account found for user [" + username + "]");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, account.getPassword(), getName());
		if (account.getSalt() != null) {
			info.setCredentialsSalt(ByteSource.Util.bytes(account.getSalt()));
		}

		return info;
	}

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		try {
			// My custom logic here
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		}
		return new SimpleAuthorizationInfo();
	}
}