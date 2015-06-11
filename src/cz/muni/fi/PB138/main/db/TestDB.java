package cz.muni.fi.PB138.main.db;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.exist.xmldb.DatabaseInstanceManager;

public class TestDB {
    public static void main(String args[]) throws Exception {
        // initialize driver
        Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
        Database database = (Database)cl.newInstance();
        database.setProperty("create-database", "true");
        database.setProperty("configuration", System.getProperty("user.dir") + "\\database\\conf.xml");
        DatabaseManager.registerDatabase(database);

        // try to read collection
        Collection col =
                DatabaseManager.getCollection("xmldb:exist:///db//first", "admin", "");
        String resources[] = col.listResources();
        System.out.println("Resources:");
        for (int i = 0; i < resources.length; i++) {
            //System.out.println(resources[i]);
            System.out.println(col.getResource(resources[i]).getContent());
        }

        /*StringBuilder builder = new StringBuilder();
        for(int k = 0; k < resources.length; k++) {
            builder.append(col.getResource(resources[k]).getContent());
        }
        String data = builder.toString();
        CreateDocument createDocument = new CreateDocument();
        createDocument.transformToXML(data);*/


        // shut down the database
        DatabaseInstanceManager manager = (DatabaseInstanceManager)
                col.getService("DatabaseInstanceManager", "1.0");
        manager.shutdown();
    }
}