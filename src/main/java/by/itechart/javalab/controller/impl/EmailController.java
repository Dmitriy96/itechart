package by.itechart.javalab.controller.impl;

import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.service.FindContactService;
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

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getPathInfo().split("/")[1];
        if ("send".equals(action)) {
            //send emails
        } else {
            String checkedContacts[] = request.getParameterValues("contact");
            try {
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
                request.setAttribute("emails", contactEmails);
                request.getServletContext().getRequestDispatcher("/WEB-INF/email.jsp").forward(request, response);
            } catch (ServiceException | ServletException | IOException e) {
                log.error(e);
            }
        }
    }
}
