package by.itechart.javalab.dao.mysql;


import by.itechart.javalab.dao.ContactModificationDao;
import by.itechart.javalab.dao.DaoException;
import by.itechart.javalab.entity.Contact;
import by.itechart.javalab.entity.ContactAttachment;
import by.itechart.javalab.entity.ContactPhone;
import by.itechart.javalab.persistence.PersistenceManager;
import by.itechart.javalab.persistence.TransactionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public final class ContactModificationMysqlDao implements ContactModificationDao {
    private final static ContactModificationMysqlDao instance = new ContactModificationMysqlDao();
    private static Logger log = LogManager.getLogger(ContactModificationMysqlDao.class.getName());

    private ContactModificationMysqlDao() {}

    public static ContactModificationMysqlDao getInstance(){
        return instance;
    }

    @Override
    public Contact addNewContact(Contact contact) throws DaoException {
        log.debug("addNewContact: {}, {}", contact.getSurname(), contact.getEmail());
        Connection connection = null;
        try {
            connection = PersistenceManager.getConnection();
            savePersonalData(connection, contact);
            for (ContactPhone phone : contact.getPhoneList()) {
                phone.setIdContact(contact.getIdContact());
            }
            saveContactPhones(connection, contact.getPhoneList());
            for (ContactAttachment attachment : contact.getAttachmentList()) {
                attachment.setIdContact(contact.getIdContact());
            }
            saveContactAttachments(connection, contact.getAttachmentList());
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't add new contact.", ex);
        }
        return contact;
    }

    @Override
    public Contact updateContact(Contact contact) throws DaoException {
        log.debug("updateContact: {}", contact.getIdContact());
        Connection connection = null;
        try {
            connection = PersistenceManager.getConnection();
            updatePersonalData(connection, contact);
            updateContactPhones(connection, contact);
            updateContactAttachments(connection, contact);
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't update contact.", ex);
        }
        return contact;
    }

    @Override
    public void deleteContacts(Long[] contactsId) throws DaoException {
        log.debug("deleteContacts: ");
        Connection connection = null;
        PreparedStatement statement = null;
        String tableName[] = {"phone", "attachment", "contact"};
        String columnName[] = {"idPhone", "idAttachment", "idContact"};
        try {
            connection = PersistenceManager.getConnection();
            for (int i = 0; i < tableName.length; i++) {
                statement = connection.prepareStatement("UPDATE " + tableName[i] +
                        " SET available = ? WHERE " + columnName[i] + " = ?");
                for (Long id : contactsId) {
                    statement.setBoolean(1, false);
                    statement.setLong(2, id);
                    statement.executeUpdate();
                }
            }
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't delete contact.", ex);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public void saveContactPhones(List<ContactPhone> phones) throws DaoException {
        log.debug("saveContactPhones: {}", phones);
        Connection connection = null;
        try {
            connection = PersistenceManager.getConnection();
            saveContactPhones(connection, phones);
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't save contact phones.", ex);
        }
    }

    @Override
    public void deleteContactPhones(List<ContactPhone> phones) throws DaoException {
        log.debug("deleteContactPhones: {}", phones);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = PersistenceManager.getConnection();
            statement = connection.prepareStatement("UPDATE phone" +
                    " SET available = ? WHERE idPhone = ?");
            for (ContactPhone phone : phones) {
                statement.setBoolean(1, false);
                statement.setLong(2, phone.getIdPhone());
                statement.executeUpdate();
            }
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't delete contact phones.", ex);
        }
    }

    @Override
    public List<ContactAttachment> saveContactAttachments(List<ContactAttachment> attachments) throws DaoException {
        log.debug("saveContactAttachments: {}", attachments);
        Connection connection = null;
        List<ContactAttachment> savedAttachments = null;
        try {
            connection = PersistenceManager.getConnection();
            savedAttachments = saveContactAttachments(connection, attachments);
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't save contact phones.", ex);
        }
        return savedAttachments;
    }

    @Override
    public void updateContactAttachments(Contact contact) throws DaoException {
        log.debug("updateContactAttachments: {}, {}", contact.getEmail(), contact.getAttachmentList());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = PersistenceManager.getConnection();
            statement = connection.prepareStatement("UPDATE attachment" +
                    " SET realFileName = ? WHERE idAttachment = ?");
            for (ContactAttachment attachment : contact.getAttachmentList()) {
                statement.setString(1, attachment.getRealFileName());
                statement.setLong(2, attachment.getIdAttachment());
                statement.executeUpdate();
            }
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't update contact attachments.", ex);
        }
    }

    @Override
    public void deleteContactAttachments(List<ContactAttachment> attachments) throws DaoException {
        log.debug("deleteContactAttachments: {}", attachments);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = PersistenceManager.getConnection();
            statement = connection.prepareStatement("UPDATE attachment" +
                    " SET available = ? WHERE idAttachment = ?");
            for (ContactAttachment attachment : attachments) {
                statement.setBoolean(1, false);
                statement.setLong(2, attachment.getIdAttachment());
                statement.executeUpdate();
            }
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't delete contact attachments.", ex);
        }
    }


    private void savePersonalData(Connection connection, Contact contact) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO contact " +
                            "(name, surname, patronymic, birthday, gender, " +
                            "maritalStatus, citizenship, website, email, company, " +
                            " city, street, houseNumber, apartmentNumber, zipCode, Country_idCountryCode) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                            "(SELECT idCountryCode from country where fullName = ?))",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getSurname());
            if (StringUtils.isNotEmpty(contact.getPatronymic()))
                statement.setString(3, contact.getPatronymic());
            else
                statement.setNull(3, Types.VARCHAR);
            if (contact.getBirthday() != null)
                statement.setDate(4, new Date(contact.getBirthday().getTime()));
            else
                statement.setNull(4, Types.DATE);
            if (contact.getGender() != null)
                statement.setString(5, contact.getGender().name());
            else
                statement.setNull(5, Types.VARCHAR);
            if (contact.getMaritalStatus() != null)
                statement.setString(6, contact.getMaritalStatus().name());
            else
                statement.setNull(6, Types.VARCHAR);
            if (StringUtils.isNotEmpty(contact.getCitizenship()))
                statement.setString(7, contact.getCitizenship());
            else
                statement.setNull(7, Types.VARCHAR);
            if (StringUtils.isNotEmpty(contact.getWebsite()))
                statement.setString(8, contact.getWebsite());
            else
                statement.setNull(8, Types.VARCHAR);
            statement.setString(9, contact.getEmail());
            statement.setString(10, contact.getCompany());
            statement.setString(11, contact.getAddress().getCity());
            statement.setString(12, contact.getAddress().getStreet());
            statement.setString(13, contact.getAddress().getHouseNumber());
            if (StringUtils.isNotEmpty(contact.getAddress().getApartmentNumber()))
                statement.setString(14, contact.getAddress().getApartmentNumber());
            else
                statement.setNull(14, Types.VARCHAR);
            if (StringUtils.isNotEmpty(contact.getAddress().getZipCode()))
                statement.setString(15, contact.getAddress().getZipCode());
            else
                statement.setNull(15, Types.VARCHAR);
            if (StringUtils.isNotEmpty(contact.getAddress().getCountry()))
                statement.setString(16, contact.getAddress().getCountry().toUpperCase());
            else
                statement.setNull(16, Types.VARCHAR);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                contact.setIdContact((long) resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            closeStatement(statement);
        }
    }

    private void saveContactPhones(Connection connection, List<ContactPhone> phones) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO phone " +
                    "(countryCode, operatorCode, phoneNumber, phoneType, comment, Contact_idContact) " +
                    "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            for (ContactPhone phone : phones) {
                if (phone.getCountryCode() != null)
                    statement.setInt(1, phone.getCountryCode());
                else
                    statement.setNull(1, Types.INTEGER);
                if (phone.getOperatorCode() != null)
                    statement.setInt(2, phone.getOperatorCode());
                else
                    statement.setNull(2, Types.INTEGER);
                statement.setInt(3, phone.getPhoneNumber());
                statement.setString(4, phone.getPhoneType().name());
                if (StringUtils.isNotEmpty(phone.getComment()))
                    statement.setString(5, phone.getComment());
                else
                    statement.setNull(5, Types.VARCHAR);
                if (phone.getIdContact() != null)
                    statement.setLong(6, phone.getIdContact());
                else
                    statement.setNull(6, Types.INTEGER);
                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    phone.setIdPhone((long) id);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            closeStatement(statement);
        }
    }

    private List<ContactAttachment> saveContactAttachments(Connection connection, List<ContactAttachment> attachments) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO attachment " +
                    "(fileName, uploadDate, comment, Contact_idContact) " +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            for (ContactAttachment attachment : attachments) {
                statement.setString(1, attachment.getFileName());
                statement.setDate(2, new Date(attachment.getUploadDate().getTime()));
                if (StringUtils.isNotEmpty(attachment.getComment()))
                    statement.setString(3, attachment.getComment());
                else
                    statement.setNull(3, Types.VARCHAR);
                statement.setLong(4, attachment.getIdContact());
                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    attachment.setIdAttachment((long) id);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            closeStatement(statement);
        }
        return attachments;
    }

    private void updatePersonalData(Connection connection, Contact contact) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE contact " +
                            "SET name = ?, surname = ?, patronymic = ?, birthday = ?, gender = ?, " +
                            "maritalStatus = ?, citizenship = ?, website = ?, email = ?, company = ?, " +
                            "city = ?, street = ?, houseNumber = ?, apartmentNumber = ?, zipCode = ?, " +
                            "Country_idCountryCode = (SELECT idCountryCode from country where fullName = ?) " +
                            "WHERE idContact = ?",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getSurname());
            if (StringUtils.isNotEmpty(contact.getPatronymic()))
                statement.setString(3, contact.getPatronymic());
            else
                statement.setNull(3, Types.VARCHAR);
            if (contact.getBirthday() != null)
                statement.setDate(4, new Date(contact.getBirthday().getTime()));
            else
                statement.setNull(4, Types.DATE);
            if (contact.getGender() != null)
                statement.setString(5, contact.getGender().name());
            else
                statement.setNull(5, Types.VARCHAR);
            if (contact.getMaritalStatus() != null)
                statement.setString(6, contact.getMaritalStatus().name());
            else
                statement.setNull(6, Types.VARCHAR);
            if (StringUtils.isNotEmpty(contact.getCitizenship()))
                statement.setString(7, contact.getCitizenship());
            else
                statement.setNull(7, Types.VARCHAR);
            if (StringUtils.isNotEmpty(contact.getWebsite()))
                statement.setString(8, contact.getWebsite());
            else
                statement.setNull(8, Types.VARCHAR);
            statement.setString(9, contact.getEmail());
            statement.setString(10, contact.getCompany());
            statement.setString(11, contact.getAddress().getCity());
            statement.setString(12, contact.getAddress().getStreet());
            statement.setString(13, contact.getAddress().getHouseNumber());
            if (StringUtils.isNotEmpty(contact.getAddress().getApartmentNumber()))
                statement.setString(14, contact.getAddress().getApartmentNumber());
            else
                statement.setNull(14, Types.VARCHAR);
            if (StringUtils.isNotEmpty(contact.getAddress().getZipCode()))
                statement.setString(15, contact.getAddress().getZipCode());
            else
                statement.setNull(15, Types.VARCHAR);
            if (StringUtils.isNotEmpty(contact.getAddress().getCountry()))
                statement.setString(16, contact.getAddress().getCountry().toUpperCase());
            else
                statement.setNull(16, Types.VARCHAR);
            statement.setLong(17, contact.getIdContact());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                contact.setIdContact((long) resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            closeStatement(statement);
        }
    }

    private void updateContactPhones(Connection connection, Contact contact) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE phone " +
                    "SET countryCode = ?, operatorCode = ?, phoneNumber = ?, phoneType = ?, comment = ?, Contact_idContact = ? " +
                    "WHERE idPhone = ?", Statement.RETURN_GENERATED_KEYS);
            for (ContactPhone phone : contact.getPhoneList()) {
                if (phone.getCountryCode() != null)
                    statement.setInt(1, phone.getCountryCode());
                else
                    statement.setNull(1, Types.INTEGER);
                if (phone.getOperatorCode() != null)
                    statement.setInt(2, phone.getOperatorCode());
                else
                    statement.setNull(2, Types.INTEGER);
                statement.setInt(3, phone.getPhoneNumber());
                statement.setString(4, phone.getPhoneType().name());
                if (StringUtils.isNotEmpty(phone.getComment()))
                    statement.setString(5, phone.getComment());
                else
                    statement.setNull(5, Types.VARCHAR);
                if (phone.getIdContact() != null)
                    statement.setLong(6, phone.getIdContact());
                else
                    statement.setNull(6, Types.INTEGER);
                statement.setLong(7, phone.getIdPhone());
                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    phone.setIdPhone((long) id);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            closeStatement(statement);
        }
    }

    private void updateContactAttachments(Connection connection, Contact contact) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE attachment " +
                    "SET fileName = ?, uploadDate = ?, comment = ?, Contact_idContact = ? " +
                    "WHERE idAttachment = ?", Statement.RETURN_GENERATED_KEYS);
            for (ContactAttachment attachment : contact.getAttachmentList()) {
                statement.setString(1, attachment.getFileName());
                statement.setDate(2, new Date(attachment.getUploadDate().getTime()));
                if (StringUtils.isNotEmpty(attachment.getComment()))
                    statement.setString(3, attachment.getComment());
                else
                    statement.setNull(3, Types.VARCHAR);
                statement.setLong(4, contact.getIdContact());
                statement.setLong(5, attachment.getIdAttachment());
                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    attachment.setIdAttachment((long) id);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw e;
        } finally {
            closeStatement(statement);
        }
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