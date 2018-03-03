package fun.textengine.ui.view;

import fun.textengine.ui.MainFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

/**
 * Created by Funnyseeker on 25.06.2017.
 */
public class StartController {
    private static String[] MODES = {"Resource intensive", "Low-cost"};
    private static String[] MODES_DESC = {"System requirements: 2Gb RAM or more", "System requirements: nothing"};
    @FXML
    private Text modeDescription;
    @FXML
    private ChoiceBox<String> modeChoiceBox;
    @FXML
    private Button runButton;

    private MainFX mainApp;

    public StartController() {
    }

    @FXML
    private void initialize() {
        modeChoiceBox.setItems(FXCollections.observableArrayList(MODES));
        modeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                modeDescription.setText(MODES_DESC[newValue.intValue()]);
            }
        });
        modeDescription.setText("");
    }

    @FXML
    private void handleRun() {
        mainApp.initRootLayout();
    }

    public void setMainApp(MainFX mainApp) {
        this.mainApp = mainApp;
    }
}
