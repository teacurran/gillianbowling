package com.gillianbowling.services.manager;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.gillianbowling.data.model.Configuration;
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
	private Configuration configuration;
	private List<Configuration> list;

	public List<Configuration> getList() {
		if (list == null) {
			list = configurationPropertyRepository.findAll();
		}
		return list;
	}

	@Transactional
	public Configuration getConfigurationProperty() {
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
