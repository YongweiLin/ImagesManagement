package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/controller/buttonControl.fxml"));

    /* Display the first scene to the user.*/
    Scene scene1 = new Scene(root, 800, 600);
    primaryStage.setScene(scene1);
    primaryStage.setTitle("Image ImageManager");
    primaryStage.show();
  }
}
