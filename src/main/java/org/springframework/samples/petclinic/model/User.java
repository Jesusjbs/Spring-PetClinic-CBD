package org.springframework.samples.petclinic.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {
	@Id
	String username;
	
	String password;
	
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

	public User(String username, String password, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User() {
		super();
	}
}
