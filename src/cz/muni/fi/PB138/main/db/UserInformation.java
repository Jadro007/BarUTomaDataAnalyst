package cz.muni.fi.PB138.main.db;

import java.time.LocalDate;

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

    /**
     * Get last time we updated database, should be used to get rid of duplicates
     * @return time of last update, if info in config missing returns LocalDate.MIN
     */
    LocalDate getCurrentUserLastTimeOfUpdate();

    /**
     * Saves information about logged user into config, used directly after login procedure
     * @param id userId
     * @param isAdmin true if user role is admin
     */
    void saveCurrentUserInformation(long id, boolean isAdmin);

    /**
     * Always has to be preceded by saveCurrentUserInformation, saves today as the last day of update
     */
    void saveCurrentUserLastTimeOfUpdate();
}
