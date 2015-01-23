package com.gillianbowling.services;

import com.gillianbowling.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Name("photoHome")
public class PhotoHome extends EntityHome<Photo> {

	@Logger
	private Log log;

	@In (create = true)
	Configuration configuration;

	@In
	protected EntityManager entityManager;

	InputStream newFile;
	String newFileName = null;

	public void setPhotoId(Integer id) {
		setId(id);
	}

	public Integer getPhotoId() {
		return (Integer) getId();
	}

	@Override
	protected Photo createInstance() {
		Photo photo = new Photo();
		return photo;
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

	public Photo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public String remove() {
		Photo photo = getInstance();
		if (photo != null) {
			photo.getCategory().getPhotos().remove(photo);
		}
		return super.remove();
	}

	public String save() {
		log.debug("Saving photo");

		if (this.isManaged()) {
			super.update();
		} else {
			getInstance().setDateCreated(new Date());
			super.persist();
		}
		getInstance().setDateModified(new Date());

		if (newFile != null) {
			uploadPhoto();
		}

		return "success";
	}

	public void uploadPhoto() {

		Photo photo = getInstance();
		photo.setFileName(newFileName.replace(" ", "_"));

		StringBuilder fileUri = new StringBuilder(200);
		StringBuilder fileUploadDir = new StringBuilder(200);
		String mediaDir = configuration.getString("media.dir");
		fileUploadDir.append(mediaDir);

		if (!mediaDir.endsWith(File.separator)) {
			fileUploadDir.append(File.separator);
		}

		fileUri.append("photos/");
		fileUploadDir.append("photos").append(File.separator);

		fileUploadDir.append(getInstance().getId());
		fileUri.append(getInstance().getId()).append("/");
		fileUri.append(newFileName);
		String fileUploadUri = fileUploadDir.toString() + File.separator + newFileName;
		photo.setFileName(fileUri.toString());

		FileOutputStream fos = null;
		try {
			File currentPathFile = new File(fileUploadDir.toString());
			if (!currentPathFile.exists()) {
				log.debug("Creating directory:#0", fileUploadDir.toString());
				if (!currentPathFile.mkdirs()) {
					log.error("Error creating directory:#0", fileUploadDir.toString());
				}
			}

			log.debug("saving image at:#0", fileUploadUri);
			File file = new File(fileUploadUri);
			fos = new FileOutputStream(file);
			byte[] b=new byte[102400];
			int readCnt=0;
			while((readCnt= this.newFile.read(b))!= -1){
				fos.write(b, 0, readCnt);
				fos.flush();
			}
			fos.close();
			fos = null;

			if ("jpg".equalsIgnoreCase(photo.getFileExtension())
				|| "png".equalsIgnoreCase(photo.getFileExtension())
				|| "gif".equalsIgnoreCase(photo.getFileExtension())) {
				java.awt.Image imgFile = ImageIO.read(file);
				photo.setWidth(imgFile.getWidth(null));
				photo.setHeight(imgFile.getHeight(null));

				if (imgFile.getWidth(null) > imgFile.getHeight(null)) {
					photo.setOrientation(Photo.ORIENTATION_HORIZONTAL);
				} else {
					photo.setOrientation(Photo.ORIENTATION_VERTICAL);
				}
			}

			entityManager.merge(photo);
			entityManager.flush();
		} catch (Exception ex) {
			//java.util.logging.Logger.getLogger(UploadFile.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					//java.util.logging.Logger.getLogger(UploadFile.class.getName()).log(Level.SEVERE, null, ex);
					ex.printStackTrace();
				}
			}
		}
	}

	public InputStream getNewFile() {
		return newFile;
	}

	public void setNewFile(InputStream newFile) {
		this.newFile = newFile;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
}
