package com.gillianbowling.data.repositories;

import com.gillianbowling.data.model.configuration;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * @author T. Curran
 *         Date: 05-Dec-2014.
 */
@Repository
public abstract class ConfigurationRepository extends AbstractEntityRepository<configuration, Integer> {

	public abstract configuration findAnyByCode(String code);

	public void set(String code, String value) {
		configuration dbProp = findAnyByCode(code);
		if (dbProp != null) {
			dbProp.setValue(value);
			save(dbProp);
		}
	}

	public String getString(String code) {
		String result = "";

		configuration dbProp = findAnyByCode(code);
		if (dbProp != null) {
			result = dbProp.getValue();
		}

		return result;
	}

	public Object get(String key) {
		Object result = "";

		//LOGGER.debug("property: #0, default value: #1", key, result);

		configuration dbProp = findAnyByCode(key);
		if (dbProp != null) {
			result = dbProp.getValueAsObject();
		}

		return result;
	}

}
