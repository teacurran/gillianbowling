package com.gillianbowling.action;

import com.gillianbowling.model.ConfigurationProperty;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("configurationPropertyHome")
public class ConfigurationPropertyHome
		extends
			EntityHome<ConfigurationProperty> {

	public void setConfigurationPropertyId(Integer id) {
		setId(id);
	}

	public Integer getConfigurationPropertyId() {
		return (Integer) getId();
	}

	@Override
	protected ConfigurationProperty createInstance() {
		ConfigurationProperty configurationProperty = new ConfigurationProperty();
		return configurationProperty;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public ConfigurationProperty getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
