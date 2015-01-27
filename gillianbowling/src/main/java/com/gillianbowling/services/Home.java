package com.gillianbowling.services;

import com.gillianbowling.model.*;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class Home implements Serializable {

	@Inject
	ViewCategory viewCategory;

	@Inject
	transient EntityManager entityManager;

	int randomNumber	= 0;
	List<Photo> photos = null;

	public void chooseRandom() {
		randomNumber = (int)(Math.round(Math.random()*3));
	}

	public int getRandom() {
		return randomNumber;
	}

	public List<Photo> getPhotos() {

		if (photos == null) {
			photos = new ArrayList<Photo>();

			// flip a coin. show a horizontal image 25% of the time.
			boolean hasHoriz = false;
			if (((int) (Math.random() * 4)) == 0) {
				Query query = entityManager.createNativeQuery("SELECT p.* " +
						"FROM photos p " +
						"WHERE p.featured = 1 " +
						"AND p.orientation=1 " +
						"AND p.category_id IS NOT NULL " +
						"ORDER BY rand() LIMIT 1", Photo.class);
				List<Photo> results = (List<Photo>)query.getResultList();

				if (results != null && results.size() > 0) {
					photos.add(results.get(0));
					hasHoriz = true;
				}
			}

			Query query = entityManager.createNativeQuery("SELECT p.* " +
					"FROM photos p " +
					"WHERE p.featured = 1 " +
					"AND p.orientation=2 " +
					"AND p.category_id IS NOT NULL ORDER BY rand() LIMIT 3", Photo.class);
			List<Photo> results = (List<Photo>)query.getResultList();

			for (Photo photo : results) {
				if (hasHoriz && photos.size() < 2) {
					photo.getCategory();
					photos.add(photo);
					break;
				} else {
					photo.getCategory();
					photos.add(photo);
				}
			}
		}

		return photos;
	}

	public void setPhotos(List<Photo> in) {

	}

	public int getTotalWidth(int height, int padding) {
		int totalWidth = 0;
		List<Photo> results = getPhotos();
		if (results != null) {
			for (Photo photo : results) {
				totalWidth += photo.getScaledWidth(height, 600, 338) + padding;
			}
		}
		return totalWidth;
	}

}
