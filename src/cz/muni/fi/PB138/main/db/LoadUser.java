package cz.muni.fi.PB138.main.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * Created by Martina on 14.5.2015.
 */
public interface LoadUser{
    /**
     * Gets payments in specified date range for current user
     * @param from date
     * @param to date
     * @return total payment
     */
    BigDecimal getPayment(LocalDate from, LocalDate to);

    /**
     * Gets collection of all drinks associated with their purchased amount in specified date range for current user
     * @param from date
     * @param to date
     * @return map: key - name of drink, value - purchased amount
     */
    Map<String, Integer> getMostPurchasedDrinks(LocalDate from, LocalDate to);

    Set<Long> getAllBars();

}
