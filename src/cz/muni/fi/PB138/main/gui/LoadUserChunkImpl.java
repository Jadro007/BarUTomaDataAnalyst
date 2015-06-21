package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.db.LoadUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class used for testing of GUI.
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

    @Override
    public Set<Long> getAllBars() {
        Set<Long> barSet = new HashSet<>();
        barSet.add(new Long(1));
        barSet.add(new Long(2));
        barSet.add(new Long(3));
        barSet.add(new Long(4));
        barSet.add(new Long(5));
        barSet.add(new Long(6));
        barSet.add(new Long(7));
        barSet.add(new Long(8));
        barSet.add(new Long(9));
        barSet.add(new Long(10));
        return barSet;
    }
}
