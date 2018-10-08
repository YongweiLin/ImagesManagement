package controller;

import backEnd.ImageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ButtonControl {
  /** the directory the user choose to open. */
  static File selectedDirectory;
  /** current AnchorPane. */
  @FXML private AnchorPane rootPane;
  /** choose a directory. */
  private DirectoryChooser directoryChooser = new DirectoryChooser();

  /**
   * Choose a directory.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void use() throws IOException {
    directoryChooser.initialDirectoryProperty();
    selectedDirectory = directoryChooser.showDialog(rootPane.getScene().getWindow());
    if (selectedDirectory != null) {
      if (selectedDirectory.listFiles() != null) {
        ImageManager.imageList = new ArrayList<>();
        ArrayList<String> image =
            ImageManager.extractImagePath(selectedDirectory.getAbsolutePath());
        if (!image.isEmpty()) {
          AnchorPane root = FXMLLoader.load(getClass().getResource("chooseImage.fxml"));
          rootPane.getChildren().setAll(root);
        } else {
          Parent loadWarning = FXMLLoader.load(getClass().getResource("loadWarning.fxml"));
          Stage loadStage = new Stage();
          loadStage.setTitle("Warning");
          loadStage.setScene(new Scene(loadWarning));
          loadStage.show();
        }
      }
    }
  }

  /**
   * Open Tag Manager Page
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void tagManager() throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("tagManagerPage.fxml"));
    rootPane.getChildren().setAll(root);
  }
}
