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

	@MessageTemplate("{ui.bootstrap.success}")
	String bootstrapSuccess();

	@MessageTemplate("{ui.categories.saved}")
	String categorySaved();

	@MessageTemplate("{ui.photos.deleted}")
	String photoDeleted();

	@MessageTemplate("{ui.photos.saved}")
	String photoSaved();

	@MessageTemplate("{ui.error.unexpected}")
	String unexpectedError(String message);

}
