import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StartBotController implements Initializable {
    FilesControler controler = new FilesControler();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    String login, password;
    public void StartBot(MouseEvent mouseEvent) throws Exception {
        if (controler.getSelected()){
            login = controler.getLogin("Login");
            password = controler.getLogin("Password");
        }else {
            login = UserProfileUI.temp_login;
            password = UserProfileUI.temp_password;
        }

        double double_delay = controler.getDelay();
        int delay = (int) double_delay;
        Bot bot = new Bot(login, password, delay);
        bot.start();
    }
}
