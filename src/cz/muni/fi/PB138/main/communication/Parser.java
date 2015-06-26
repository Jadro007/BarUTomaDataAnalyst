package cz.muni.fi.PB138.main.communication;

import java.util.List;

/**
 * BarUTomaDataAnalyst
 */
public interface Parser {
    /**
     * Parser received json into list of entities based on class
     * @param json JSON in format of string
     * @return list of objects
     */
    List parse(String json);

}
