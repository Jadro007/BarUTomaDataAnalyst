package cz.muni.fi.PB138.main.gui;

import javax.swing.*;

/**
 * Class continues visual loading while logging in is proceeding.
 * Created by Eva on 21.6.2015.
 */
public class LoadingWorker extends SwingWorker<Void, Integer> {
    //LoginWorker for which job is waited
    private LoginWorker loginWorker;
    //Label where loading is proceeding
    private JLabel loadingLabel;

    /**
     * Constructor of the class.
     * @param loginWorker
     * @param loadingLabel
     */
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
