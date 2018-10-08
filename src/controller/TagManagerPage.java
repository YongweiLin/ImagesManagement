package controller;

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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class TagManagerPage {

  /** The ObservableList that would be shown in the newlyAdd ListView. */
  static ObservableList<String> newTags = FXCollections.observableArrayList();
  /** The current directory path that user choose. */
  private static String directory;
  /** The tag in tagBase ListView that user choose to see the images of this tag. */
  private static String tagName;
  /** The directoryChooser that ask user to choose a directory. */
  private static DirectoryChooser directoryChooser;
  /** The ListView of the tags that the tagBase has. */
  @FXML javafx.scene.control.ListView<String> tagBase;
  /** The ListView of the tags that the new tags that add by Image Manager System. */
  @FXML javafx.scene.control.ListView<String> newlyAdd;
  /** The ListView of the images the tag has. */
  @FXML javafx.scene.control.ListView<ImageView> thumbnail;
  /** The button to add names. */
  @FXML Button add;
  /** The button to delete the names. */
  @FXML Button delete;
  /** the Button to end the program. */
  @FXML Button quit;
  /** The button to back to the previous page. */
  @FXML Button back;
  /** Current AnchorPane. */
  @FXML AnchorPane now;
  /** The button to choose a directory to show the images in the directory that tags contains. */
  @FXML Button search;
  /** The TextField to show the current directory that user chooses. */
  @FXML TextField currentDirectory;
  /** The TextField that enter the tag that user wanted to add to the tagBase. */
  @FXML TextField adding;
  /** The button that clear the directory that user chooses. */
  @FXML Button clear;
  /** The ObservableList of tags that would be shown in the tagBase ListView. */
  private ObservableList<String> tags = FXCollections.observableArrayList();

  /** Initialize the scene to show current tags that tagBase has. */
  public void initialize() {
    if (new File("phase1/src/serializeImage.ser").length() != 0) {
      ImageManager.deserializeImage();
    }
    refreshTags();
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
    quitStage.setTitle("Quit Conformation");
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
    AnchorPane root = FXMLLoader.load(getClass().getResource("buttonControl.fxml"));
    now.getChildren().setAll(root);
    ChooseImage.selected = null;
  }

  /** Refresh the list of tagBase. */
  private void refreshTags() {
    Set<String> hisTags = TagManager.tagBase.keySet();
    tags = FXCollections.observableArrayList();
    tags.addAll(hisTags);
    tagBase.setItems(tags);
  }

  /**
   * Add tag to tagBase; And show the current tagBase and newlyAdd ListView.
   *
   * @param event event happened when user click add button.
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void setAdd(ActionEvent event) throws IOException {
    if (event.getSource() == add) {
      if (!adding.getText().isEmpty() && !adding.getText().matches("\\s+")) {
        if (new File("phase1/src/serializeImage.ser").length() != 0) {
          ImageManager.deserializeImage();
        }
        if (TagManager.tagBase.containsKey(adding.getText())) {
          Parent addWarning = FXMLLoader.load(getClass().getResource("addTagWarning.fxml"));
          Stage warningStage = new Stage();
          warningStage.setTitle("Warning");
          warningStage.setScene(new Scene(addWarning));
          warningStage.show();
        } else {
          TagManager.tagBase.put(adding.getText(), 0);
          refreshTags();
          newTags.add(adding.getText());
          newlyAdd.setItems(newTags);
        }

      } else {
        Parent addWarning = FXMLLoader.load(getClass().getResource("addWarning.fxml"));
        Stage warningStage = new Stage();
        warningStage.setTitle("Warning");
        warningStage.setScene(new Scene(addWarning));
        warningStage.show();
      }
    }
  }

  /**
   * Open the deleteTags scene to delete tags that added by the program.
   *
   * @throws IOException load FXMLLoader would cause IOException.
   */
  @FXML
  protected void setDelete() throws IOException {
    if (!newTags.isEmpty()) {
      FXMLLoader delete = new FXMLLoader(getClass().getResource("deleteTags.fxml"));
      Stage addStage = new Stage();
      addStage.setTitle("Delete Tags");
      addStage.setScene(new Scene(delete.load()));
      addStage.show();
      DeleteTags deleteTags = delete.getController();
      deleteTags.init(this);
    }
  }

  /**
   * To show the images in thumbnails in current directory that the selected tag has.
   *
   * @param mouseEvent event happened when user clicked once.
   */
  @FXML
  public void setTagBase(MouseEvent mouseEvent) {

    if (mouseEvent.getClickCount() == 1) {
      if (tagBase.getSelectionModel().getSelectedIndex() != -1) {
        tagName = tags.get(tagBase.getSelectionModel().getSelectedIndex());
      }
      if (directoryChooser != null) {
        Thumbnail();
      }
    }
  }

  /** Make images to thumbnails and show thumbnails in the thumbnail ListView. */
  private void Thumbnail() {
    ObservableList<ImageView> imageList = FXCollections.observableArrayList();
    ArrayList<String> tagToImage = ImageManager.findImageByTag(tagName, directory);
    for (String imagePath : tagToImage) {
      javafx.scene.image.Image image;
      image = new javafx.scene.image.Image("File:" + imagePath);
      ImageView images = new ImageView(image);
      images.setFitHeight(180);
      images.setFitWidth(240);
      imageList.add(images);
    }

    thumbnail.setItems(imageList);
  }

  /**
   * to choose a directory.
   *
   * @param event the event happened when user clicks search button.
   */
  @FXML
  protected void setSearch(ActionEvent event) {
    if (event.getSource() == search) {
      directoryChooser = new DirectoryChooser();
      File selectDirectory = directoryChooser.showDialog(now.getScene().getWindow());
      if (selectDirectory != null) {
        directory = selectDirectory.getAbsolutePath();
        currentDirectory.setText(directory);
      }
    }
  }

  /** To clear the directory information. */
  @FXML
  protected void setClear() {
    currentDirectory.setText(null);
    if (currentDirectory.getText() == null) {
      directory = null;
      directoryChooser = null;
      thumbnail.setItems(null);
    }
  }
}
