package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.entities.Drink;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

/**
 * BarUTomaDataAnalyst
 */
public class ParserBarDrink implements Parser {



    public List<Drink> parse(String json) {

        JSONObject obj = new JSONObject(json);
        List<Drink> drinkList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject jsonDrink = array.getJSONObject(i).getJSONObject("Drink");
            String name = jsonDrink.getString("Name");

            JSONObject jsonPrice = array.getJSONObject(i).getJSONObject("Price");

            BigDecimal price = BigDecimal.valueOf(jsonPrice.getDouble("Amount"));
            double multiplierToBase = jsonPrice.getJSONObject("Unit").getDouble("MultiplierToBase");
            price = price.multiply(BigDecimal.valueOf(multiplierToBase));

            JSONArray jsonIngredients = jsonDrink.getJSONArray("IngredientsUsed");
            double alcoholQuantity = 0;
            for (int j = 0; j < jsonIngredients.length(); j++) {
                //TODO: Ingredients
            }
            Drink drink = new Drink(name, price, alcoholQuantity);
            drinkList.add(drink);
        }

        return drinkList;
    }

    public boolean save(List list) {
        return false;
    }
}
