package com.inn.jewelry.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**

 This class provides utility methods for sending emails using Spring's JavaMailSender.
 */
@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender emailSender;

    /**
     * This method sends a simple email message to the specified recipient(s).
     *
     * @param to The email address of the recipient(s).
     * @param subject The subject line of the email.
     * @param text The body of the email.
     * @param list An optional list of email addresses to CC on the email.
     */
    public void sendSimpleMessage(String to, String subject,String text, List<String> list){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("krupka1994@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list != null && list.size() > 0){
            message.setCc(getCcArray(list));
        }
        emailSender.send(message);
    }

    /**
     * This is a helper method to convert a list of email addresses to an array of email addresses.
     *
     * @param ccList The list of email addresses to convert.
     * @return An array of email addresses.
     */
    private String[] getCcArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for (int i = 0; i < ccList.size(); i++){
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    /**
     * This method sends an email to a user who has forgotten their password, providing them with their login details and a link to log in.
     *
     * @param to The email address of the user who has forgotten their password.
     * @param subject The subject line of the email.
     * @param password The user's password.
     * @throws MessagingException if there is a problem with creating or sending the email message.
     */
    public void forgotMail (String to, String subject, String password) throws MessagingException{
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("krupka1994@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for Store Management System</b><br><b>Email: </b> "
                + to
                + " <br><b>Password: </b> "
                + password
                + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMsg, "text/html");
        emailSender.send(message);
    }

}
