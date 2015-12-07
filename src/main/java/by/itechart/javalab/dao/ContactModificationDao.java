package by.itechart.javalab.dao;

import by.itechart.javalab.entity.Contact;


public interface ContactModificationDao {
    Contact addNewContact(Contact contact) throws DaoException;
    void deleteContacts(Integer contactsId[]) throws DaoException;
}
