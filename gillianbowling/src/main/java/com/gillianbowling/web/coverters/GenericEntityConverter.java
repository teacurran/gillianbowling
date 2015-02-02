package com.gillianbowling.web.coverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;

import com.gillianbowling.data.model.GeneratedIdEntity;

/**
 * Date: 11/30/13
 *
 * @author T. Curran
 */
public class GenericEntityConverter<T extends GeneratedIdEntity> implements Converter {

	private final Class<T> type;

	private final EntityManager entityManager;

	public GenericEntityConverter(final Class<T> inType, EntityManager entityManager) {

		this.type = inType;

		this.entityManager = entityManager;
	}

	@Override
	public T getAsObject(final FacesContext inContext, final UIComponent inComponent, final String inValue) {

		if (inValue == null || "".equals(inValue) || "--Select--".equals(inValue)) {
			return null;
		}

		T entity = this.entityManager.find(this.type, Integer.valueOf(inValue));
		return entity;
	}

	@Override
	public String getAsString(final FacesContext inContext, final UIComponent inComponent, final Object inValue) {

		if (String.class.isInstance(inValue) && "".equals(inValue)) {
			return null;
		}

		if (inValue == null) {
			return null;
		}

		if (!this.type.isInstance(inValue)) {
			throw new IllegalArgumentException("Invalid type for converter.");
		}

		final T entity = this.type.cast(inValue);
		return entity.getId() == null ? null : entity.getId().toString();
	}
}