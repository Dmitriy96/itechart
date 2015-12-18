package by.itechart.javalab.service;

import by.itechart.javalab.dao.ContactModificationDao;
import by.itechart.javalab.dao.DaoException;
import by.itechart.javalab.dao.DaoFactory;
import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.entity.ContactAttachment;
import by.itechart.javalab.entity.ContactPhone;
import by.itechart.javalab.persistence.PersistenceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ModificationContactService {
    private static Logger log = LogManager.getLogger(ModificationContactService.class.getName());

    private ModificationContactService() {}

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

    public static void updateContact(Contact contact, Map<String, List<ContactPhone>> phoneGroups,
                                        Map<String, List<ContactAttachment>> attachmentGroups) throws ServiceException{
        log.debug("updateContact: " + contact.getSurname() + " " + contact.getEmail());
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactModificationDao modificationDao = daoFactory.getContactModificationDao();
            PersistenceManager.startTransaction();
            contact.setPhoneList(phoneGroups.get("update"));
            contact.setAttachmentList(attachmentGroups.get("update"));
            modificationDao.updateContact(contact);
            modificationDao.deleteContactPhones(phoneGroups.get("delete"));
            modificationDao.deleteContactAttachments(attachmentGroups.get("delete"));
            modificationDao.saveContactPhones(phoneGroups.get("new"));
            modificationDao.saveContactAttachments(attachmentGroups.get("new"));
            PersistenceManager.finishTransaction();
        } catch (SQLException | NamingException e) {
            log.error(e);
            throw new ServiceException("Can't update contact.", e);
        } catch (DaoException e) {
            try {
                PersistenceManager.rollbackTransaction();
            } catch (SQLException e1) {
                log.error(e1);
            }
            log.error(e);
            throw new ServiceException("Can't update contact.", e);
        }
    }

    public static void deleteContacts(Long contactsId[]) throws ServiceException {
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

    public static void saveContactPhones(List<ContactPhone> phones) throws ServiceException {
        log.debug("saveContactPhones: " + phones);
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactModificationDao modificationDao = daoFactory.getContactModificationDao();
            PersistenceManager.startTransaction();
            modificationDao.saveContactPhones(phones);
            PersistenceManager.finishTransaction();
        } catch (SQLException | NamingException e) {
            log.error(e);
            throw new ServiceException("Can't save contact phones.", e);
        } catch (DaoException e) {
            try {
                PersistenceManager.rollbackTransaction();
            } catch (SQLException e1) {
                log.error(e1);
            }
            log.error(e);
            throw new ServiceException("Can't save contact phones.", e);
        }
    }

    public static void deleteContactPhones(List<ContactPhone> phones) throws ServiceException {
        log.debug("deleteContactPhones: " + phones);
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactModificationDao modificationDao = daoFactory.getContactModificationDao();
            PersistenceManager.startTransaction();
            modificationDao.deleteContactPhones(phones);
            PersistenceManager.finishTransaction();
        } catch (SQLException | NamingException e) {
            log.error(e);
            throw new ServiceException("Can't delete contact phones.", e);
        } catch (DaoException e) {
            try {
                PersistenceManager.rollbackTransaction();
            } catch (SQLException e1) {
                log.error(e1);
            }
            log.error(e);
            throw new ServiceException("Can't delete contact phones.", e);
        }
    }

    public static List<ContactAttachment> saveContactAttachments(List<ContactAttachment> attachments) throws ServiceException {
        log.debug("saveContactAttachments: " + attachments);
        List<ContactAttachment> savedAttachments = new ArrayList<>();
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactModificationDao modificationDao = daoFactory.getContactModificationDao();
            PersistenceManager.startTransaction();
            savedAttachments = modificationDao.saveContactAttachments(attachments);
            PersistenceManager.finishTransaction();
        } catch (SQLException | NamingException e) {
            log.error(e);
            throw new ServiceException("Can't save contact attachments.", e);
        } catch (DaoException e) {
            try {
                PersistenceManager.rollbackTransaction();
            } catch (SQLException e1) {
                log.error(e1);
            }
            log.error(e);
            throw new ServiceException("Can't save contact attachments.", e);
        }
        return savedAttachments;
    }

    public static void updateContactAttachments(Contact contact) throws ServiceException {
        log.debug("updateContactAttachments: " + contact.getIdContact());
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactModificationDao modificationDao = daoFactory.getContactModificationDao();
            PersistenceManager.startTransaction();
            modificationDao.updateContactAttachments(contact);
            PersistenceManager.finishTransaction();
        } catch (SQLException | NamingException e) {
            log.error(e);
            throw new ServiceException("Can't update contact attachments.", e);
        } catch (DaoException e) {
            try {
                PersistenceManager.rollbackTransaction();
            } catch (SQLException e1) {
                log.error(e1);
            }
            log.error(e);
            throw new ServiceException("Can't update contact attachments.", e);
        }
    }

    public static void deleteContactAttachments(List<ContactAttachment> attachments) throws ServiceException {
        log.debug("deleteContactAttachments: " + attachments);
        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            ContactModificationDao modificationDao = daoFactory.getContactModificationDao();
            PersistenceManager.startTransaction();
            modificationDao.deleteContactAttachments(attachments);
            PersistenceManager.finishTransaction();
        } catch (SQLException | NamingException e) {
            log.error(e);
            throw new ServiceException("Can't delete contact attachments.", e);
        } catch (DaoException e) {
            try {
                PersistenceManager.rollbackTransaction();
            } catch (SQLException e1) {
                log.error(e1);
            }
            log.error(e);
            throw new ServiceException("Can't delete contact attachments.", e);
        }
    }
}
