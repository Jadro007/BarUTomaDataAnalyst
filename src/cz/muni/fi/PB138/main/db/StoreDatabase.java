package cz.muni.fi.PB138.main.db;

import org.exist.xmldb.DatabaseInstanceManager;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import java.io.File;

/**
 * BarUTomaDataAnalyst
 */
public class StoreDatabase {

    private static String URI = "xmldb:exist:///db";

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
     * Store to database
     * @param collectionName collection name want I want to read
     */
    public void store(String collectionName) {
        Collection col = null;
        XMLResource xmlResource = null;
        Collection parent = null;
        try {
            parent = DatabaseManager.getCollection(URI, "admin", "");
            CollectionManagementService managementService = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
            col = managementService.createCollection("first"); //col = mgt.createCollection(collectionName);

            // create new XMLResource; an id will be assigned to the new resource
            xmlResource = (XMLResource)col.createResource(null, "XMLResource");
            File f = new File(System.getProperty("user.dir") + "\\database\\user-example.xml"); //todo change to input stream
            if(!f.canRead()) {
                System.out.println("cannot read file " + System.getProperty("user.dir") + "\\database\\user-example.xml");
                return;
            }

            xmlResource.setContent(f);
            System.out.print("storing document " + xmlResource.getId() + "...");
            col.storeResource(xmlResource);
            System.out.println("ok.");
        } catch (XMLDBException e) {
            e.printStackTrace();
        } finally {
            //dont forget to cleanup
            if(xmlResource != null) {
                try {
                    ((EXistResource)xmlResource).freeResources();
                } catch(XMLDBException xe) {
                    xe.printStackTrace();
                }
            }

            if(col != null) {
                try {
                    col.close();
                } catch(XMLDBException xe) {
                    xe.printStackTrace();
                }
            }

            destroyDB(col);
        }
    }
}