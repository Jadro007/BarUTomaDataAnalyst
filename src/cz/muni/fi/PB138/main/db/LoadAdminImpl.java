package cz.muni.fi.PB138.main.db;

import cz.muni.fi.PB138.main.entities.Bar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Tomáš on 17.6.2015.
 */
public class LoadAdminImpl implements LoadAdmin {

    private Document document;


    public LoadAdminImpl() {
        ReadDatabase readDatabase = new ReadDatabase();
        this.document = readDatabase.read("admin");
    }

    @Override
    public BigDecimal getIncome(LocalDate from, LocalDate to, long barId) {
        //ReadDatabase readDatabase = new ReadDatabase();
        //Document document = readDatabase.read("admin");
        BigDecimal totalIncome = BigDecimal.ZERO;

        NodeList dateList = document.getElementsByTagName("date");
        for (int i = 0; i < dateList.getLength(); i++) {
            Element dateElement = (Element) dateList.item(i);
            LocalDate dateValue = LocalDate.parse(dateElement.getAttribute("value"));

            if (dateValue.isBefore(to) && (dateValue.isAfter(from))) {
                NodeList barList = dateElement.getElementsByTagName("bar");
                for (int j = 0; j < barList.getLength(); j++) {
                    Element barElement = (Element) barList.item(j);
                    long barIdAttr = Long.parseLong(barElement.getAttribute("bar_id"));

                    if (barIdAttr == barId) {
                        Element incomeElement = (Element) barElement.getElementsByTagName("income");
                        BigDecimal income = new BigDecimal(incomeElement.getTextContent());
                        totalIncome.add(income);
                    }
                }
            }
        }

        return totalIncome;
    }

    @Override
    public double getAlcohol(LocalDate from, LocalDate to, long barId) {
        //ReadDatabase readDatabase = new ReadDatabase();
        //Document document = readDatabase.read("admin");
        double totalAlcohol = 0.0;

        NodeList dateList = document.getElementsByTagName("date");
        for (int i = 0; i < dateList.getLength(); i++) {
            Element dateElement = (Element) dateList.item(i);
            LocalDate dateValue = LocalDate.parse(dateElement.getAttribute("value"));

            if (dateValue.isBefore(to) && (dateValue.isAfter(from))) {
                NodeList barList = dateElement.getElementsByTagName("bar");
                for (int j = 0; j < barList.getLength(); j++) {
                    Element barElement = (Element) barList.item(j);
                    long barIdAttr = Long.parseLong(barElement.getAttribute("bar_id"));

                    if (barIdAttr == barId) {
                        Element alcoholElement = (Element) barElement.getElementsByTagName("alcohol");
                        double alcohol = Double.parseDouble(alcoholElement.getTextContent());
                        totalAlcohol += alcohol;
                    }
                }
            }
        }

        return totalAlcohol;
    }

    @Override
    public Map<String, Integer> getMostSoldDrinks(LocalDate from, LocalDate to, long barId) {
        //ReadDatabase readDatabase = new ReadDatabase();
        //Document document = readDatabase.read("admin");
        Map<String, Integer> drinkMap = new TreeMap<>();

        NodeList dateList = document.getElementsByTagName("date");
        for (int i = 0; i < dateList.getLength(); i++) {
            Element dateElement = (Element) dateList.item(i);
            LocalDate dateValue = LocalDate.parse(dateElement.getAttribute("value"));

            if (dateValue.isBefore(to) && (dateValue.isAfter(from))) {
                NodeList barList = dateElement.getElementsByTagName("bar");
                for (int j = 0; j < barList.getLength(); j++) {
                    Element barElement = (Element) barList.item(j);
                    long barIdAttr = Long.parseLong(barElement.getAttribute("bar_id"));

                    if (barIdAttr == barId) {
                        NodeList drinkNodeList = barElement.getElementsByTagName("drink");
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

    @Override
    public Map<String, Double> getMostUsedIngredients(LocalDate from, LocalDate to, long barId) {
        //ReadDatabase readDatabase = new ReadDatabase();
        //Document document = readDatabase.read("admin");
        Map<String, Double> ingredientMap = new TreeMap<>();

        NodeList dateList = document.getElementsByTagName("date");
        for (int i = 0; i < dateList.getLength(); i++) {
            Element dateElement = (Element) dateList.item(i);
            LocalDate dateValue = LocalDate.parse(dateElement.getAttribute("value"));

            if (dateValue.isBefore(to) && (dateValue.isAfter(from))) {
                NodeList barList = dateElement.getElementsByTagName("bar");
                for (int j = 0; j < barList.getLength(); j++) {
                    Element barElement = (Element) barList.item(j);
                    long barIdAttr = Long.parseLong(barElement.getAttribute("bar_id"));

                    if (barIdAttr == barId) {
                        NodeList IngredientNodeList = barElement.getElementsByTagName("drink");
                        for (int k = 0; k < IngredientNodeList.getLength(); k++) {
                            Element ingredientElement = (Element) IngredientNodeList.item(k);
                            double ingredientAmount = Double.parseDouble(ingredientElement.getAttribute("amount"));
                            int ingredientUsed = Integer.parseInt(ingredientElement.getTextContent());

                            if (ingredientMap.containsKey(ingredientElement.getAttribute("name"))) {
                                double sold = ingredientMap.get(ingredientElement.getAttribute("name"));
                                sold += (ingredientAmount * ingredientUsed);

                                ingredientMap.put(ingredientElement.getAttribute("name"), sold);
                            } else {
                                ingredientMap.put(ingredientElement.getAttribute("name"),
                                                  ingredientAmount * ingredientUsed);
                            }
                        }
                    }
                }
            }
        }

        return ingredientMap;
    }

    @Override
    public List<Bar> getAdminsBars() {
        //ReadDatabase readDatabase = new ReadDatabase();
        //Document document = readDatabase.read("bar");
        List<Bar> barArrayList = new ArrayList<>();
        UserInformation userInfo = new UserInformationImpl();

        if (userInfo.isCurrentUserAdmin()) {
            NodeList barNodeList = document.getElementsByTagName("bar");
            for (int i = 0; i < barNodeList.getLength(); i++) {
                Element barElement = (Element) barNodeList.item(i);
                long ownerId = Long.parseLong(barElement.getAttribute("owner_id"));

                if (ownerId == userInfo.getCurrentUserId()) {
                    NodeList barProperties = barElement.getChildNodes();

                    String name = barProperties.item(0).getTextContent();
                    String info = barProperties.item(1).getTextContent();
                    long id = Long.parseLong(barProperties.item(2).getTextContent());

                    barArrayList.add(new Bar(name, info, id));
                }
            }
        } else {
            return null;
        }

        return barArrayList;
    }
}
