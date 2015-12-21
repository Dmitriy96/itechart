package by.itechart.javalab.controller.impl;


import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.service.FindContactService;
import by.itechart.javalab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewContactController implements Controller {
    private static Logger log = LogManager.getLogger(ViewContactController.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doGet: " + request.getPathInfo());
        String splittedURL[] = request.getPathInfo().split("/");
        if (splittedURL.length != 3) return;
        Contact contact = null;
        try {
            Long contactId = Long.parseLong(splittedURL[2]);
            contact = FindContactService.getContact(contactId);
            request.setAttribute("contact", contact);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
        } catch (ServiceException | ServletException | IOException | NumberFormatException e) {
            log.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}