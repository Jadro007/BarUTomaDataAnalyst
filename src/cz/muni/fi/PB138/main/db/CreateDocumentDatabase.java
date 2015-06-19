package cz.muni.fi.PB138.main.db;

import java.io.File;

import org.exist.xmldb.DatabaseInstanceManager;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
public class CreateDocumentDatabase {

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
        try {
            col = DatabaseManager.getCollection(URI + "//first", "admin", "");
            XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");

            xqs.setProperty("indent", "yes");
            // XQuery insert
            CompiledExpression compiled = xqs.compile("update insert" + "<head>bla</head>"
                    + "into //division[@did=\"production_zlin\"]");
            ResourceSet result = xqs.execute(compiled);
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

