package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.db.LoadUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class used for testing of GUI.
 *
 * Created by Eva on 22.5.2015.
 */
public class LoadUserChunkImpl implements LoadUser {

    @Override
    public BigDecimal getPayment(LocalDate from, LocalDate to) {
        return new BigDecimal(from.getDayOfMonth()*53*Math.abs(Math.sin(to.getMonthValue()/3.35*Math.PI)));
    }

    @Override
    public Map<String, Integer> getMostPurchasedDrinks(LocalDate from, LocalDate to) {
        Map<String, Integer> drinksMap = new TreeMap<>();
        drinksMap.put("Beer", from.getDayOfMonth()*5);
        drinksMap.put("Wine", to.getDayOfMonth()*4);
        drinksMap.put("Vodka with Juice", from.getMonthValue()*3);
        drinksMap.put("Tequila", to.getMonthValue()*2);
        drinksMap.put("Whisky", from.getDayOfMonth()*1);
        return drinksMap;
    }
}
