package fun.textengine.ui;

import fun.textengine.ui.model.TextEngineInfo;
import fun.textengine.ui.model.TextEnginesListWrapper;
import fun.textengine.ui.view.AddTextController;
import fun.textengine.ui.view.StartController;
import fun.textengine.ui.view.TextEngineOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

/**
 * Created by Funnyseeker on 21.05.2017.
 */
public class MainFX extends Application {
    private Stage primaryStage;
    private AnchorPane startLayout;
    private SplitPane textEngineLayout;
    private ObservableList<TextEngineInfo> data = FXCollections.observableArrayList();
    private String path = "texts.xml";

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        java.net.URL documentUrl = ClassLoader.getSystemClassLoader().getResource("senticnet4/senticnet4.rdf.xml");
//        Senticnet4RdfParser parser = new Senticnet4RdfParser();
//        parser.parse(documentUrl);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Text Sentic Engine (using Senticnet4 dictionary)");
        this.getPrimaryStage().setOnCloseRequest(event -> {
            File file = new File(path);
            saveDataToFile(file);
        });

        File file = new File(path);
        loadDataFromFile(file);

        initStartLayout();
    }

    public void initStartLayout() {
        try {
            //Загружаем основной макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = getClass().getClassLoader();
            loader.setLocation(classLoader.getResource("fun/textengine/ui/view/StartMenu.fxml"));
            startLayout = loader.load();

            //Отображаем сцену, содержащую основной макет.
            Scene scene = new Scene(startLayout);

            StartController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootLayout() {
        try {
            //Загружаем основной макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = getClass().getClassLoader();
            loader.setLocation(classLoader.getResource("fun/textengine/ui/view/TextEngineUI.fxml"));
            textEngineLayout = loader.load();

            //Отображаем сцену, содержащую основной макет.
            Scene scene = new Scene(textEngineLayout);

            TextEngineOverviewController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showAddTextDialog(TextEngineInfo info) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            ClassLoader classLoader = getClass().getClassLoader();
            loader.setLocation(classLoader.getResource("fun/textengine/ui/view/AddText.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add text");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            AddTextController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEngineInfo(info);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TextEnginesListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            TextEnginesListWrapper wrapper = (TextEnginesListWrapper) um.unmarshal(file);

            data.clear();
            data.addAll(wrapper.getInfos());

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TextEnginesListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            TextEnginesListWrapper wrapper = new TextEnginesListWrapper();
            wrapper.setInfos(data);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<TextEngineInfo> getData() {
        return data;
    }
}
