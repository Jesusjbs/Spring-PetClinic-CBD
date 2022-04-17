package org.springframework.samples.petclinic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.util.CustomAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
		.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
		.antMatchers("/users/new").permitAll()
		.antMatchers("/session/**").permitAll()
		.antMatchers("/populate").permitAll()
		.antMatchers("/customLogout").permitAll()	
		.antMatchers("/admin/**").hasAnyAuthority("admin")
		.antMatchers("/owners/**").hasAnyAuthority("owner","admin")				
		.antMatchers("/vets/**").authenticated()
		.antMatchers("/vets.xml").authenticated()
		.anyRequest().denyAll()
		.and()
		 	.formLogin()
		 	.failureUrl("/login-error")
		.and()
			.logout(); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {	//Modificar el método para utilizarlo
	    return super.authenticationManagerBean();								//en el controlador
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider(userService));
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		return NoOpPasswordEncoder.getInstance();
	}
	
}


