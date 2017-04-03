package dao;

import models.DBDate;

import java.util.Map;

public interface SQLQueryDao {
    void add(DBDate date);;
    Map showAllByMap();
    void syncDBWithXML(Map mapBD);
}
