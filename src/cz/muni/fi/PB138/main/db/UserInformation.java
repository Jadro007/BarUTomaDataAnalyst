package cz.muni.fi.PB138.main.db;

/**
 * Created by Martina on 14.5.2015.
 */
public interface UserInformation {
    /**
     * Gets current user id from config file
     * @return user id
     */
    long getCurrentUserId();

    /**
     * Check if current user role is admin
     * @return true if admin
     */
    boolean isCurrentUserAdmin();
}
