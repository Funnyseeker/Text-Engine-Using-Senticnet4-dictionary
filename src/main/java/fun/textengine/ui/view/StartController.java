package fun.textengine.ui.view;

import fun.textengine.core.ConceptMatcher;
import fun.textengine.core.MapDictionaryMatcher;
import fun.textengine.core.SQLDictionaryMatcher;
import fun.textengine.core.TextEngineSolver;
import fun.textengine.core.importers.Senticnet4RdfImporter;
import fun.textengine.ui.MainFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.net.URL;

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
        ConceptMatcher matcher;
        if (modeChoiceBox.getSelectionModel().selectedIndexProperty().get() == 0) {
            URL documentUrl = ClassLoader.getSystemClassLoader().getResource("senticnet4/senticnet4.rdf.xml");
            try {
                Senticnet4RdfImporter.parse(documentUrl);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Invalid Field");
                alert.setHeaderText("Please correct invalid field");
                alert.setContentText(e.getMessage());

                alert.showAndWait();
            }
            matcher = new MapDictionaryMatcher();
        } else {
            matcher = new SQLDictionaryMatcher();
        }
        AddTextController.setTextEngineSolver(new TextEngineSolver(matcher));
        mainApp.initRootLayout();
    }

    public void setMainApp(MainFX mainApp) {
        this.mainApp = mainApp;
    }
}
