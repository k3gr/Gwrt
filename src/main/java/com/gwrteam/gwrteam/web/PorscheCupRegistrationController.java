package com.gwrteam.gwrteam.web;

import com.gwrteam.gwrteam.model.User;
import com.gwrteam.gwrteam.model.UserLeague;
import com.gwrteam.gwrteam.service.UserService;
import com.gwrteam.gwrteam.service.UserServiceImpl;
import com.gwrteam.gwrteam.web.dto.PlayerRegistrationDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/porschecup/zapisz-sie")
public class PorscheCupRegistrationController {

    private UserService userService;
    private UserServiceImpl us;

    public PorscheCupRegistrationController(UserService userService, UserServiceImpl us) {
        super();
        this.userService = userService;
        this.us = us;
    }

    @ModelAttribute("player")
    public PlayerRegistrationDto PlayerRegistrationDto(PlayerRegistrationDto player) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        User byUsername = us.findByUsername(principal);
        Long id = byUsername.getId();
        return new PlayerRegistrationDto(id, 1L, player.getCar(), player.getTeam());
    }

    @GetMapping
    public String showRegistrationForm() {
        return "signup-porschecup";
    }

    @PostMapping
    public String registerPlayerAccount(@ModelAttribute("player") PlayerRegistrationDto playerRegistrationDto, Model model) {
        Optional<UserLeague> userLeagueByUserIdAndLeagueId = us.findUserLeagueByUserIdAndLeagueId(playerRegistrationDto.getUser_id(), 1L);
        Optional<UserLeague> userleague = us.findUserLeagueByUserId(playerRegistrationDto.getUser_id());
        if (userleague.isPresent() && userLeagueByUserIdAndLeagueId.isPresent()) {

            model.addAttribute("message", "Jesteś już zapisany do ligi.");
            model.addAttribute("icon", "fa-solid fa-circle-xmark");
            return "messageSignupPlayer";
        }
        us.joinGame(playerRegistrationDto);
        model.addAttribute("message", "Gratulacje! Zostałeś zapisany do ligi.");
        model.addAttribute("icon", "fa-solid fa-circle-check");
        return "messageSignupPlayer";
    }
}