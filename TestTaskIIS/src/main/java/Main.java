import dao.LoadXMLDao;
import dao.SaveXMLDao;
import factorys.DaoSupportFactory;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("1 - сохранение БД в Xml");
        System.out.println("2 - выгрузка БД из Xml");
        System.out.println("0 - выход");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        while (Integer.parseInt(text)!= 0) {
            if (Integer.parseInt(text)==1) {
                SaveXMLDao saveXMLDao = DaoSupportFactory.getInstance().getSaveXMLDao();
                saveXMLDao.createDocument();
            }
            if (Integer.parseInt(text)==2) {
                LoadXMLDao loadXMLDao = DaoSupportFactory.getInstance().getLoadXMLDao();
                loadXMLDao.LoadXML();
            }
            System.out.println("1 - сохранение БД в Xml");
            System.out.println("2 - выгрузка БД из Xml");
            System.out.println("0 - выход");
            text = scanner.nextLine();
        }
        System.exit(0);

    }
}
