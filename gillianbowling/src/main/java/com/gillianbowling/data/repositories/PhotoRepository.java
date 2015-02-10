package com.gillianbowling.data.repositories;

import java.util.List;

import com.gillianbowling.data.model.Category;
import com.gillianbowling.data.model.Photo;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

/**
 * @author T. Curran
 *         Date: 05-Dec-2014.
 */
@Repository
public abstract class PhotoRepository extends AbstractEntityRepository<Photo, Integer> {

	@Query(named = Photo.NATIVE_QUERY_RAND_FEATURED, isNative = true)
	public abstract List<Photo> findRandomFeatured(
			@QueryParam("orientation") int orientation,
			@QueryParam("limit") int limit);


	public abstract List<Photo> findByCategory(Category category);

}
