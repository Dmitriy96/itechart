package by.itechart.javalab.controller.impl;

import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.entity.Address;
import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.entity.Gender;
import by.itechart.javalab.entity.MaritalStatus;
import by.itechart.javalab.service.ContactAttributesService;
import by.itechart.javalab.service.ContactSearchAttributes;
import by.itechart.javalab.service.FindContactService;
import by.itechart.javalab.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class SearchContactsController implements Controller {
    private static Logger log = LogManager.getLogger(SearchContactsController.class.getName());
    private final Integer CONTACTS_PER_PAGE = 10;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doGet:");
        try {
            List<String> countries = ContactAttributesService.getAllCountries();
            request.setAttribute("countries", countries);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(request, response);
        } catch (ServiceException | ServletException | IOException e) {
            log.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        setEncoding(request, "UTF-8");
        log.debug("doPost:");
        ContactSearchAttributes searchAttributes = parseSearchAttributes(request, response);
        try {
            List<Contact> contacts = FindContactService.getSearchContacts(searchAttributes, 0, false);
            request.setAttribute("isSearch", true);
            resolvePagination(request, contacts);
            request.setAttribute("contacts", contacts);
            request.setAttribute("startContactIdForPreviousPage", 1);
            request.setAttribute("searchAttributes", searchAttributes);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            log.error(e);
        }
    }

    private void setEncoding(HttpServletRequest request, String encoding) {
        try {
            request.setCharacterEncoding(encoding);
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
    }

    private void resolvePagination(HttpServletRequest request, List<Contact> contacts) {
        request.setAttribute("hasPrevious", false);
        if (contacts.size() > CONTACTS_PER_PAGE) {
            request.setAttribute("hasNext", true);
            contacts.remove(contacts.size() - 1);
        } else {
            request.setAttribute("hasNext", false);
        }
    }

    private ContactSearchAttributes parseSearchAttributes(HttpServletRequest request, HttpServletResponse response) {
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
        String genderParameter = request.getParameter("gender");
        Gender gender = "NONE".equals(genderParameter) ? null : Gender.valueOf(genderParameter);
        return gender;
    }

    private MaritalStatus parseMaritalStatus(HttpServletRequest request) {
        String maritalStatusParameter = request.getParameter("marital");
        MaritalStatus maritalStatus = "NONE".equals(maritalStatusParameter) ? null : MaritalStatus.valueOf(maritalStatusParameter);
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
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(request, response);
        } catch (ServletException | IOException e1) {
            log.error(e1);
        }
    }
}
