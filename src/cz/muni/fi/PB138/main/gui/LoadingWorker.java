package cz.muni.fi.PB138.main.gui;

import javax.swing.*;

/**
 * Created by Eva on 21.6.2015.
 */
public class LoadingWorker extends SwingWorker<Void, Integer> {
    private LoginWorker loginWorker;
    private JLabel loadingLabel;

    public LoadingWorker(LoginWorker loginWorker, JLabel loadingLabel) {
        this.loginWorker = loginWorker;
        this.loadingLabel = loadingLabel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        loadingLabel.setVisible(true);
        while (!loginWorker.isDone()) {
            loadingLabel.setText("Loading ");
            if (loginWorker.isDone()) break;
            Thread.sleep(1000);
            loadingLabel.setText("Loading .");
            if (loginWorker.isDone()) break;
            Thread.sleep(1000);
            loadingLabel.setText("Loading ..");
            if (loginWorker.isDone()) break;
            Thread.sleep(1000);
            loadingLabel.setText("Loading ...");
            if (loginWorker.isDone()) break;
            Thread.sleep(1000);
        }
        loadingLabel.setVisible(false);
        return null;
    }
}
