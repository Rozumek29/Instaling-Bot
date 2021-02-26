import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InstalingBot extends Application {
    final static FilesControler controler = new FilesControler();
    public static void main(String[] args) {
       controler.dirExist();
       Application.launch(args);
    }
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        root.setOnMousePressed(e -> {
            xOffset = stage.getX() - e.getScreenX();
            yOffset = stage.getY() - e.getScreenY();
        });
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + xOffset);
            stage.setY(e.getScreenY() + yOffset);
        });
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("InstalingBot");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}
