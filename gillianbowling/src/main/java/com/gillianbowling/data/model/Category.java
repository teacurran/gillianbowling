package com.gillianbowling.data.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

/**
 * Date: May 4, 2010
 *
 * @author T. Curran
 */
@Entity
@NamedQueries( {
	@NamedQuery(
		name = Category.NAMED_QUERY_ALL,
		query = "SELECT a " +
				"FROM Category a " +
				"ORDER BY a.name"
	),

	@NamedQuery(name = Category.NAMED_QUERY_TOP_LEVEL,
			query = "SELECT distinct(a) " +
					"FROM Category a " +
					"LEFT JOIN FETCH a.children c " +
					"WHERE a.parent IS NULL " +
					"ORDER BY a.rank, c.rank"
	)
})
public class Category extends GeneratedIdEntity implements Serializable {

	public static final String NAMED_QUERY_ALL = "Category.all";
	public static final String NAMED_QUERY_TOP_LEVEL = "Category.getTopLevel";

	@Column(length=100)
	String name;

	@Column(length=100)
	String code;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	@OrderBy("rank")
	List<Photo> photos;

	@ManyToOne(fetch = FetchType.LAZY)
	Category parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	@OrderBy("name")
	List<Category> children;

	@Column
	Integer rank;


	public String getName() {
		return name;
	}

	public String getNameWithParent() {
		if (getParent() != null) {
			return getParent().getName() + " > " + getName();
		}
		return getName();
	}

	public String getCode() {
		return code;
	}

	public List<Photo> getPhotos() {
		if (photos == null) {
			photos = new ArrayList<Photo>();
		}
		return this.photos;
	}

	public Category getParent() {
		return parent;
	}

	public Integer getRank() {
		if (rank == null) {
			rank = 0;
		}
		return rank;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
}
