package com.gillianbowling.services.manager;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.gillianbowling.data.model.configuration;
import com.gillianbowling.data.repositories.ConfigurationRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

@Named
@ViewScoped
public class ConfigurationManager implements Serializable {

	@Inject
	protected EntityManager em;

	@Inject
	ConfigurationRepository configurationPropertyRepository;

	Integer id = null;
	boolean newRecord = false;
	private com.gillianbowling.data.model.configuration configuration;
	private List<com.gillianbowling.data.model.configuration> list;

	public List<com.gillianbowling.data.model.configuration> getList() {
		if (list == null) {
			list = configurationPropertyRepository.findAll();
		}
		return list;
	}

	@Transactional
	public com.gillianbowling.data.model.configuration getConfigurationProperty() {
		if (configuration == null) {
			if (id == null) {
				configuration = new configuration();
				newRecord = true;
			} else {
				configuration = em.find(com.gillianbowling.data.model.configuration.class, id);

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
