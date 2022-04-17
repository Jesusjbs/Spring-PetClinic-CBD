package org.springframework.samples.petclinic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private final UserService userService;

	@Autowired
    public CustomAuthenticationProvider(UserService userService) {
		this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<User> users = new ArrayList<>();
        userService.getUsers().forEach(x -> users.add(x)); 
        Optional<User> authenticatedUser = users.stream().filter(
                user -> user.getUsername().equals(name) && user.getPassword().equals(password)
        ).findFirst();

        if(!authenticatedUser.isPresent()){
            throw new BadCredentialsException("El nombre de usuario y la contraseña que ingresaste no coinciden "
					+ "con nuestros registros. Por favor, revisa e inténtalo de nuevo.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userService.getAuthority(authenticatedUser.get().getUsername())));
        Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
