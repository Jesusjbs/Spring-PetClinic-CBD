package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetTypeService {

	private PetTypeRepository petTypeRepository;

	@Autowired
	public PetTypeService(PetTypeRepository petTypeRepository) {
		this.petTypeRepository = petTypeRepository;
	}

	@Transactional
	public void deletePetTypes() throws DataAccessException {
		petTypeRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	public PetType findByType(String type) throws DataAccessException {
		return petTypeRepository.findByType(type);
	}

	@Transactional
	public void populatePetTypes() throws DataAccessException {
		petTypeRepository.save(new PetType("cat"));
		petTypeRepository.save(new PetType("dog"));
		petTypeRepository.save(new PetType("lizard"));
		petTypeRepository.save(new PetType("snake"));
		petTypeRepository.save(new PetType("bird"));
		petTypeRepository.save(new PetType("hamster"));
		petTypeRepository.save(new PetType("turtle"));
	}

}
