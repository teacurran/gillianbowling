package com.gillianbowling.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "photos")
public class Photo implements java.io.Serializable {

	public static final int ORIENTATION_HORIZONTAL	= 1;
	public static final int ORIENTATION_VERTICAL	= 2;

	private int id;
	private Integer orientation;
	private String description;
	private String fileName;
	private String fileExtension;
	private Integer height;
	private Integer rank;
	private Integer width;
	private Date dateCreated;
	private Date dateModified;
	private Category category;
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

	@Id
	@GeneratedValue(strategy = AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "category_id", nullable = false)
	public Category getCategory() {
		return category;
	}

	@Column(name = "description")
	@Lob
	public String getDescription() {
		return this.description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", length = 19)
	public Date getDateCreated() {
		return dateCreated == null ? null : new Date(dateCreated.getTime());
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", length = 19)
	public Date getDateModified() {
		return dateModified == null ? null : new Date(dateModified.getTime());
	}

	@Column(name = "file_name", length = 100)
	@Max(100)
	public String getFileName() {
		return this.fileName;
	}

	@Column(name = "file_extension", length = 5)
	@Max(5)
	public String getFileExtension() {
		return this.fileExtension;
	}

	@Column(name = "height")
	public Integer getHeight() {
		return this.height;
	}

	@Column
	public Integer getOrientation() {
		// never return null
		if (orientation == null) {
			return ORIENTATION_HORIZONTAL;
		}
		return orientation;
	}

	@Column(name = "rank")
	public Integer getRank() {
		return this.rank;
	}

	@Transient
	public int getScaledWidth(int height, int defaultScaledWidthHoriz, int defaultScaledWidthVert) {
		if (height > 0
				&& this.getHeight() != null
				&& this.getWidth() != null
				&& this.getHeight() > 0
				&& this.getWidth() > 0) {
			double scaleX = (double)height / (double)this.getHeight();
			int returnValue = new Double(scaleX * this.getWidth()).intValue();
			if (returnValue > 0) {
				return returnValue;
			}
		}
		if (this.getOrientation() == Photo.ORIENTATION_HORIZONTAL) {
			return defaultScaledWidthHoriz;
		}
		return defaultScaledWidthVert;
	}

	@Transient
	public int getScaledWidth(int height) {
		return getScaledWidth(height, 600, 338);
	}

	@Column(name = "width")
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

	public void setId(int id) {
		this.id = id;
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
