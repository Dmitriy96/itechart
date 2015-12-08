package by.itechart.javalab.dao.mysql;


import by.itechart.javalab.dao.ContactFindDao;
import by.itechart.javalab.dao.DaoException;
import by.itechart.javalab.entity.*;
import by.itechart.javalab.persistence.PersistenceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class ContactFindMysqlDao implements ContactFindDao {
    private final static ContactFindMysqlDao instance = new ContactFindMysqlDao();
    private static Logger log = LogManager.getLogger(ContactFindMysqlDao.class.getName());
    private final int MAX_CONTACTS_NUMBER = 10;

    public static ContactFindMysqlDao getInstance(){
        return instance;
    }

    @Override
    public List<Contact> getContacts(Integer offset) throws DaoException {
        log.debug("getContacts: " + offset);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Contact> contacts = new ArrayList<>();
        try {
            connection = PersistenceManager.createConnection();
            statement = connection.prepareStatement("select idContact, name, surname, birthday, " +
                    " company, city, street, houseNumber, apartmentNumber from contact where " +
                    "idContact > ? AND enabled = ? LIMIT ?");
            statement.setInt(1, offset);
            statement.setBoolean(2, true);
            statement.setInt(3, MAX_CONTACTS_NUMBER);
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
    public Contact getContact(Integer contactId) throws DaoException {
        log.debug("getContact: " + contactId);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Contact contact = null;
        try {
            connection = PersistenceManager.createConnection();
            statement = connection.prepareStatement("SELECT contact.idContact, contact.name, " +
                    "contact.surname, contact.patronymic, contact.birthday, contact.gender, " +
                    "contact.citizenship, contact.website, contact.email, contact.company, contact.maritalStatus, " +
                    "country.fullName, contact.city, contact.street, contact.houseNumber, contact.apartmentNumber, contact.zipCode, " +
                    "phone.idPhone, phone.countryCode, phone.operatorCode, phone.phoneNumber, phone.phoneType, phone.comment" +
                    "attachment.idAttachment, attachment.fileName, attachment.uploadDate, attachment.comment " +
                    "FROM contact " +
                    "JOIN country " +
                    "ON contact.Country_idCountryCode = country.idCountryCode " +
                    "JOIN phone\n" +
                    "on phone.Contact_idContact = contact.idContact " +
                    "JOIN attachment " +
                    "on attachment.Contact_idContact = contact.idContact " +
                    "WHERE contact.idContact = ? AND contact.enabled = ? and phone.enabled = ? and attachment.enabled = ?");
            statement.setInt(1, contactId);
            statement.setBoolean(2, true);
            statement.setBoolean(3, true);
            statement.setBoolean(4, true);
            resultSet = statement.executeQuery();
            contact = new Contact();
            List<ContactPhone> phones = new ArrayList<>();
            List<ContactAttachment> attachments = new ArrayList<>();
            if (resultSet.next()) {
                contact.setIdContact(resultSet.getLong("contact.idContact"));
                contact.setName(resultSet.getString("contact.name"));
                contact.setSurname(resultSet.getString("contact.surname"));
                contact.setPatronymic(resultSet.getString("contact.patronymic"));
                contact.setBirthday(resultSet.getDate("contact.birthday"));
                contact.setGender(Gender.valueOf(resultSet.getString("contact.gender")));
                contact.setCitizenship(resultSet.getString("contact.citizenship"));
                contact.setWebsite(resultSet.getString("contact.website"));
                contact.setEmail(resultSet.getString("contact.email"));
                contact.setCompany(resultSet.getString("contact.company"));
                contact.setMaritalStatus(MaritalStatus.valueOf(resultSet.getString("contact.maritalStatus")));
                Address address = new Address();
                address.setCountry(resultSet.getString("country.fullName"));
                address.setCity(resultSet.getString("contact.city"));
                address.setStreet(resultSet.getString("contact.street"));
                address.setHouseNumber(resultSet.getString("contact.houseNumber"));
                address.setApartmentNumber(resultSet.getString("contact.apartmentNumber"));
                address.setZipCode(resultSet.getInt("contact.zipCode"));
                contact.setAddress(address);
                ContactPhone phone = getContactPhone(resultSet);
                phones.add(phone);
                ContactAttachment attachment = getContactAttachment(resultSet);
                attachments.add(attachment);
            }
            while (resultSet.next()) {
                ContactPhone phone = getContactPhone(resultSet);
                phones.add(phone);
                ContactAttachment attachment = getContactAttachment(resultSet);
                attachments.add(attachment);
            }
            contact.setPhoneList(phones);
            contact.setAttachmentList(attachments);
        } catch (NamingException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't get contacts.", ex);
        } finally {
            closeStatement(statement);
            PersistenceManager.closeConnection(connection);
        }
        return contact;
    }

    private ContactPhone getContactPhone(ResultSet resultSet) throws SQLException{
        ContactPhone phone = new ContactPhone();
        phone.setIdPhone(resultSet.getLong("phone.idPhone"));
        phone.setCountryCode(resultSet.getInt("phone.countryCode"));
        phone.setOperatorCode(resultSet.getInt("phone.operatorCode"));
        phone.setPhoneNumber(resultSet.getInt("phone.phoneNumber"));
        phone.setPhoneType(PhoneType.valueOf(resultSet.getString("phone.phoneType")));
        phone.setComment(resultSet.getString("phone.comment"));
        return phone;
    }

    private ContactAttachment getContactAttachment(ResultSet resultSet) throws SQLException {
        ContactAttachment attachment = new ContactAttachment();
        attachment.setIdAttachment(resultSet.getLong("attachment.idAttachment"));
        attachment.setFileName(resultSet.getString("attachment.fileName"));
        attachment.setUploadDate(resultSet.getDate("attachment.uploadDate"));
        attachment.setComment(resultSet.getString("attachment.comment"));
        return attachment;
    }

    @Override
    public List<String> getEmails(Integer[] contactId) throws DaoException {
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
