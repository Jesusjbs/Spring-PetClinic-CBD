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

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private PetRepository petRepository;
	private VisitRepository visitRepository;
	private OwnerService ownerService;
	private PetTypeService petTypeService;

	@Autowired
	public PetService(PetRepository petRepository, VisitRepository visitRepository, PetTypeService petTypeService,
			OwnerService ownerService) {
		this.petRepository = petRepository;
		this.petTypeService = petTypeService;
		this.visitRepository = visitRepository;
		this.ownerService = ownerService;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}

	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}

	@Transactional
	public void populatePetsAndVisits() throws DataAccessException {
		// Crear mascotas
		Pet p1 = new Pet("Leo", LocalDate.of(2010, 9, 7), petTypeService.findByType("cat"), ownerService.findOwnerByUsername("owner1"));
		Pet p2 = new Pet("Basil", LocalDate.of(2012, 8, 6), petTypeService.findByType("hamster"), ownerService.findOwnerByUsername("owner2"));
		Pet p3 = new Pet("Rosy", LocalDate.of(2011, 4, 17), petTypeService.findByType("dog"), ownerService.findOwnerByUsername("owner3"));
		Pet p4 = new Pet("Jewel", LocalDate.of(2010, 3, 7), petTypeService.findByType("lizard"), ownerService.findOwnerByUsername("owner3"));
		Pet p5 = new Pet("Iggy", LocalDate.of(2010, 11, 30), petTypeService.findByType("snake"), ownerService.findOwnerByUsername("owner4"));
		Pet p6 = new Pet("George", LocalDate.of(2010, 1, 20), petTypeService.findByType(""), ownerService.findOwnerByUsername("owner5"));
		Pet p7 = new Pet("Samantha", LocalDate.of(2012, 9, 4), petTypeService.findByType("cat"), ownerService.findOwnerByUsername("owner6"));
		Pet p8 = new Pet("Max", LocalDate.of(2012, 9, 4), petTypeService.findByType("cat"), ownerService.findOwnerByUsername("owner6"));
		Pet p9 = new Pet("Lucky", LocalDate.of(2011, 8, 6), petTypeService.findByType("bird"), ownerService.findOwnerByUsername("owner7"));
		Pet p10 = new Pet("Mulligan", LocalDate.of(2007, 2, 24), petTypeService.findByType("dog"), ownerService.findOwnerByUsername("owner8"));
		Pet p11 = new Pet("Freddy", LocalDate.of(2010, 3, 9), petTypeService.findByType("bird"), ownerService.findOwnerByUsername("owner9"));
		Pet p12 = new Pet("Lucky",LocalDate.of(2010, 6, 24), petTypeService.findByType("dog"), ownerService.findOwnerByUsername("owner10"));
		Pet p13 = new Pet("Sly", LocalDate.of(2012, 6,8), petTypeService.findByType("cat"), ownerService.findOwnerByUsername("owner10"));
		
		try {
			savePet(p1);
			savePet(p2);
			savePet(p3);
			savePet(p4);
			savePet(p5);
			savePet(p6);
			savePet(p7);
			savePet(p8);
			savePet(p9);
			savePet(p10);
			savePet(p11);
			savePet(p12);
			savePet(p13);
			
			// Crear visitas
			saveVisit(new Visit(p7, LocalDate.of(2013, 1, 1), "rabies shot"));
			saveVisit(new Visit(p8, LocalDate.of(2013, 1, 2), "rabies shot"));
			saveVisit(new Visit(p8, LocalDate.of(2013, 1, 3), "neutered"));
			saveVisit(new Visit(p7, LocalDate.of(2013, 1, 4), "spayed"));
		} catch (DuplicatedPetNameException e) {
		}
	}

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
		if (pet.getOwner() != null) {
			Pet otherPet = pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
			if (StringUtils.hasLength(pet.getName()) && (otherPet != null && otherPet.getId() != pet.getId())) {
				throw new DuplicatedPetNameException();
			} else
				petRepository.save(pet);
		} else
			petRepository.save(pet);
	}

	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}

	@Transactional
	public void deletePets() {
		petRepository.deleteAll();
	}

}
