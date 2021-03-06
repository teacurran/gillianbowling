package com.gillianbowling.data.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@NamedQueries({
		@NamedQuery(name = Configuration.NAMED_QUERY_GET_ALL,
			query = "SELECT c " +
					"FROM Configuration c",
			hints = {
					@QueryHint(name = "org.hibernate.cacheable", value = "true"),
					@QueryHint(name = "org.hibernate.cacheRegion", value = "configurationPropertyQueries")
			}
		),

		@NamedQuery(name = Configuration.NAMED_QUERY_GET_BY_ID,
			query = "SELECT c " +
					"FROM Configuration c " +
					"WHERE id  = :id",
			hints = {
					@QueryHint(name = "org.hibernate.cacheable", value = "true"),
					@QueryHint(name = "org.hibernate.cacheRegion", value = "configurationPropertyQueries")
			}
		),

		@NamedQuery(name = Configuration.NAMED_QUERY_GET_BY_CODE,
			query = "SELECT c " +
					"FROM Configuration c " +
					"WHERE code  = :code",
			hints = {
					@QueryHint(name = "org.hibernate.cacheable", value = "true"),
					@QueryHint(name = "org.hibernate.cacheRegion", value = "configurationPropertyQueries")
			}
		)
})
public class Configuration extends GeneratedIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String NAMED_QUERY_GET_ALL = "configurationProperty.getAll";
	public static final String NAMED_QUERY_GET_BY_CODE = "configurationProperty.getByKey";
	public static final String NAMED_QUERY_GET_BY_ID = "configurationProperty.getById";

	private String code;
	private String type;
	private String value;
	private String defaultValue;
	private Long version; // for optimistic locking

	public Configuration() {
	}

	@Transient
	public Object getValueAsObject() {
		Object result = this.value;
		if ("string".equals(this.type)) {
			return value;
		}
		if ("int".equals(this.type)) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				return null;
			}
		}
		if ("boolean".equals(this.type)) {
			try {
				return Boolean.parseBoolean(value);
			} catch (Exception e) {
				return false;
			}
		}
		return result;
	}

	@Column(name = "code", length = 100, unique = true, nullable = false)
	@NotNull
	public String getCode() {
		return this.code;
	}

	@Column(name = "type", length = 100)
	public String getType() {
		return type;
	}

	@Column(name = "value", length = 255)
	public String getValue() {
		return this.value;
	}

	@Column(name = "defaultValue", length = 255)
	public String getDefaultValue() {
		return this.defaultValue;
	}

	@Version
	@Column(name = "version")
	@NotNull
	public Long getVersion() {
		return version;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Configuration other = (Configuration) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		if (defaultValue == null) {
			if (other.defaultValue != null) {
				return false;
			}
		} else if (!defaultValue.equals(other.defaultValue)) {
			return false;
		}
		if (version == null && other.version != null) {
			return false;
		}
		if (version != null && other.version == null) {
			return false;
		}
		if (version != null && version.equals(other.version)) {
			return false;
		}
		return true;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation
	 * of this object.
	 */
	@Override
	public String toString() {
		final String TAB = "    ";

		StringBuilder retValue = new StringBuilder();

		retValue.append("ConfigurationProperty ( ").append("id = ").append(this.id).append(TAB).append("name = ")
				.append(this.code).append(TAB).append("type = ").append(this.type).append(TAB).append("value = ")
				.append(this.value).append(TAB).append("defaultValue = ").append(this.defaultValue).append(TAB).append
				("version = ").append(this.version).append(TAB).append(" )");

		return retValue.toString();
	}
}
