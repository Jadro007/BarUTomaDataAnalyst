package cz.muni.fi.PB138.main.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Created by Eva on 21.5.2015.
 */
public class LoginWindow {
    private JTextField usernameTextField;
    private JButton logInButton;
    private JPasswordField passwordField;
    private JPanel panel;
    private JLabel loadingLabel;
    private Frame frame;

    public LoginWindow() {
        logInButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            char[] password = passwordField.getPassword();
            if (username == null || username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Please enter your username", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password == null || password.length == 0) {
                JOptionPane.showMessageDialog(null, "Please enter your password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LoginWorker loginWorker = new LoginWorker(this.frame, username, password, loadingLabel, logInButton);
            loginWorker.execute();
            LoadingWorker loadingWorker = new LoadingWorker(loginWorker, loadingLabel);
            loadingWorker.execute();
        });
    }

    public static void main(String args[]) {
        createLoginWindow();
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public static void createLoginWindow() {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Log In");
            LoginWindow window = new LoginWindow();
            window.setFrame(frame);
            frame.setContentPane(window.panel);
            frame.setPreferredSize(new Dimension(250, 270));
            frame.setLocationRelativeTo(null);

            frame.pack();
            frame.setVisible(true);
        });
    }

}
