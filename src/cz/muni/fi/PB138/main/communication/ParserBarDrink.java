package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.entities.Drink;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.*;

/**
 *
 * Rest link /bar/{barID}/drink
 * @author Martina Minátová
 * @author Benjamin Varga
 * @version 20.6.2015
 */
public class ParserBarDrink implements Parser {

    private static final Logger logger = Logger.getLogger(ParserBarDrink.class.getName());

    public List<Drink> parse(String json) {

        JSONObject obj = new JSONObject(json);
        List<Drink> drinkList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject jsonDrink = array.getJSONObject(i).getJSONObject("Drink");
            String name = jsonDrink.optString("Name");

            JSONObject jsonPrice = array.getJSONObject(i).getJSONObject("Price");

            BigDecimal price = BigDecimal.ZERO;
            try {
                price = BigDecimal.valueOf(jsonPrice.getDouble("Amount"));
            } catch (JSONException ex) {
                logger.log(Level.SEVERE, "Amount is null.", ex);
            }
            double multiplierToBase = 0.0;
            try {
                multiplierToBase = jsonPrice.getJSONObject("Unit").getDouble("MultiplierToBase");
            } catch (JSONException ex) {
                logger.log(Level.SEVERE, "MultiplierToBase is null.", ex);
            }
            price = price.multiply(BigDecimal.valueOf(multiplierToBase));

            /*JSONArray jsonIngredients = jsonDrink.getJSONArray("IngredientsUsed");
            for (int j = 0; j < jsonIngredients.length(); j++) {
                //TODO: Ingredients
            }*/

            double alcoholQuantity = 0.0;
            /*try {
                alcoholQuantity = 0;//todo dorobit alkohol ked budú ingrediencie
            } catch (JSONException ex) {
                logger.log(Level.SEVERE, "", ex);
            }*/

            Drink drink = new Drink(name, price, alcoholQuantity);
            drinkList.add(drink);
        }

        return drinkList;
    }

}
