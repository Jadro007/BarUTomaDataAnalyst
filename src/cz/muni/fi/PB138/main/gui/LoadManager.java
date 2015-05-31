package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.db.LoadAdmin;
import cz.muni.fi.PB138.main.db.LoadUser;
import cz.muni.fi.PB138.main.db.UserInformation;

/**
 * Created by Eva on 30.5.2015.
 */
public final class LoadManager {

    //TODO change theese before launching
    private static final LoadAdmin loadAdmin = new LoadAdminChunkImpl();
    private static final LoadUser loadUser = new LoadUserChunkImpl();
    private static final UserInformation userInformation = new UserInformationChunkImpl();

    public static LoadAdmin getLoadAdmin() {
        return loadAdmin;
    }

    public static LoadUser getLoadUser() {
        return loadUser;
    }

    public static UserInformation getUserInformation() {
        return userInformation;
    }
}
