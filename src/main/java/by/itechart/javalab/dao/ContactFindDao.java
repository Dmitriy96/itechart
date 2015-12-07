package by.itechart.javalab.dao;

import by.itechart.javalab.entity.Contact;

import java.util.List;


public interface ContactFindDao {
    List<Contact> getContacts(Integer offset) throws DaoException;
    Contact getContact(Integer contactId) throws DaoException;
    List <String> getEmails(Integer contactId[]) throws DaoException;
}