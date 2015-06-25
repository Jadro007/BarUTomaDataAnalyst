package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.db.LoadAdmin;
import cz.muni.fi.PB138.main.entities.Bar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class used for testing of GUI.
 * Created by Eva on 22.5.2015.
 */
public class LoadAdminChunkImpl implements LoadAdmin {

    @Override
    public BigDecimal getIncome(LocalDate from, LocalDate to, long barId) {
        return new BigDecimal(barId*12457*Math.abs(Math.sin(barId / 3.35 * Math.PI * from.getDayOfMonth() *to.getMonthValue())) + 5000 );
    }

    @Override
    public double getAlcohol(LocalDate from, LocalDate to, long barId) {
        return barId*23.5*Math.abs(Math.sin(barId / 4.28 * Math.PI * to.getDayOfMonth() * from.getMonthValue())) + 6;
    }

    @Override
    public Map<String, Integer> getMostSoldDrinks(LocalDate from, LocalDate to, long barId) {
        Map<String, Integer> drinksMap = new TreeMap<>();
        switch ((int)barId) {
            case 1:
                drinksMap.put("Vodka with Juice", 10*from.getDayOfMonth());
                drinksMap.put("Rum with Coke", 20*to.getDayOfMonth());
                drinksMap.put("Gin with Tonic", 6*from.getDayOfMonth());
                drinksMap.put("Jägermeister", 7*to.getDayOfMonth());
                break;
            case 2:
                drinksMap.put("Beer", from.getDayOfMonth()*6);
                drinksMap.put("Wine", to.getDayOfMonth()*5);
                drinksMap.put("Vodka with Juice", from.getMonthValue()*4);
                drinksMap.put("Tequila", to.getMonthValue()*2);
                break;
            case 3:
                drinksMap.put("Whisky", from.getDayOfMonth()*6);
                drinksMap.put("Wine", to.getDayOfMonth()*20);
                drinksMap.put("Water", from.getMonthValue()*3);
                drinksMap.put("Milk", to.getMonthValue()*30);
                break;
            case 4:
                drinksMap.put("Amaretto", from.getDayOfMonth()*4);
                drinksMap.put("Wine", to.getDayOfMonth()*5);
                drinksMap.put("Absinthe", from.getMonthValue()*8);
                drinksMap.put("Curacao Blue", to.getMonthValue()*2);
                drinksMap.put("Coke", 10*to.getMonthValue());
                drinksMap.put("Juice", 2*from.getDayOfMonth());
                break;
            case 5:
                drinksMap.put("Whisky", from.getDayOfMonth()*7);
                drinksMap.put("Cactus Wine", to.getDayOfMonth()*5);
                drinksMap.put("Beer", from.getMonthValue()*10);
                drinksMap.put("Lemonade", to.getMonthValue()*8);
                break;
            case 6:
                drinksMap.put("Beer", from.getDayOfMonth()*9);
                drinksMap.put("Wine", to.getDayOfMonth()*7);
                drinksMap.put("Vodka with Juice", from.getMonthValue()*8);
                drinksMap.put("Rum with Coke", to.getMonthValue()*9);
                break;
            case 7:
                drinksMap.put("Rum", 16*to.getMonthValue());
                break;
            case 8:
                drinksMap.put("Milk", 12*to.getMonthValue());
                break;
            case 9:
                drinksMap.put("Beer", 14*from.getDayOfMonth());
                break;
            case 10:
                drinksMap.put("Blood of our enemies", 8*from.getDayOfMonth());
                break;
            default:
                drinksMap.put("Water", 7*to.getMonthValue());
                break;
        }
        return drinksMap;
    }

    @Override
    public Map<String, Double> getMostUsedIngredients(LocalDate from, LocalDate to, long barId) {
        Map<String, Double> ingredientsMap = new TreeMap<>();
        switch ((int)barId) {
            case 1:
                ingredientsMap.put("Vodka", from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 5);
                ingredientsMap.put("Rum", to.getDayOfMonth()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 4);
                ingredientsMap.put("Gin", from.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)));
                ingredientsMap.put("Jägermeister", to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 3);
                ingredientsMap.put("Coke", 10*to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 6);
                ingredientsMap.put("Juice", 5*from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 7);
                ingredientsMap.put("Tonic", 2*from.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 3);
                break;
            case 2:
                ingredientsMap.put("Beer", from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 2);
                ingredientsMap.put("Wine", to.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 4);
                ingredientsMap.put("Vodka", from.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 7);
                ingredientsMap.put("Tequila", to.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 4);
                ingredientsMap.put("Coke", 8*to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 9);
                ingredientsMap.put("Juice", 4*from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 4);
                break;
            case 3:
                ingredientsMap.put("Whisky", from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 3);
                ingredientsMap.put("Wine", to.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 2);
                ingredientsMap.put("Water", from.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 1);
                ingredientsMap.put("Milk", to.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 8);
                ingredientsMap.put("Coke", 9*to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 9);
                break;
            case 4:
                ingredientsMap.put("Amaretto", from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 9);
                ingredientsMap.put("Wine", to.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 7);
                ingredientsMap.put("Absinthe", from.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 6);
                ingredientsMap.put("Curacao Blue", to.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 4);
                ingredientsMap.put("Coke", 10*to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 10);
                ingredientsMap.put("Juice", 2.7*from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 7);
                break;
            case 5:
                ingredientsMap.put("Whisky", from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 3);
                ingredientsMap.put("Cactus Wine", to.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 9);
                ingredientsMap.put("Beer", from.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 5);
                ingredientsMap.put("Lemonade", 10*to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 10);
                break;
            case 6:
                ingredientsMap.put("Beer", from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 6);
                ingredientsMap.put("Wine", to.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 9);
                ingredientsMap.put("Vodka", from.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 5);
                ingredientsMap.put("Rum", to.getMonthValue()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 3);
                ingredientsMap.put("Coke", 9.5*to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 4);
                ingredientsMap.put("Juice", 2.7*from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 7);
                break;
            case 7:
                ingredientsMap.put("Rum", 10*to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 10);
                break;
            case 8:
                ingredientsMap.put("Milk", 10*to.getMonthValue()*2.3*Math.abs(Math.cos(barId / 4.28 * Math.PI)) + 10);
                break;
            case 9:
                ingredientsMap.put("Beer", 15*from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId /4.28 *Math.PI)) + 4);
                break;
            case 10:
                ingredientsMap.put("Blood of our enemies", 2.7*from.getDayOfMonth()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 7);
                break;
            default:
                ingredientsMap.put("Water", 7.8*to.getMonthValue()*2.3*Math.abs(Math.sin(barId / 4.28 * Math.PI)) + 9);
                break;
        }
        return ingredientsMap;
    }

    @Override
    public List<Bar> getAdminsBars() {
        List<Bar> barList = new ArrayList<>();
        barList.add(new Bar("My Place", "Feels like a home.", 1));
        barList.add(new Bar("The Pour house", "Best drinks.", 2));
        barList.add(new Bar("The Recovery Room", "True recovery.", 3));
        barList.add(new Bar("The Wonder Bar", "You won't believe.", 4));
        barList.add(new Bar("West Saloon", "Just for rough guys", 5));
        barList.add(new Bar("The No Name Bar", "Most creative drinks.", 6));
        barList.add(new Bar("At Drunken Sailor", "Yohoho and the bottle of rum!", 7));
        barList.add(new Bar("The Cool Bar", "So cool you can't handle it.", 8));
        barList.add(new Bar("The Smelling Sock", "No sandals allowed.", 9));
        barList.add(new Bar("Ogre's Inn", "For green ones.", 10));
        return barList;
    }
}
