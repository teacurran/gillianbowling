package com.gillianbowling.data.repositories;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;

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
public abstract class CategoryRepository extends AbstractEntityRepository<Category, Integer> {

	public abstract Category findAnyByCode(String code);

	@Query(named = Category.NAMED_QUERY_TOP_LEVEL)
	public abstract List<Category> findTopLevel();

}
