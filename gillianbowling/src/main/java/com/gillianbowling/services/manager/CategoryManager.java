package com.gillianbowling.services.manager;

import com.gillianbowling.Constants;
import com.gillianbowling.data.repositories.CategoryRepository;
import com.gillianbowling.model.*;
import com.gillianbowling.services.Configuration;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
public class CategoryManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryManager.class);

	@Inject
	protected EntityManager em;

	@Inject
	Configuration configuration;

	@Inject
	CategoryRepository categoryRepository;

	InputStream newPhotoFile;
	String newPhotoFilename = null;
	List<Photo> photos = null;

	Integer id;
	Category category;
	List<Category> list;
	boolean newRecord = false;

	public List<Category> getList() {
		if (list == null) {
			list = categoryRepository.findAll();
		}
		return list;
	}

	@Transactional
	public Category getCategory() {
		if (category == null) {
			if (id == null) {
				category = new Category();
				newRecord = true;
			} else {
				category = em.find(Category.class, id);

				if (category == null) {
					id = null;
				}
			}
		}

		if (category != null) {
			Hibernate.initialize(category.getChildren());
			Hibernate.initialize(category.getPhotos());
		}

		return category;
	}

	public List<Category> getAvailableParents() {
		TypedQuery<Category> query = em.createNamedQuery(Category.NAMED_QUERY_TOP_LEVEL, Category.class);
		List<Category> results = query.getResultList();

		List<Category> returnList = new ArrayList<Category>();
		for (Category ag : results) {
			if (!ag.equals(getCategory())) {
				returnList.add(ag);
			}
		}
		return returnList;
	}

	public void uploadImage() {
		LOGGER.debug("uploadImage called");
		if (this.newPhotoFile == null || this.newPhotoFilename == null) {
			LOGGER.debug("file upload null newImageFile:{}, newImageFilename:{}", newPhotoFile, newPhotoFilename);
			return;
		}

		Photo newPhoto = new Photo();
		newPhoto.setCategory(getCategory());
		newPhoto.setDateCreated(new Date());
		newPhoto.setDateModified(new Date());
		newPhoto.setFileName(this.newPhotoFilename);

		em.persist(newPhoto);
		em.flush();

		newPhoto.setFileName(newPhoto.getId() + "_" + newPhotoFilename);

		FileOutputStream fos = null;
		try {
			String uploadDir = configuration.getString(Constants.CONFIG_KEY_PHOTO_DIR);
			File uploadDirFile = new File(uploadDir);
			if (!uploadDirFile.exists()) {
				LOGGER.debug("Creating product directory:#0", uploadDir);
 				if (!uploadDirFile.mkdirs()) {
					LOGGER.error("unable to mkdirs:{}", uploadDir);
				};
			}

			String uploadFileDir = uploadDir + File.pathSeparator + getCategory().getId();
			File uploadFileFile = new File(uploadFileDir);
			if (!uploadFileFile.exists()) {
				LOGGER.debug("Creating product directory:#0", uploadFileDir);
				if (!uploadFileFile.mkdirs()) {
					LOGGER.error("unable to mkdirs:{}", uploadFileDir);
				}
			}

			LOGGER.debug("saving image at:#0", uploadFileDir + newPhoto.getFileName());
			File file = new File(uploadFileDir + newPhoto.getFileName());
			fos = new FileOutputStream(file);
			byte[] b=new byte[102400];
			int readCnt=0;
			while((readCnt= newPhotoFile.read(b))!= -1){
				fos.write(b, 0, readCnt);
				fos.flush();
			}
			em.merge(newPhoto);
			em.flush();
		} catch (Exception ex) {
			LOGGER.error("Exception saving file", ex);
			em.remove(newPhoto);
			em.flush();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					LOGGER.error("unable to close file output stream", ex);
				}
			}
		}
		this.photos = null;
	}

	public InputStream getNewPhotoFile() {
		return newPhotoFile;
	}

	public String getNewPhotoFilename() {
		return newPhotoFilename;
	}

	public void setNewPhotoFile(InputStream in) {
		this.newPhotoFile = in;
	}
	public void setNewPhotoFilename(String in) {
		this.newPhotoFilename = in;
	}

	public List<Photo> getPhotos() {
		if (getCategory() == null) {
			return null;
		}
		if (photos == null) {
			photos = new ArrayList<Photo>(getCategory().getPhotos());
		}
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
		int rank = 0;
		for (Photo photo : photos) {
			rank++;
			photo.setRank(rank);
		}
		if (getCategory() == null) {
			getCategory().setPhotos(photos);
		}
	}


}
