package com.gillianbowling.security;

import com.gillianbowling.data.repositories.AccountRepository;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

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

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

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

		if (accountRepository != null) {
			accountRepository.findAnyByUsername((String) token.getPrincipal());
		}

		SimpleAuthenticationInfo authn = new SimpleAuthenticationInfo(token.getPrincipal(),
				token.getCredentials(), "myRealm");

		return authn;
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