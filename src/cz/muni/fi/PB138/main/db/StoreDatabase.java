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
import java.io.IOException;
import java.io.InputStream;

/**
 * BarUTomaDataAnalyst
 * @author Benjamin Varga
 * @author Martina Minatova
 * @version 17.6.2015
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (XMLDBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Store XML document to Database
     * @param collectionName collection name user or admin or bar
     * @param document input stream contain XML document
     */
    public void store(String collectionName, InputStream document) {
        init();
        Collection col = null;
        XMLResource xmlResource = null;
        Collection parent;
        try {
            parent = DatabaseManager.getCollection(URI, "admin", "");
            CollectionManagementService managementService = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
            col = managementService.createCollection(collectionName);

            // create new XMLResource; an id will be assigned to the new resource
            xmlResource = (XMLResource)col.createResource(null, "XMLResource");

            //transform inputstream to string
            String data = "";
            try {
                int size = document.available();
                char[] theChars = new char[size];
                byte[] bytes    = new byte[size];
                document.read(bytes, 0, size);
                for (int i = 0; i < size;) {
                    theChars[i] = (char) (bytes[i++] & 0xff);
                }
                data = new String(theChars);
            } catch (IOException e) {
                e.printStackTrace();
            }

            xmlResource.setContent(data);
            System.out.print("storing document " + xmlResource.getId() + "...");
            col.storeResource(xmlResource);
            System.out.println("is successful.");
        } catch (XMLDBException e) {
            e.printStackTrace();
        } finally {
            //don't forget to cleanup
            if(xmlResource != null) {
                try {
                    ((EXistResource)xmlResource).freeResources();
                } catch(XMLDBException xe) { xe.printStackTrace();}
            }

            if(col != null) {
                try {
                    col.close();
                } catch(XMLDBException xe) { xe.printStackTrace();}
            }
            //Shutdown Database
            try {
                DatabaseInstanceManager instanceManager = (DatabaseInstanceManager) col.getService("DatabaseInstanceManager", "1.0");
                instanceManager.shutdown();
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Delete database data
     */
    public void deleteDatabaseData() {
        File databaseDirectory = new File(System.getProperty("user.dir") + "\\database\\data");
        if (databaseDirectory.isDirectory()){
            File[] files = databaseDirectory.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files){
                    removeDirectory(file);
                }
            }
        }
    }

    /**
     * Recursive remove directory
     * @param dir directory path
     */
    private void removeDirectory(File dir) {
        if (dir.isDirectory()){
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    removeDirectory(file);
                }
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }
}