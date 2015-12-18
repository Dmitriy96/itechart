package by.itechart.javalab.dao;

import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.entity.ContactAttachment;
import by.itechart.javalab.entity.ContactPhone;

import java.util.List;


public interface ContactModificationDao {
    Contact addNewContact(Contact contact) throws DaoException;
    Contact updateContact(Contact contact) throws DaoException;
    void deleteContacts(Long contactsId[]) throws DaoException;
    void saveContactPhones(List<ContactPhone> phones) throws DaoException;
    void deleteContactPhones(List<ContactPhone> phones) throws DaoException;
    List<ContactAttachment> saveContactAttachments(List<ContactAttachment> attachments) throws DaoException;
    void updateContactAttachments(Contact contact) throws DaoException;
    void deleteContactAttachments(List<ContactAttachment> attachments) throws DaoException;
}
