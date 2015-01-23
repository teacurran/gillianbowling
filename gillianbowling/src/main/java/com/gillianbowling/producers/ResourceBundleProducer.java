package com.gillianbowling.producers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.gillianbowling.locales.UTF8ResourceBundleControl;
import com.gillianbowling.qualifiers.ClasspathResource;
import com.gillianbowling.qualifiers.Localization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Date: 9/6/13
 *
 * @author T. Curran
 */
@ApplicationScoped
public class ResourceBundleProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBundleProducer.class);

	@Produces
	@Localization
	public ResourceBundle getLocalization() {
		// default the locale to US (kind of an arrogant thing to do)
		Locale locale = Locale.US;

		// TODO: get teh locale of the currently logged in user
		// currently we are only supporting US

		return ResourceBundle.getBundle("com.scholastic.intl.bookmanager.locales.I18n",
				locale, new UTF8ResourceBundleControl());
	}

	@Produces
	@ClasspathResource("")
	Properties loadPropertiesBundle(InjectionPoint injectionPoint) {
		String name = getName(injectionPoint);

		Properties properties = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(name);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error("error closing properties file", e);
				}
			}
		}
		return properties;
	}

	private String getName(InjectionPoint ip) {
		Set<Annotation> qualifiers = ip.getQualifiers();
		for (Annotation qualifier : qualifiers) {
			if (qualifier.annotationType().equals(ClasspathResource.class)) {
				return ((ClasspathResource) qualifier).value();
			}
		}
		throw new IllegalArgumentException("Injection point " + ip + " does not have @Resource qualifier");
	}


}