package com.gillianbowling.services;

import com.gillianbowling.data.repositories.ConfigurationRepository;
import com.gillianbowling.data.repositories.PhotoRepository;
import com.gillianbowling.data.model.*;
import org.hibernate.Hibernate;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class Home implements Serializable {

	@Inject
	transient EntityManager entityManager;

	@Inject
	transient PhotoRepository photoRepository;

	@Inject
	ConfigurationRepository configuration;

	int randomNumber	= 0;
	List<Photo> photos = null;
	Boolean underConstruction = null;

	public void chooseRandom() {
		randomNumber = (int)(Math.round(Math.random()*3));
	}

	public int getRandom() {
		return randomNumber;
	}

	public List<Photo> getPhotos() {

		if (photos == null) {
			photos = new ArrayList<>();

			// flip a coin. show horizontal image 50% of the time.
			boolean hasHoriz = false;
			if (((int) (Math.random() * 2)) == 0) {
				List<Photo> horizPhotos = photoRepository.findRandomFeatured(Photo.ORIENTATION_HORIZONTAL, 2);
				if (horizPhotos != null && !horizPhotos.isEmpty()) {
					for (Photo photo : horizPhotos) {
						Hibernate.initialize(photo.getCategory());
						photos.add(photo);
					}
					hasHoriz = true;
				}
			}

			if (photos.size() < 2) {
				List<Photo> vertPhotos = photoRepository.findRandomFeatured(Photo.ORIENTATION_VERTICAL,
						(hasHoriz) ? 1 : 4);
				for (Photo photo : vertPhotos) {
					Hibernate.initialize(photo.getCategory());
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

	public boolean isUnderConstruction() {

		if (underConstruction == null) {
			underConstruction = configuration.getBool("site.under-construction");
		}
		return underConstruction;
	}

	public String getView() {

		if (isUnderConstruction()) {
			return "/under-construction.xhtml";
		}

		return "/home.xhtml";
	}
}
