package communication;

import cz.muni.fi.PB138.main.communication.ParserBarDrink;
import cz.muni.fi.PB138.main.entities.Drink;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ParserBarDrinkTest extends TestCase{

    @Test
    public void testParse() throws Exception {

        String json = "{\"ContentEncoding\":null,\"ContentType\":null,\"Data\":[{\"Bar\":{\"Address\":{\"AddressId\":1,\"StreetWithNumber\":\"Botanicka 68a\",\"City\":\"Brno\",\"PostCode\":\"61200\",\"Country\":\"CZ\"},\"BarType\":{\"BarTypeId\":1,\"Name\":\"Custom\"},\"BarId\":1,\"Name\":\"myBar1\",\"Info\":null},\"Drink\":{\"IngredientsUsed\":[],\"Price\":{\"Unit\":{\"UnitId\":1,\"Name\":\"Koruna ceska\",\"Code\":\"Kc\",\"MultiplierToBase\":1},\"QuantityId\":2,\"Amount\":0},\"DrinkId\":1,\"Name\":\"MojDrink\",\"Info\":\"Toto si fakt dajte!\"},\"Price\":{\"Unit\":{\"UnitId\":1,\"Name\":\"Koruna ceska\",\"Code\":\"Kc\",\"MultiplierToBase\":1},\"QuantityId\":1,\"Amount\":13},\"DrinkBarId\":1,\"Info\":\"Moj super drink\"}],\"JsonRequestBehavior\":1,\"MaxJsonLength\":null,\"RecursionLimit\":null}";
        Drink expectedDrink = new Drink("MojDrink", new BigDecimal(13.00), 0.0);
        ParserBarDrink parserBarDrink = new ParserBarDrink();
        List<Drink> list = parserBarDrink.parse(json);
        for (Drink drink: list) {
            Assert.assertEquals(expectedDrink.getName(), drink.getName());
            Assert.assertTrue(expectedDrink.getAlcoholQuantity() == drink.getAlcoholQuantity());
            Assert.assertTrue(expectedDrink.getPrice().compareTo(drink.getPrice()) == 0);
        }
    }
}