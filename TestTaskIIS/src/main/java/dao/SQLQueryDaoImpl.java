package dao;

import factorys.ConnectionSupportFactory;
import models.DBDate;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SQLQueryDaoImpl implements SQLQueryDao {

    private Connection connection;
    private static final Logger log = Logger.getLogger(SQLQueryDaoImpl.class);

    // language=SQL
    private static final String SQL_ADD_TO_DB = "INSERT INTO depdb (depcode, depjob, description) VALUES (?, ?, ?);";
    //language=SQL
    public static final String SQL_GET_ALL = "SELECT * FROM depdb";
    // language=SQL
    private static final String SQL_DELETE_FROM_DB = "DELETE FROM depdb WHERE depcode = ? AND depjob =?";

    public SQLQueryDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /** Insert new lines to DB
     * @param date - модель строки БД*/
    public void add(DBDate date) {
        try {
            PreparedStatement preparedStatement = ConnectionSupportFactory.getInstance().getConnection().prepareStatement(SQL_ADD_TO_DB);
            preparedStatement.setString(1, date.getDepCode());
            preparedStatement.setString(2, date.getDepJob());
            preparedStatement.setString(3, date.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error(e);
            System.out.println(e);
        }
    }

    /** Show all lines in DB*/
    public Map showAllByMap() {
        try {
            Map mapBD = new HashMap();
            Statement statement = ConnectionSupportFactory.getInstance().getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL_GET_ALL);
            while (result.next()) {
                DBDate dbDate = new DBDate(result.getString("depcode"), result.getString("depjob"),
                        result.getString("description"));
                mapBD.put(dbDate.getDepJob() + dbDate.getDepCode(), dbDate);
            }
            return mapBD;
        } catch (SQLException e) {
            log.error(e);
            throw new IllegalArgumentException();
        }
    }

    /** Deleting lines missing in XML file
     * @param mapBD - карта элементов БД на удаление*/
    public void syncDBWithXML(Map mapBD) {
        try {
            Statement statement = ConnectionSupportFactory.getInstance().getConnection().createStatement();
            ResultSet result = statement.executeQuery(SQL_GET_ALL);
            while (result.next()) {
                String depcode = result.getString("depcode");
                String depjob = result.getString("depjob");
                String description = result.getString("description");
                if (mapBD.containsKey(depjob+depcode) == true) {
                    PreparedStatement preparedStatement =
                            ConnectionSupportFactory.getInstance().getConnection().prepareStatement(SQL_DELETE_FROM_DB);
                    preparedStatement.setString(1, depcode);
                    preparedStatement.setString(2, depjob);
                    preparedStatement.execute();
                }
            }
        } catch (SQLException e) {
            log.error(e);
            System.out.println(e);
        }
    }


}
