package fun.textengine.ui.view;

import fun.textengine.ui.MainFX;
import fun.textengine.ui.model.TextEngineInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * Created by Funnyseeker on 21.05.2017.
 */
public class TextEngineOverviewController {
    @FXML
    private TableView<TextEngineInfo> engineTable;
    @FXML
    private TableColumn<TextEngineInfo, Integer> idColumn;
    @FXML
    private TableColumn<TextEngineInfo, String> textColumn;
    @FXML
    private TableColumn<TextEngineInfo, Float> intensityColumn;
    @FXML
    private TableColumn<TextEngineInfo, String> polarityColumn;

    @FXML
    private TextArea textArea;
    @FXML
    private Label intensityLabel;
    @FXML
    private Label polarityLabel;
    @FXML
    private TextArea negativeGroup;
    @FXML
    private TextArea positiveGroup;
    @FXML
    private Label positiveCounter;
    @FXML
    private Label negativeCounter;


    // Ссылка на главное приложение.
    private MainFX mainApp;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public TextEngineOverviewController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        textColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());
        intensityColumn.setCellValueFactory(cellData -> cellData.getValue().intensityProperty().asObject());
        polarityColumn.setCellValueFactory(cellData -> cellData.getValue().polarityProperty());

        showTextInfo(null);

        engineTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTextInfo(newValue));
    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param mainApp
     */
    public void setMainApp(MainFX mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        engineTable.setItems(mainApp.getData());
    }

    @FXML
    private void showTextInfo(TextEngineInfo engineInfo) {
        if (engineInfo != null) {
            textArea.setText(engineInfo.getText());
            intensityLabel.setText(String.valueOf(engineInfo.getIntensity()));
            polarityLabel.setText(engineInfo.getPolarity());
            positiveGroup.setText(engineInfo.getPositiveGroup());
            negativeGroup.setText(engineInfo.getNegativeGroup());
            int negCount = engineInfo.getNegativeGroup().split(";").length - 1;
            negativeCounter.setText(String.valueOf(negCount));
            int posCount = engineInfo.getPositiveGroup().split(";").length - 1;
            positiveCounter.setText(String.valueOf(posCount));
        } else {
            textArea.setText("");
            intensityLabel.setText("");
            polarityLabel.setText("");
            positiveGroup.setText("");
            negativeGroup.setText("");
            positiveCounter.setText("");
            negativeCounter.setText("");
        }
    }

    @FXML
    private void handleAddText() {
        TextEngineInfo tempInfo = new TextEngineInfo();
        tempInfo.setId(mainApp.getData().size() + 1);
        boolean okClicked = mainApp.showAddTextDialog(tempInfo);
        if (okClicked) {
            mainApp.getData().add(tempInfo);
        }
    }

    @FXML
    private void handleDeleteText() {
        int selectedIndex = engineTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            mainApp.getData().remove(selectedIndex);
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No text selected");
            alert.setContentText("Please select a text in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleManualAnalisis() {

    }
}
