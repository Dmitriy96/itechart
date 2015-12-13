package by.itechart.javalab.dao.mysql;


import by.itechart.javalab.dao.ContactAttributes;
import by.itechart.javalab.dao.DaoException;
import by.itechart.javalab.persistence.PersistenceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public final class ContactAttributesMysqlDao implements ContactAttributes {
    private final static ContactAttributesMysqlDao instance = new ContactAttributesMysqlDao();
    private static Logger log = LogManager.getLogger(ContactAttributesMysqlDao.class.getName());

    private ContactAttributesMysqlDao() {}

    public static ContactAttributesMysqlDao getInstance(){
        return instance;
    }

    @Override
    public List<String> getCountries() throws DaoException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> countries = new ArrayList<>();
        try {
            connection = PersistenceManager.createConnection();
            statement = connection.prepareStatement("SELECT `fullName` FROM `country`");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                countries.add(resultSet.getString(1));
            }
        } catch (NamingException | SQLException ex) {
            log.error(ex);
            throw new DaoException("Can't get countries.", ex);
        } finally {
            closeStatement(statement);
            PersistenceManager.closeConnection(connection);
        }
        return countries;
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
