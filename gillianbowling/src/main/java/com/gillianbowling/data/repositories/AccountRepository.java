package com.gillianbowling.data.repositories;

import com.gillianbowling.data.model.Account;
import org.apache.deltaspike.data.api.AbstractEntityRepository;

/**
 * Date: 2/13/15
 *
 * @author T. Curran
 */
public abstract class AccountRepository extends AbstractEntityRepository<Account, Integer> {

	public abstract Account findAnyByUsername(String username);

}
