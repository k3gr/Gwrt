package com.gwrteam.gwrteam.web;

import com.gwrteam.gwrteam.model.User;
import com.gwrteam.gwrteam.service.UserService;
import com.gwrteam.gwrteam.service.Utility;
import com.gwrteam.gwrteam.web.dto.UserRegistrationDto;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Utilities;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Controller
@RequestMapping("/rejestracja")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "signup";
    }

    @ResponseBody
    @PostMapping
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto registrationDto, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        Optional<User> usernameEntry = Optional.ofNullable(userService.findByUsername(registrationDto.getUsername()));
        Optional<User> emailEntry = Optional.ofNullable(userService.findByEmail(registrationDto.getEmail()));
        Optional<User> custidEntry = Optional.ofNullable(userService.findByCustId(registrationDto.getCustId()));
        String username = registrationDto.getUsername();
        if (username.contains(" ")){
            return new ModelAndView("signup", "errorMessage", "Niedozwolony format nazwy użytkownika.");
        }
        if (usernameEntry.isPresent()) {
            return new ModelAndView("signup", "errorMessage", "Użytkownik o takiej nazwie już istnieje.");
        }
        if (emailEntry.isPresent()) {
            return new ModelAndView("signup", "errorMessage", "Użytkownik o takim e-mailu już istnieje.");
        }
        if (custidEntry.isPresent()) {
            return new ModelAndView("signup", "errorMessage", "Użytkownik o takim ID już istnieje.");
        }
        userService.save(registrationDto);
        String siteURL = Utility.getSiteURL(request);
        User byUsername = userService.findByUsername(registrationDto.getUsername());
        userService.sendVerificationEmail(byUsername, siteURL);
        return new ModelAndView("register_success");
    }
}
