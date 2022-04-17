package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpecialtyService {

	private SpecialtyRepository specialtyRepository;


	@Autowired
	public SpecialtyService(SpecialtyRepository specialtyRepository) {
		this.specialtyRepository = specialtyRepository;
	}		

	@Transactional
	public void deleteSpecialties() throws DataAccessException {
		specialtyRepository.deleteAll();
	}	

}
