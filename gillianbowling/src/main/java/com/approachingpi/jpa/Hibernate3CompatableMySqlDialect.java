package com.approachingpi.jpa;

import java.sql.Types;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Date: 11/23/12
 *
 * @author T. Curran
 *
 * This class is used because hbm2ddl only supports hibernate 3.6 and we are using 4.0+ in production.
 * Hibernate 4 uses bit for booleans, 3.6 uses tinyint(1)
 *
 */
public class Hibernate3CompatableMySqlDialect extends MySQL5InnoDBDialect {

	public Hibernate3CompatableMySqlDialect() {
		super();
		registerColumnType( Types.BOOLEAN, "bit" );
	}
}
