package com.gillianbowling.services;

import com.gillianbowling.Constants;
import com.gillianbowling.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Name("categoryHome")
public class CategoryHome extends EntityHome<Category> {

	@Logger
	Log log;

	@In
	protected EntityManager entityManager;

	@In(create = true)
	Configuration configuration;

	InputStream newPhotoFile;
	String newPhotoFilename = null;
	List<Photo> photos = null;

	public void setCategoryId(Integer id) {
		setId(id);
	}

	public Integer getCategoryId() {
		return (Integer) getId();
	}

	@Override
	protected Category createInstance() {
		Category category = new Category();
		return category;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Category getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Category> getAvailableParents() {
		Query query = entityManager.createNamedQuery(Category.NAMED_QUERY_TOP_LEVEL);
		List<Category> results = query.getResultList();

		List<Category> returnList = new ArrayList<Category>();
		for (Category ag : results) {
			if (!ag.equals(getInstance())) {
				returnList.add(ag);
			}
		}
		return returnList;
	}

	public void uploadImage() {
		log.debug("uploadImage called");
		if (this.newPhotoFile == null || this.newPhotoFilename == null) {
			log.debug("file upload null newImageFile:#0, newImageFilename:#1", newPhotoFile, newPhotoFilename);
			return;
		}

		Photo newPhoto = new Photo();
		newPhoto.setCategory(this.getInstance());
		newPhoto.setDateCreated(new Date());
		newPhoto.setDateModified(new Date());
		newPhoto.setFileName(this.newPhotoFilename);

		entityManager.persist(newPhoto);
		entityManager.flush();

		newPhoto.setFileName(newPhoto.getId() + "_" + newPhotoFilename);

		FileOutputStream fos = null;
		try {
			String uploadDir = configuration.getString(Constants.CONFIG_KEY_PHOTO_DIR);
			File uploadDirFile = new File(uploadDir);
			if (!uploadDirFile.exists()) {
				log.debug("Creating product directory:#0", uploadDir);
				uploadDirFile.mkdirs();
			}

			String uploadFileDir = uploadDir + File.pathSeparator + getInstance().getId();
			File uploadFileFile = new File(uploadFileDir);
			if (!uploadFileFile.exists()) {
				log.debug("Creating product directory:#0", uploadFileDir);
				uploadFileFile.mkdirs();
			}

			log.debug("saving image at:#0", uploadFileDir + newPhoto.getFileName());
			File file = new File(uploadFileDir + newPhoto.getFileName());
			fos = new FileOutputStream(file);
			byte[] b=new byte[102400];
			int readCnt=0;
			while((readCnt= newPhotoFile.read(b))!= -1){
				fos.write(b, 0, readCnt);
				fos.flush();
			}
			entityManager.merge(newPhoto);
			entityManager.flush();
		} catch (Exception ex) {
			//java.util.logging.Logger.getLogger(UploadFile.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
			entityManager.remove(newPhoto);
			entityManager.flush();
		} finally {
			try {
				fos.close();
			} catch (IOException ex) {
				//java.util.logging.Logger.getLogger(UploadFile.class.getName()).log(Level.SEVERE, null, ex);
				ex.printStackTrace();
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
		if (getInstance() == null) {
			return null;
		}
		if (photos == null) {
			photos = new ArrayList<Photo>(getInstance().getPhotos());
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
		if (getInstance() == null) {
			getInstance().setPhotos(photos);
		}
	}


}
