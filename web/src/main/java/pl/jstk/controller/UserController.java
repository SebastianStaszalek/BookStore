package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.jstk.constants.ViewNames;
import pl.jstk.service.UserService;
import pl.jstk.to.UserTo;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;

	
	@GetMapping(value = "/login")
	public String login() {
		return ViewNames.LOGIN;
	}
	
	@PostMapping(value = "/login")
	public String processLogin(@ModelAttribute("user") UserTo userTo) {
		return ViewNames.LOGIN;
	}
	
}
