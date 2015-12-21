package by.itechart.javalab.controller.impl;


import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.entity.Address;
import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.entity.Gender;
import by.itechart.javalab.entity.MaritalStatus;
import by.itechart.javalab.service.ContactSearchAttributes;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsController implements Controller {
    private static Logger log = LogManager.getLogger(ContactsController.class.getName());
    private final Integer CONTACTS_PER_PAGE = 10;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doGet: ");
        List<Contact> contacts = new ArrayList<>();
        try {
            boolean isLowerIds = false;
            contacts = FindContactService.getContacts(0, isLowerIds);
            checkIfPaginationPossible(request, contacts, isLowerIds);
            request.setAttribute("hasPrevious", false);
            request.setAttribute("contacts", contacts);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
        } catch (ServiceException | ServletException | IOException | NumberFormatException e) {
            log.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String deletingContactsId = request.getParameter("deletingContactsId");
        log.debug("doPost: " + deletingContactsId);
        if (StringUtils.isNotEmpty(deletingContactsId)) {
            deleteContacts(request, response, deletingContactsId);
        } else {
            findContacts(request, response);
        }
    }

    private void deleteContacts(HttpServletRequest request, HttpServletResponse response, String deletingContactsId) {
        String checkedContactIds[] = deletingContactsId.split(",");
        Long contactsId[] = new Long[checkedContactIds.length];
        for (int i = 0; i < checkedContactIds.length; i++) {
            contactsId[i] = Long.parseLong(checkedContactIds[i]);
        }
        List<Contact> contacts = new ArrayList<>();
        try {
            ModificationContactService.deleteContacts(contactsId);
            contacts = FindContactService.getContacts(0, false);
            request.setAttribute("contacts", contacts);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
        } catch (ServletException | ServiceException | IOException | NumberFormatException e) {
            log.error(e);
        }
    }

    private void findContacts(HttpServletRequest request, HttpServletResponse response) {
        String isSearchParameter = request.getParameter("isSearch");
        Integer offset = Integer.parseInt(request.getParameter("startContactIdForPage"));
        Boolean isLowerIds = Boolean.parseBoolean(request.getParameter("isLowerIds"));
        if (StringUtils.isNotEmpty(isSearchParameter)) {
            ContactSearchAttributes searchAttributes = getSearchAttributes(request, response);
            try {
                List<Contact> contacts = FindContactService.getSearchContacts(searchAttributes, offset, isLowerIds);
                checkIfPaginationPossible(request, contacts, isLowerIds);
                request.setAttribute("contacts", contacts);
                request.setAttribute("searchAttributes", searchAttributes);
                request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
            } catch (ServletException | IOException | ServiceException e) {
                log.error(e);
            }
        } else {
            List<Contact> contacts = new ArrayList<>();
            try {
                contacts = FindContactService.getContacts(offset, isLowerIds);
                checkIfPaginationPossible(request, contacts, isLowerIds);
                request.setAttribute("contacts", contacts);
                request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
            } catch (ServiceException | ServletException | IOException | NumberFormatException e) {
                log.error(e);
            }
        }
    }

    private void checkIfPaginationPossible(HttpServletRequest request, List<Contact> contacts, Boolean isLowerIds) {
        if (isLowerIds) {
            if (contacts.size() > CONTACTS_PER_PAGE) {
                request.setAttribute("hasPrevious", true);
                request.setAttribute("hasNext", true);
                contacts.remove(0);
            } else {
                request.setAttribute("hasPrevious", false);
                request.setAttribute("hasNext", true);
            }
        } else {
            if (contacts.size() > CONTACTS_PER_PAGE) {
                request.setAttribute("hasNext", true);
                request.setAttribute("hasPrevious", true);
                contacts.remove(contacts.size() - 1);
            } else {
                request.setAttribute("hasNext", false);
                request.setAttribute("hasPrevious", true);
            }
        }
    }

    private ContactSearchAttributes getSearchAttributes(HttpServletRequest request, HttpServletResponse response) {
        ContactSearchAttributes searchAttributes = new ContactSearchAttributes();
        searchAttributes.setName(request.getParameter("name"));
        searchAttributes.setSurname(request.getParameter("surname"));
        searchAttributes.setPatronymic(request.getParameter("patronymic"));
        searchAttributes.setCitizenship(request.getParameter("citizenship"));
        Date birthdayDateFrom = parseBirthdayDateFrom(request, response);
        searchAttributes.setBirthdayDateFrom(birthdayDateFrom);
        Date birthdayDateTo = parseBirthdayDateTo(request, response);
        searchAttributes.setBirthdayDateTo(birthdayDateTo);
        Gender gender = parseGender(request);
        searchAttributes.setGender(gender);
        MaritalStatus maritalStatus = parseMaritalStatus(request);
        searchAttributes.setMaritalStatus(maritalStatus);
        Address address = parseAddress(request);
        searchAttributes.setAddress(address);
        return searchAttributes;
    }

    private Date parseBirthdayDateFrom(HttpServletRequest request, HttpServletResponse response) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String birthdayDateFrom = request.getParameter("lowerBirthday");
            if (StringUtils.isNotEmpty(birthdayDateFrom)) {
                Date parsedDate = format.parse(request.getParameter("lowerBirthday"));
                date = new Date(parsedDate.getTime());
            }
        } catch (ParseException e) {
            log.error(e);
            request.setAttribute("dateInvalidFormat", "Invalid date format.");
            returnPage(request, response);
        }
        return date;
    }

    private Date parseBirthdayDateTo(HttpServletRequest request, HttpServletResponse response) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String birthdayDateTo = request.getParameter("upperBirthday");
            if (StringUtils.isNotEmpty(birthdayDateTo)) {
                Date parsedDate = format.parse(request.getParameter("upperBirthday"));
                date = new Date(parsedDate.getTime());
            }
        } catch (ParseException e) {
            log.error(e);
            request.setAttribute("dateInvalidFormat", "Invalid date format.");
            returnPage(request, response);
        }
        return date;
    }

    private Gender parseGender(HttpServletRequest request) {
        Gender gender = null;
        String genderParameter = request.getParameter("gender");
        if (StringUtils.isNotEmpty(genderParameter)) {
            gender = Gender.valueOf(genderParameter);
        }
        return gender;
    }

    private MaritalStatus parseMaritalStatus(HttpServletRequest request) {
        MaritalStatus maritalStatus = null;
        String maritalStatusParameter =  request.getParameter("marital");
        if (StringUtils.isNotEmpty(maritalStatusParameter)) {
            maritalStatus = MaritalStatus.valueOf(maritalStatusParameter);
        }
        return maritalStatus;
    }

    private Address parseAddress(HttpServletRequest request) {
        Address address = new Address();
        String country = request.getParameter("country");
        country = "NONE".equals(country) ? null : country;
        address.setCountry(country);
        address.setCity(request.getParameter("city"));
        address.setStreet(request.getParameter("street"));
        address.setHouseNumber(request.getParameter("houseNumber"));
        address.setApartmentNumber(request.getParameter("apartmentNumber"));
        address.setZipCode(request.getParameter("zipCode"));
        return address;
    }

    private void returnPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
        } catch (ServletException | IOException e1) {
            log.error(e1);
        }
    }
}
