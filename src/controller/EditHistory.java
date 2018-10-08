package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Show user the editing history of all Images */
public class EditHistory {

  /** The area to show the edit history. */
  @FXML TextArea history;

  /** Initialize the scene to show the editing history */
  public void initialize() {
    Path path = Paths.get("phase1/src/reNameLog.txt");
    if (Files.exists(path)) {
      try (BufferedReader input = Files.newBufferedReader(path)) {
        String line = input.readLine();
        StringBuilder txt = new StringBuilder();
        while (line != null) {
          txt.append(line).append("\n");
          line = input.readLine();
        }
        history.setText(txt.toString());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
