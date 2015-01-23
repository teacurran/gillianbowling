package com.gillianbowling.services;

import com.gillianbowling.model.ConfigurationProperty;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("configurationPropertyList")
public class ConfigurationPropertyList
		extends
			EntityQuery<ConfigurationProperty> {

	private static final String EJBQL = "select configurationProperty from ConfigurationProperty configurationProperty";

	private static final String[] RESTRICTIONS = {
			"lower(configurationProperty.code) like lower(concat(#{configurationPropertyList.configurationProperty.code},'%'))",
			"lower(configurationProperty.defaultValue) like lower(concat(#{configurationPropertyList.configurationProperty.defaultValue},'%'))",
			"lower(configurationProperty.type) like lower(concat(#{configurationPropertyList.configurationProperty.type},'%'))",
			"lower(configurationProperty.value) like lower(concat(#{configurationPropertyList.configurationProperty.value},'%'))",};

	private ConfigurationProperty configurationProperty = new ConfigurationProperty();

	public ConfigurationPropertyList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}
}
