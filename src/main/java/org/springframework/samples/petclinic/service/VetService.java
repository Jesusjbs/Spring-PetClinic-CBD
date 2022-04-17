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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class VetService {

	private VetRepository vetRepository;
	private SpecialtyRepository specialtyRepository;


	@Autowired
	public VetService(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
		this.vetRepository = vetRepository;
		this.specialtyRepository = specialtyRepository;
	}		

	@Transactional(readOnly = true)	
	public Collection<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}	
	
	@Transactional
	public void deleteVets() throws DataAccessException {
		vetRepository.deleteAll();
	}	
	
	@Transactional
	public void populateVetsAndSpecialties() throws DataAccessException {
		// Crear y guardar especialidades
		Specialty e1 = new Specialty("radiology");
		Specialty e2 = new Specialty("surgery");
		Specialty e3 = new Specialty("dentistry");
		
		specialtyRepository.save(e1);
		specialtyRepository.save(e2);
		specialtyRepository.save(e3);
		
		// Crear y guardar veterinarios
		vetRepository.save(new Vet("James", "Carter", new HashSet<>()));
		vetRepository.save(new Vet("Helen", "Leary", new HashSet<>(Arrays.asList(e1))));
		vetRepository.save(new Vet("Linda", "Douglas", new HashSet<>(Arrays.asList(e2,e3))));
		vetRepository.save(new Vet("Rafael", "Ortega", new HashSet<>(Arrays.asList(e2))));
		vetRepository.save(new Vet("Henry", "Stevens", new HashSet<>(Arrays.asList(e1,e2))));
		vetRepository.save(new Vet("Sharon", "Jenkins", new HashSet<>()));
	}	
}
