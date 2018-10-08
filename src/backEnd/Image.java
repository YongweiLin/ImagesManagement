package backEnd;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import static backEnd.TagManager.fileToMap;
import static backEnd.TagManager.tagBase;

/* An Image object*/
public class Image implements Serializable {

  public ArrayList<String> getNameTrack() {
    return nameTrack;
  }

  public void setNameTrack(ArrayList<String> nameTrack) {
    this.nameTrack = nameTrack;
  }

  /**
   * the ArrayList that keeps track of all the previous names (with path) that have been given to
   * this Image.
   */
  private ArrayList<String> nameTrack = new ArrayList<>();
  /** the path of this Image. It functions as the directory in the requirement of the project. */
  private String path;
  /** the current tags of this image */
  private ArrayList<String> currentTags;

  /**
   * Construct an new Image with the specific directory path. If there are valid tags in this
   * Image's path name, extract them and put it into currentTags.
   *
   * @param pathName the path of this Image.
   */
  public Image(String pathName) {
    this.path = pathName;
    /* A Image's path may contain tags that are not in the tagBase.*/
    this.currentTags = extractTags(this.path);
    /* Add these extracted tags to tagBase.*/
    addTagsBack(currentTags);
    /* Record the original name of Image in nameTrack.*/
    nameTrack.add(this.path);
    ImageManager.imageBase.add(this);
  }

  /**
   * Return the current Tags of this Image;
   *
   * @return the ArrayList of String currentTags.
   */
  public ArrayList<String> getCurrentTags() {
    return currentTags;
  }

  /**
   * Return this Image's path.
   *
   * @return the string that represents the path of this Image.
   */
  public String getterOfPath() {
    return path;
  }

  /**
   * Modify this Image's path to newPath
   *
   * @param newPath the new path we want to set on this Image.
   */
  public void setPath(String newPath) {
    this.path = newPath;
  }

  /**
   * Return an ArrayList of previous names of this Image.
   *
   * @return ArrayList of previous names.
   */
  public ArrayList<String> getPreviousName() {
    ArrayList<String> result = new ArrayList<>();
    /* Add each names in nameTrack to the result ArrayList*/
    for (String fullPath : nameTrack) {
      File file = new File(fullPath);
      result.add(file.getName());
    }
    return result;
  }

  /**
   * Add a Tag named tagName to this Image. Add " @ + tagName" to the end of this Image's file name
   * after we add a tag.
   *
   * @param tagName the name of this Tag that will be added.
   */
  public void addTag(String tagName) {
    File originFile = new File(this.path);
    /* Check if already contains the same tag*/
    if (!this.currentTags.contains(tagName)) {
      String oldName = originFile.getPath();
      String newName = pathTagAdded(originFile, tagName);
      boolean renameSuccess = originFile.renameTo(new File(newName));
        /* Record this renaming into the editing history*/
        log(oldName, newName);
        /* Replace old Image object in imageBase. Store tagBase and imageBase to
        config files*/
        int pos = ImageManager.imageBase.indexOf(this);
        this.path = newName;
        this.currentTags.add(tagName);
        nameTrack.add(this.path);
        TagManager.tagBase = fileToMap("phase1/src/tagBase.txt");
        ImageManager.imageBase.set(pos, this);
        ImageManager.serializeImage();
    }
  }

  /**
   * Return the path of a file after a tag's name is added on this file's name.
   *
   * @param originFile a file we want to add tags to.
   * @param tagName the String name of a tag.
   * @return the path of originFile after added a tag to its file name.
   */
  private static String pathTagAdded(File originFile, String tagName) {
    int lastIndex = originFile.getPath().lastIndexOf(".");
    return originFile.getPath().substring(0, lastIndex)
        + " @"
        + new Tag(tagName).getName()
        + originFile.getPath().substring(lastIndex);
  }

  /**
   * Delete a Tag named tagName from this Image. Remove this tag's name from this Image's file name
   *
   * @param tagName the name of this Tag that will be deleted.
   */
  public void deleteNameTag(String tagName) {
    File originFile = new File(this.path);
    if (this.currentTags.contains(tagName)) {
      String oldName = originFile.getPath();
      String newName = pathTagDeleted(originFile, tagName);
      boolean renameSuccess = originFile.renameTo(new File(newName));
        /* Record this renaming into the editing history*/
        log(oldName, newName);
        /* Replace old Image object in imageBase. Store tagBase and imageBase to
        config files*/
        int pos = ImageManager.imageBase.indexOf(this);
        this.path = newName;
        this.currentTags.remove(tagName);
        nameTrack.add(this.path);
        removeTagsFromBase(tagName);
        TagManager.removeLine(tagName);
        ImageManager.imageBase.set(pos, this);
        ImageManager.serializeImage();

    }
  }

  /**
   * Return the path of a file after a tag's name is deleted from this file's name.
   *
   * @param originFile a file we want to delete tags from it.
   * @param tagName the String name of a tag.
   * @return the path of originFile after deleted a tag from its file name
   */
  private static String pathTagDeleted(File originFile, String tagName) {
    String replace = " @" + tagName;
    int replaceIndex = originFile.getPath().lastIndexOf(replace);
    return originFile.getPath().substring(0, replaceIndex)
        + originFile.getPath().substring(replaceIndex + replace.length());
  }

  /**
   * Keep a editing history of all renaming ever done using the application in reNameLog.txt.
   *
   * @param oldName The old name of a file before we change its name.
   * @param newName The new name of a file after we change its name.
   */
  private void log(String oldName, String newName) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    TagManager.writePersist(
        System.lineSeparator()
            + oldName
            + " \n changed to: \n  "
            + newName
            + "\n  at \n "
            + timestamp,
        "phase1/src/reNameLog.txt");
  }

  /**
   * Return the previous path by the previous name of the Image.
   *
   * @param previousName the previous name of this Image.
   * @return the path according to this previous name.
   */
  public String getPathByName(String previousName) {
    String result;
    for (String fullPath : nameTrack) {
      File file = new File(fullPath);
      if (file.getName().equals(previousName)) {
        result = fullPath;
        return result;
      }
    }
    return null;
  }

  /**
   * Modify this Image's name to one of its old names based on the oldPath chosen.
   *
   * @param oldPath the path of the selected old name.
   */
  public void backOldName(String oldPath) {
    /* Make sure that oldPath is a previous name of this Image.*/
    if (nameTrack.contains(oldPath)) {
      File originFile = new File(this.path);
      boolean renameSuccess = originFile.renameTo(new File(oldPath));
        /* Remove the tags of this Image's tags in current name from tagBase since
         * the current Image's name will be changed. */
        ArrayList<String> oldTags = extractTags(this.path);
        for (String tag : oldTags) {
          removeTagsFromBase(tag);
        }

      /* Record this renaming into the editing history*/
      log(this.path, oldPath);
      int pos = ImageManager.imageBase.indexOf(this);
      this.path = oldPath;
      /* After we change the path, extract tags from this newly-set path. */
      this.currentTags = extractTags(oldPath);
      /* Add the previous image's tags back to tagBase.*/
      addTagsBack(currentTags);
      ImageManager.imageBase.set(pos, this);
      ImageManager.serializeImage();
    }
  }

  /**
   * Add elements of an ArrayList of tags back to the tagBase.
   *
   * @param listOfTags an ArrayList of tags.
   */
  private void addTagsBack(ArrayList<String> listOfTags) {
    for (String tag : listOfTags) {
      if (tagBase.containsKey(tag)) {
        int value = tagBase.get(tag) + 1;
        tagBase.put(tag, value);
      } else {
        tagBase.put(tag, 1);
      }
      TagManager.writePersist(tag, "phase1/src/tagBase.txt");
    }
  }

  /**
   * Remove a tag with name tagName from the tagBase.
   *
   * @param tagName the name of a tag we want to remove from tagBase.
   */
  private void removeTagsFromBase(String tagName) {
    int value = TagManager.tagBase.get(tagName) - 1;
    if (value == 0) {
      TagManager.tagBase.remove(tagName);
    } else {
      TagManager.tagBase.put(tagName, value);
    }
  }

  /**
   * Return an ArrayList of tags by extracting tags from this Image's name.
   *
   * @return a list of tags in this Image's name.
   */
  static ArrayList<String> extractTags(String path) {
    ArrayList<String> result = new ArrayList<>();
    /* Based on the syntax of added tag in files name, extract tags*/
    String parts[] = path.split(" @");
    result.addAll(Arrays.asList(parts));
    String lastItem = result.get(result.size() - 1);
    int pos = lastItem.lastIndexOf(".");
    String replaceLast = lastItem.substring(0, pos);
    result.set(result.size() - 1, replaceLast);
    result.remove(0);
    return result;
  }
}
