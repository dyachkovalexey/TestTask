package factorys;

import dao.SQLQueryDao;
import dao.LoadXMLDao;
import dao.SaveXMLDao;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Properties;

public class DaoSupportFactory {
    private static DaoSupportFactory instance;
    private Properties properties;
    private SQLQueryDao SQLQueryDao;
    private SaveXMLDao saveXMLDao;
    private LoadXMLDao loadXMLDao;
    private static final Logger log = Logger.getLogger(DaoSupportFactory.class);

    private  DaoSupportFactory() {
        this.properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("dao.properties"));

            String fillDBDaoClassName = properties.getProperty("filldbdao.class");
            String saveXMLDaoClassName = properties.getProperty("savexmldao.class");
            String loadXMLDaoClassName = properties.getProperty("loadxmldao.class");

            Class fillClass = Class.forName(fillDBDaoClassName);
            Class saveClass = Class.forName(saveXMLDaoClassName);
            Class loadClass = Class.forName(loadXMLDaoClassName);

            Constructor fillConstructor = fillClass.getConstructor(Connection.class);
            Constructor saveConstructor = saveClass.getConstructor(Connection.class);
            Constructor loasConstructor = loadClass.getConstructor(Connection.class);

            this.SQLQueryDao = (SQLQueryDao)fillConstructor.newInstance(ConnectionSupportFactory.getInstance().getConnection());
            this.saveXMLDao = (SaveXMLDao)saveConstructor.newInstance(ConnectionSupportFactory.getInstance().getConnection());
            this.loadXMLDao = (LoadXMLDao)loasConstructor.newInstance(ConnectionSupportFactory.getInstance().getConnection());

        } catch (FileNotFoundException e) {
            log.error(e);
            System.out.println(e);
        } catch (InstantiationException e) {
            log.error(e);
            System.out.println(e);
        } catch (InvocationTargetException e) {
            log.error(e);
            System.out.println(e);
        } catch (IllegalAccessException e) {
            log.error(e);
            System.out.println(e);
        } catch (NoSuchMethodException e) {
            log.error(e);
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            log.error(e);
            System.out.println(e);
        } catch (IOException e) {
            log.error(e);
            System.out.println(e);
        }
    }

    static {
        instance = new DaoSupportFactory();
    }

    public static DaoSupportFactory getInstance() {
        return instance;
    }

    public SQLQueryDao getSQLQueryDao() {
        return this.SQLQueryDao;
    }

    public SaveXMLDao getSaveXMLDao() {
        return this.saveXMLDao;
    }

    public LoadXMLDao getLoadXMLDao() {
        return this.loadXMLDao;
    }
}
