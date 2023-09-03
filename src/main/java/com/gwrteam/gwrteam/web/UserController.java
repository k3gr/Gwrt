package com.gwrteam.gwrteam.web;

import com.gwrteam.gwrteam.model.User;
import com.gwrteam.gwrteam.repository.UserRepository;
import com.gwrteam.gwrteam.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/user/{username}")
    String getUser(@PathVariable String username, Model model){
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "useraccount";
    }
}
