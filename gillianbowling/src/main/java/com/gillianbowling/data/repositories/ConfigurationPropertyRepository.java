package com.gillianbowling.data.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.gillianbowling.data.model.Category;
import com.gillianbowling.data.model.ConfigurationProperty;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * @author T. Curran
 *         Date: 05-Dec-2014.
 */
@Repository
public abstract class ConfigurationPropertyRepository extends AbstractEntityRepository<ConfigurationProperty, Integer> {

	public abstract ConfigurationProperty findAnyByCode(String code);

	public void set(String code, String value) {
		ConfigurationProperty dbProp = findAnyByCode(code);
		if (dbProp != null) {
			dbProp.setValue(value);
			save(dbProp);
		}
	}

	public String getString(String code) {
		String result = "";

		ConfigurationProperty dbProp = findAnyByCode(code);
		if (dbProp != null) {
			result = dbProp.getValue();
		}

		return result;
	}

	public Object get(String key) {
		Object result = "";

		//LOGGER.debug("property: #0, default value: #1", key, result);

		ConfigurationProperty dbProp = findAnyByCode(key);
		if (dbProp != null) {
			result = dbProp.getValueAsObject();
		}

		return result;
	}

}
