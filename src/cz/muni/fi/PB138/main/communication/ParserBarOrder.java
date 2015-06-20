package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.entities.Drink;
import cz.muni.fi.PB138.main.entities.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * All orders from bar
 * Rest link /bar/{barID}/order
 * @author Martina Minátová
 * @author Benjamin Varga
 * @version 20.6.2015
 */
public class ParserBarOrder implements Parser{

    private Logger logger = Logger.getLogger(ParserBarOrder.class.getName());

    public List parse(String json) {
        JSONObject obj = new JSONObject(json);
        List<Order> orderList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        for (int i = 0; i < array.length(); i++) {
            JSONArray orderDrinks = array.getJSONObject(i).getJSONArray("OrderDrinks");

            List<Drink> drinkList = new ArrayList<>();
            for (int j = 0; j < orderDrinks.length(); j++) {
                JSONObject drink = orderDrinks.getJSONObject(j).getJSONObject("Drink");
                String drinkName = drink.optString("Name");

                JSONObject jsonPrice = drink.getJSONObject("Price");
                double drinkAmount = 0.0;
                try {
                    drinkAmount = jsonPrice.getDouble("Amount");
                } catch (JSONException ex) {
                    logger.log(Level.SEVERE, "Price Amount is null.", ex);
                }

                JSONObject quantity = orderDrinks.getJSONObject(j).getJSONObject("Quantity");
                double quantityAmount = 0.0;
                try {
                    quantityAmount = quantity.getDouble("Amount");
                } catch (JSONException ex) {
                    logger.log(Level.SEVERE, "Quantity Amount is null.", ex);
                }

                BigDecimal price = BigDecimal.valueOf(drinkAmount * quantityAmount);

                double alcoholQuantity = 0.0;
                try {
                    alcoholQuantity = 0;//todo dorobit rovnako ako parserBarDrink
                } catch (JSONException ex) {
                    logger.log(Level.SEVERE, "", ex);
                }

                Drink myDrink = new Drink(drinkName, price, alcoholQuantity);
                drinkList.add(myDrink);
            }

            String dateTime = array.getJSONObject(i).optString("DateTime");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            BigDecimal totalPrice = BigDecimal.ZERO;
            for (Drink drink : drinkList) {
                totalPrice = totalPrice.add(drink.getPrice());
            }

            Order order = new Order(LocalDate.parse(dateTime, dateTimeFormatter), totalPrice, 0 /*todo barID*/, 0 /*todo userID*/, drinkList);
            orderList.add(order);
        }
        return orderList;
    }

}
