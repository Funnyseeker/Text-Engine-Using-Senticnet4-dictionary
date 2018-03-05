package fun.textengine.ui.view;

import fun.textengine.core.ConceptObject;
import fun.textengine.ui.MainFX;
import fun.textengine.ui.model.TextEngineInfo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.TextFlow;

import java.util.List;

public class ManualAnalisisController {
    @FXML
    private TextFlow textFlow;
    @FXML
        private ListView<ConceptObject> groupFound;
    @FXML
    private ListView<ConceptObject> groupMarked;

    // Ссылка на главное приложение.
    private MainFX mainApp;

    private TextEngineInfo engineInfo;

    public ManualAnalisisController(TextEngineInfo engineInfo) {
        this.engineInfo = engineInfo;
    }

    @FXML
    private void initialize(){
        groupFound.setItems(FXCollections.observableArrayList());
    }
}
