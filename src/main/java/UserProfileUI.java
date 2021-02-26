import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileUI implements Initializable {
    @FXML
    Pane pane;
    @FXML
    TextField login;
    @FXML
    PasswordField password;

    FilesControler controler = new FilesControler();
    public static String temp_login, temp_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (controler.getSelected())
        {
            login.setText(controler.getLogin("Login"));
            password.setText(controler.getLogin("Password"));
        }else {
            login.setText(temp_login);
            password.setText(temp_password);
        }
    }

    public void login_ok(MouseEvent mouseEvent) throws IOException {
        if (controler.getSelected()){
            controler.savePass(login.getText(), password.getText());
        }else {
            temp_login = login.getText();
            temp_password = password.getText();
        }
        Parent fxml = FXMLLoader.load(getClass().getResource("/StartBotUI.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }
}
