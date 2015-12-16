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
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        log.debug("doPost:");
        ContactSearchAttributes searchAttributes = getSearchAttributes(request, response);
        try {
            List<Contact> contacts = FindContactService.getSearchContacts(searchAttributes, 0, false);
            request.setAttribute("isSearch", true);
            request.setAttribute("hasPrevious", false);
            if (contacts.size() > CONTACTS_PER_PAGE) {
                request.setAttribute("hasNext", true);
                contacts.remove(contacts.size() - 1);
            } else {
                request.setAttribute("hasNext", false);
            }
            request.setAttribute("contacts", contacts);
            request.setAttribute("startContactIdForPreviousPage", 1);
            request.setAttribute("searchAttributes", searchAttributes);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/contacts.jsp").forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            log.error(e);
        }
    }

    private ContactSearchAttributes getSearchAttributes(HttpServletRequest request, HttpServletResponse response) {
        ContactSearchAttributes searchAttributes = new ContactSearchAttributes();
        searchAttributes.setName(request.getParameter("name"));
        searchAttributes.setSurname(request.getParameter("surname"));
        searchAttributes.setPatronymic(request.getParameter("patronymic"));
        searchAttributes.setCitizenship(request.getParameter("citizenship"));
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String birthdayDateFrom = request.getParameter("lowerBirthday");
            if (StringUtils.isNotEmpty(birthdayDateFrom)) {
                Date parsedDate = format.parse(request.getParameter("lowerBirthday"));
                searchAttributes.setBirthdayDateFrom(new Date(parsedDate.getTime()));
            }
            else
                searchAttributes.setBirthdayDateFrom(null);
            String birthdayDateTo = request.getParameter("upperBirthday");
            if (StringUtils.isNotEmpty(birthdayDateTo)) {
                Date parsedDate = format.parse(request.getParameter("upperBirthday"));
                searchAttributes.setBirthdayDateFrom(new Date(parsedDate.getTime()));
            }
            else
                searchAttributes.setBirthdayDateFrom(null);
        } catch (ParseException e) {
            log.error(e);
            request.setAttribute("dateInvalidFormat", "Invalid date format.");
            try {
                request.getServletContext().getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(request, response);
            } catch (ServletException | IOException e1) {
                log.error(e1);
            }
        }
        String genderParameter = request.getParameter("gender");
        Gender gender = "NONE".equals(genderParameter) ? null : Gender.valueOf(genderParameter);
        searchAttributes.setGender(gender);
        String maritalStatusParameter =  request.getParameter("marital");
        MaritalStatus maritalStatus = "NONE".equals(maritalStatusParameter) ? null : MaritalStatus.valueOf(maritalStatusParameter);
        searchAttributes.setMaritalStatus(maritalStatus);
        Address address = new Address();
        String country = request.getParameter("country");
        country = "NONE".equals(country) ? null : country;
        address.setCountry(country);
        address.setCity(request.getParameter("city"));
        address.setStreet(request.getParameter("street"));
        address.setHouseNumber(request.getParameter("houseNumber"));
        address.setApartmentNumber(request.getParameter("apartmentNumber"));
        String zipCodeParameter = request.getParameter("zipCode");
        Integer zipCode = StringUtils.isNotEmpty(zipCodeParameter) ? Integer.parseInt(zipCodeParameter) : null;
        address.setZipCode(zipCode);
        searchAttributes.setAddress(address);
        return searchAttributes;
    }
}
