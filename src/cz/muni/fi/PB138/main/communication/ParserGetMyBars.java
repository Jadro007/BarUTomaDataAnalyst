package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.entities.Bar;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * BarUTomaDataAnalyst
 */
public class ParserGetMyBars implements Parser {

    public List<Bar> parse(String json) {

        JSONObject obj = new JSONObject(json);
        List<Bar> barList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        for (int i = 0; i < array.length(); i++)
        {
            int id = array.getJSONObject(i).getInt("BarId");
            String name = array.getJSONObject(i).getString("Name");
            String info = array.getJSONObject(i).getString("Info");
            Bar bar = new Bar(name, info, id);
            barList.add(bar);
        }

        return barList;
    }

    public boolean save(List list) {
        return false;
    }
}
