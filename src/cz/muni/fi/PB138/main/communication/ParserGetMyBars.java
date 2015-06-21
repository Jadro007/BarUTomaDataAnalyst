package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.entities.Bar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Rest link /bar/GetMyBars
 * @author Martina Minátová
 * @author Benjamin Varga
 * @version 20.6.2015
 */
public class ParserGetMyBars implements Parser {

    private static final Logger logger = Logger.getLogger(ParserGetMyBars.class.getName());

    public List<Bar> parse(String json) {
        String name, info;

        JSONObject obj = new JSONObject(json);
        List<Bar> barList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        for (int i = 0; i < array.length(); i++) {
            int id = -1;
            try {
                id = array.getJSONObject(i).getInt("BarId");
            } catch (JSONException ex) {
                logger.log(Level.SEVERE, "BarId is null.", ex);
            }
            name = array.getJSONObject(i).optString("Name");
            info = array.getJSONObject(i).optString("Info");
            Bar bar = new Bar(name, info, id);
            barList.add(bar);
        }
        return barList;
    }
}
