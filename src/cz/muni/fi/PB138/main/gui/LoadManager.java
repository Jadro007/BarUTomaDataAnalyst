package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.db.*;

/**
 * Created by Eva on 30.5.2015.
 */
public final class LoadManager {

    //TODO change these before launching
    private static final LoadAdmin loadAdmin = new LoadAdminImpl();
    private static final LoadUser loadUser = new LoadUserImpl();
    private static final UserInformation userInformation = new UserInformationImpl();

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
