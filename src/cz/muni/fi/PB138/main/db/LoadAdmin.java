package cz.muni.fi.PB138.main.db;

import cz.muni.fi.PB138.main.entities.Bar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by Martina on 14.5.2015.
 */
public interface LoadAdmin{
    /**
     * Gets income of bar with barId in specified date range
     * @param from date
     * @param to date
     * @param barId id
     * @return total income
     */
    BigDecimal getIncome(LocalDate from, LocalDate to, long barId);

    /**
     * Gets consumed pure alcohol of bar with barId in specified date range
     * @param from date
     * @param to date
     * @param barId id
     * @return total pure alcohol consumption
     */
    double getAlcohol(LocalDate from, LocalDate to, long barId);

    /**
     * Gets collection of all drinks associated with their purchased amount in specified date range for bar with barId
     * @param from date
     * @param to date
     * @param barId id
     * @return  map: key - name of drink, value - purchased amount
     */
    Map<String, Integer> getMostSoldDrinks(LocalDate from, LocalDate to, long barId);

    /**
     * Gets collection of all ingredients associated with their used amount in specified date range for bar with BarId
     * @param from date
     * @param to date
     * @param barId id
     * @return  map: key - name of ingredient, value - used amount
     */
    Map<String, Double> getMostUsedIngredients(LocalDate from, LocalDate to, long barId);

    /**
     * Returns all Bars owned by current admin
     * @return list of Bars
     */
    List<Bar> getAdminsBars();
}
