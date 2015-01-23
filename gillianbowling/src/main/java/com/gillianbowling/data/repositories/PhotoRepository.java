package com.gillianbowling.data.repositories;

import com.gillianbowling.model.Category;
import com.gillianbowling.model.Photo;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * @author T. Curran
 *         Date: 05-Dec-2014.
 */
@Repository
public abstract class PhotoRepository extends AbstractEntityRepository<Photo, Integer> {

}
