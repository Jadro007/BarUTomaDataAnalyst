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

    public void destroyDB(Collection collection) {
        try {
            DatabaseInstanceManager instanceManager =
                    (DatabaseInstanceManager) collection.getService("DatabaseInstanceManager", "1.0");
            instanceManager.shutdown();
        } catch (XMLDBException e) {
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
            String resources[] = collection.listResources();

            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < resources.length; i++) {
                builder.append(collection.getResource(resources[i]).getContent());
            }
            String data = builder.toString();
            CreateDocument createDocument = new CreateDocument();
            Document document = createDocument.transformToXML(data);

            // shut down the database
            destroyDB(collection);

            return document;
        } catch (XMLDBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
