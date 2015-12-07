package by.itechart.javalab.dao.factoryimpl;


import by.itechart.javalab.dao.ContactAttributes;
import by.itechart.javalab.dao.ContactFindDao;
import by.itechart.javalab.dao.ContactModificationDao;
import by.itechart.javalab.dao.DaoFactory;
import by.itechart.javalab.dao.mysql.ContactAttributesMysqlDao;
import by.itechart.javalab.dao.mysql.ContactFindMysqlDao;
import by.itechart.javalab.dao.mysql.ContactModificationMysqlDao;

public final class MysqlDaoFactory extends DaoFactory {
	private final static MysqlDaoFactory instance = new MysqlDaoFactory();
	
	private MysqlDaoFactory(){}
	
	public final static MysqlDaoFactory getInstance(){
		return instance;
	}

    @Override
    public ContactFindDao getContactFindDao() {
        return ContactFindMysqlDao.getInstance();
    }

    @Override
    public ContactModificationDao getContactModificationDao() {
        return ContactModificationMysqlDao.getInstance();
    }

    @Override
    public ContactAttributes getContactAttributes() {
        return ContactAttributesMysqlDao.getInstance();
    }
}
