package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.entities.Drink;
import cz.muni.fi.PB138.main.entities.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * BarUTomaDataAnalyst
 */
public class ParserUserOrders implements Parser {

    public List parse(String json, long barID, long userID) {
        //todo /bar/{barid}/order pod USER
        //todo vráti všetky objednavky pre uživatela,
        /*{
            "OrderDrinks": [
                {
                    "Drink": {
                        "IngredientsUsed": [],
                        "Price": {
                            "Unit": {
                                "UnitId": 67,
                                "Name": "Kè",
                                "Code": "Kè",
                                "MultiplierToBase": 0
                            },
                            "QuantityId": 19,
                            "Amount": 25
                        },
                        "DrinkId": 15,
                        "Name": "Captain+Cola",
                        "Info": "Very Good"
                    },
                    "Quantity": {
                        "Unit": {
                            "UnitId": 1,
                            "Name": "kus",
                            "Code": "ks",
                            "MultiplierToBase": 1
                        },
                        "QuantityId": 44,
                        "Amount": 1
                    },
                    "OrderDrinkId": 1
                }
            ],
            "OrderId": 9,
            "DateTime": "2015-06-16T20:56:42.867",
            "Place": "TestBar"
        },*/


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
                double drinkAmount = jsonPrice.getDouble("Amount");

                JSONObject quantity = orderDrinks.getJSONObject(j).getJSONObject("Quantity");
                double quantityAmount = quantity.getDouble("Amount");

                BigDecimal price = BigDecimal.valueOf(drinkAmount * quantityAmount);

                double alcoholQuantity = 0; //todo dorobit

                Drink myDrink = new Drink(drinkName, price, alcoholQuantity);
                drinkList.add(myDrink);
            }

            String dateTime = array.getJSONObject(i).optString("DateTime");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            BigDecimal totalPrice = BigDecimal.ZERO;
            for (Drink drink : drinkList) {
                totalPrice = totalPrice.add(drink.getPrice());
            }

            Order order = new Order(LocalDate.parse(dateTime, dateTimeFormatter), totalPrice, barID, userID, drinkList);
            orderList.add(order);
        }

        return orderList;
    }

    @Override
    public List parse(String json) {
        return null;
    }

    public boolean save(List list) {
        return false;
    }
}
