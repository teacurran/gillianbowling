package com.gillianbowling.data.repositories;

import com.gillianbowling.data.model.Category;
import com.gillianbowling.data.model.ConfigurationProperty;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * @author T. Curran
 *         Date: 05-Dec-2014.
 */
@Repository
public abstract class ConfigurationPropertyRepository extends AbstractEntityRepository<ConfigurationProperty, Integer> {

}
