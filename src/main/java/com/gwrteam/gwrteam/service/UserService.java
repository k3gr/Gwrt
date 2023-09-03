package com.gwrteam.gwrteam.service;

import com.gwrteam.gwrteam.model.*;
import com.gwrteam.gwrteam.web.dto.PlayerRegistrationDto;
import com.gwrteam.gwrteam.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);

    void updatePassword(String code, String email) throws UserNotFoundException;

    boolean verifyUser(String code);

    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    User findByUsername(String username);

    Player joinGame(PlayerRegistrationDto playerRegistrationDto);

    User findByEmail(String email);

    User findByCustId(String custId);

    List<UserLeague> findAllByLeagueId(Long league_id);

    Optional<UserLeague> findByUserId(Long userId);

    List<GT4TableMain> findAllGT4Main();

    List<GT4TableLast> findAllGT4Last();

    List<PorscheTableMain> findAllPorscheMain();

    List<PorscheTableLast> findAllPorscheLast();

    Optional <UserLeague> findUserLeagueByUserId(Long id);

    Optional <UserLeague> findUserLeagueByUserIdAndLeagueId(Long id, Long leagueId);

}

