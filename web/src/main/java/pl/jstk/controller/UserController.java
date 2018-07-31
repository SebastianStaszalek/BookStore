package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pl.jstk.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	

}
