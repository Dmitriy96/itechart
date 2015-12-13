package by.itechart.javalab.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class PersistenceManager {
    private static Logger log = LogManager.getLogger(PersistenceManager.class.getName());
    private static ConcurrentMap<Long, Connection> connectionMap = new ConcurrentHashMap<>();
    private static ConcurrentMap<Long, Savepoint> savepointMap = new ConcurrentHashMap<>();
    private static final String dataSourceName;

    private PersistenceManager() {}

    static {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {}
        dataSourceName = properties.getProperty("contextDataSource");
    }

    public static void startTransaction() throws NamingException, SQLException {
        Connection connection = createConnection();
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        Savepoint savepoint = connection.setSavepoint();
        connectionMap.put(Thread.currentThread().getId(), connection);
        savepointMap.put(Thread.currentThread().getId(), savepoint);
    }

    public static Connection createConnection() throws NamingException, SQLException {
        InitialContext ctx = new InitialContext();
        DataSource dataSource = (DataSource)ctx.lookup(dataSourceName);
        return dataSource.getConnection();
    }

    public static Connection getConnection() throws TransactionException {
        Long threadID = Thread.currentThread().getId();
        Connection connection = connectionMap.get(threadID);
        if (connection == null) {
            throw new TransactionException("Method should be called into transaction.");
        }
        return connection;
    }

    public static void finishTransaction() throws SQLException {
        Long threadID = Thread.currentThread().getId();
        savepointMap.remove(threadID);
        Connection connection = connectionMap.remove(threadID);
        connection.commit();
        closeConnection(connection);
    }

    public static void rollbackTransaction() throws SQLException {
        Long threadID = Thread.currentThread().getId();
        Savepoint savepoint = savepointMap.remove(threadID);
        Connection connection = connectionMap.remove(threadID);
        connection.rollback(savepoint);
        closeConnection(connection);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error(ex);
            }
        }
    }
}
