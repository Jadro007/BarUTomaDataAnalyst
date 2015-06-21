package cz.muni.fi.PB138.main.communication;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.logging.Level;
        import java.util.logging.Logger;

/**
 * Created by Martina on 21.6.2015.
 */
public class ParserBar implements Parser {

    private static final Logger logger = Logger.getLogger(ParserBar.class.getName());

    @Override
    public List<Long> parse(String json) {

        JSONObject obj = new JSONObject(json);
        List<Long> idsList = new ArrayList<>();
        JSONArray array = obj.getJSONArray("Data");
        for (int i = 0; i < array.length(); i++) {
            try {
                long id = array.getJSONObject(i).getLong("BarId");
                idsList.add(id);
            } catch (JSONException ex) {
                logger.log(Level.SEVERE, "" + ex);
            }
        }
        return idsList;
    }
}
