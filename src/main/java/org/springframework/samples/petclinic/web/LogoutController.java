package org.springframework.samples.petclinic.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LogoutController {
	
    @RequestMapping("/customLogout")
    public void exit(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
        }
    }
}
