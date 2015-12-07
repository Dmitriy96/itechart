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

public final class ContactModificationMysqlDao implements ContactModificationDao {
    private final static ContactModificationMysqlDao instance = new ContactModificationMysqlDao();
    private static Logger log = LogManager.getLogger(ContactModificationMysqlDao.class.getName());

    public static ContactModificationMysqlDao getInstance(){
        return instance;
    }

    @Override
    public Contact addNewContact(Contact contact) throws DaoException {
        log.debug("addNewContact " + contact.getSurname() + " " + contact.getEmail());
        Connection connection = null;
        try {
            connection = PersistenceManager.getConnection();
            savePersonalData(connection, contact);
            saveContactPhones(connection, contact);
            saveContactAttachments(connection, contact);
        } catch (TransactionException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't add new contact.", ex);
        }
        return contact;
    }

    @Override
    public void deleteContacts(Integer[] contactsId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        String tableName[] = {"phone", "attachment", "contact"};
        String columnName[] = {"idPhone", "idAttachment", "idContact"};
        try {
            connection = PersistenceManager.getConnection();
            for (int i = 0; i < tableName.length; i++) {
                statement = connection.prepareStatement("UPDATE " + tableName[i] +
                        " SET enabled = ? WHERE " + columnName[i] + " = ?");
                for (Integer id : contactsId) {
                    statement.setBoolean(1, false);
                    statement.setInt(2, id);
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
            statement.setString(3, contact.getPatronymic());
            statement.setDate(4, new Date(contact.getBirthday().getTime()));
            statement.setString(5, contact.getGender().name());
            statement.setString(6, contact.getMaritalStatus().name());
            statement.setString(7, contact.getCitizenship());
            statement.setString(8, contact.getWebsite());
            statement.setString(9, contact.getEmail());
            statement.setString(10, contact.getCompany());
            statement.setString(11, contact.getAddress().getCity());
            statement.setString(12, contact.getAddress().getStreet());
            statement.setString(13, contact.getAddress().getHouseNumber());
            statement.setString(14, contact.getAddress().getApartmentNumber());
            statement.setInt(15, contact.getAddress().getIndex());
            statement.setString(16, contact.getAddress().getCountry().toUpperCase());
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

    private void saveContactPhones(Connection connection, Contact contact) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO phone " +
                    "(countryCode, operatorCode, phoneNumber, phoneType, Contact_idContact) " +
                    "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            for (ContactPhone phone : contact.getPhoneList()) {
                statement.setInt(1, phone.getCountryCode());
                statement.setInt(2, phone.getOperatorCode());
                statement.setInt(3, phone.getPhoneNumber());
                statement.setString(4, phone.getPhoneType().name());
                statement.setLong(5, contact.getIdContact());
                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    log.debug("saveContactPhones: " + id);
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

    private void saveContactAttachments(Connection connection, Contact contact) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO attachment " +
                    "(fileName, uploadDate, comment, Contact_idContact) " +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            for (ContactAttachment attachment : contact.getAttachmentList()) {
                statement.setString(1, attachment.getFileName());
                statement.setDate(2, new Date(attachment.getUploadDate().getTime()));
                if (StringUtils.isNotEmpty(attachment.getComment()))
                    statement.setString(3, attachment.getComment());
                else
                    statement.setNull(3, Types.VARCHAR);
                statement.setLong(4, contact.getIdContact());
                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    log.debug("saveContactAttachments: " + id);
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
