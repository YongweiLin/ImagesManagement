package controller;

import backEnd.TagManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DeleteTags {
  /** The place to show the checkbox that contains the tags that user can delete. */
  @FXML VBox deleteTags;
  /** The button to delete the multiple tags of selected checkbox. */
  @FXML Button multiDelete;
  /** The button to back to the previous scene. */
  @FXML Button back;
  /** The controller of TagManagerPage. */
  private TagManagerPage tagManagerPage;
  /** The ObservableList of checkbox that would be shown on the scene. */
  private ObservableList<CheckBox> chooseDelete = FXCollections.observableArrayList();

  /**
   * Initialize the controller of deleteTags.
   *
   * @param tagManagerPage the controller of tagManagerPage.
   */
  void init(TagManagerPage tagManagerPage) {
    this.tagManagerPage = tagManagerPage;
  }

  /** Initialize the scene that shows the checkbox of tags that user can select to delete. */
  public void initialize() {
    for (int i = 0; i != TagManagerPage.newTags.size(); i++) {
      CheckBox tag = new CheckBox(TagManagerPage.newTags.get(i));
      tag.setId(TagManagerPage.newTags.get(i));
      chooseDelete.add(tag);
    }
    deleteTags.getChildren().addAll(chooseDelete);
  }

  /** The button to delete multiple tags that user selected. */
  @FXML
  protected void multipleDelete() {
    for (CheckBox tag : chooseDelete) {
      if (tag.isSelected()) {
        TagManager.tagBase.remove(tag.getText());
        TagManagerPage.newTags.remove(tag.getText());
        tagManagerPage.newlyAdd.setItems(TagManagerPage.newTags);
      }
    }
    Stage stage0 = (Stage) deleteTags.getScene().getWindow();
    stage0.fireEvent(new WindowEvent(stage0, WindowEvent.WINDOW_CLOSE_REQUEST));
  }

  /** To back to previous page. */
  @FXML
  protected void back() {
    Stage stage0 = (Stage) back.getScene().getWindow();
    stage0.close();
  }
}
