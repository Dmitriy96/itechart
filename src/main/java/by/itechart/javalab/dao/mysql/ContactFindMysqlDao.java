package by.itechart.javalab.dao.mysql;


import by.itechart.javalab.dao.ContactFindDao;
import by.itechart.javalab.dao.DaoException;
import by.itechart.javalab.entity.Address;
import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.persistence.PersistenceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ContactFindMysqlDao implements ContactFindDao {
    private final static ContactFindMysqlDao instance = new ContactFindMysqlDao();
    private static Logger log = LogManager.getLogger(ContactFindMysqlDao.class.getName());
    private final int MAX_CONTACTS_NUMBER = 10;

    public static ContactFindMysqlDao getInstance(){
        return instance;
    }

    @Override
    public List<Contact> getContacts(int offset) throws DaoException {
        log.debug("getContacts: " + offset);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Contact> contacts = new ArrayList<>();
        try {
            connection = PersistenceManager.createConnection();
            statement = connection.prepareStatement("select idContact, name, surname, birthday, " +
                    " company, city, street, houseNumber, apartmentNumber from contact where idContact > " +
                    "(select idContact from " +
                    "((SELECT idContact from contact ORDER BY idContact ASC LIMIT " +
                    offset * MAX_CONTACTS_NUMBER + ") as T) " +
                    "ORDER BY idContact DESC limit 1) LIMIT " + MAX_CONTACTS_NUMBER);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setIdContact(resultSet.getLong("idContact"));
                contact.setName(resultSet.getString("name"));
                contact.setSurname(resultSet.getString("surname"));
                contact.setBirthday(resultSet.getDate("birthday"));
                contact.setCompany(resultSet.getString("company"));
                Address address = new Address();
                address.setCity(resultSet.getString("city"));
                address.setStreet(resultSet.getString("street"));
                address.setHouseNumber(resultSet.getString("houseNumber"));
                address.setApartmentNumber(resultSet.getString("apartmentNumber"));
                contact.setAddress(address);
                contacts.add(contact);
            }
        } catch (NamingException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't get contacts.", ex);
        } finally {
            closeStatement(statement);
            PersistenceManager.closeConnection(connection);
        }
        return contacts;
    }

    @Override
    public List<String> getEmails(Integer[] contactId) throws DaoException {
        log.debug("getContacts: " + Arrays.asList(contactId).toString());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> emails = new ArrayList<>();
        try {
            connection = PersistenceManager.createConnection();
            statement = connection.prepareStatement("SELECT email FROM contact WHERE idContact = ?");
            for (Integer id : contactId) {
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    emails.add(resultSet.getString("email"));
                }
            }
        } catch (NamingException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't get emails.", ex);
        } finally {
            closeStatement(statement);
            PersistenceManager.closeConnection(connection);
        }
        return emails;
    }

    private void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
    }
}
