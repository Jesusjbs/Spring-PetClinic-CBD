package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, Integer> {

	@Query("select p from PetType p where p.name = :type")
	PetType findByType(@Param("type") String type) throws DataAccessException;
}
