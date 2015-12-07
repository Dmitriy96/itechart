package by.itechart.javalab.service;

import by.itechart.javalab.dao.ContactFindDao;
import by.itechart.javalab.dao.DaoException;
import by.itechart.javalab.dao.DaoFactory;
import by.itechart.javalab.entity.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class FindContactService {
    private final static FindContactService instance = new FindContactService();
    private static Logger log = LogManager.getLogger(FindContactService.class.getName());

    public static List<Contact> getContacts(int offset) throws ServiceException {
        List<Contact> contacts = new ArrayList<>();
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactFindDao findDao = daoFactory.getContactFindDao();
            contacts = findDao.getContacts(offset);
        } catch (DaoException ex) {
            log.error(ex);
            throw new ServiceException(ex);
        }
        return contacts;
    }

    public static List<String> getEmails(Integer contactsID[]) throws ServiceException {
        List<String> emails = new ArrayList<>();
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactFindDao findDao = daoFactory.getContactFindDao();
            emails = findDao.getEmails(contactsID);
        } catch (DaoException ex) {
            log.error(ex);
            throw new ServiceException(ex);
        }
        return emails;
    }
}
