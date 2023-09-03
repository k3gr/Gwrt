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
public class ReportTheIncidentController {

    UserServiceImpl userService;
    @Autowired
    JavaMailSender mailSender;

    public ReportTheIncidentController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/zglos-incydent")
    public String reportTheIncident() {
        return "protest";
    }

    @PostMapping("/zglos-incydent")
    public String reportTheIncidentMethod(HttpServletRequest request, @RequestParam("clip") MultipartFile multipart, Model model) throws MessagingException, UnsupportedEncodingException {

        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(principal);

        String series = request.getParameter("series");
        String round = request.getParameter("round");
        String applicant = user.getFirstName() + " " + user.getLastName();
        String reported = request.getParameter("reported");
        String description = request.getParameter("description");

        if(multipart.getSize()<14485760) {
            sendEmail(series, round, applicant, reported, description, multipart);
            model.addAttribute("message", "Incydent został zgłoszony.");
            return "protest";
        }else{
            model.addAttribute("error", "Zbyt duży plik.");
        }
        return "protest";
    }

    private void sendEmail(String series, String round, String applicant, String reported, String description, MultipartFile multipart) throws MessagingException, UnsupportedEncodingException {
        String subject = "Protest: " + series + " " + round + " " + applicant + " " + reported;
        String mailContent = "<p>Cześć Adminie</p>"
                + "<p>Runda: " + series + " " + round + "</p>"
                + "<p>Zgłaszający: " + applicant + "</p>"
                + "<p>Sprawca: " + reported + "</p>"
                + "<p>Opis: " + description + "</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("ligafds.poczta@gmail.com", "Liga Fast Drive School");
        helper.setTo("ligafds.poczta@gmail.com");
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        if(!multipart.isEmpty()){
            String fileName = StringUtils.cleanPath(multipart.getOriginalFilename());

            InputStreamSource source = new InputStreamSource() {

                @Override
                public InputStream getInputStream() throws IOException {
                    return multipart.getInputStream();
                }
            };
            helper.addAttachment(fileName, source);
        }
        mailSender.send(message);
    }
}
