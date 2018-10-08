package tests;

import backEnd.Image;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ImageTestNameTrack {

    private Image imageObject;
    private ArrayList<String> nameTrackResult;

    @BeforeEach
    public void before() {
        imageObject = new Image("phase2/testImage/adfree @tag1 @tag2.png");
        nameTrackResult = new ArrayList<>();
        nameTrackResult.add(imageObject.getterOfPath());
    }

    /*
  Tests of nameTrack.
   */

    @Test
    public void getNameTrack() throws Exception {
        assertEquals(nameTrackResult, imageObject.getNameTrack());
    }

    @Test
    public void nameTrackAddTagSuccess() throws Exception {
        imageObject.addTag("thisIsNew!");
        nameTrackResult.add("phase2/testImage/adfree @tag1 @tag2 @thisIsNew!.png");
        assertEquals(nameTrackResult, imageObject.getNameTrack());
    }

    @Test
    public void nameTrackAddTagFail() throws Exception {
        imageObject.addTag("tag2");
        assertEquals(nameTrackResult, imageObject.getNameTrack());
    }

    @Test
    public void nameTrackDeleteTagSuccess() throws Exception {
        imageObject.deleteNameTag("tag2");
        nameTrackResult.add("phase2/testImage/adfree @tag1.png");
        assertEquals(nameTrackResult, imageObject.getNameTrack());
    }

    @Test
    public void nameTrackDeleteTagFail() throws Exception {
        imageObject.deleteNameTag("tag3");
        assertEquals(nameTrackResult, imageObject.getNameTrack());
    }

    @Test
    public void nameTrackBackNameSuccess() throws Exception {
        imageObject.deleteNameTag("tag2");
        nameTrackResult.add("phase2/testImage/adfree @tag1.png");
        imageObject.deleteNameTag("tag1");
        nameTrackResult.add("phase2/testImage/adfree.png");
        imageObject.backOldName("phase2/testImage/adfree @tag1 @tag2.png");
        assertEquals(nameTrackResult, imageObject.getNameTrack());
    }

    @Test
    public void nameTrackBackNameFail() throws Exception {
        imageObject.deleteNameTag("tag2");
        nameTrackResult.add("phase2/testImage/adfree @tag1.png");
        imageObject.deleteNameTag("tag1");
        nameTrackResult.add("phase2/testImage/adfree.png");
        imageObject.backOldName("phase2/testImage/adfree @tag1 @whatIsThis @tag2.png");
        assertEquals(nameTrackResult, imageObject.getNameTrack());
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
