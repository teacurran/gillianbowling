package com.gillianbowling.services;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * Date: Mar 28, 2010
 *
 * @author T. Curran
 */

@Scope(ScopeType.CONVERSATION)
public class EntityHome<E> extends org.jboss.seam.framework.EntityHome<E> {

	/**
	 * Flush any changes made to the managed entity instance to the underlying
	 * database.
	 * <br />
	 * If the update is successful, a log message is printed, a
	 * {@link javax.faces.application.FacesMessage} is added and a transaction
	 * success event raised.
	 *
	 * @return "updated" if the update is successful
	 * @see org.jboss.seam.framework.Home#updatedMessage()
	 * @see org.jboss.seam.framework.Home#raiseAfterTransactionSuccessEvent()
	 */
	@Transactional
	public String updateWithNoMessage() {
		joinTransaction();
		getEntityManager().flush();
		//updatedMessage();
		raiseAfterTransactionSuccessEvent();
		return "updated";
	}

}
