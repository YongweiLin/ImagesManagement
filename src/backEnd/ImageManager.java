package backEnd;

import java.io.*;
import java.util.ArrayList;

public class ImageManager {

  public static ArrayList<String> imageList = new ArrayList<>();
  public static ArrayList<Image> imageBase = new ArrayList<>();

  public static void serializeImage() {
    File file = new File("phase1/src/serializeImage.ser");
    /*If file gets created then the createNewFile()
     * method would return true or if the file is
     * already present it would return false
     */
    try {
      @SuppressWarnings("unused")
      boolean fvar = file.createNewFile();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    try {
      FileOutputStream fout = new FileOutputStream("phase1/src/serializeImage.ser");
      ObjectOutputStream out = new ObjectOutputStream(fout);
      out.writeObject(imageBase);
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<String> findImageByTag(String tagName, String directory) {
    ArrayList<String> candidates = extractImagePath(directory);
    ArrayList<String> result = new ArrayList<>();
    for (String path : candidates) {
      if (Image.extractTags(path).contains(tagName)) {
        if(!result.contains(path)){
          result.add(path);
        }
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static void deserializeImage() {
    File file = new File("phase1/src/serializeImage.ser");
    /*If file gets created then the createNewFile()
     * method would return true or if the file is
     * already present it would return false
     */
    TagManager.createFile(file);
    try {
      ObjectInputStream in =
          new ObjectInputStream(new FileInputStream("phase1/src/serializeImage.ser"));
      imageBase = (ArrayList) in.readObject();

      in.close();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<String> extractImagePath(String selectPath) {
    File selectDirectory = new File(selectPath);
    File[] listFile = selectDirectory.listFiles();

    if (selectDirectory.isDirectory() && listFile != null) {
      for (File subFile : listFile) {

        // check if the file type is Image
        String fileType = null;
        if (subFile.isDirectory()) {
          extractImagePath(subFile.getAbsolutePath());
        } else {
          String name = subFile.getName();
          int dotIndex = name.lastIndexOf('.');
          if (dotIndex != -1) {
            fileType = name.substring(dotIndex + 1);
          }
          if (fileType != null) {
            if (fileType.equals("jpeg")
                | fileType.equals("jpg")
                | fileType.equals("png")
                | fileType.equals("bmp")
                | fileType.equals("gif")) {
              imageList.add(subFile.getAbsolutePath());
              @SuppressWarnings("unused")
              Image image = new Image(subFile.getAbsolutePath());
            }
          }
        }
      }
      serializeImage();
    }
    return imageList;
  }

  public static void moveImage(Image selectImage, String targetPath) {
    File selectDirectory = new File(selectImage.getterOfPath());
    int pos = ImageManager.imageBase.indexOf(selectImage);
    // String oldPath = selectDirectory.getPath();
    @SuppressWarnings("unused")
    boolean result = selectDirectory.renameTo(new File(targetPath + selectDirectory.getName()));
    selectImage.setPath(targetPath + selectDirectory.getName());
    ImageManager.imageBase.set(pos, selectImage);
    ImageManager.serializeImage();
  }
}
