package com.gillianbowling.data.repositories;

import java.util.List;

import com.gillianbowling.data.model.Category;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.SingleResultType;

/**
 * @author T. Curran
 *         Date: 05-Dec-2014.
 */
@Repository
public abstract class CategoryRepository extends AbstractEntityRepository<Category, Integer> {

	public abstract Category findAnyByCode(String code);

	@Query(named = Category.NAMED_QUERY_TOP_LEVEL)
	public abstract List<Category> findTopLevel();

	@Query(named = Category.NAMED_QUERY_BY_CODE_WITH_PHOTOS, singleResult = SingleResultType.ANY)
	public abstract Category findAnyByCodeWithPhotos(@QueryParam("code") String code);

	@Query(named = Category.NAMED_QUERY_BY_ID_WITH_PHOTOS)
	public abstract Category findByIdWithPhotos(@QueryParam("id") Integer id);
}
