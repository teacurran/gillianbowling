package com.gillianbowling.action;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;

import com.gillianbowling.model.ConfigurationProperty;

@Name("configuration")
@Scope(ScopeType.STATELESS)
public class Configuration {

	@Logger
	Log log;

	@In
	protected EntityManager entityManager;

	public void set(String key, String value) {
		log.debug("Attempting to set property (#0) value (#1)", key, value);

		ConfigurationProperty dbProp = getProperty(key);
		if (dbProp != null) {
			log.debug("Current value: #0", dbProp.getValue());
			dbProp.setValue(value);
		}
	}

	public String getString(String key) {
		String result = "";

		//log.debug("property: #0, default value: #1", key, result);

		ConfigurationProperty dbProp = getProperty(key);
		if (dbProp != null) {
			result = dbProp.getValue();
		}

		log.debug("property: #0, actual value: #1", key, result);

		return result;
	}

	public Object get(String key) {
		Object result = "";

		//log.debug("property: #0, default value: #1", key, result);

		ConfigurationProperty dbProp = getProperty(key);
		if (dbProp != null) {
			result = dbProp.getValueAsObject();
		}

		log.debug("property: #0, actual value: #1", key, result);

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<ConfigurationProperty> getAll() {
		return (List<ConfigurationProperty>) entityManager.createNamedQuery(ConfigurationProperty.NAMED_QUERY_GET_ALL).getResultList();
	}

	private ConfigurationProperty getProperty(String code) {
		ConfigurationProperty dbProp = null;
		try {
			dbProp = (ConfigurationProperty) entityManager.createNamedQuery(ConfigurationProperty.NAMED_QUERY_GET_BY_CODE)
					.setParameter("code", code).getSingleResult();
		} catch (NoResultException nre) {
			log.debug("Error loading property (#0) from the database - returning the default value (#1)", code, "");
		} catch (Exception e) {
			log.error("Error loading property (#0)", e, code);
		}
		return dbProp;
	}

    @Destroy
    public void destroy() {}
}
