package com.gwrteam.gwrteam.service;

import com.gwrteam.gwrteam.model.*;
import com.gwrteam.gwrteam.repository.*;
import com.gwrteam.gwrteam.web.dto.PlayerRegistrationDto;
import com.gwrteam.gwrteam.web.dto.UserRegistrationDto;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final UserLeagueRepository userLeagueRepository;
    private final GT4MainRepository gt4mainTable;
    private final GT4LastRepository gt4lastTable;
    private final PorscheMainRepository porscheMainTable;
    private final PorscheLastRepository porscheLastTable;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PlayerRepository playerRepository, UserLeagueRepository userLeagueRepository, GT4MainRepository gt4mainTable,
                           GT4LastRepository gt4lastTable, PorscheMainRepository porscheMainTable, PorscheLastRepository porscheLastTable) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.userLeagueRepository = userLeagueRepository;
        this.gt4mainTable = gt4mainTable;
        this.gt4lastTable = gt4lastTable;
        this.porscheMainTable = porscheMainTable;
        this.porscheLastTable = porscheLastTable;
    }

    String trimUsername(String username) {
        return username.toLowerCase();
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(trimUsername(registrationDto.getUsername()), passwordEncoder.encode(registrationDto.getPassword()),
                registrationDto.getEmail(), registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getCustId(), Arrays.asList(new Role("ROLE_USER")));

        String username = registrationDto.getUsername().trim();
        user.setUsername(username);
        user.setCreatedTime(new Date());
        user.setEnabled(false);
        user.setVerificationCode(RandomString.make(64));

        return userRepository.save(user);
    }

    @Override
    public Player joinGame(PlayerRegistrationDto playerRegistrationDto) {
        Player player = new Player(playerRegistrationDto.getUser_id(), playerRegistrationDto.getLeague_id(),
                playerRegistrationDto.getCar(), playerRegistrationDto.getTeam());

        return playerRepository.save(player);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByCustId(String custId) {
        return userRepository.findByCustId(custId);
    }

    @Override
    public List<UserLeague> findAllByLeagueId(Long league_id) {
        return userLeagueRepository.findAllByLeagueId(league_id);
    }

    @Override
    public Optional<UserLeague> findByUserId(Long userId) {
        return userLeagueRepository.findByUserId(userId);
    }

    @Override
    public List<GT4TableMain> findAllGT4Main() {
        return gt4mainTable.findAll();
    }

    @Override
    public List<GT4TableLast> findAllGT4Last() {
        return gt4lastTable.findAll();
    }

    @Override
    public List<PorscheTableMain> findAllPorscheMain() {
        return porscheMainTable.findAll();
    }

    @Override
    public List<PorscheTableLast> findAllPorscheLast() {
        return porscheLastTable.findAll();
    }

    @Override
    public Optional<UserLeague> findUserLeagueByUserId(Long id) {
        return userLeagueRepository.findUserLeagueByUserId(id);
    }

    @Override
    public Optional<UserLeague> findUserLeagueByUserIdAndLeagueId(Long id, Long leagueId) {
        return userLeagueRepository.findUserLeagueByUserIdAndAndLeagueId(id, leagueId);
    }

    @Override
    public void updatePassword(String code, String email) throws UserNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setPasswordCode(code);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Nie znaleziono użytkownika na podstawie emaila " + email);
        }
    }

    public User get(String resetPasswordCode) {
        return userRepository.findByPasswordCode(resetPasswordCode);
    }

    public void updatePassword(User user, String newPassword) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setPasswordCode(null);

        userRepository.save(user);
    }

    @Override
    public boolean verifyUser(String code) {

        User user = userRepository.findByVerificationCode(code);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            userRepository.enabled(user.getId());
            return true;
        }
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String subject = "Aktywuj swoje konto.";
        String sender = "Liga Fast Drive School";
        String mailContent = "<p>Cześć " + user.getUsername() + ",</p>";
        mailContent += "<p>Kliknij w link aktywacyjny:</p>";

        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        mailContent += "<h3><a href=\"" + verifyURL + "\">Link aktywacyjny</a></h3>";

        mailContent += "<p>Dziękujemy<br> Zespół Ligi Fast Drive School</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("ligafds.poczta@gmail.com", sender);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null || !user.isEnabled()) {
            throw new UsernameNotFoundException("Niewłaściwy login lub hasło");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public List<User> getAllUsers() throws Exception {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

}

