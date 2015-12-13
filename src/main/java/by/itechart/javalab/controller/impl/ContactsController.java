package by.itechart.javalab.controller.impl;


import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.service.FindContactService;
import by.itechart.javalab.service.ModificationContactService;
import by.itechart.javalab.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactsController implements Controller {
    private static Logger log = LogManager.getLogger(ContactsController.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doGet: " + request.getParameter("page"));
        List<Contact> contacts = new ArrayList<>();
        try {
            Integer page = 1;
            if (StringUtils.isNotEmpty(request.getParameter("page"))) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            contacts = FindContactService.getContacts(page);
            log.debug("contacts list size: " + contacts.size());
            request.setAttribute("contacts", contacts);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
        } catch (ServiceException | ServletException | IOException | NumberFormatException e) {
            log.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String checkedContacts[] = request.getParameterValues("contact");
        Integer contactsId[] = new Integer[checkedContacts.length];
        for (int i = 0; i < checkedContacts.length; i++) {
            contactsId[i] = Integer.parseInt(checkedContacts[i]);
        }
        List<Contact> contacts = new ArrayList<>();
        try {
            ModificationContactService.deleteContacts(contactsId);
            contacts = FindContactService.getContacts(0);
            request.setAttribute("contacts", contacts);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
        } catch (ServletException | ServiceException | IOException e) {
            log.error(e);
        }
    }
}
