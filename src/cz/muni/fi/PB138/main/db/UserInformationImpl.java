package cz.muni.fi.PB138.main.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Tomáš on 17.6.2015.
 */
public class UserInformationImpl implements UserInformation {
    // todo: vytvorit config.properties soubor

    @Override
    public long getCurrentUserId() {
        Properties properties = new Properties();
        InputStream input = null;
        long userId = 0l;

        try {
            input = new FileInputStream("config.properties");
            properties.load(input);

            userId = Long.parseLong(properties.getProperty("user_id"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return userId;
    }

    @Override
    public boolean isCurrentUserAdmin() {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            properties.load(input);

            if (properties.getProperty("role").equals("admin")) return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
