package com.gwrteam.gwrteam.web;

import com.gwrteam.gwrteam.model.User;
import com.gwrteam.gwrteam.service.UserNotFoundException;
import com.gwrteam.gwrteam.service.UserService;
import com.gwrteam.gwrteam.service.UserServiceImpl;
import com.gwrteam.gwrteam.service.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;


@Controller
public class ForgotPasswordController {

    UserServiceImpl userService;
    @Autowired
    JavaMailSender mailSender;

    public ForgotPasswordController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/przypomnij-haslo")
    public String forgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgot_password";
    }

    @PostMapping("/przypomnij-haslo")
    public String forgotPasswordFormMethod(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String code = RandomString.make(45);
        User byEmail = userService.findByEmail(email);
        if (byEmail == null){
            model.addAttribute("error", "Nie ma użytkownika o podanym adresie e-mail.");
            return "forgot_password";
        }
        try {
            userService.updatePassword(code, email);

            String resetPasswordLink = Utility.getSiteURL(request) + "/zresetuj-haslo?code=" + code;

            sendEmail(email, resetPasswordLink);

            model.addAttribute("message", "Wysłaliśmy na skrzynkę e-mailową link resetujący hasło.");

        } catch (UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", "Błąd podczas wysyłania emaila. Sprawdź skrzynkę e-mailową.");
        }

        return "forgot_password";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        User byEmail = userService.findByEmail(email);
        String subject = "Link resetujący hasło";
        String mailContent = "<p>Cześć " + byEmail.getUsername() + "</p>"
                + "<p>Kliknij w link aby zresetować hasło</p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Zresetuj hasło<a/><b></p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("ligafds.poczta@gmail.com", "Liga Fast Drive School");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }

    @GetMapping("/zresetuj-haslo")
    public String resetPasswordForm(@Param(value = "code") String code, Model model) {

        User user = userService.get(code);

        if (user == null) {
            model.addAttribute("Title", "Zresetuj swoje hasło.");
            model.addAttribute("message", "Nieprawidłowy kod");
            return "message";
        }

        model.addAttribute("code", code);
        model.addAttribute("pageTitle", "Zresetuj swoje hasło");

        return "reset_password";

    }

    @PostMapping("/zresetuj-haslo")
    public String resetPasswordMethod(HttpServletRequest request, Model model) {

        String code = request.getParameter("code");
        String password = request.getParameter("password");

        User user = userService.get(code);

        if (user == null) {
            model.addAttribute("title", "Zresetuj swoje hasło.");
            model.addAttribute("message", "Nieprawidłowy kod");
            model.addAttribute("icon", "fa-solid fa-circle-xmark");
            return "message";
        }else{
            userService.updatePassword(user,password);
            model.addAttribute("message", "Hasło zostało zmienione.");
            model.addAttribute("icon", "fa-solid fa-circle-check");
        }
        return "message";
    }
}
