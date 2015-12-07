package by.itechart.javalab.dao;


import by.itechart.javalab.dao.factoryimpl.MysqlDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
	private static final String DAO_TYPE;
	private static Logger log = LogManager.getLogger(DaoFactory.class.getName());

    static {
        Properties properties = new Properties();
        try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
			log.error(e);
		}
        DAO_TYPE = properties.getProperty("storageType");
    }

	public static DaoFactory getDaoFactory() throws DaoException {
		switch (DAO_TYPE){
		case "mysql":
			return MysqlDaoFactory.getInstance();
		}
		throw new DaoException("Can't find storage type.");
	}

	public abstract ContactFindDao getContactFindDao();
	public abstract ContactModificationDao getContactModificationDao();
	public abstract ContactAttributes getContactAttributes();
}
