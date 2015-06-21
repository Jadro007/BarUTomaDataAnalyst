package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.communication.HTTPRequest;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Created by Eva on 21.6.2015.
 */
public class LoginWorker extends SwingWorker<Void, Integer> {

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
        if (!httpRequest.hasRegistered(username, String.valueOf(password))) {
            logInButton.setVisible(true);
            loadingLabel.setVisible(false);
            JOptionPane.showMessageDialog(null, "Incorrect combination of username and password.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        MainWindow.createMainWindow();
        loginFrame.setVisible(false);
        loginFrame.dispose();
        return null;
    }
}
