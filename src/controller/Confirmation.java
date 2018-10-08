package controller;

import backEnd.ImageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/** Ask the user if he or she wants to quit the program */
public class Confirmation {

  /** The button to conform to close the program. */
  @FXML Button yes;
  /** The button to cancel to quit. */
  @FXML Button no;

  /** to conform to close the program. */
  @FXML
  protected void yes() {
    ImageManager.serializeImage();
    Stage stage0 = (Stage) yes.getScene().getWindow();
    stage0.close();
    System.exit(0);
  }

  /** to cancel to quit. */
  @FXML
  protected void no() {
    Stage stage0 = (Stage) yes.getScene().getWindow();
    stage0.close();
  }
}
