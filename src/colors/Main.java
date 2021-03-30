package colors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private Image icon;


    public Main() {
        icon = new Image(Main.class.getResource("/resources/colorWheelIcon.png").toExternalForm());
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("view/colorView.fxml"));
        primaryStage.setTitle("Color App");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
