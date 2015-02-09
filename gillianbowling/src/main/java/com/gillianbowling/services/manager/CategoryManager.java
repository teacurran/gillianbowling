package com.gillianbowling.services.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.gillianbowling.Constants;
import com.gillianbowling.data.repositories.CategoryRepository;
import com.gillianbowling.data.model.Category;
import com.gillianbowling.data.model.Photo;
import com.gillianbowling.data.repositories.ConfigurationRepository;
import com.gillianbowling.locales.I18n;
import com.gillianbowling.web.coverters.GenericEntityConverter;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.hibernate.Hibernate;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class CategoryManager implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryManager.class);

	@Inject
	protected EntityManager em;

	@Inject
	ConfigurationRepository configuration;

	@Inject
	CategoryRepository categoryRepository;

	@Inject
	private JsfMessage<I18n> messages;

	InputStream newPhotoFile;
	String newPhotoFilename = null;
	List<Photo> photos = null;

	Integer id;
	Category category;
	List<Category> list;
	List<Category> topLevelCategories;
	boolean newRecord = false;
	String code;

	public List<Category> getList() {
		if (list == null) {
			list = categoryRepository.findAll();
		}
		return list;
	}

	public List<Category> getListWithOrderedChildren() {
		List<Category> results = new ArrayList<>();
		List<Category> topCats = categoryRepository.findTopLevel();

		for (Category category : topCats) {
			results.add(category);
			for (Category child : category.getChildren()) {
				results.add(child);
			}
		}
		return results;

	}

	public List<Category> getTopLevelCats() {
		if (topLevelCategories == null) {
			TypedQuery<Category> query = em.createNamedQuery(Category.NAMED_QUERY_TOP_LEVEL, Category.class);
			topLevelCategories = query.getResultList();
		}
		return topLevelCategories;
	}

	@Transactional
	public Category getCategory() {
		if (category == null) {
			if (code != null) {
				category = categoryRepository.findAnyByCodeWithPhotos(code);

				if (category == null) {
					id = null;
				}

			} else if (id != null) {
				category = categoryRepository.findBy(id);

				if (category == null) {
					id = null;
				}
			} else {
				category = new Category();
				newRecord = true;
			}
		}

		if (category != null && !newRecord) {
		}

		return category;
	}

	public List<Category> getAvailableParents() {
		List<Category> results = categoryRepository.findTopLevel();

		List<Category> returnList = new ArrayList<>();
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
			int readCnt;
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
			photos = new ArrayList<>(getCategory().getPhotos());
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
		if (getCategory() != null) {
			getCategory().setPhotos(photos);
		}
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return
	 * 		Converter instance.
	 */
	public Converter getConverter() {
		return new GenericEntityConverter<>(Category.class, em);
	}

	/**
	 * @return
	 * 		String instance.
	 */
	@Transactional
	public String save() {
		category = em.merge(category);
		messages.addInfo().categorySaved();
		return Constants.ACTION_SUCCESS;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void onSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject()
				.toString()));
	}
}
