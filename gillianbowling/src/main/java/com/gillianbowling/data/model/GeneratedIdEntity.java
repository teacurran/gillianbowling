package com.gillianbowling.data.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Date: 02-02-2015
 *
 * @Author T. Curran
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class GeneratedIdEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
