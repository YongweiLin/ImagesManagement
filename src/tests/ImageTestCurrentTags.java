package tests;

import backEnd.Image;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ImageTestCurrentTags {

  private Image imageObject;
  private ArrayList<String> currentTagsResult;

  @BeforeEach
  public void before() {
    imageObject = new Image("phase2/testImage/adfree @tag1 @tag2.png");
    currentTagsResult = new ArrayList<>();
    currentTagsResult.add("tag1");
    currentTagsResult.add("tag2");
  }

  /*
  Tests about current tags.
   */
  @Test
  public void getCurrentTags() throws Exception {
    assertEquals(currentTagsResult, imageObject.getCurrentTags());
  }

  @Test
  public void currentTagsAddedSuccess() throws Exception {
    imageObject.addTag("c");
    currentTagsResult.add("c");
    assertEquals(currentTagsResult, imageObject.getCurrentTags());
  }

  @Test
  public void currentTagsAddedFail() throws Exception {
    imageObject.addTag("tag1");
    assertEquals(currentTagsResult, imageObject.getCurrentTags());
  }

  @Test
  public void currentTagsDeletedSuccess() throws Exception {
    imageObject.deleteNameTag("tag2");
    currentTagsResult.remove("tag2");
    assertEquals(currentTagsResult, imageObject.getCurrentTags());
  }

  @Test
  public void currentTagsDeletedFail() throws Exception {
    imageObject.deleteNameTag("notHere");
    assertEquals(currentTagsResult, imageObject.getCurrentTags());
  }

  @Test
  public void currentTagsBackName() throws Exception {
    imageObject.deleteNameTag("tag2");
    imageObject.deleteNameTag("tag1");
    imageObject.backOldName("phase2/testImage/adfree @tag1 @tag2.png");
    assertEquals(currentTagsResult, imageObject.getCurrentTags());
  }

  @Test
  public void currentTagsBackNameFail() throws Exception {
    imageObject.deleteNameTag("tag2");
    imageObject.deleteNameTag("tag1");
    currentTagsResult.remove("tag2");
    currentTagsResult.remove("tag1");

    imageObject.backOldName("phase2/testImage/adfree @notAnOldName @tag1 @tag2.png");
    assertEquals(currentTagsResult, imageObject.getCurrentTags());
  }


  @AfterAll
  public static void after() {
    File file = new File("phase1");
    deleteDir(file);
  }

  private static void deleteDir(File file) {
    File[] contents = file.listFiles();
    if (contents != null) {
      for (File f : contents) {
        deleteDir(f);
      }
    }
    file.delete();
  }

}
