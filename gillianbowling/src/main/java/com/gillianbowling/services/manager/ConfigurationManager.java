package com.gillianbowling.services.manager;

import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.gillianbowling.services.Configuration;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

@Named
@ViewScoped
public class ConfigurationManager implements Serializable {

	@Inject
	protected EntityManager em;

	Integer id = null;
	boolean newRecord = false;
	private Configuration configuration;


	@Transactional
	public Configuration getCategory() {
		if (configuration == null) {
			if (id == null) {
				configuration = new Configuration();
				newRecord = true;
			} else {
				configuration = em.find(Configuration.class, id);

				if (configuration == null) {
					id = null;
				}
			}
		}

		return configuration;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
