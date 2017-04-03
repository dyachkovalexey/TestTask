package dao;

import org.apache.log4j.Logger;
import org.jdom2.*;
import org.jdom2.output.*;
import utils.FileChooser;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SaveXMLDaoImpl  implements SaveXMLDao{
    private Connection connection;
    private static final Logger log = Logger.getLogger(SaveXMLDaoImpl.class);

    // language=SQL
    private static final String SQL_GET_ALL = "SELECT * FROM depdb";

    public  SaveXMLDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /** Saving Document to Xml file
     * @param doc - xml файл*/
    public void SaveToXML(Document doc) {
        try {
            FileChooser fileChooser = new FileChooser();
            String fileName = fileChooser.ChooseFile();
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            FileWriter fw = new FileWriter(fileName);
            outputter.output(doc, fw);
            fw.close();
            System.out.println("Сохранение прошло успешно");
            log.info("Xml файл успешно создан/обновлен");
        } catch (IOException e) {
            System.out.println(e);
            log.error(e);
        }
    }


    /** Completion of a document */
    public void createDocument() {
        try {
            Map dates = new HashMap();
            Document document = new Document();
            Element rootElement = new Element("Departament");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL);

            while (resultSet.next()) {
                Element element = new Element((resultSet.getString("depjob")
                        + resultSet.getString("depcode")));
                dates.put("depcode", resultSet.getString("depcode"));
                dates.put("depjob", resultSet.getString("depjob"));
                dates.put("description", resultSet.getString("description"));
                element = TransformToXML(dates, element);
                rootElement.addContent(element);
            }

            document.addContent(rootElement);
            log.info("данные из БД успешно перенесены в обьект Document");
            SaveToXML(document);
        } catch (SQLException e) {
            log.error(e);
            throw new IllegalArgumentException();
        }
    }

    /** add childElements to rootElement
     * @param map - карта данных БД
     * @param rootElement - элемент xml файла(строка) */
    public Element TransformToXML(Map map, Element rootElement) {
        rootElement.setAttribute("depcode", (String) map.get("depcode"));
        rootElement.setAttribute("depjob",(String) map.get("depjob"));
        rootElement.setAttribute("description", (String) map.get("description"));
        return rootElement;
    }
}
