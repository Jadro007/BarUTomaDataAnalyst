package communication;

import cz.muni.fi.PB138.main.communication.GetFileFromURL;
import cz.muni.fi.PB138.main.communication.ParserBarDrink;
import cz.muni.fi.PB138.main.entities.Drink;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ParserBarDrinkTest extends TestCase{

    private final String url = "http://barutoma.azurewebsites.net/bar/1/drink";
    /*{
  "ContentEncoding": null,
  "ContentType": null,
  "Data": [
    {
      "Bar": {"Address": {"AddressId": 16, "StreetWithNumber": "Botanicka 68a", "City": "Brno", "PostCode": "61200", "Country": "CZ" }, "BarType": { "BarTypeId": 16, "Name": "Custom" }, "BarId": 1, "Name": "myBar1", "Info": "muhehe"},
      "Drink": {"IngredientsUsed": [], "Price": {"Unit": {"UnitId": 1, "Name": "Koruna ceska", "Code": "Kc", "MultiplierToBase": 1.0}, "QuantityId": 2, "Amount": 0.00}, "DrinkId": 1, "Name": "MojDrink", "Info": "Toto si fakt dajte!"},
      "Price": { "Unit": { "UnitId": 1, "Name": "Koruna ceska", "Code": "Kc", "MultiplierToBase": 1.0}, "QuantityId": 1, "Amount": 13.00},
      "DrinkBarId": 1,
      "Info": "Moj super drink"
    }
  ],
  "JsonRequestBehavior": 1,
  "MaxJsonLength": null,
  "RecursionLimit": null
}*/

    @Test
    public void testParse() throws Exception {

        GetFileFromURL getFileFromURL = new GetFileFromURL();
        ParserBarDrink parserBarDrink = new ParserBarDrink();
        String json = getFileFromURL.downloadFile(url);
        List<Drink> actualList = parserBarDrink.parse(json);

        Drink drink1 = new Drink("MojDrink", new BigDecimal(13.00), 0.0);

        Assert.assertTrue(actualList.size()==1);
        Assert.assertEquals(actualList.get(0).getName(), drink1.getName());
        Assert.assertTrue(actualList.get(0).getPrice().compareTo(drink1.getPrice()) == 0);

    }
}