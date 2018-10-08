package controller;

import backEnd.ImageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** The scene that helps user choose Images */
public class ChooseImage {
  /** The path of current tagManager. */
  static String selected;
  /** The ListView of the images that the directory contains. */
  @FXML javafx.scene.control.ListView<String> list;
  /** the Button to end the program. */
  @FXML Button quit;
  /** The button to back to the previous page. */
  @FXML Button back;
  /** The button to open the tagManager. */
  @FXML Button Open;
  /** The place that show the tagManager. */
  @FXML ImageView imageLook;
  /** Current AnchorPane. */
  @FXML AnchorPane now;
  /** The button to check the edit history. */
  @FXML Button history;
  /** ArrayList of tagManager path. */
  private ArrayList<String> path;

  /** Initialize the scene. */
  public void initialize() {
    if (new File("phase1/src/serializeImage.ser").length() != 0) {
      ImageManager.deserializeImage();
    }
    refreshImageList();
  }

  /**
   * Click the path to load the tagManager.
   *
   * @param mouseEvent event happened when user click once.
   */
  @FXML
  public void loadingImages(MouseEvent mouseEvent) {
    if (mouseEvent.getClickCount() == 1) {
      if (list.getSelectionModel().getSelectedIndex() != -1) {
        selected = path.get(list.getSelectionModel().getSelectedIndex());
        showImages();
      }
      refreshImageList();
    }
  }

  /** Refresh the list of tagManager path. */
  private void refreshImageList() {
    ImageManager.imageList = new ArrayList<>();
    ObservableList<String> imagePath;
    File file = new File(ButtonControl.selectedDirectory.getAbsolutePath());
    imagePath = FXCollections.observableArrayList();
    if (file.isDirectory()) {
      path = ImageManager.extractImagePath(ButtonControl.selectedDirectory.getAbsolutePath());
      if (!path.isEmpty()) {
        imagePath.addAll(path);
      }
    } else {
      path = new ArrayList<>();
      path.add(ButtonControl.selectedDirectory.getAbsolutePath());
      imagePath.addAll(path);
    }
    ObservableList<String> imageNames = FXCollections.observableArrayList();;
    for (String path: imagePath){
      int separator = path.lastIndexOf(File.separator);
      if (separator != -1) {
        String imageName = path.substring(separator + 1);
        imageNames.add(imageName);
      }
    }
    list.setItems(imageNames);
  }

  /** Show the tagManager on the scene. */
  private void showImages() {
    javafx.scene.image.Image image = new javafx.scene.image.Image("file:" + selected);
    imageLook.setImage(image);
    if (new File("phase1/src/serializeImage.ser").length() != 0) {
      ImageManager.deserializeImage();
    }
  }

  /**
   * Back to previous page.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void back() throws IOException {
    AnchorPane root = FXMLLoader.load(getClass().getResource("/controller/buttonControl.fxml"));
    now.getChildren().setAll(root);
  }

  /**
   * End the program.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void quit() throws IOException {
    Parent quit = FXMLLoader.load(getClass().getResource("confirmation.fxml"));
    Stage quitStage = new Stage();
    quitStage.setTitle("Quit Conformation");
    quitStage.setScene(new Scene(quit));
    quitStage.show();
  }

  /**
   * Open the tagManager that users select.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  void open() throws IOException {
    if (selected != null) {
      AnchorPane root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
      now.getChildren().setAll(root);
    } else {
      Parent select = FXMLLoader.load(getClass().getResource("selectWarning.fxml"));
      Stage selectStage = new Stage();
      selectStage.setTitle("Warning");
      selectStage.setScene(new Scene(select));
      selectStage.show();
    }
  }

  /**
   * Show the whole edit history.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void getHistory() throws IOException {
    Parent his = FXMLLoader.load(getClass().getResource("editHistory.fxml"));
    Stage moveStage = new Stage();
    moveStage.setTitle("Edit History");
    moveStage.setScene(new Scene(his));
    moveStage.show();
  }
}
