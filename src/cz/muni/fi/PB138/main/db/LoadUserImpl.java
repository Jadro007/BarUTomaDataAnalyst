package cz.muni.fi.PB138.main.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * BarUTomaDataAnalyst
 */
public class LoadUserImpl implements LoadUser {

    private Document document;
    private long userId;

    public LoadUserImpl() {
        ReadDatabase readDatabase = new ReadDatabase();
        document = readDatabase.read("user");
        userId = new UserInformationImpl().getCurrentUserId();
    }

    public BigDecimal getPayment(LocalDate from, LocalDate to) {
        BigDecimal totalPayment = BigDecimal.ZERO;

        NodeList userNode = document.getElementsByTagName("user");
        for (int i = 0; i < userNode.getLength(); i++) {
            Element userElement = (Element) userNode.item(i);
            if (userElement.getAttribute("user_id").equals(""+userId)) {
                NodeList dateList = userElement.getElementsByTagName("date");
                for (int j = 0; j < dateList.getLength(); j++) {
                    Element dateElement = (Element) dateList.item(j);
                    LocalDate dateValue = LocalDate.parse(dateElement.getAttribute("value"));
                    if (!dateValue.isBefore(from) && !(dateValue.isAfter(to))) {
                        NodeList paymentList = dateElement.getElementsByTagName("payment");
                        Element paymentElement = (Element) paymentList.item(0);
                        BigDecimal payment = new BigDecimal(paymentElement.getTextContent());
                        totalPayment = totalPayment.add(payment);
                    }
                }
            }
        }
        return totalPayment;
    }

    public Map<String, Integer> getMostPurchasedDrinks(LocalDate from, LocalDate to) {

        Map<String, Integer> drinkMap = new TreeMap<>();

        NodeList userList = document.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Element userElement = (Element) userList.item(i);
            if (userElement.getAttribute("user_id").equals(""+userId)) {
                NodeList dateList = userElement.getElementsByTagName("date");
                for (int j = 0; j < dateList.getLength(); j++) {
                    Element dateElement = (Element) dateList.item(j);
                    LocalDate dateValue = LocalDate.parse(dateElement.getAttribute("value"));

                    if (!dateValue.isBefore(from) && !(dateValue.isAfter(to))) {
                        NodeList drinkNodeList = dateElement.getElementsByTagName("drink");
                        for (int k = 0; k < drinkNodeList.getLength(); k++) {
                            Element drinkElement = (Element) drinkNodeList.item(k);
                            if (drinkMap.containsKey(drinkElement.getAttribute("name"))) {
                                Integer sold = drinkMap.get(drinkElement.getAttribute("name"));
                                sold += Integer.parseInt(drinkElement.getTextContent());
                                drinkMap.put(drinkElement.getAttribute("name"), sold);
                            } else {
                                drinkMap.put(drinkElement.getAttribute("name"),
                                Integer.parseInt(drinkElement.getTextContent()));
                            }
                        }
                    }
                }
            }
        }
        return drinkMap;
    }

    public static void main(String[] args) {
        LoadUserImpl loadUser = new LoadUserImpl();
        BigDecimal payment = loadUser.getPayment(LocalDate.of(2015, 5, 14), LocalDate.of(2015, 5, 15));
        Map<String, Integer> map = loadUser.getMostPurchasedDrinks(LocalDate.of(2015, 5, 14), LocalDate.of(2015, 5, 15));
        System.out.println(payment); //ok
        System.out.println(map); //ok
    }
}
