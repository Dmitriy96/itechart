package by.itechart.javalab.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class SendEmailService {
    private static final String username;
    private static final String password;

    static {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {}
        username = properties.getProperty("gmail.username");
        password = properties.getProperty("gmail.password");
    }

    public static boolean sendEmail(EmailAttributes emailAttributes) throws ServiceException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
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
            message.setText(emailAttributes.getEmailText());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new ServiceException("Не удалось отправить email.", e);
        }
        return true;
    }
}
