package com.gillianbowling.locales;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageContextConfig;
import org.apache.deltaspike.core.api.message.MessageTemplate;
import org.apache.deltaspike.jsf.impl.message.JsfMessageResolver;

/**
 * Date: 04-02-2015
 *
 * @Author T. Curran
 */
@MessageBundle
@MessageContextConfig(messageResolver = JsfMessageResolver.class)
public interface I18n {

	@MessageTemplate("{ui.categories.saved}")
	String categorySaved();

}