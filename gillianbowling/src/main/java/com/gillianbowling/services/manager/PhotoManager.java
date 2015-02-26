package com.gillianbowling.services.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.faces.convert.Converter;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.gillianbowling.Constants;
import com.gillianbowling.data.model.Category;
import com.gillianbowling.data.model.Photo;
import com.gillianbowling.data.repositories.CategoryRepository;
import com.gillianbowling.data.repositories.ConfigurationRepository;
import com.gillianbowling.data.repositories.PhotoRepository;
import com.gillianbowling.locales.I18n;
import com.gillianbowling.web.coverters.GenericEntityConverter;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class PhotoManager implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryManager.class);

	@Inject
	protected EntityManager em;

	@Inject
	ConfigurationRepository configuration;

	@Inject
	PhotoRepository photoRepository;

	@Inject
	CategoryRepository categoryRepository;

	@Inject
	private JsfMessage<I18n> messages;

	UploadedFile file;
	Boolean newRecord = false;
	Integer id = null;
	Photo photo;
	List<Photo> list;
	Integer filterCatId = null;
	Category filterCategory = null;

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

	public List<Photo> getList() {
		if (list == null) {
			if (filterCategory != null) {
				list = photoRepository.findByCategory(filterCategory);
			} else {
				list = photoRepository.findAll();
			}
		}
		return list;
	}

	@Transactional
	public String save() {
		LOGGER.debug("Saving photo");

		if (photo != null) {
			photo.setDateModified(new Date());
			photo = em.merge(photo);
		}

		uploadPhoto();

		messages.addInfo().photoSaved();

		return "photo-view.xhtml?faces-redirect=true&id=" + photo.getId();
	}

	@Transactional
	public String remove() {
		LOGGER.debug("Deleting photo");

		if (photo != null) {
			photo = em.merge(photo);
			em.remove(photo);
			photo = null;
		}

		messages.addInfo().photoDeleted();

		return Constants.ACTION_SUCCESS;
	}

	@Transactional
	public void uploadPhoto() {
		if (file == null || file.getFileName() == null || file.getFileName().isEmpty()) {
			return;
		}

		Photo photo = getPhoto();
		String newFileName = file.getFileName();
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

			InputStream fileInputStream = file.getInputstream();
			File file = new File(fileUploadUri);
			fos = new FileOutputStream(file);
			byte[] b=new byte[102400];
			int readCnt=0;
			while((readCnt= fileInputStream.read(b))!= -1){
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

			this.photo = em.merge(photo);
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

	/**
	 * @return
	 * 		Converter instance.
	 */
	public Converter getConverter() {
		return new GenericEntityConverter<>(Photo.class, em);
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFilterCatId() {
		return filterCatId;
	}

	public void setFilterCatId(Integer filterCatId) {
		this.filterCatId = filterCatId;
	}

	public Category getFilterCategory() {
		if (filterCatId != null && filterCategory == null) {
			filterCategory = categoryRepository.findBy(filterCatId);
		}

		return filterCategory;
	}

	public void setFilterCategory(Category filterCategory) {
		this.filterCategory = filterCategory;
	}
}
