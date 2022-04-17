package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
	@Query("select u.authority from Authorities u where u.user.username = :username")
	String getAuthority(@Param("username") String username) throws DataAccessException;
}
