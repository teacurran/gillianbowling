package com.gillianbowling.services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.gillianbowling.data.model.ConfigurationProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Stateless
public class Configuration implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	@Inject
	transient EntityManager em;

	public void set(String key, String value) {
		LOGGER.debug("Attempting to set property (#0) value (#1)", key, value);

		ConfigurationProperty dbProp = getProperty(key);
		if (dbProp != null) {
			LOGGER.debug("Current value: #0", dbProp.getValue());
			dbProp.setValue(value);
		}
	}

	public String getString(String key) {
		String result = "";

		//LOGGER.debug("property: #0, default value: #1", key, result);

		ConfigurationProperty dbProp = getProperty(key);
		if (dbProp != null) {
			result = dbProp.getValue();
		}

		LOGGER.debug("property: #0, actual value: #1", key, result);

		return result;
	}

	public Object get(String key) {
		Object result = "";

		//LOGGER.debug("property: #0, default value: #1", key, result);

		ConfigurationProperty dbProp = getProperty(key);
		if (dbProp != null) {
			result = dbProp.getValueAsObject();
		}

		LOGGER.debug("property: #0, actual value: #1", key, result);

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<ConfigurationProperty> getAll() {
		return (List<ConfigurationProperty>) em.createNamedQuery(ConfigurationProperty.NAMED_QUERY_GET_ALL)
				.getResultList();
	}

	private ConfigurationProperty getProperty(String code) {
		ConfigurationProperty dbProp = null;
		try {
			dbProp = (ConfigurationProperty) em.createNamedQuery(ConfigurationProperty.NAMED_QUERY_GET_BY_CODE)
					.setParameter("code", code).getSingleResult();
		} catch (NoResultException nre) {
			LOGGER.debug("Error loading property (#0) from the database - returning the default value (#1)", code, "");
		} catch (Exception e) {
			LOGGER.error("Error loading property (#0)", e, code);
		}
		return dbProp;
	}

}
