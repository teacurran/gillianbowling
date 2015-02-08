package com.gillianbowling.data.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "photos")
@NamedNativeQueries({
	@NamedNativeQuery(name = Photo.NATIVE_QUERY_RAND_FEATURED,
		query="SELECT p.* " +
				"FROM photos p " +
				"WHERE p.featured = 1 " +
				"AND p.orientation = :orientation " +
				"AND p.category_id IS NOT NULL " +
				"ORDER BY rand() LIMIT :limit",
		resultClass = Photo.class
	)
})
public class Photo extends GeneratedIdEntity implements java.io.Serializable {

	public static final int ORIENTATION_HORIZONTAL	= 1;
	public static final int ORIENTATION_VERTICAL	= 2;

	public static final String NATIVE_QUERY_RAND_FEATURED = "Photo.3RandFeatured";

	@Column
	private Integer orientation;

	@Column(name = "description")
	@Lob
	private String description;

	@Column(name = "file_name", length = 100)
	private String fileName;

	@Column(name = "file_extension", length = 5)
	private String fileExtension;

	@Column(name = "height")
	private Integer height;

	@Column(name = "rank")
	private Integer rank;

	@Column(name = "width")
	private Integer width;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", length = 19)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", length = 19)
	private Date dateModified;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column
	private boolean featured;

	public Photo() {
	}

	public Photo(int id) {
		this.id = id;
	}
	public Photo(int id, String description, String fileName, Integer height,
			Integer rank, Integer width, Category category) {
		this.id = id;
		this.description = description;
		this.fileName = fileName;
		this.height = height;
		this.rank = rank;
		this.width = width;
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public String getDescription() {
		return this.description;
	}

	public Date getDateCreated() {
		return dateCreated == null ? null : new Date(dateCreated.getTime());
	}

	public Date getDateModified() {
		return dateModified == null ? null : new Date(dateModified.getTime());
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getFileExtension() {
		return this.fileExtension;
	}

	public Integer getHeight() {
		return this.height;
	}

	public Integer getOrientation() {
		// never return null
		if (orientation == null) {
			return ORIENTATION_HORIZONTAL;
		}
		return orientation;
	}

	public Integer getRank() {
		return this.rank;
	}

	public int getScaledWidth(int height, int defaultScaledWidthHoriz, int defaultScaledWidthVert) {
		if (height > 0
				&& this.getHeight() != null
				&& this.getWidth() != null
				&& this.getHeight() > 0
				&& this.getWidth() > 0) {
			double scaleX = (double)height / (double)this.getHeight();
			int returnValue = (int)(scaleX * this.getWidth());
			if (returnValue > 0) {
				return returnValue;
			}
		}
		if (this.getOrientation() == Photo.ORIENTATION_HORIZONTAL) {
			return defaultScaledWidthHoriz;
		}
		return defaultScaledWidthVert;
	}

	public int getScaledWidth(int height) {
		return getScaledWidth(height, 600, 338);
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated == null ? null : new Date(dateCreated.getTime());
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified == null ? null : new Date(dateModified.getTime());
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		if (fileName != null) {
			if (fileName.lastIndexOf(".") < fileName.length()) {
				this.fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
		}
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
}
