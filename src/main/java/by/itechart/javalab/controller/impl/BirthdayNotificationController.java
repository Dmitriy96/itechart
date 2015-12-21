package by.itechart.javalab.controller.impl;


import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.service.EmailAttributes;
import by.itechart.javalab.service.FindContactService;
import by.itechart.javalab.service.SendEmailService;
import by.itechart.javalab.service.ServiceException;
import by.itechart.javalab.util.MainUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class BirthdayNotificationController implements Job {
    private static Logger log = LogManager.getLogger(BirthdayNotificationController.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        EmailAttributes emailAttributes = new EmailAttributes();
        emailAttributes.setEmailTitle("Birthday contacts");
        emailAttributes.setRecipientEmails(MainUtils.getEmail());
        emailAttributes.setFromEmail(MainUtils.getEmail());
        try {
            List<Contact> contacts = FindContactService.getBirthdayContacts();
            if (contacts.size() > 0) {
                StringBuilder messageText = new StringBuilder();
                for (Contact contact : contacts) {
                    messageText.append(contact.getSurname() + " " + contact.getName() + " " +
                            contact.getPatronymic() + " " + contact.getEmail() + " " +
                            contact.getBirthday() + " " + System.lineSeparator());
                }
                emailAttributes.setEmailText(messageText.toString());
                SendEmailService.sendEmail(emailAttributes);
            }
        } catch (ServiceException e) {
            log.error(e);
        }
    }
}
