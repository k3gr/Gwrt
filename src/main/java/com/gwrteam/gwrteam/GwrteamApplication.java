package com.gwrteam.gwrteam;

import com.gwrteam.gwrteam.model.Player;
import com.gwrteam.gwrteam.model.Role;
import com.gwrteam.gwrteam.model.User;
import com.gwrteam.gwrteam.repository.PlayerRepository;
import com.gwrteam.gwrteam.repository.UserRepository;
import com.gwrteam.gwrteam.service.UserService;
import com.gwrteam.gwrteam.web.dto.UserRegistrationDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class GwrteamApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GwrteamApplication.class, args);
    }

}
