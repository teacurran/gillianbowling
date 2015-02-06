package com.gillianbowling.services.manager;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.gillianbowling.data.model.ConfigurationProperty;
import com.gillianbowling.data.repositories.ConfigurationPropertyRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

@Named
@ViewScoped
public class ConfigurationManager implements Serializable {

	@Inject
	protected EntityManager em;

	@Inject
	ConfigurationPropertyRepository configurationPropertyRepository;

	Integer id = null;
	boolean newRecord = false;
	private ConfigurationProperty configuration;
	private List<ConfigurationProperty> list;

	public List<ConfigurationProperty> getList() {
		if (list == null) {
			list = configurationPropertyRepository.findAll();
		}
		return list;
	}

	@Transactional
	public ConfigurationProperty getConfigurationProperty() {
		if (configuration == null) {
			if (id == null) {
				configuration = new ConfigurationProperty();
				newRecord = true;
			} else {
				configuration = em.find(ConfigurationProperty.class, id);

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
