package fun.textengine.ui.utils;

import fun.textengine.core.utils.ProgressTraker;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class ProgressTrackerImpl implements ProgressTraker {
    private static final double EPSILON = 0.000000001;
    private double currProgress = 0;
    private double progressPointWeight;
    private ProgressBar progressBar;

    public ProgressTrackerImpl(ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.progressBar.setProgress(0);
    }

    @Override
    public void setProgressPointWeight(double weight) {
        this.progressPointWeight = weight;
    }

    @Override
    public void addProgressPoints(double progressPoints) {
        currProgress += progressPoints * progressPointWeight;
        if (currProgress >= 1 - EPSILON) {
            currProgress = 1;
        }
        Platform.runLater(() -> progressBar.setProgress(currProgress));

    }
}
