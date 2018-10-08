package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Back {

  /** Button to close the warning page. */
  @FXML Button back;

  /** to close the warning page. */
  @FXML
  protected void backToMain() {
    Stage stage0 = (Stage) back.getScene().getWindow();
    stage0.close();
  }
}
