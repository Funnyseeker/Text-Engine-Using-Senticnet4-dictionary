package fun.textengine.ui.view;

import fun.textengine.core.TextObject;
import fun.textengine.core.utils.TextEngineParser;
import fun.textengine.ui.model.TextEngineInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Created by Funnyseeker on 22.05.2017.
 */
public class AddTextController {
    private Stage dialogStage;
    private TextEngineInfo engineInfo;
    private boolean okClicked = false;

    @FXML
    private TextArea textArea;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEngineInfo(TextEngineInfo engineInfo) {
        this.engineInfo = engineInfo;
    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            TextObject textObject = new TextEngineParser().parseText(textArea.getText());
            engineInfo.setText(textObject.getText());
            engineInfo.setIntensity(textObject.getPolarityObject().getIntensity());
            engineInfo.setPolarity(textObject.getPolarityObject().getPolarity().name());
            engineInfo.setNegativeGroup(textObject.getNegativeGroup());
            engineInfo.setPositiveGroup(textObject.getPositiveGroup());
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (textArea.getText() != null && textArea.getText().isEmpty()) {
            errorMessage = "Please enter the text!";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Field");
            alert.setHeaderText("Please correct invalid field");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
