package communication;

import cz.muni.fi.PB138.main.communication.GetFileFromURL;
import cz.muni.fi.PB138.main.communication.ParserGetMyBars;
import cz.muni.fi.PB138.main.entities.Bar;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * BarUTomaDataAnalyst
 */
public class ParserGetMyBarsTest extends TestCase {

    private final String url = "http://barutoma.azurewebsites.net/bar";
    /*{
  "ContentEncoding": null,
  "ContentType": null,
  "Data": [{ "Address": {"AddressId": 16, "StreetWithNumber": "Botanicka 68a", "City": "Brno", "PostCode": "61200", "Country": "CZ"}, "BarType": {"BarTypeId": 16, "Name": "Custom"}, "BarId": 1, "Name": "myBar1", "Info": "muhehe"},
           { "Address": { "AddressId": 1002, "StreetWithNumber": "test", "City": "test", "PostCode": "test", "Country": "test"}, "BarType": {"BarTypeId": 1002, "Name": "Custom"}, "BarId": 5, "Name": "BlaBla", "Info": "omg omg"} ],
  "JsonRequestBehavior": 1,
  "MaxJsonLength": null,
  "RecursionLimit": null
}*/

    @Test
    public void testParse() throws Exception {

        GetFileFromURL getFileFromURL = new GetFileFromURL();
        ParserGetMyBars parserGetMyBars = new ParserGetMyBars();
        String json = getFileFromURL.downloadFile(url);
        List<Bar> actualList = parserGetMyBars.parse(json);

        Bar bar1 = new Bar("myBar1","muhehe",1);
        Bar bar2 = new Bar("BlaBla", "omg omg", 5);

        Assert.assertTrue(actualList.size()==2);
        Assert.assertEquals(actualList.get(0).getName(), bar1.getName());
        Assert.assertEquals(actualList.get(0).getInfo(), bar1.getInfo());
        Assert.assertEquals(actualList.get(0).getId(), bar1.getId());
        Assert.assertEquals(actualList.get(1).getName(), bar2.getName());
        Assert.assertEquals(actualList.get(1).getInfo(), bar2.getInfo());
        Assert.assertEquals(actualList.get(1).getId(), bar2.getId());

    }
}
