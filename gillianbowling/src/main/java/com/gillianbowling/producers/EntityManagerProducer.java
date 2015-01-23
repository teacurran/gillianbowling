package com.gillianbowling.producers;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Date: 3/26/14
 *
 * @author T. Curran
 */
public class EntityManagerProducer {

	@PersistenceContext(type= PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Produces
	public EntityManager create() {
		return this.entityManager;
	}

	public void close(@Disposes EntityManager em) {
		// closing entitymanager is handled by the server.
		//if (em.isOpen()) {
			// em.close();
		//}
	}

}
