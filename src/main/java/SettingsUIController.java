import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsUIController implements Initializable {

    ObservableList<String> list = FXCollections.observableArrayList("Chrome", "Other");

    @FXML
    private Pane pane;
    @FXML
    private JFXToggleButton s_RememberMe;
    @FXML
    private JFXSlider s_Delay;
    @FXML
    private ChoiceBox select_browser;

    FilesControler controler = new FilesControler();
    private String browserPath;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        select_browser.setItems(list);
        select_browser.setValue(controler.getSettings("Browser"));
        s_RememberMe.setSelected(controler.getSelected());
        s_Delay.setValue(controler.getDelay());
    }

    public void cancel_action(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/StartBotUI.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }

    public void save_settings(MouseEvent mouseEvent) {
        controler.setSettings(s_RememberMe.isSelected(), s_Delay.getValue(), select_browser.getValue().toString(), browserPath);
    }


    public void setDirectory(MouseEvent mouseEvent) {
        try{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Exe Files", "*.exe"));
            List<File> f = fc.showOpenMultipleDialog(null);
            for (File file : f){
                this.browserPath = file.getAbsolutePath();
            }
        }catch (Exception e){}

    }
}
