package colors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("view/colorView.fxml"));
        primaryStage.setTitle("Color App");

        primaryStage.getIcons().add(new Image("/resources/colorWheelIcon.png"));
       primaryStage.getIcons().add(new Image("/resources/apple-icon.png"));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/resources/icon16.png")));
         primaryStage.getIcons().add(new Image("file:icon32.png"));
        primaryStage.getIcons().add(new Image("/resources/icon150.png"));
        primaryStage.getIcons().add(new Image("/resources/icon192.png"));
        primaryStage.getIcons().add(new Image("/resources/icon512.png"));

        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
