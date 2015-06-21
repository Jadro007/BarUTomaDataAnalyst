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

}
