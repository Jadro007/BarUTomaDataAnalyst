package cz.muni.fi.PB138.main.db;

import org.exist.xmldb.DatabaseInstanceManager;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

/**
 * BarUTomaDataAnalyst
 */
public class ReadDatabase {

    /**
     * Initialize driver
     */
    private void init() {
        try {
            Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database database = (Database) cl.newInstance();
            database.setProperty("create-database", "true");
            database.setProperty("configuration", System.getProperty("user.dir") + "\\database\\conf.xml");
            DatabaseManager.registerDatabase(database);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (XMLDBException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read from database
     * @param collectionName collection name want I want to read
     * @return XML document
     */
    public Document read(String collectionName) {
        init();

        // try to read collection
        try {
            Collection collection = DatabaseManager.getCollection("xmldb:exist:///db//" + collectionName, "admin", "");
            if (collection == null) return null;
            String resources[] = collection.listResources();
            if (resources == null || resources.length == 0) return null;
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < resources.length; i++) {
                //we cant easily transform into string because it will contain multiple roots
                //it's ugly but it works
                if (i == 0) {
                    builder.append(collection.getResource(resources[i]).getContent());
                }
                else {
                    builder.replace(0, builder.indexOf(".xsd\">") + ".xsd\">".length(),
                            collection.getResource(resources[i]).getContent().toString().replace("</data>", ""));
                }
            }
            String data = builder.toString();
            CreateDocument createDocument = new CreateDocument();
            Document document = createDocument.transformToXML(data);

            // shut down the database
            try {
                DatabaseInstanceManager instanceManager =
                        (DatabaseInstanceManager) collection.getService("DatabaseInstanceManager", "1.0");
                instanceManager.shutdown();
            } catch (XMLDBException e) {
                e.printStackTrace();
            }

            return document;
        } catch (XMLDBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
