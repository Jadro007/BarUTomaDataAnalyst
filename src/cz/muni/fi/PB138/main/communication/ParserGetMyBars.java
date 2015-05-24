package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.entities.Bar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * BarUTomaDataAnalyst
 */
public class ParserGetMyBars implements Parser {

    public List<Bar> parse(String json) {

        String name = null, info = null;

        JSONObject obj = new JSONObject(json);
        List<Bar> barList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        for (int i = 0; i < array.length(); i++)
        {
            int id = array.getJSONObject(i).getInt("BarId");
            name = array.getJSONObject(i).getString("Name");
            try {
                info = array.getJSONObject(i).getString("Info");
            } catch (JSONException e) {
                System.err.println("Object \"Info\" is null.");
                info = null;
            }
            Bar bar = new Bar(name, info, id);
            barList.add(bar);
        }

        return barList;
    }

    public boolean save(List list) {
        return false;
    }
}
