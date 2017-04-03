package dao;
import factorys.DaoSupportFactory;
import models.DBDate;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import utils.FileChooser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.util.*;


public class LoadXMLDaoImpl implements LoadXMLDao{
    private Connection connection;
    private static final Logger log = Logger.getLogger(LoadXMLDaoImpl.class);



    public LoadXMLDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /** Создает объект Document */
    public void LoadXML() {
        try {
            FileChooser fileChooser = new FileChooser();
            String fileName = fileChooser.ChooseFile();
            final File xmlFile = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            log.info("объект документа из указаного XML файла создан");
            TransformFromXML(doc);
        }
        catch (Exception e) {
            log.error(e);
            throw new IllegalArgumentException();
        }
    }



    /** Синхронизация БД и Xml-файла
     * @param document - xml файл*/
    public void TransformFromXML(Document document) {
         document.getDocumentElement().normalize();
         Element element = document.getDocumentElement();
         List<DBDate> dbDateList = new ArrayList<DBDate>();

         Node node = element.getFirstChild().getNextSibling();
         Map mapXML = new HashMap();
         Map mapSupport = new HashMap();

         SQLQueryDao SQLQueryDao = DaoSupportFactory.getInstance().getSQLQueryDao();
         Map mapBD = SQLQueryDao.showAllByMap();

         while (node!=null) {
             NamedNodeMap namedNodeMap = node.getAttributes();

             String depcode = namedNodeMap.item(0).getNodeValue();
             String depjob = namedNodeMap.item(1).getNodeValue();
             String description = namedNodeMap.item(2).getNodeValue();

             DBDate dbDate = new DBDate(depcode, depjob, description);

             if (mapSupport.containsKey(depjob + depcode) == false) {
                if (mapBD.containsKey(depjob+depcode) == true) {
                    mapBD.remove(depjob+depcode);
                } else {
                    mapXML.put(depjob + depcode, dbDate);
                    dbDateList.add(dbDate);
                }
                 mapSupport.put(depjob+depcode, dbDate);
             } else {
                System.out.println("Exception: selected XML file has repeats");
                log.error("\"Exception: selected XML file has repeats\"");
                     return;
             }
             node = node.getNextSibling().getNextSibling();
         }
        log.info("файлы обработки БД получены");
        SQLQueryDao.syncDBWithXML(mapBD);
        FillDBFromXML(dbDateList);
        System.out.println("Загрузка прошла успешно");
    }

    /**adding lines to DB
     * @param dateList - список отсутсвующих в бд строк из xml*/
    public void FillDBFromXML(List<DBDate> dateList) {
        SQLQueryDao SQLQueryDao = DaoSupportFactory.getInstance().getSQLQueryDao();
        for (int i=0; i < dateList.size();i++) {
            SQLQueryDao.add(dateList.get(i));
        }
        log.info("отсутсвующие в бд строки из XML добавлены");
    }

}
