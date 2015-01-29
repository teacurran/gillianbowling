package com.gillianbowling.data.repositories;

import com.gillianbowling.model.Category;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * @author T. Curran
 *         Date: 05-Dec-2014.
 */
@Repository
public abstract class CategoryRepository extends AbstractEntityRepository<Category, Integer> {

	public abstract Category findAnyByCode(String code);

}
