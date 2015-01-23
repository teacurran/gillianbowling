package com.gillianbowling.action;

import com.gillianbowling.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@Name("categoryList")
public class CategoryList extends EntityQuery<Category> {

	private static final String EJBQL = "select category from Category category LEFT JOIN category.parent parent";

	private static final String[] RESTRICTIONS = {"lower(category.name) like lower(concat(#{categoryList.category.name},'%'))",};

	@In
	EntityManager entityManager;

	private Category category = new Category();

	public CategoryList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		this.setOrder("parent.name, category.name asc");
	}

	public Category getCategory() {
		return category;
	}

	public List<Category> getTopLevelCats() {
		Query query = entityManager.createNamedQuery(Category.NAMED_QUERY_TOP_LEVEL);
		List<Category> resultList = (List<Category>)query.getResultList();
		return resultList;
	}
}
