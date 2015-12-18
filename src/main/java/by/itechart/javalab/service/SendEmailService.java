package by.itechart.javalab.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;


public class SendEmailService {
    private static Logger log = LogManager.getLogger(SendEmailService.class.getName());
    private static final String username;
    private static final String password;

    static {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            log.error(e);
        }
        username = properties.getProperty("mail.username");
        password = properties.getProperty("mail.password");
        log.debug(username, password);
    }

    private SendEmailService() {}

    public static boolean sendEmail(EmailAttributes emailAttributes) throws ServiceException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "25");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAttributes.getFromEmail()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailAttributes.getRecipientEmails()));
            message.setSubject(emailAttributes.getEmailTitle());
            message.setContent(emailAttributes.getEmailText(), "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e);
            throw new ServiceException("Не удалось отправить email.", e);
        }
        return true;
    }
}
