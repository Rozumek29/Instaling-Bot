import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowControler implements Initializable {

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/StartBotUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }

    public void open_settings(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/SettingsUI.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }

    public void open_user_profile(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/UserProfileUI.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }

    public void app_close(MouseEvent mouseEvent){
        Platform.exit();
        System.exit(0);
    }

    public void open_Words_List(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AuthorUI.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }
}
