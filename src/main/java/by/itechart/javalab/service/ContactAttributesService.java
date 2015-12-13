package by.itechart.javalab.service;

import by.itechart.javalab.dao.ContactAttributes;
import by.itechart.javalab.dao.DaoException;
import by.itechart.javalab.dao.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class ContactAttributesService {
    private static Logger log = LogManager.getLogger(ContactAttributesService.class.getName());

    private ContactAttributesService() {}

    public static List<String> getAllCountries() throws ServiceException {
        List<String> countries = new ArrayList<>();
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactAttributes contactAttributes = daoFactory.getContactAttributes();
            countries = contactAttributes.getCountries();
        } catch (DaoException ex) {
            log.error(ex);
            throw new ServiceException(ex);
        }
        return countries;
    }
}
