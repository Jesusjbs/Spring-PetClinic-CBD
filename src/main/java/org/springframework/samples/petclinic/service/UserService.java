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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.AuthoritiesRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private UserRepository userRepository;
	private AuthoritiesRepository authorityRepository;

	
	@Autowired
	public UserService(UserRepository userRepository, AuthoritiesRepository authorityRepository) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	@Transactional
	public void populateUser() throws DataAccessException {
		// Crear usuarios y asignales autoridades
		User admin = new User("admin","admin",true);
		User owner1 = new User("owner1","owner",true);
		User owner2 = new User("owner2","owner",true);
		User owner3 = new User("owner3","owner",true);
		User owner4 = new User("owner4","owner",true);
		User owner5 = new User("owner5","owner",true);
		User owner6 = new User("owner6","owner",true);
		User owner7 = new User("owner7","owner",true);
		User owner8 = new User("owner8","owner",true);
		User owner9 = new User("owner9","owner",true);
		User owner10 = new User("owner10","owner",true);
		User vet = new User("vet","vet",true);

		Authorities a1 = new Authorities(admin,"admin");
		Authorities a2 = new Authorities(vet,"veterinarian");
		Authorities a3 = new Authorities(owner1,"owner");
		Authorities a4 = new Authorities(owner2,"owner");
		Authorities a5 = new Authorities(owner3,"owner");
		Authorities a6 = new Authorities(owner4,"owner");
		Authorities a7 = new Authorities(owner5,"owner");
		Authorities a8 = new Authorities(owner6,"owner");
		Authorities a9 = new Authorities(owner7,"owner");
		Authorities a10 = new Authorities(owner8,"owner");
		Authorities a11 = new Authorities(owner9,"owner");
		Authorities a12 = new Authorities(owner10,"owner");
		
		authorityRepository.save(a1);
		authorityRepository.save(a2);
		authorityRepository.save(a3);
		authorityRepository.save(a4);
		authorityRepository.save(a5);
		authorityRepository.save(a6);
		authorityRepository.save(a7);
		authorityRepository.save(a8);
		authorityRepository.save(a9);
		authorityRepository.save(a10);
		authorityRepository.save(a11);
		authorityRepository.save(a12);
		
		admin.setAuthorities(new HashSet<>(Arrays.asList(a1)));
		vet.setAuthorities(new HashSet<>(Arrays.asList(a2)));
		owner1.setAuthorities(new HashSet<>(Arrays.asList(a3)));
		owner2.setAuthorities(new HashSet<>(Arrays.asList(a4)));
		owner3.setAuthorities(new HashSet<>(Arrays.asList(a5)));
		owner4.setAuthorities(new HashSet<>(Arrays.asList(a6)));
		owner5.setAuthorities(new HashSet<>(Arrays.asList(a7)));
		owner6.setAuthorities(new HashSet<>(Arrays.asList(a8)));
		owner7.setAuthorities(new HashSet<>(Arrays.asList(a9)));
		owner8.setAuthorities(new HashSet<>(Arrays.asList(a10)));
		owner9.setAuthorities(new HashSet<>(Arrays.asList(a11)));
		owner10.setAuthorities(new HashSet<>(Arrays.asList(a12)));
				
		userRepository.save(admin);
		userRepository.save(owner1);
		userRepository.save(owner2);
		userRepository.save(owner3);
		userRepository.save(owner4);
		userRepository.save(owner5);
		userRepository.save(owner6);
		userRepository.save(owner7);
		userRepository.save(owner8);
		userRepository.save(owner9);
		userRepository.save(owner10);
		userRepository.save(vet);
	}
	
	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}
	
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}
	
	public String getAuthority(String username) {
		return authorityRepository.getAuthority(username);
	}

	@Transactional
	public void deleteUsers() {
		userRepository.deleteAll();
	}
}
