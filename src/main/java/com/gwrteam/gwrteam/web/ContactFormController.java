package com.gwrteam.gwrteam.web;

import com.gwrteam.gwrteam.model.User;
import com.gwrteam.gwrteam.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


@Controller
public class ContactFormController {

    UserServiceImpl userService;
    @Autowired
    JavaMailSender mailSender;

    public ContactFormController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/liga/kontakt")
    public String contact() {
        return "contact";
    }

    @PostMapping("/liga/kontakt")
    public String contactMethod(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(principal);

        String text = request.getParameter("text");
        String name = request.getParameter("name");;
        String email = request.getParameter("email");

        sendEmail(text, email, name);
        model.addAttribute("message", "Wiadomość została wysłana.");

        return "contact";
    }

    private void sendEmail(String text, String email, String name) throws MessagingException, UnsupportedEncodingException {
        String subject = "Wiadomość od " + name;
        String mailContent = "<p>Cześć Adminie</p>"
                + "<p>" + text + "</p>"
                + "<p>" + name + "</p>"
                + "<p>" + email + "</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("ligafds.poczta@gmail.com", "Liga Fast Drive School");
        helper.setTo("ligafds.poczta@gmail.com");
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }
    @GetMapping("/kontakt")
    public String contactGwr() {
        return "contact-gwr";
    }

    @PostMapping("/kontakt")
    public String contactMethodGwr(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(principal);

        String text = request.getParameter("text");
        String name = request.getParameter("name");;
        String email = request.getParameter("email");

        sendEmail(text, email, name);
        model.addAttribute("message", "Wiadomość została wysłana.");

        return "contact-gwr";
    }
}
