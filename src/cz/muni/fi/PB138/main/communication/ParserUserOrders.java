package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.db.UserInformation;
import cz.muni.fi.PB138.main.db.UserInformationImpl;
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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * All orders from bar for user
 * Rest link /bar/{barID}/order/?username={username} + TOKEN
 * @author Martina Minatova
 * @author Benjamin Varga
 * @version 20.6.2015
 */
public class ParserUserOrders implements Parser {

    private Logger logger = Logger.getLogger(ParserBarOrder.class.getName());

    /**
     * Parses json into list of all orders that logged user made at corresponding bar. Orders don't contain id of bar.
     * @param json JSON in format of string
     * @return list of orders
     */
    public List parse(String json) {
        UserInformation ui = new UserInformationImpl();
        JSONObject obj = new JSONObject(json);
        List<Order> orderList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        if (array == null) return orderList;

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

                //randomized alcohol
                Random random = new Random();
                double alcoholQuantity = Math.round(((random.nextDouble() % 1) * 10000) / 10000);

                Drink myDrink = new Drink(drinkName, price, alcoholQuantity);
                drinkList.add(myDrink);
            }

            String dateTime = array.getJSONObject(i).optString("DateTime");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            BigDecimal totalPrice = BigDecimal.ZERO;
            for (Drink drink : drinkList) {
                totalPrice = totalPrice.add(drink.getPrice());
            }
            long userId = ui.getCurrentUserId();
            Order order = new Order(LocalDate.parse(dateTime, dateTimeFormatter), totalPrice, 0 , userId, drinkList);
            orderList.add(order);
        }
        return orderList;
    }
    /**
     * Parses json into list of all orders that logged user made at corresponding bar. Orders are linked with bar id.
     * @param json JSON in format of string
     * @param barId id of bar
     * @return list of orders
     */
    public List parse(String json, long barId) {
        List<Order> list = parse(json);
        for(Order order: list) {
            order.setBarID(barId);
        }
        return list;
    }
}
