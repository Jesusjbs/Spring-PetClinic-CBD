/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class OwnerService {

	private OwnerRepository ownerRepository;	
	private UserService userService;
	private AuthoritiesService authoritiesService;

	@Autowired
	public OwnerService(OwnerRepository ownerRepository, UserService userService, AuthoritiesService authoritiesService) {
		this.ownerRepository = ownerRepository;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
	}	

	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) throws DataAccessException {
		return ownerRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Owner findOwnerByUsername(String username) throws DataAccessException {
		return ownerRepository.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
		return ownerRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveOwner(Owner owner) throws DataAccessException {
		//creating owner
		ownerRepository.save(owner);		
		//creating user
		userService.saveUser(owner.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(owner.getUser().getUsername(), "owner");
	}
	
	@Transactional
	public void populateUsersAuthoritiesAndOwners() throws DataAccessException {
		userService.populateUser();
		ownerRepository.save(new Owner("George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023",userService.findUser("owner1").get()));
		ownerRepository.save(new Owner("Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749", userService.findUser("owner2").get()));
		ownerRepository.save(new Owner("Eduardo", "Rodriquez", "2693 Commerce St.", "McFarland", "6085558763", userService.findUser("owner3").get()));
		ownerRepository.save(new Owner("Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198", userService.findUser("owner4").get()));
		ownerRepository.save(new Owner("Peter", "McTavish", "2387 S. Fair Way", "Madison", "6085552765", userService.findUser("owner5").get()));
		ownerRepository.save(new Owner("Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654", userService.findUser("owner6").get()));
		ownerRepository.save(new Owner("Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387", userService.findUser("owner7").get()));
		ownerRepository.save(new Owner("Maria", "Escobito", "345 Maple St.", "Madison", "6085557683", userService.findUser("owner8").get()));
		ownerRepository.save(new Owner("David", "Schroeder", "2749 Blackhawk Trail", "Madison", "6085559435", userService.findUser("owner9").get()));
		ownerRepository.save(new Owner("Carlos", "Estaban", "2335 Independence La.", "Waunakee", "6085555487", userService.findUser("owner10").get()));
		
		
	}
	
	@Transactional
	public void deleteOwners() throws DataAccessException {
		ownerRepository.deleteAll();
	}		

}
