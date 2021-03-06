package by.itechart.javalab.dao;

import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.service.ContactSearchAttributes;

import java.util.List;


public interface ContactFindDao {
    List<Contact> getContacts(Integer offset, boolean isLowerIds) throws DaoException;
    List<Contact> getContacts(ContactSearchAttributes searchAttributes, Integer offset, boolean isLowerIds) throws DaoException;
    Contact getContact(Long contactId) throws DaoException;
    List <String> getEmails(Long contactId[]) throws DaoException;
    List<Contact> getBirthdayContacts() throws DaoException;
}
