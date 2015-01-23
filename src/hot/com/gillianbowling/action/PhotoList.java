package com.gillianbowling.action;

import com.gillianbowling.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("photoList")
public class PhotoList extends EntityQuery<Photo> {

	private static final String EJBQL = "select photo from Photo photo";

	private static final String[] RESTRICTIONS = {
			"lower(photo.description) like lower(concat(#{photoList.photo.description},'%'))",
			"lower(photo.fileName) like lower(concat(#{photoList.photo.fileName},'%'))",};

	private Photo photo = new Photo();

	public PhotoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		//setMaxResults(null);
	}

	public Photo getPhoto() {
		return photo;
	}

	public int getTotalWidth() {
		int totalWidth = 0;
		for (Photo photo : getResultList()) {
			totalWidth += photo.getWidth();
		}
		return totalWidth;
	}
}
