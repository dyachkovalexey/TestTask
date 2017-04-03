package dao;


import models.DBDate;
import org.w3c.dom.Document;

import java.util.List;

public interface LoadXMLDao {
    void LoadXML();
    void TransformFromXML(Document document);
    void FillDBFromXML(List<DBDate> dateList);
}
