package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.communication.HTTPRequest;
import cz.muni.fi.PB138.main.communication.LoginProcedure;

import javax.swing.*;
import java.awt.*;

/**
 * Class used for authentication of the user.
 * If logging in is successful closes login window and opens new main window of app.
 * Created by Eva on 21.6.2015.
 */
public class LoginWorker extends SwingWorker<Void, Integer> {

    //frame of the login screen
    private Frame loginFrame;
    private String username;
    private char[] password;
    private JButton logInButton;
    private JLabel loadingLabel;

    public LoginWorker(Frame loginFrame, String username, char[] password, JLabel loadingLabel, JButton logInButton) {
        this.loginFrame = loginFrame;
        this.username = username;
        this.password = password;
        this.logInButton = logInButton;
        this.loadingLabel = loadingLabel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        logInButton.setVisible(false);
        HTTPRequest httpRequest = new HTTPRequest();
        String token = httpRequest.postRequestToken(username, String.valueOf(password));
        if (!httpRequest.hasRegistered(token)) {
            logInButton.setVisible(true);
            loadingLabel.setVisible(false);
            JOptionPane.showMessageDialog(null, "Incorrect combination of username and password.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        LoginProcedure lp = new LoginProcedure(username, token);
        lp.UpdateDatabase();
        MainWindow.createMainWindow();
        loginFrame.setVisible(false);
        loginFrame.dispose();
        return null;
    }
}
