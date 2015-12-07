package by.itechart.javalab.controller.impl;

import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.service.EmailAttributes;
import by.itechart.javalab.service.FindContactService;
import by.itechart.javalab.service.SendEmailService;
import by.itechart.javalab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class EmailController implements Controller {
    private static Logger log = LogManager.getLogger(EmailController.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doGet: ");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String splittedURL[] = request.getPathInfo().split("/");
        if (splittedURL.length > 2) {
            if ("send".equals(splittedURL[2]))
                sendEmail(request, response);
        } else {
            showPage(request, response);
        }
    }

    private void sendEmail(HttpServletRequest request, HttpServletResponse response) {
        EmailAttributes emailAttributes = new EmailAttributes();
        emailAttributes.setEmailText(request.getParameter("text"));
        emailAttributes.setEmailTitle(request.getParameter("subject"));
        emailAttributes.setRecipientEmails(request.getParameter("recipients"));
        emailAttributes.setFromEmail("sankodmitriy100796@gmail.com");       // TODO change to some another
        try {
            SendEmailService.sendEmail(emailAttributes);
            request.getServletContext().getRequestDispatcher("/pages/contacts").forward(request, response);
        } catch (ServiceException | ServletException | IOException e) {
            log.error(e);
        }
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) {
        String checkedContacts[] = request.getParameterValues("contact");
        try {
            if (checkedContacts != null) {
                Integer contactId[] = new Integer[checkedContacts.length];
                for (int i = 0; i < checkedContacts.length; i++) {
                    contactId[i] = Integer.parseInt(checkedContacts[i]);
                }
                List<String> emailsList = FindContactService.getEmails(contactId);
                StringBuilder emails = new StringBuilder();
                for (String email : emailsList) {
                    emails.append(email);
                    emails.append(",");
                }
                String contactEmails = emails.toString();
                contactEmails = contactEmails.substring(0, contactEmails.length() - 1);
                log.debug(contactEmails);
                request.setAttribute("emails", contactEmails);
            }
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/email.jsp").forward(request, response);
        } catch (ServiceException | ServletException | IOException e) {
            log.error(e);
        }
    }
}
