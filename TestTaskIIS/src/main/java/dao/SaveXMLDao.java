package dao;

import org.jdom2.Document;
import org.jdom2.Element;
import java.util.Map;

public interface SaveXMLDao {

    void SaveToXML(Document doc);
    void createDocument();
    Element TransformToXML(Map map, Element rootElement);
}
