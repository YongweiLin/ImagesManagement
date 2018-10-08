package controller;

import backEnd.Image;
import backEnd.ImageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MoveDirectory {
  /** The directory text. */
  @FXML TextField newDirectory;
  /** move to the new directory. */
  @FXML Button move;
  /** Search the directory that user want to move. */
  @FXML Button search;
  /** The button that back to previous page. */
  @FXML Button back;

  /** current AnchorPane. */
  @FXML private AnchorPane searchPane;
  /** The MainView controller. */
  private MainView mainView;

  /**
   * Initialize the MoveDirectory controller.
   *
   * @param mainView the mainView controller.
   */
  void init(MainView mainView) {
    this.mainView = mainView;
  }

  /** To back to previous page. */
  @FXML
  protected void back() {
    Stage stage0 = (Stage) back.getScene().getWindow();
    stage0.close();
  }

  /** To search the new directory to move. */
  @FXML
  protected void search(ActionEvent event) {
    if (event.getSource() == search) {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      File selectDirectory = directoryChooser.showDialog(searchPane.getScene().getWindow());
      if (selectDirectory != null) {
        newDirectory.setText(selectDirectory.getAbsolutePath());
      }
    }
  }

  /**
   * To move the tagManager to the new directory.
   *
   * @param event event happened when user clicks the move button.
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void movePlace(ActionEvent event) throws IOException {
    if (event.getSource() == move) {
      if (MainView.currentImageAddress != null) {
        Image current;
        File file = new File(newDirectory.getText());
        if (file.isDirectory()) {
          for (Image image : ImageManager.imageBase) {
            if (MainView.currentImageAddress.equals(image.getterOfPath())) {
              current = image;
              ImageManager.moveImage(current, newDirectory.getText() + File.separator);
              MainView.currentImageAddress = current.getterOfPath();
            }
          }
          Stage stage0 = (Stage) move.getScene().getWindow();
          stage0.close();
          mainView.refreshToNew();
        } else {
          Parent directoryWarning =
              FXMLLoader.load(getClass().getResource("directoryWarning.fxml"));
          Stage warningStage = new Stage();
          warningStage.setTitle("Warning");
          warningStage.setScene(new Scene(directoryWarning));
          warningStage.show();
        }
      }
    }
  }
}
