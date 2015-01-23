package com.gillianbowling.services;

import com.gillianbowling.data.repositories.PhotoRepository;
import com.gillianbowling.model.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

@ViewScoped
public class ViewCategory implements Serializable {

	private static final String EJBQL = "select photo from Photo photo";

	private static final String[] RESTRICTIONS = {
			"lower(photo.category.code) = lower(#{viewCategory.category.code})", };

	private Photo photo = new Photo();
	private Category category = new Category();

	@Inject
	PhotoRepository photoRepository;

	public Photo getPhoto() {
		return photo;
	}

	public Category getCategory() {
		return category;
	}

	public int getTotalWidth(int height, int padding) {
		int totalWidth = 0;
//		List<Photo> results = getResultList();
//		if (results != null) {
//			for (Photo photo : results) {
//				totalWidth += photo.getScaledWidth(height, 600, 338) + padding;
//			}
//		}
		return totalWidth;
	}
}
