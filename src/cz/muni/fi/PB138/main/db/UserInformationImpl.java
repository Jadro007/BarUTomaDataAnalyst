package cz.muni.fi.PB138.main.db;

import java.io.*;
import java.time.LocalDate;
import java.util.Properties;

/**
 * Created by Tomáš on 17.6.2015.
 */
public class UserInformationImpl implements UserInformation {

    public static final String CONFIG = System.getProperty("user.dir") + "/resources/config.properties";
    public static final String USER_ID = "user_id";
    public static final String ROLE = "role";
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String ID_PREFIX = "id_";
    public static final String NAME_PREFIX = "name_";
    public static final String LAST_ID = "last_id";

    /**
     * Gets id of logged user
     * @return id
     */
    @Override
    public long getCurrentUserId() {
        Properties properties = new Properties();
        InputStream input = null;
        long userId = 0l;

        try {
            input = new FileInputStream(CONFIG);
            properties.load(input);
            String user = properties.getProperty(USER_ID);
            userId = Long.parseLong(user);
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

    /**
     * Checks if logged user is admin
     * @return true if admin
     */
    @Override
    public boolean isCurrentUserAdmin() {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(CONFIG);
            properties.load(input);

            if (properties.getProperty(ROLE).equals(ADMIN)) return true;
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

    /**
     * Fetches from config file last time logged user updated database
     * @return date of last update
     */
    @Override
    public LocalDate getCurrentUserLastTimeOfUpdate() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(CONFIG);
            properties.load(input);

            long userId = Long.parseLong(properties.getProperty(USER_ID));
            String date = properties.getProperty(ID_PREFIX + userId);

            if (date == null) return LocalDate.MIN;
            else return LocalDate.parse(date);

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

        return null;
    }

    /**
     * Saves into config file information about logged user - userRole and his id
     * @param id userId
     * @param isAdmin true if user role is admin
     */
    @Override
    public void saveCurrentUserInformation(long id, boolean isAdmin) {
        Properties properties = new Properties();
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(CONFIG);
            properties.load(input);

            properties.setProperty(USER_ID, String.valueOf(id));
            if (isAdmin) properties.setProperty(ROLE, ADMIN);
            else properties.setProperty(ROLE, USER);

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

        try {
            output = new FileOutputStream(CONFIG);
            properties.store(output, "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Saves today as last time logged user had his database updated into config file
     */
    @Override
    public void saveCurrentUserLastTimeOfUpdate() {
        Properties properties = new Properties();
        InputStream input = null;
        OutputStream output = null;
        try {
            LocalDate currentDate = LocalDate.now();
            input = new FileInputStream(CONFIG);
            properties.load(input);
            long userId = Long.parseLong(properties.getProperty(USER_ID));
            properties.setProperty(ID_PREFIX + userId, currentDate.toString());
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

        try {
            output = new FileOutputStream(CONFIG);
            properties.store(output, "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Fetches id linked with user name from config file, or if doesn't exist yet, generate new unique one
     * @param name user name
     * @return id linked with name
     */
    @Override
    public long getUserId(String name) {
        Properties properties = new Properties();
        InputStream input = null;
        OutputStream output = null;
        long userId = -1;
        try {
            input = new FileInputStream(CONFIG);
            properties.load(input);
            String id = properties.getProperty(NAME_PREFIX + name);
            if (id == null) {
                String lastIdString = properties.getProperty(LAST_ID);
                long lastId;
                if (lastIdString == null) lastId = 1;
                else lastId = Long.parseLong(lastIdString);
                userId = lastId + 1;
                properties.setProperty(NAME_PREFIX + name, String.valueOf(userId));
                properties.setProperty(LAST_ID, String.valueOf(userId));
            }
            else {
                userId = Long.parseLong(id);
            }
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

        try {
            output = new FileOutputStream(CONFIG);
            properties.store(output, "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return userId;
    }

}
