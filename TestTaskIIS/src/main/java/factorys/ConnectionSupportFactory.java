package factorys;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSupportFactory {

    private static ConnectionSupportFactory instance;
    private static final Logger log = Logger.getLogger(ConnectionSupportFactory.class);

    private Properties properties;
    private Connection connection;

    private ConnectionSupportFactory() {
        try {
            properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("SQL.properties"));
            Class.forName("org.postgresql.Driver");
            this.connection = null;

            this.connection = DriverManager.getConnection(properties.getProperty("jdbc.URL"),
                    properties.getProperty("jdbc.name"),
                    properties.getProperty("jdbc.password"));

        log.info("подключение БД прошло успешно");
        } catch (ClassNotFoundException e) {
            log.error(e);
            System.out.println(e);
        } catch (IOException e) {
            log.error(e);
            System.out.println(e);
        } catch (SQLException e) {
            log.error(e);
            System.out.println(e);
        }
    }

    static {
        instance = new ConnectionSupportFactory();
    }

    public static ConnectionSupportFactory getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
