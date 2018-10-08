package backEnd;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TagManager {
  public static Map<String, Integer> tagBase = fileToMap("phase1/src/tagBase.txt");

  public static void deleteTagBase(String deleteTag) {
    if (tagBase.containsKey(deleteTag)) {
      tagBase.remove(deleteTag);
    }
  }

  static void writePersist(String input, String path) {
    try {
      FileWriter fw = new FileWriter(path, true);
      fw.write(input + "\n");
      fw.close();
    } catch (FileNotFoundException e) {
      File file = new File(path);
      /*If file gets created then the createNewFile()
       * method would return true or if the file is
       * already present it would return false
       */
      createFile(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  static void createFile(File file) {
    try {
      boolean resultCreate = file.createNewFile();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  static Map<String, Integer> fileToMap(String pathName) {
    File file = new File(pathName);
    /*If file gets created then the createNewFile()
     * method would return true or if the file is
     * already present it would return false
     */
    if (!file.exists()) {

      boolean result = file.getParentFile().mkdirs();
      createFile(file);
    }

    Map<String, Integer> content = new HashMap<>();
    Path filePath = Paths.get(pathName);
    try {
      BufferedReader fileInput = Files.newBufferedReader(filePath);
      String line = fileInput.readLine();

      while (line != null) {
        if (!content.containsKey(line)) {
          content.put(line, 1);
        } else {
          int value = content.get(line) + 1;
          content.put(line, value);
        }
        line = fileInput.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return content;
  }

  @SuppressWarnings("unused")
  static void removeLine(String tag) {
    File inputFile = new File("phase1/src/tagBase.txt");
    File tempFile = new File("phase1/src/tempTagBase.txt");

    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(inputFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter(tempFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
    String currentLine;
    try {
      assert reader != null;
      while ((currentLine = reader.readLine()) != null) {
        // trim newline when comparing with lineToRemove
        String trimmedLine = currentLine.trim();
        if (trimmedLine.equals(tag)) continue;
        assert writer != null;
        writer.write(currentLine + System.getProperty("line.separator"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      assert writer != null;
      writer.close();
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    boolean successful = tempFile.renameTo(inputFile);
  }
}
