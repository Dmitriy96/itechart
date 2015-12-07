package by.itechart.javalab.service;

import by.itechart.javalab.dao.ContactModificationDao;
import by.itechart.javalab.dao.DaoException;
import by.itechart.javalab.dao.DaoFactory;
import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.persistence.PersistenceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;


public class ModificationContactService {
    private final static ModificationContactService instance = new ModificationContactService();
    private static Logger log = LogManager.getLogger(ModificationContactService.class.getName());

    public static Contact addNewContact(Contact contact) throws ServiceException {
        log.debug("addNewContact: " + contact.getSurname() + " " + contact.getEmail());
        Contact savedContact = null;
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactModificationDao modificationDao = daoFactory.getContactModificationDao();
            PersistenceManager.startTransaction();
            savedContact = modificationDao.addNewContact(contact);
            PersistenceManager.finishTransaction();
        } catch (SQLException | NamingException e) {
            log.error(e);
            throw new ServiceException("Can not add new contact.", e);
        } catch (DaoException e) {
            try {
                PersistenceManager.rollbackTransaction();
            } catch (SQLException e1) {
                log.error(e1);
            }
            log.error(e);
            throw new ServiceException("Can not add new contact.", e);
        }
        return savedContact;
    }

    public static void deleteContacts(Integer contactsId[]) throws ServiceException {
        log.debug("deleteContacts: ");
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactModificationDao modificationDao = daoFactory.getContactModificationDao();
            PersistenceManager.startTransaction();
            modificationDao.deleteContacts(contactsId);
            PersistenceManager.finishTransaction();
        } catch (SQLException | NamingException e) {
            log.error(e);
            throw new ServiceException("Can not delete contacts.", e);
        } catch (DaoException e) {
            try {
                PersistenceManager.rollbackTransaction();
            } catch (SQLException e1) {
                log.error(e1);
            }
            log.error(e);
            throw new ServiceException("Can not delete contacts.", e);
        }
    }
}
