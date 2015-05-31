package communication;

//import cz.muni.fi.PB138.main.communication.GetFileFromURL;
import cz.muni.fi.PB138.main.communication.HTTPRequest;
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

    @Test
    public void testParse() throws Exception {
        HTTPRequest httpRequest = new HTTPRequest();
        String json = httpRequest.getRequest("/bar");
        ParserGetMyBars parserGetMyBars = new ParserGetMyBars();
        List<Bar> actualList = parserGetMyBars.parse(json);

        Bar bar1 = new Bar("myBar1","",1);
        Bar bar2 = new Bar("myBar1", "", 2);
        Bar bar3 = new Bar("myBar1", "", 3);

        Assert.assertTrue(actualList.size() == 5);
        Assert.assertEquals(actualList.get(0).getName(), bar1.getName());
        //Assert.assertEquals(actualList.get(0).getInfo(), bar1.getInfo());
        Assert.assertEquals(actualList.get(0).getId(), bar1.getId());
        Assert.assertEquals(actualList.get(1).getName(), bar2.getName());
        //Assert.assertEquals(actualList.get(1).getInfo(), bar2.getInfo());
        Assert.assertEquals(actualList.get(1).getId(), bar2.getId());
        Assert.assertEquals(actualList.get(2).getName(), bar3.getName());
        //Assert.assertEquals(actualList.get(2).getInfo(), bar3.getInfo());
        Assert.assertEquals(actualList.get(2).getId(), bar3.getId());

    }
}
