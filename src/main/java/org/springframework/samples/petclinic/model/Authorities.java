package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity implements Serializable {
	
	@ManyToOne
	@JoinColumn(name = "username")
	User user;
	
	@Size(min = 3, max = 50)
	String authority;
	

	public Authorities(User user, @Size(min = 3, max = 50) String authority) {
		this.user = user;
		this.authority = authority;
	}

	public Authorities() {
	}
}
