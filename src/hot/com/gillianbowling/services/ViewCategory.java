package com.gillianbowling.services;

import com.gillianbowling.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;

import java.util.Arrays;
import java.util.List;

@Name("viewCategory")
@Scope(ScopeType.PAGE)
public class ViewCategory extends EntityQuery<Photo> {

	private static final String EJBQL = "select photo from Photo photo";

	private static final String[] RESTRICTIONS = {
			"lower(photo.category.code) = lower(#{viewCategory.category.code})", };

	private Photo photo = new Photo();
	private Category category = new Category();

	public ViewCategory() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(null);
		this.setRestrictionLogicOperator("and");
		this.setOrderColumn("photo.rank");
	}

	public Photo getPhoto() {
		return photo;
	}

	public Category getCategory() {
		return category;
	}

	public int getTotalWidth(int height, int padding) {
		int totalWidth = 0;
		List<Photo> results = getResultList();
		if (results != null) {
			for (Photo photo : results) {
				totalWidth += photo.getScaledWidth(height, 600, 338) + padding;
			}
		}
		return totalWidth;
	}
}
