package org.springframework.samples.petclinic;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication()
public class PetclinicApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PetclinicApplication.class, args);
	}
	
	@Bean(name="entityManagerFactory")
	public EntityManagerFactory getEntityManagerFactoryBean() {
		Map<String, String> properties = new HashMap<String, String>();
	  properties.put("javax.persistence.jdbc.user", "admin");
	  properties.put("javax.persistence.jdbc.password", "admin");
	  return Persistence.createEntityManagerFactory("objectdb://localhost:6136/spring-data-cbd.odb;drop", properties);
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}
}
