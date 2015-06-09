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

        //Todo OptJSONArray vrati null ked neexistuje kluc alebo neni typu JSONArray
        //Todo GetJSONArray vyhodi v˝nimku JSONException ked neexistuje kluc alebo neni typu JSONArray
        //Todo tak podobne funguju dalsie metody opt% get%, treba sa rozhodnut Ëi budeme pracovaù s v˝nimkami alebo nie

        JSONObject obj = new JSONObject(json);
        List<Bar> barList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        for (int i = 0; i < array.length(); i++)
        {
            int id = array.getJSONObject(i).optInt("BarId");
            name = array.getJSONObject(i).optString("Name");
            info = array.getJSONObject(i).optString("Info");
            Bar bar = new Bar(name, info, id);
            barList.add(bar);
        }

        return barList;
    }

    public boolean save(List list) {
        return false;
    }
}
