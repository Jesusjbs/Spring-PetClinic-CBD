package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.PetTypeService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	private final OwnerService ownerService;
	private final VetService vetService;
	private final SpecialtyService specialtyService;
	private final PetTypeService petTypeService;
	private final UserService userService;
	private final PetService petService;
	private final AuthoritiesService authoritiesService;
	
	@Autowired
	public WelcomeController(OwnerService ownerService, VetService vetService, SpecialtyService specialtyService,
			PetTypeService petTypeService, AuthoritiesService authoritiesService, PetService petService, 
			UserService userService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.specialtyService = specialtyService;
		this.petTypeService = petTypeService;
		this.authoritiesService = authoritiesService;
		this.petService = petService;
		this.userService = userService;
	}
	
	@GetMapping({"/","/welcome"})
	public String welcome(Map<String, Object> model) { 
		return "welcome";
	}
	
	@GetMapping("/populate")
	public String dropAndPopulate() {
		// Drop DB
		ownerService.deleteOwners();
		vetService.deleteVets();
		specialtyService.deleteSpecialties();
		petTypeService.deletePetTypes();
		authoritiesService.deleteAuthorities();
		userService.deleteUsers();
		petService.deletePets();
	
		// Populate DB
		ownerService.populateUsersAuthoritiesAndOwners();
		vetService.populateVetsAndSpecialties();
		petTypeService.populatePetTypes();
		petService.populatePetsAndVisits();
		return "redirect:/customLogout";
	}
}
