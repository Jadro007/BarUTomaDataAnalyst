package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.db.*;

/**
 * Class provides implemented key interfaces of the project.
 * Created by Eva on 30.5.2015.
 */
public abstract class LoadManager {

    //data which only admin can access
    private static final LoadAdmin loadAdmin = new LoadAdminImpl();
    //data which every registered user can access
    private static final LoadUser loadUser = new LoadUserImpl();
    //basic information about the logged user
    private static final UserInformation userInformation = new UserInformationImpl();

    /**
     * Returns implementation of interface providing data which only admin can access.
     * @return loadAdmin implementation of interface providing data which only admin can access
     */
    public static LoadAdmin getLoadAdmin() {
        return loadAdmin;
    }

    /**
     * Returns implementation of interface providing data which every registered user can access.
     * @return loadUser implementation of interface providing data which every registered user can access
     */
    public static LoadUser getLoadUser() {
        return loadUser;
    }

    /**
     * Returns implementation of interface providing basic information about the logged user.
     * @return implementation of interface providing basic information about the logged user
     */
    public static UserInformation getUserInformation() {
        return userInformation;
    }

}
