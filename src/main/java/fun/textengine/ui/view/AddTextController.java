package fun.textengine.ui.view;

import fun.textengine.core.TextEngineSolver;
import fun.textengine.core.TextObject;
import fun.textengine.ui.model.TextEngineInfo;
import fun.textengine.ui.utils.ProgressTrackerImpl;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Created by Funnyseeker on 22.05.2017.
 */
public class AddTextController {
    private static TextEngineSolver TEXT_ENGINE_SOLVER;
    private Stage dialogStage;
    private TextEngineInfo engineInfo;
    private boolean okClicked = false;
    private Thread thread;

    @FXML
    private TextArea textArea;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button okButton;

    public static TextEngineSolver getTextEngineSolver() {
        return TEXT_ENGINE_SOLVER;
    }

    public static void setTextEngineSolver(TextEngineSolver textEngineSolver) {
        TEXT_ENGINE_SOLVER = textEngineSolver;
    }

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        progressBar.progressProperty().addListener(observable -> {
            if (progressBar.getProgress() >= 1) {
                okClicked = true;
                dialogStage.close();
            }
        });
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
            okButton.setDisable(true);
            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    TEXT_ENGINE_SOLVER.getMatcher().setProgressTraker(new ProgressTrackerImpl(progressBar));
                    TextObject textObject = TEXT_ENGINE_SOLVER.parseText(textArea.getText());
                    engineInfo.setText(textObject.getText());
                    engineInfo.setIntensity(textObject.getPolarityObject().getIntensity());
                    engineInfo.setPolarity(textObject.getPolarityObject().getPolarity().name());
                    engineInfo.setNegativeGroup(textObject.getNegativeGroup());
                    engineInfo.setPositiveGroup(textObject.getPositiveGroup());
                    return null;
                }
            };

            thread = new Thread(task, "task-thread");
            thread.setDaemon(true);
            thread.start();
//            okClicked = true;
//            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
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
