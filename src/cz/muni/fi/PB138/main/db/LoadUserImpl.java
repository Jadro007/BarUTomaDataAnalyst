package cz.muni.fi.PB138.main.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * BarUTomaDataAnalyst
 */
public class LoadUserImpl implements LoadUser {

    public BigDecimal getPayment(LocalDate from, LocalDate to) {
        ReadDatabase readDatabase = new ReadDatabase();
        Document document = readDatabase.read("user");

        System.out.println(document);

        NodeList userNode = document.getElementsByTagName("user");
        for (int i = 0; i < userNode.getLength(); i++) {
            Element userElement = (Element) userNode.item(i);
            if (userElement.getAttribute("user_id").equals("5")) {
                System.out.println("user5");
            }
        }


        return null;
    }

    public Map<String, Integer> getMostPurchasedDrinks(LocalDate from, LocalDate to) {
        return null;
    }

    public static void main(String[] args) {
        LoadUserImpl loadUser = new LoadUserImpl();
        loadUser.getPayment(LocalDate.now(), LocalDate.now());
    }
}
