package com.gwrteam.gwrteam.web;

import com.gwrteam.gwrteam.model.*;
import com.gwrteam.gwrteam.repository.GT4LastRepository;
import com.gwrteam.gwrteam.service.UserNotFoundException;
import com.gwrteam.gwrteam.service.UserServiceImpl;
import com.gwrteam.gwrteam.service.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class MainController {

    UserServiceImpl us;

    public MainController(UserServiceImpl us, GT4LastRepository table) {
        this.us = us;
    }

    @GetMapping("/logowanie")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/porschecup/regulamin")
    public String rulesporschecup() {
        return "rules-porschecup";
    }

    @GetMapping("/gt4sprintseries/regulamin")
    public String rulesgt4sprintseries() {
        return "rules-gt4sprintseries";
    }
    @GetMapping("/glowny-regulamin")
    public String rulesMain() {
        return "rules-main";
    }

    @GetMapping("/liga")
    public String league() {
        return "league-main";
    }

    @GetMapping("/polityka-prywatnosci")
    public String privacyPolicy() {
        return "privacyPolicy";
    }

    @GetMapping("/porschecup")
    public String leagueporschecup(Model model) {
        List <PorscheTableMain> standings = us.findAllPorscheMain();
        model.addAttribute("porschestandings", standings);
        List <PorscheTableLast> laststandings = us.findAllPorscheLast();
        model.addAttribute("porschelaststandings", laststandings);
        return "league-porschecup";
    }

    @GetMapping("/gt4sprintseries")
    public String leaguegt4sprintseries(Model model) {
        List <GT4TableMain> standings = us.findAllGT4Main();
        model.addAttribute("gt4standings", standings);
        List <GT4TableLast> laststandings = us.findAllGT4Last();
        model.addAttribute("gt4laststandings", laststandings);
        return "league-gt4sprintseries";
    }

    @GetMapping("/porschecup/zawodnicy")
    String allUsersPorscheCup(Model model) throws Exception {
        List<UserLeague> users = us.findAllByLeagueId(1L);
        model.addAttribute("users", users);
        return "list-porschecup";
    }

    @GetMapping("/gt4sprintseries/zawodnicy")
    String allUsersgt4sprintseries(Model model) throws Exception {
        List<UserLeague> users = us.findAllByLeagueId(2L);
        model.addAttribute("users", users);
        return "list-gt4sprintseries";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/rejestracja-powiodla-sie")
    public String registerSuccess() {
        return "register_success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code, Model model){
        boolean verified = us.verifyUser(code);

        String pageTitle = verified ? "Weryfikacja zakończona sukcesem." : "Weryfikacja zakończona niepowodzeniem";
        model.addAttribute("pageTitle", pageTitle);

        return (verified ? "verify_success" : "verify_fail");
    }
}


