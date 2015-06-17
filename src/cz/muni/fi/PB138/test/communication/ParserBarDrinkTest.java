package communication;

import cz.muni.fi.PB138.main.communication.HTTPRequest;
import cz.muni.fi.PB138.main.communication.ParserBarDrink;
import cz.muni.fi.PB138.main.entities.Drink;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ParserBarDrinkTest extends TestCase{

    @Test
    public void testParse() throws Exception {

        HTTPRequest httpRequest = new HTTPRequest();
        String json = httpRequest.getRequest("/bar/1/drink");
        ParserBarDrink parserBarDrink = new ParserBarDrink();
        List<Drink> actualList = parserBarDrink.parse(json);

        Drink drink1 = new Drink("MojDrink", new BigDecimal(13.00), 0.0);

        Assert.assertTrue(actualList.size()==1);
        Assert.assertEquals(actualList.get(0).getName(), drink1.getName());
        Assert.assertTrue(actualList.get(0).getPrice().compareTo(drink1.getPrice()) == 0);

    }
}