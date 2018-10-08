package controller;

import backEnd.Image;
import backEnd.ImageManager;
import backEnd.TagManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainView {

  /** Current directory of the select tagManager. */
  static String currentImageAddress = ChooseImage.selected;
  /** The place to show the tagManager. */
  @FXML ImageView imageLook;
  /** The button to add names. */
  @FXML Button addOk;
  /** The button to end the program. */
  @FXML Button quit;
  /** The button to delete the names. */
  @FXML Button deleteOk;
  /** The button to move the tagManager to other directory. */
  @FXML Button move;
  /** The button to back to the previous names. */
  @FXML Button backToPrev;
  /** the current names that tagManager has. */
  @FXML ListView<String> imageTags;

  /** the current path of the tagManager. */
  @FXML Label name;
  /** The button to back to previous page. */
  @FXML Button back;
  /** The current AnchorPane. */
  @FXML AnchorPane now;

  /** Initialize the scene to show the image */
  public void initialize() {
    if (new File("phase1/src/serializeImage.ser").length() != 0) {
      ImageManager.deserializeImage();
    }
    currentImageAddress = ChooseImage.selected;
    showImages();
    refreshToNew();
  }

  /** Show current tagManager to the String. */
  private void showImages() {
    javafx.scene.image.Image currentImage =
        new javafx.scene.image.Image("file:" + currentImageAddress);
    imageLook.setImage(currentImage);
    Image current;
    if (new File("phase1/src/serializeImage.ser").length() != 0) {
      ImageManager.deserializeImage();
    }
    for (Image image : ImageManager.imageBase) {
      if (image.getterOfPath().equals(currentImageAddress)) {
        current = image;
        refreshTags(current);
      }
    }
  }

  /** Adding names to tagManager. */
  @FXML
  protected void addOk() throws IOException {
    FXMLLoader select = new FXMLLoader(getClass().getResource("addTags.fxml"));
    Stage selectStage = new Stage();
    selectStage.setTitle("Add Tag/Tags to Image");
    selectStage.setScene(new Scene(select.load()));
    selectStage.show();
    AddTags addTags = select.getController();
    addTags.init(this);
  }

  /**
   * Refresh the current tag
   *
   * @param current the current tagManager
   */
  private void refreshTags(Image current) {
    ObservableList<String> tags = FXCollections.observableArrayList();
    tags.addAll(current.getCurrentTags());
    imageTags.setItems(tags);
  }

  /**
   * Button to delete current tag.
   *
   * @param event event happened when user clicks the button of deleteOk.
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void deleteOk(ActionEvent event) throws IOException {
    if (event.getSource() == deleteOk) {
      if (currentImageAddress == null) {
        Parent select = FXMLLoader.load(getClass().getResource("selectWarning.fxml"));
        Stage selectStage = new Stage();
        selectStage.setTitle("Warning");
        selectStage.setScene(new Scene(select));
        selectStage.show();
      } else {
        if (!imageTags.getSelectionModel().isEmpty()) {
          ObservableList<String> currentTags = imageTags.getItems();
          String selectTag = currentTags.get(imageTags.getSelectionModel().getSelectedIndex());
          if (imageTags.getSelectionModel().getSelectedIndex() != -1) {
            Image current;
            if (new File("phase1/src/serializeImage.ser").length() != 0) {
              ImageManager.deserializeImage();
            }
            for (Image image : ImageManager.imageBase) {
              if (currentImageAddress.equals(image.getterOfPath())) {
                current = image;
                current.deleteNameTag(selectTag);
                TagManager.deleteTagBase(selectTag);
                currentImageAddress = current.getterOfPath();
                refreshToNew();
                return;
              }
            }
          }
        } else {
          Parent deleteWarning = FXMLLoader.load(getClass().getResource("emptyDelete.fxml"));
          Stage warningStage = new Stage();
          warningStage.setTitle("Warning");
          warningStage.setScene(new Scene(deleteWarning));
          warningStage.show();
        }
      }
    }
  }

  /**
   * Button to move the tagManager to other directory.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void move() throws IOException {
    if (currentImageAddress != null) {
      FXMLLoader moveD = new FXMLLoader(getClass().getResource("moveDirectory.fxml"));
      Stage moveStage = new Stage();
      moveStage.setTitle("Move Directory");
      moveStage.setScene(new Scene(moveD.load()));
      moveStage.show();
      MoveDirectory moveController = moveD.getController();
      moveController.init(this);

    } else {
      Parent select = FXMLLoader.load(getClass().getResource("selectWarning.fxml"));
      Stage selectStage = new Stage();
      selectStage.setTitle("Warning");
      selectStage.setScene(new Scene(select));
      selectStage.show();
    }
  }

  /**
   * Open a list of previous names to choose.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void backPrev() throws IOException {
    if (currentImageAddress != null) {
      FXMLLoader prev = new FXMLLoader(getClass().getResource("prevNames.fxml"));
      Stage moveStage = new Stage();
      moveStage.setTitle("Previous Name");
      moveStage.setScene(new Scene(prev.load()));
      moveStage.show();
      PrevNames prevNames = prev.getController();
      prevNames.init(this);
    }
  }

  /**
   * Button to Quit the Program.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void quit() throws IOException {
    Parent quit = FXMLLoader.load(getClass().getResource("confirmation.fxml"));
    Stage quitStage = new Stage();
    quitStage.setTitle("Confirmation");
    quitStage.setScene(new Scene(quit));
    quitStage.show();
  }

  /**
   * Button to back to a previous page.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void back() throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("chooseImage.fxml"));
    now.getChildren().setAll(root);
    ChooseImage.selected = null;
  }

  /** Refresh to latest information */
  void refreshToNew() {
    Image current;
    if (new File("phase1/src/serializeImage.ser").length() != 0) {
      ImageManager.deserializeImage();
    }
    for (Image image : ImageManager.imageBase) {
      if (currentImageAddress.equals(image.getterOfPath())) {
        current = image;
        name.setText(current.getterOfPath());
        refreshTags(current);
      }
    }
  }
}
