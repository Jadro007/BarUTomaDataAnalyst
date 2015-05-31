package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.db.UserInformation;

/**
 * Class used for testing of GUI.
 *
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
}
