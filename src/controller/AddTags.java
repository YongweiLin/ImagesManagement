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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class AddTags {
  /** The place to show the history tags that can add to image. */
  @FXML VBox addTags;
  /** The new tag that user wants to add. */
  @FXML TextField add;
  /** The button to add names. */
  @FXML Button addOk;
  /** The button to add multiple tags to the image. */
  @FXML Button multiAdd;
  /** The button to back to the previous scene. */
  @FXML Button back;

  /** The MainView Controller. */
  private MainView mainView;
  /** The set that contains all the tags in tagBase. */
  private Set<String> tagBase = TagManager.tagBase.keySet();
  /** The ArrayList that contains all the tags in tagBase. */
  private ArrayList<String> tagsBase = new ArrayList<>();
  /** The ObservableList to show all the history tags CheckBox that can be chose to add to image. */
  private ObservableList<CheckBox> chooseAdd = FXCollections.observableArrayList();

  /**
   * Initialize the AddTags controller.
   *
   * @param mainView the MainView controller.
   */
  void init(MainView mainView) {
    this.mainView = mainView;
  }

  /** Initialize the CheckBoxes of history tags that can choose to add to the image. */
  public void initialize() {
    if (new File("phase1/src/serializeImage.ser").length() != 0) {
      ImageManager.deserializeImage();
    }
    tagsBase.addAll(tagBase);
    for (int i = 0; i != tagsBase.size(); i++) {
      CheckBox hisTag = new CheckBox(tagsBase.get(i));
      hisTag.setId(tagsBase.get(i));
      chooseAdd.add(hisTag);
    }
    addTags.getChildren().addAll(chooseAdd);
  }

  /**
   * Adding names to tagManager.
   *
   * @param event event happened when addOK button is clicked.
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void addOk(ActionEvent event) throws IOException {
    if (MainView.currentImageAddress != null) {
      if (event.getSource() == addOk) {
        if (!add.getText().isEmpty() && !add.getText().matches("\\s+")) {
          Image current;
          if (new File("phase1/src/serializeImage.ser").length() != 0) {
            ImageManager.deserializeImage();
          }
          for (Image image : ImageManager.imageBase) {
            if (MainView.currentImageAddress.equals(image.getterOfPath())) {
              current = image;
              if (current.getCurrentTags().contains(add.getText())) {
                Parent addWarning = FXMLLoader.load(getClass().getResource("existTag.fxml"));
                Stage warningStage = new Stage();
                warningStage.setTitle("Warning");
                warningStage.setScene(new Scene(addWarning));
                warningStage.show();
              } else {
                current.addTag(add.getText());
                MainView.currentImageAddress = current.getterOfPath();
              }
              Stage stage0 = (Stage) addOk.getScene().getWindow();
              stage0.fireEvent(new WindowEvent(stage0, WindowEvent.WINDOW_CLOSE_REQUEST));
              mainView.refreshToNew();
              break;
            }
          }
        } else {
          Parent addWarning = FXMLLoader.load(getClass().getResource("addWarning.fxml"));
          Stage warningStage = new Stage();
          warningStage.setTitle("Warning");
          warningStage.setScene(new Scene(addWarning));
          warningStage.show();
        }
      }
    } else {
      Parent select = FXMLLoader.load(getClass().getResource("selectWarning.fxml"));
      Stage selectStage = new Stage();
      selectStage.setTitle("Warning");
      selectStage.setScene(new Scene(select));
      selectStage.show();
    }
  }

  /**
   * Using checkbox to add multiple tags to an image at same time.
   *
   * @param event event happened when multiAdd button is clicked.
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void multipleAdd(ActionEvent event) throws IOException {
    if (MainView.currentImageAddress != null) {
      if (event.getSource() == multiAdd) {
        for (Image image : ImageManager.imageBase) {
          if (MainView.currentImageAddress.equals(image.getterOfPath())) {
            for (CheckBox tag : chooseAdd) {
              if (tag.isSelected()) {
                if (image.getCurrentTags().contains(tag.getText())) {
                  Parent addWarning = FXMLLoader.load(getClass().getResource("existTag.fxml"));
                  Stage warningStage = new Stage();
                  warningStage.setTitle("Warning");
                  warningStage.setScene(new Scene(addWarning));
                  warningStage.show();
                  return;
                }
              }
            }
            for (CheckBox tag : chooseAdd) {
              if (tag.isSelected()) {
                image.addTag(tag.getText());
                MainView.currentImageAddress = image.getterOfPath();
              }
            }
            Stage stage0 = (Stage) addOk.getScene().getWindow();
            stage0.fireEvent(new WindowEvent(stage0, WindowEvent.WINDOW_CLOSE_REQUEST));
            mainView.refreshToNew();
            break;
          }
        }
      }
    }
  }

  /** To back to previous page. */
  @FXML
  protected void back() {
    Stage stage0 = (Stage) back.getScene().getWindow();
    stage0.close();
  }
}
