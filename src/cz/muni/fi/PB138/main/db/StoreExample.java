package cz.muni.fi.PB138.main.db;

import java.io.File;

import org.exist.xmldb.DatabaseInstanceManager;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
public class StoreExample {

    private static String URI = "xmldb:exist:///db";


    public static void main(String args[]) throws Exception {

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        database.setProperty("configuration", System.getProperty("user.dir") + "\\database\\conf.xml");
        DatabaseManager.registerDatabase(database);
        Collection col = null;
        XMLResource res = null;
        Collection parent = null;
        try {
            parent = DatabaseManager.getCollection(URI, "admin", "");
            CollectionManagementService mgt = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
            col = mgt.createCollection("first");

            //col = DatabaseManager.getCollection(URI, "admin", "");

            // create new XMLResource; an id will be assigned to the new resource
                    res = (XMLResource)col.createResource(null, "XMLResource");
            File f = new File(System.getProperty("user.dir") + "\\database\\user-example.xml");
            if(!f.canRead()) {
                System.out.println("cannot read file " + System.getProperty("user.dir") + "\\database\\user-example.xml");
                return;
            }

            res.setContent(f);
            System.out.print("storing document " + res.getId() + "...");
            col.storeResource(res);
            System.out.println("ok.");
        } finally {
            //dont forget to cleanup
            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            DatabaseInstanceManager manager = (DatabaseInstanceManager)
                    col.getService("DatabaseInstanceManager", "1.0");
            manager.shutdown();
        }
    }

    }

