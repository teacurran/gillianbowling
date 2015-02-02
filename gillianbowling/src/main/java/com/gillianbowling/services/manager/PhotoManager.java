package com.gillianbowling.services.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.gillianbowling.data.model.Photo;
import com.gillianbowling.services.Configuration;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class PhotoManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryManager.class);

	@Inject
	protected EntityManager em;

	@Inject
	Configuration configuration;

	Boolean newRecord = false;
	Integer id = null;
	InputStream newFile;
	String newFileName = null;
	Photo photo;

	@Transactional
	public Photo getPhoto() {
		if (photo == null) {
			if (id == null) {
				photo = new Photo();
				newRecord = true;
			} else {
				photo = em.find(Photo.class, id);

				if (photo == null) {
					id = null;
				}
			}
		}

		return photo;
	}

	public String save() {
		LOGGER.debug("Saving photo");

		if (photo != null) {
			photo.setDateModified(new Date());
			em.merge(photo);
		}

		if (newFile != null) {
			uploadPhoto();
		}

		return "success";
	}

	public void uploadPhoto() {

		Photo photo = getPhoto();
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

		fileUploadDir.append(photo.getId());
		fileUri.append(photo.getId()).append("/");
		fileUri.append(newFileName);
		String fileUploadUri = fileUploadDir.toString() + File.separator + newFileName;
		photo.setFileName(fileUri.toString());

		FileOutputStream fos = null;
		try {
			File currentPathFile = new File(fileUploadDir.toString());
			if (!currentPathFile.exists()) {
				LOGGER.debug("Creating directory:#0", fileUploadDir.toString());
				if (!currentPathFile.mkdirs()) {
					LOGGER.error("Error creating directory:#0", fileUploadDir.toString());
				}
			}

			LOGGER.debug("saving image at:#0", fileUploadUri);
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

			em.merge(photo);
			em.flush();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
