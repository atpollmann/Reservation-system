package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.service.IEmailService;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

@Service
public class EmailService implements IEmailService {

    private static Properties serviceProperties;

    public static void setProperties(String auth, String startTlsEnable, String host, String port, String username,
            String password) {
        serviceProperties = new Properties();
        serviceProperties.put("mail.smtp.auth", auth);
        serviceProperties.put("mail.smtp.starttls.enable", startTlsEnable);
        serviceProperties.put("mail.smtp.host", host);
        serviceProperties.put("mail.smtp.port", port);

        serviceProperties.put("username", username);
        serviceProperties.put("password", password);
    }

    static {
        setProperties(
                "true", // auth
                "true", // starttls
                "smtp.gmail.com", // host
                "587", // port
                System.getProperty("system.email.username"), // Google username
                System.getProperty("system.email.password") // Google password
        );
    }

    @Override
    public boolean sendMessage(String from, String to, String subject, String body) {
        try {
            Session session = Session.getInstance(serviceProperties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            serviceProperties.getProperty("username"),
                            serviceProperties.getProperty("password")
                    );
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
