package controller;

import backEnd.Image;
import backEnd.ImageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PrevNames {

  /** The list of previous tagManager names. */
  @FXML ListView<String> names;

  /** The list of names. */
  private ObservableList<String> listNames;

  /** The MainView controller. */
  private MainView mainView;

  /**
   * Initialize the PrevNames controller.
   *
   * @param mainView the mainView controller.
   */
  void init(MainView mainView) {
    this.mainView = mainView;
  }

  /** Initialize the scene. */
  public void initialize() {
    listNames = FXCollections.observableArrayList();
    Image current;
    for (Image image : ImageManager.imageBase) {
      if (MainView.currentImageAddress.equals(image.getterOfPath())) {
        current = image;
        for (String prevName : current.getPreviousName()) {
          if (!names.getItems().contains(prevName)) {
            listNames.add(prevName);
            names.setItems(listNames);
          }
        }
      }
    }
  }

  /** to choose to back to the previous names. */
  @FXML
  public void getPrevNames(MouseEvent mouseEvent) {

    if (mouseEvent.getClickCount() == 1) {
      if (names.getSelectionModel().getSelectedIndex() != -1) {
        String select = listNames.get(names.getSelectionModel().getSelectedIndex());
        Image current;
        for (Image image : ImageManager.imageBase) {
          if (MainView.currentImageAddress.equals(image.getterOfPath())) {
            current = image;
            current.backOldName(current.getPathByName(select));
            MainView.currentImageAddress = current.getterOfPath();
            mainView.refreshToNew();
          }
          Stage stage0 = (Stage) names.getScene().getWindow();
          stage0.fireEvent(new WindowEvent(stage0, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
      }
    }
  }
}
