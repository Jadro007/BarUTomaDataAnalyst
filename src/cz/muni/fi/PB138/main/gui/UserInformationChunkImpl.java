package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.db.UserInformation;

import java.time.LocalDate;

/**
 * Class used for testing of GUI.
 * Created by Eva on 22.5.2015.
 */
public class UserInformationChunkImpl implements UserInformation{

    @Override
    public long getCurrentUserId() {
        return 11;
    }

    @Override
    public boolean isCurrentUserAdmin() {
        return true;
    }

    @Override
    public LocalDate getCurrentUserLastTimeOfUpdate() {
        return null;
    }

    @Override
    public void saveCurrentUserInformation(long id, boolean isAdmin) {   }

    @Override
    public void saveCurrentUserLastTimeOfUpdate() {   }

    @Override
    public long getUserId(String name) { return 0; }
}
